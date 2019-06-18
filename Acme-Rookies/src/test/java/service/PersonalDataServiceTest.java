
package service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.PersonalDataRepository;
import services.PersonalDataService;
import utilities.AbstractTest;
import domain.PersonalData;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PersonalDataServiceTest extends AbstractTest {

	@Autowired
	private PersonalDataService		personalDataService;
	@Autowired
	private PersonalDataRepository	personalDataRepository;


	@Test
	public void PersonalDataService() {

		/*
		 * a) Requeriment: Manage his or her curricula, which includes listing,
		 * showing, creating, updating, and deleting them. When a hacker makes
		 * an application, he or she must select one of his or her curricula so
		 * that it’s attached to the application. Note that attaching a
		 * curriculum makes a copy; the updates that a hacker performs on the
		 * original curricu-lum are not propagated to the applications to which
		 * he or she’s attached a previous version.
		 * 
		 * b) Broken bussines rule: Un hacker intenta mostrar un personalData que no le pertenece.
		 * 
		 * c) Sentence coverage:
		 * Sentencias metodo findOne-> 7
		 * Sentencias totales-> 7
		 * Sentence covegare positive test -> 7 (100%)
		 * Sentence covegare negative test -> 6 (85,71%)
		 * 
		 * d) Data coverage: 1 atributo incorrecto de 2 atributos-> 50%
		 */
		final Object testingData4[][] = {
			{//Positive test
				"rookie", super.getEntityId("personalData1"), null
			}, {//Negative test
				"rookie1", super.getEntityId("personalData1"), IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData4.length; i++)
			this.personalDataServiceTemplateShow((String) testingData4[i][0], (int) testingData4[i][1], (Class<?>) testingData4[i][2]);

		/*
		 * a) Requeriment: Manage his or her curricula, which includes listing,
		 * showing, creating, updating, and deleting them. When a hacker makes
		 * an application, he or she must select one of his or her curricula so
		 * that it’s attached to the application. Note that attaching a
		 * curriculum makes a copy; the updates that a hacker performs on the
		 * original curricu-lum are not propagated to the applications to which
		 * he or she’s attached a previous version.
		 * 
		 * b) Broken bussines rule: Un hacker intenta modificar un personalData que no le pertenece.
		 * 
		 * c) Sentence coverage:
		 * Sentencias metodo findOne-> 7
		 * Sentencias metodo save-> 16
		 * Sentencias totales-> 23
		 * Sentence covegare positive test -> 17 (73,91%)
		 * Sentence covegare negative test -> 6 (26,08%)
		 * 
		 * d) Data coverage: 1 atributo incorrecto de 7 atributos-> 14,28%
		 */
		final Object testingData[][] = {
			{//Positive test
				"rookie", super.getEntityId("personalData1"), "full name", "statement", "+34 123456789", "http://githubProfile", "http://linkedlnProfile", null
			}, {//Negative test
				"rookie1", super.getEntityId("personalData1"), "full name", "statement", "+34 123456789", "http://githubProfile", "http://linkedlnProfile", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.personalDataServiceTemplateSave((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(Class<?>) testingData[i][7]);

		/*
		 * a) Requeriment: Manage his or her curricula, which includes listing,
		 * showing, creating, updating, and deleting them. When a hacker makes
		 * an application, he or she must select one of his or her curricula so
		 * that it’s attached to the application. Note that attaching a
		 * curriculum makes a copy; the updates that a hacker performs on the
		 * original curricu-lum are not propagated to the applications to which
		 * he or she’s attached a previous version.
		 * 
		 * b) Broken bussines rule: Un hacker intenta crear un personalData con el fullName vacio
		 * 
		 * c) Sentence coverage:
		 * Sentencias metodo create-> 8
		 * Sentencias metodo save-> 16
		 * Sentencias totales-> 24
		 * Sentence covegare positive test -> 24 (100%)
		 * Sentence covegare negative test -> 17 (70,83%)
		 * 
		 * d) Data coverage: 1 atributo incorrecto de 5 atributos-> 20%
		 */
		final Object testingData2[][] = {
			{//Positive test
				"full name", "statement", "+34 123456789", "http://githubProfile", "http://linkedlnProfile", null
			}, {//Negative test
				"", "statement", "+34 123456789", "http://githubProfile", "http://linkedlnProfile", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.personalDataServiceTemplateCreate((String) testingData2[i][0], (String) testingData2[i][1], (String) testingData2[i][2], (String) testingData2[i][3], (String) testingData2[i][4], (Class<?>) testingData2[i][5]);

	}

	protected void personalDataServiceTemplateSave(final String authority, final int id, final String fullName, final String statement, final String phoneNumber, final String githubProfile, final String linkedlnProfile, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);

			final PersonalData p = this.personalDataService.findOne(id);
			p.setFullName(fullName);
			p.setStatement(statement);
			p.setPhoneNumber(phoneNumber);
			p.setLinkedlnProfile(linkedlnProfile);
			p.setGithubProfile(githubProfile);
			this.personalDataService.save(p);

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void personalDataServiceTemplateCreate(final String fullName, final String statement, final String phoneNumber, final String githubProfile, final String linkedlnProfile, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate("rookie");

			final PersonalData p = this.personalDataService.create();
			p.setFullName(fullName);
			p.setStatement(statement);
			p.setPhoneNumber(phoneNumber);
			p.setLinkedlnProfile(linkedlnProfile);
			p.setGithubProfile(githubProfile);
			this.personalDataService.save(p);
			this.personalDataRepository.flush();

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void PersonalDataServiceTemplateDelete(final String authority, final int id, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);

			final PersonalData p = this.personalDataService.findOne(id);
			this.personalDataService.delete(p);

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	protected void personalDataServiceTemplateShow(final String authority, final int id, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);

			final PersonalData p = this.personalDataService.findOne(id);
			Assert.notNull(p);
			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
