
package services;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AuditorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Auditor;
import domain.CreditCard;
import domain.Position;
import forms.RegistrationFormAuditor;

@Service
@Transactional
public class AuditorService {

	@Autowired
	private AuditorRepository			auditorRepository;

	@Autowired
	private CustomizableSystemService	customizableService;

	@Autowired
	private ActorService				actorService;

	@Autowired
	private Validator					validator;


	public Auditor create() {
		final Auditor res = new Auditor();

		res.setAddress("");
		res.setEmail("");
		res.setName("");
		res.setVatNumber("");
		final String telephoneCode = this.customizableService.getTelephoneCode();
		res.setPhone(telephoneCode + " ");
		res.setPhoto("");
		res.setSurnames("");
		res.setAddress("");
		res.setCreditCard(new CreditCard());
		res.setPositions(new HashSet<Position>());

		//PREGUNTAR
		final UserAccount user = new UserAccount();
		user.setAuthorities(new HashSet<Authority>());
		final Authority ad = new Authority();
		ad.setAuthority(Authority.AUDITOR);
		user.getAuthorities().add(ad);
		user.setUsername("");
		user.setPassword("");
		res.setUserAccount(user);

		return res;
	}

	public Collection<Auditor> findAll() {
		return this.auditorRepository.findAll();
	}

	public Auditor findOne(final int auditorId) {
		final Auditor auditor = this.auditorRepository.findOne(auditorId);
		final UserAccount userLoged = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(userLoged.getId());
		Assert.isTrue(userLoged.getAuthorities().iterator().next().getAuthority().equals("AUDITOR"));
		Assert.isTrue(auditor.equals(a));
		return this.auditorRepository.findOne(auditorId);
	}

	public Auditor save(final Auditor r) {
		final UserAccount userLoged = LoginService.getPrincipal();
		if (r.getId() == 0)
			Assert.isTrue(userLoged.getAuthorities().iterator().next().getAuthority().equals("ADMIN") || userLoged.getAuthorities().iterator().next().getAuthority().equals("AUDITOR"), "Comprobar que hay admin conectado");
		else
			Assert.isTrue(userLoged.getAuthorities().iterator().next().getAuthority().equals("AUDITOR"), "Comprobar que es un Auditor el que esta intentando modificar los datos");

		Auditor res = null;

		Assert.isTrue(r != null && r.getName() != null && r.getSurnames() != null && r.getName() != "" && r.getUserAccount() != null && r.getEmail() != null && r.getEmail() != "", "Auditor.save -> Name, Surname or email invalid");
		Assert.isTrue(r.getVatNumber() != null, "Auditor.save -> VatNumber  invalid");
		Assert.isTrue(r.getCreditCard() != null, "Auditor.save -> VatNumber  invalid");

		final String regexEmail1 = "[^@]+@[^@]+\\.[a-zA-Z]{2,}";
		final Pattern patternEmail1 = Pattern.compile(regexEmail1);
		final Matcher matcherEmail1 = patternEmail1.matcher(r.getEmail());

		final String regexEmail2 = "^[A-z0-9]+\\s*[A-z0-9\\s]*\\s\\<[A-z0-9]+\\@[A-z0-9]+\\.[A-z0-9.]+\\>";
		final Pattern patternEmail2 = Pattern.compile(regexEmail2);
		final Matcher matcherEmail2 = patternEmail2.matcher(r.getEmail());
		Assert.isTrue(matcherEmail1.find() == true || matcherEmail2.find() == true, "CustomerService.save -> Correo inválido");

		final List<String> emails = this.actorService.getEmails();

		if (r.getId() == 0)
			Assert.isTrue(!emails.contains(r.getEmail()), "Auditor.Email -> The email you entered is already being used");
		//		else {
		//			final Auditor a = this.AuditorRepository.findOne(r.getId());
		//			Assert.isTrue(a.getEmail().equals(r.getEmail()));
		//		}

		//NUEVO
		Assert.isTrue(r.getUserAccount().getUsername() != null && r.getUserAccount().getUsername() != "");
		Assert.isTrue(r.getUserAccount().getPassword() != null && r.getUserAccount().getPassword() != "");

		if (r.getId() == 0) {

			final Md5PasswordEncoder encoder;
			encoder = new Md5PasswordEncoder();
			final String hash = encoder.encodePassword(r.getUserAccount().getPassword(), null);
			final UserAccount user = r.getUserAccount();
			user.setPassword(hash);
		}

		res = this.auditorRepository.save(r);
		return res;
	}

	public Auditor auditorUserAccount(final Integer id) {
		return this.auditorRepository.auditorUserAccount(id);
	}

