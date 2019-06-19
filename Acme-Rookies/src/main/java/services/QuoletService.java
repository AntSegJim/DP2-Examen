
package services;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AuditRepository;
import repositories.CompanyRepository;
import repositories.QuoletRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Audit;
import domain.Company;
import domain.Quolet;

@Service
@Transactional
public class QuoletService {

	@Autowired
	private QuoletRepository	quoletRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private AuditRepository		auditRepository;

	@Autowired
	private CompanyRepository	companyRepository;

	@Autowired
	private Validator			validator;


	public Quolet create() {
		final Quolet q = new Quolet();

		q.setTicker("");
		q.setMoment(null);
		q.setBody("");
		q.setPicture(null);
		q.setDraftMode(1);
		q.setAudit(new Audit());
		q.setCompany(new Company());
		q.setNumMonth(0);

		return q;
	}

	public Collection<Quolet> findAll() {
		return this.quoletRepository.findAll();
	}

	public Quolet findOne(final Integer id) {
		return this.quoletRepository.findOne(id);
	}

	public Collection<Quolet> getQuoletsByCompany(final Integer idAudit) {
		return this.quoletRepository.getQuoletsByCompany(idAudit);
	}

	public Quolet save(final Quolet q) {

		if (q.getId() != 0) {
			//Si el quolet ya estaba creado
			//Lo modifica la company que lo ha creado
			final UserAccount user = LoginService.getPrincipal();
			final Actor a = this.actorService.getActorByUserAccount(user.getId());
			Assert.isTrue(q.getCompany().equals(a));

			//El que estaba en base de datos no estaba en modo final
			final Quolet older = this.quoletRepository.findOne(q.getId());
			Assert.isTrue(older.getDraftMode() == 1);

			//Si el quolet esta en save mode, moment != null
			if (q.getDraftMode() == 0)
				Assert.isTrue(q.getMoment() != null);
		}

		//Comprobacion general: audit asociado este fuera de drafMode

		Assert.isTrue(q.getAudit().getDraftMode() == 0);

		final Quolet saved = this.quoletRepository.save(q);
		return saved;
	}

	public Quolet reconstruct(final Quolet quolet, final Integer idAudit, final BindingResult binding) {

		//A�adido idAudit solo para crear
		//Para modificar obviarlo

		final Quolet res;

		//if (position.getId() == 0) {
		res = quolet;

		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());

		quolet.setCompany((Company) a);
		quolet.setTicker(this.generar_ticker_quolet(new Date()));
		quolet.setNumMonth(0);
		quolet.setMoment(new Date());
		final Audit audit = this.auditRepository.findOne(idAudit);
		quolet.setAudit(audit);

		this.validator.validate(res, binding);

		return res;

	}

	public void delete(final Quolet quolet) {
		Assert.isTrue(this.quoletRepository.getQuoletsByMyCompany(this.companyRepository.companyUserAccount(LoginService.getPrincipal().getId()).getId()).contains(quolet));
		this.quoletRepository.delete(quolet);
	}

	//METODOS AUXILIARES

	//Ticker para Letras&Numeros x5 - NumerosFecha x6
	private String generar_ticker_quolet2(final Date date) {
		final int tam = 5;
		final Integer ano = date.getYear() + 1900;
		final Integer mes = date.getMonth() + 1;
		final Integer dia = date.getDate();

		String day = dia.toString();
		String month = mes.toString();
		if (mes < 10)
			month = "0" + mes.toString();
		if (dia < 10)
			day = "0" + dia.toString();

		String ticker = "";
		final String a = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

		for (int i = 0; i < tam; i++) {
			final Integer random = (int) (Math.floor(Math.random() * a.length()) % a.length());
			ticker = ticker + a.charAt(random);
		}

		return ticker + "-" + ano.toString().substring(ano.toString().length() - 2, ano.toString().length()) + month + day;

	}

	private String generar_ticker_quolet(final Date date) {
		final int tam = 4;
		final Integer ano = date.getYear() + 1900;
		final Integer mes = date.getMonth() + 1;
		final Integer dia = date.getDate();

		String day = dia.toString();
		String month = mes.toString();
		if (mes < 10)
			month = "0" + mes.toString();
		if (dia < 10)
			day = "0" + dia.toString();
		final String d = ano.toString().substring(ano.toString().length() - 2, ano.toString().length()) + month + day;

		String ticker = "-";
		final String a = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

		for (int i = 0; i < tam; i++) {
			final Integer random = (int) (Math.floor(Math.random() * a.length()) % a.length());
			ticker = ticker + a.charAt(random);
		}

		return d + ticker;
	}

	//CALCULA LOS MESES DE DIFERENCIA DE UN QUOLET
	public static Integer getMonths(final Quolet quolet) {
		Integer res = 0;

		final Date dateCheck = quolet.getMoment();
		final Date actual = new Date();

		final Integer difA = ((actual.getYear() + 1900) - (dateCheck.getYear() + 1900));
		res = difA * 12 + actual.getMonth() - dateCheck.getMonth();

		return res;
	}

	//METODO QUE ACTUALIZA LOS MESES DE LOS QUOLETS QUE ESTAN EN SAVE MODE
	public void updateMonths() {
		final List<Quolet> quolets = (List<Quolet>) this.findAll();

		for (int i = 0; i < quolets.size(); i++)
			if (quolets.get(i).getDraftMode() == 1) {
				final Integer months = QuoletService.getMonths(quolets.get(i));
				quolets.get(i).setNumMonth(months);
				this.quoletRepository.save(quolets.get(i));
			}
	}

}
