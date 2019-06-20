
package service;

import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import repositories.AuditRepository;
import repositories.QuoletRepository;
import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.QuoletService;
import utilities.AbstractTest;
import domain.Company;
import domain.Quolet;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class QuoletServiceTest extends AbstractTest {

	@Autowired
	private QuoletService		quoletService;
	@Autowired
	private QuoletRepository	quoletRepository;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private AuditRepository		auditRepository;


	/*
	 * a) Requeriment: An actor who is authenticated as a ACTOR try to create a new quolet
	 * 
	 * b) Broken bussines rule:
	 * se intenta crear un quolet con el body vacío
	 * 
	 * c) Sentence coverage: 28%
	 * 
	 * d) Data coverage: 20%
	 */

	@Test
	public void CreatePositionService() {
		final Object testingData[][] = {
			{//Positive test
				"765411-juyt", null, "Body1", "https://www.imagen.com.mx/assets/img/imagen_share.png", 1, super.getEntityId("audit3"), 0, "company1", null
			}, {//Negative test: body vacio
				"134531-juyt", null, "", "https://www.imagen.com.mx/assets/img/imagen_share.png", 1, super.getEntityId("audit3"), 0, "company1", ConstraintViolationException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.CreateQuoletTemplate((String) testingData[i][0], (Date) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (int) testingData[i][4], (int) testingData[i][5], (int) testingData[i][6], (String) testingData[i][7],
				(Class<?>) testingData[i][8]);
	}

	protected void CreateQuoletTemplate(final String ticker, final Date moment, final String body, final String picture, final int draftMode, final int idAudit, final int numMonths, final String authority, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);

			final Quolet q = this.quoletService.create();

			final UserAccount user = LoginService.getPrincipal();
			final Company a = (Company) this.actorService.getActorByUserAccount(user.getId());

			q.setCompany(a);
			q.setTicker(ticker);
			q.setMoment(moment);
			q.setBody(body);
			q.setPicture(picture);
			q.setDraftMode(draftMode);
			q.setAudit(this.auditRepository.findOne(idAudit));
			q.setNumMonth(numMonths);

			this.quoletService.save(q);
			this.quoletRepository.flush();
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
