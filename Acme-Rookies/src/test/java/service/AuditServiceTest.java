
package service;

import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.AuditRepository;
import services.AuditService;
import services.PositionService;
import utilities.AbstractTest;
import domain.Audit;
import domain.Position;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AuditServiceTest extends AbstractTest {

	@Autowired
	private AuditService	auditService;
	@Autowired
	private PositionService	positionService;
	@Autowired
	private AuditRepository	auditRepository;


	/*
	 * a) Requeriment: Manage his or her audits, which includes listing them, showing them, creating them,
	 * updating, and deleting them. An audit can be updated or deleted as long as it’s
	 * saved in draft mode.
	 * 
	 * 
	 * b) Broken bussines rule: Un auditor crea un audit con el atributo text vacio.
	 * 
	 * c) Sentence coverage:
	 * Sentencias metodo create-> 9
	 * Sentencias metodo save-> 9
	 * Sentencias totales-> 18
	 * Sentence covegare positive test -> 14 (100%)
	 * Sentence covegare negative test -> 13 (92,85%)
	 * 
	 * d) Data coverage: 1 atributo incorrecto de 5 atributos-> 20%
	 */
	@Test
	public void CreateAuditService() {
		final Object testingData[][] = {
			{//Positive test
				"text", new Date(), 2, 1, super.getEntityId("position1"), null
			}, {//Positive test
				"", new Date(), 2, 1, super.getEntityId("position1"), ConstraintViolationException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.CreateAuditTemplate((String) testingData[i][0], (Date) testingData[i][1], (int) testingData[i][2], (int) testingData[i][3], (int) testingData[i][4], (Class<?>) testingData[i][5]);
	}
	protected void CreateAuditTemplate(final String text, final Date moment, final int score, final int draftMode, final int positionId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate("auditor");

			final Audit audit = this.auditService.create();
			final Position position = this.positionService.findOne(positionId);

			audit.setText(text);
			audit.setMoment(moment);
			audit.setScore(score);
			audit.setDraftMode(draftMode);
			audit.setPosition(position);

			this.auditService.save(audit);
			this.auditRepository.flush();

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * a) Requeriment: Manage his or her audits, which includes listing them, showing them, creating them,
	 * updating, and deleting them. An audit can be updated or deleted as long as it’s
	 * saved in draft mode.
	 * 
	 * 
	 * b) Broken bussines rule: Un auditor intenta mostrar un audit que no le pertenece.
	 * 
	 * c) Sentence coverage:
	 * Sentencias metodo findOne-> 6
	 * Sentencias totales-> 6
	 * Sentence covegare positive test -> 6 (100%)
	 * Sentence covegare negative test -> 5 (83,33%)
	 * 
	 * d) Data coverage: 1 atributo incorrecto de 2 atributos-> 50%
	 */
	@Test
	public void ShowAuditService() {
		final Object testingData[][] = {
			{//Positive test
				"auditor", super.getEntityId("audit1"), null
			}, {//Positive test
				"auditor", super.getEntityId("audit2"), IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.ShowAuditTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void ShowAuditTemplate(final String authority, final int auditId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);
			final Audit audit = this.auditService.findOne(auditId);
			Assert.notNull(audit);
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * a) Requeriment: Manage his or her audits, which includes listing them, showing them, creating them,
	 * updating, and deleting them. An audit can be updated or deleted as long as it’s
	 * saved in draft mode.
	 * 
	 * 
	 * b) Broken bussines rule: Un auditor intenta editar un audit que no le pertenece.
	 * 
	 * c) Sentence coverage:
	 * Sentencias metodo findOne-> 6
	 * Sentencias metodo save-> 9
	 * Sentencias totales-> 15
	 * Sentence covegare positive test -> 15 (100%)
	 * Sentence covegare negative test -> 5 (33,33%)
	 * 
	 * d) Data coverage: 1 atributo incorrecto de 7 atributos-> 14,28%
	 */
	@Test
	public void EditAuditService() {
		final Object testingData[][] = {
			{//Positive test
				"auditor", super.getEntityId("audit1"), "text", new Date(), 2, 1, super.getEntityId("position1"), null
			}, {//Positive test
				"auditor1", super.getEntityId("audit1"), "text", new Date(), 2, 1, super.getEntityId("position1"), IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.EditAuditTemplate((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Date) testingData[i][3], (int) testingData[i][4], (int) testingData[i][5], (int) testingData[i][6], (Class<?>) testingData[i][7]);
	}
	protected void EditAuditTemplate(final String authority, final int auditId, final String text, final Date moment, final int score, final int draftMode, final int positionId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);

			final Audit audit = this.auditService.findOne(auditId);
			final Position position = this.positionService.findOne(positionId);

			audit.setText(text);
			audit.setMoment(moment);
			audit.setScore(score);
			audit.setDraftMode(draftMode);
			audit.setPosition(position);

			this.auditService.save(audit);
			this.auditRepository.flush();

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
