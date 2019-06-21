
package services;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PersonalDataRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Curricula;
import domain.PersonalData;
import domain.Rookie;
import forms.PersonalDataForm;

@Service
@Transactional
public class PersonalDataService {

	@Autowired
	private CustomizableSystemService	customizableService;
	@Autowired
	private PersonalDataRepository		personalDataRepository;
	@Autowired
	private ActorService				actorS;
	@Autowired
	private CurriculaService			curriculaService;
	@Autowired
	HackerService						hackerService;
	@Autowired
	private Validator					validator;


	public PersonalData create() {
		final PersonalData res = new PersonalData();
		final String telephoneCode = this.customizableService.getTelephoneCode();
		res.setFullName("");
		res.setStatement("");
		res.setPhoneNumber(telephoneCode + " ");
		res.setGithubProfile("");
		res.setLinkedlnProfile("");
		return res;
	}

	public Collection<PersonalData> findAll() {
		return this.personalDataRepository.findAll();
	}

	public PersonalData findOne(final Integer personalDataId) {
		final PersonalData profileData = this.personalDataRepository.findOne(personalDataId);
		final Curricula curricula = this.curriculaService.getCurriculaByProfileData(profileData.getId());
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorS.getActorByUserAccount(user.getId());
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("ROOKIE"));
		Assert.isTrue(curricula.getRookie() == a);
		return profileData;
	}

	public PersonalData save(final PersonalData personalData) {
		final PersonalData personalDataSave;
		final Rookie h = this.hackerService.hackerUserAccount(LoginService.getPrincipal().getId());
		//		if (h.getPhone() != "" || h.getPhone() != null) {
		//			final String regexTelefono = "^\\+[1-9][0-9]{0,2}\\ \\([1-9][0-9]{0,2}\\)\\ [0-9]{4,}$|^\\+[1-9][0-9]{0,2}\\ [0-9]{4,}$|^[0-9]{4,}$";
		//			final Pattern patternTelefono = Pattern.compile(regexTelefono);
		//			final Matcher matcherTelefono = patternTelefono.matcher(h.getPhone());
		//			Assert.isTrue(matcherTelefono.find() == true, "ProfileDataService.save -> Telefono no valido");
		//		}

		personalDataSave = this.personalDataRepository.save(personalData);
		if (personalData.getId() == 0) {
			final Curricula curricula = this.curriculaService.create();
			curricula.setRookie(h);
			curricula.setPersonalData(personalDataSave);
			this.curriculaService.save(curricula);
		}
		return personalDataSave;
	}

	public void delete(final PersonalData personalData) {
		final Curricula curriculaD = this.curriculaService.getCurriculaByProfileData(personalData.getId());
		this.curriculaService.delete(curriculaD);

	}

	public PersonalData reconstruct(final PersonalDataForm registrationForm, final BindingResult binding) {
		PersonalData res = new PersonalData();

		if (registrationForm.getId() == 0) {
			res.setId(registrationForm.getId());
			res.setVersion(registrationForm.getVersion());
			res.setFullName(registrationForm.getFullName());
			res.setGithubProfile(registrationForm.getGithubProfile());
			res.setLinkedlnProfile(registrationForm.getLinkedlnProfile());
			res.setPhoneNumber(registrationForm.getPhoneNumber());
			res.setStatement(registrationForm.getStatement());

			if (registrationForm.getPatternPhone() == false) {
				final String regexTelefono = "^\\+[0-9]{0,3}\\s\\([0-9]{0,3}\\)\\ [0-9]{4,}$|^\\+[1-9][0-9]{0,2}\\ [0-9]{4,}$|^[0-9]{4,}|^\\+[0-9]\\ $|^$|^\\+$";
				final Pattern patternTelefono = Pattern.compile(regexTelefono);
				final Matcher matcherTelefono = patternTelefono.matcher(res.getPhoneNumber());
				Assert.isTrue(matcherTelefono.find() == true, "PersonalDataService.save -> Telefono no valido");
			}

			this.validator.validate(res, binding);

		} else {
			res = this.personalDataRepository.findOne(registrationForm.getId());
			final PersonalData p = new PersonalData();

			p.setId(res.getId());
			p.setVersion(res.getVersion());
			p.setFullName(registrationForm.getFullName());
			p.setGithubProfile(registrationForm.getGithubProfile());
			p.setLinkedlnProfile(registrationForm.getLinkedlnProfile());
			p.setStatement(registrationForm.getStatement());
			p.setPhoneNumber(registrationForm.getPhoneNumber());

			if (registrationForm.getPatternPhone() == false) {
				final String regexTelefono = "^\\+[0-9]{0,3}\\s\\([0-9]{0,3}\\)\\ [0-9]{4,}$|^\\+[1-9][0-9]{0,2}\\ [0-9]{4,}$|^[0-9]{4,}|^\\+[0-9]\\ $|^$|^\\+$";
				final Pattern patternTelefono = Pattern.compile(regexTelefono);
				final Matcher matcherTelefono = patternTelefono.matcher(res.getPhoneNumber());
				Assert.isTrue(matcherTelefono.find() == true, "PersonalDataService.save -> Telefono no valido");
			}

			this.validator.validate(p, binding);
			res = p;
		}
		return res;

	}

}