	public Auditor reconstruct(final RegistrationFormAuditor registrationForm, final BindingResult binding) {
		Auditor res = new Auditor();

		if (registrationForm.getId() == 0) {
			res.setId(registrationForm.getUserAccount().getId());
			res.setVersion(registrationForm.getVersion());
			res.setAddress(registrationForm.getAddress());
			res.setEmail(registrationForm.getEmail());
			res.setVatNumber(registrationForm.getVatNumber());
			res.setName(registrationForm.getName());
			res.setPhone(registrationForm.getPhone());
			res.setPhoto(registrationForm.getPhoto());
			res.setSurnames(registrationForm.getSurnames());
			res.setCreditCard(registrationForm.getCreditCard());
			res.setPositions(new HashSet<Position>());
			final Authority ad = new Authority();
			final UserAccount user = new UserAccount();
			user.setAuthorities(new HashSet<Authority>());
			ad.setAuthority(Authority.AUDITOR);
			user.getAuthorities().add(ad);
			res.setUserAccount(user);
			user.setUsername(registrationForm.getUserAccount().getUsername());
			user.setPassword(registrationForm.getUserAccount().getPassword());

			Assert.isTrue(registrationForm.getPassword().equals(registrationForm.getUserAccount().getPassword()));

			if (res.getPhone().length() <= 5)
				res.setPhone("");

			if (registrationForm.getPatternPhone() == false) {
				final String regexTelefono = "^\\+[0-9]{0,3}\\s\\([0-9]{0,3}\\)\\ [0-9]{4,}$|^\\+[1-9][0-9]{0,2}\\ [0-9]{4,}$|^[0-9]{4,}|^\\+[0-9]\\ $|^$|^\\+$";
				final Pattern patternTelefono = Pattern.compile(regexTelefono);
				final Matcher matcherTelefono = patternTelefono.matcher(res.getPhone());
				Assert.isTrue(matcherTelefono.find() == true, "auditorService.save -> Telefono no valido");
			}
			this.validator.validate(res, binding);

		} else {
			res = this.auditorRepository.findOne(registrationForm.getId());
			final Auditor p = new Auditor();

			if (registrationForm.getUserAccount().getPassword().equals("") && registrationForm.getPassword().equals(""))
				p.setUserAccount(res.getUserAccount());
			else {
				final UserAccount user = registrationForm.getUserAccount();
				final Md5PasswordEncoder encoder;
				encoder = new Md5PasswordEncoder();
				final String hash = encoder.encodePassword(registrationForm.getUserAccount().getPassword(), null);
				user.setPassword(hash);
				registrationForm.setUserAccount(user);

				if (!registrationForm.getUserAccount().getPassword().equals(res.getUserAccount().getPassword())) {
					final Md5PasswordEncoder encoder2;
					encoder2 = new Md5PasswordEncoder();
					final String hash2 = encoder2.encodePassword(registrationForm.getPassword(), null);
					registrationForm.setPassword(hash2);
					Assert.isTrue(registrationForm.getPassword().equals(registrationForm.getUserAccount().getPassword()));

				}

				p.setUserAccount(res.getUserAccount());
				p.getUserAccount().setPassword(registrationForm.getUserAccount().getPassword());

			}

			p.setId(res.getId());
			p.setVersion(res.getVersion());
			p.setAddress(registrationForm.getAddress());
			p.setEmail(registrationForm.getEmail());
			p.setVatNumber(registrationForm.getVatNumber());
			p.setName(registrationForm.getName());
			p.setPhone(registrationForm.getPhone());
			p.setPhoto(registrationForm.getPhoto());
			p.setSurnames(registrationForm.getSurnames());
			p.setCreditCard(registrationForm.getCreditCard());
			p.setPositions(registrationForm.getPositions());

			if (p.getPhone().length() <= 5)
				p.setPhone("");

			if (registrationForm.getPatternPhone() == false) {
				final String regexTelefono = "^\\+[0-9]{0,3}\\s\\([0-9]{0,3}\\)\\ [0-9]{4,}$|^\\+[1-9][0-9]{0,2}\\ [0-9]{4,}$|^[0-9]{4,}|^\\+[0-9]\\ $|^$|^\\+$";
				final Pattern patternTelefono = Pattern.compile(regexTelefono);
				final Matcher matcherTelefono = patternTelefono.matcher(p.getPhone());
				Assert.isTrue(matcherTelefono.find() == true, "AuditorService.save -> Telefono no valido");
			}

			p.getUserAccount().setUsername(registrationForm.getUserAccount().getUsername());

			this.validator.validate(p, binding);
			res = p;

		}
		return res;

	}

	public Auditor reconstruct(final Auditor auditor, final BindingResult binding) {
		final Auditor res;

		res = this.auditorRepository.findOne(this.auditorRepository.auditorUserAccount(LoginService.getPrincipal().getId()).getId());
		final Auditor p = new Auditor();
		p.setId(res.getId());
		p.setVersion(res.getVersion());
		p.setAddress(res.getAddress());
		p.setEmail(res.getEmail());
		p.setVatNumber(res.getVatNumber());
		p.setName(res.getName());
		p.setPhone(res.getPhone());
		p.setPhoto(res.getPhoto());
		p.setSurnames(res.getSurnames());
		p.setCreditCard(res.getCreditCard());
		p.setPositions(auditor.getPositions());
		p.setUserAccount(res.getUserAccount());

		//			if (res.getId() != 0 && copy.getDraftMode() == 0)
		//				if (!(this.getProblemsWithoutDraftMode(copy.getId()) >= 2))
		//					binding.rejectValue("title", "ProblemSize");

		this.validator.validate(p, binding);
		if (binding.hasErrors())
			throw new ValidationException();
		return p;

	}

	public List<Auditor> getAuditorByPosition(final Integer id) {
		return this.auditorRepository.getAuditorByPosition(id);
	}

}
