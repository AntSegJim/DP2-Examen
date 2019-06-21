
package service;

import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import repositories.PositionRepository;
import services.CompanyService;
import services.PositionService;
import utilities.AbstractTest;
import domain.Company;
import domain.Position;
import domain.Problem;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PositionServiceTest extends AbstractTest {

	@Autowired
	private PositionService		positionService;
	@Autowired
	private CompanyService		companyService;

	//Para el uso del flush
	@Autowired
	private PositionRepository	positionRepository;


	/*
	 * a) Requeriment: An actor who is authenticated as a company must be able to:
	 * Manage their positions, which includes creating them.
	 * 
	 * b) Broken bussines rule:
	 * se intenta crear una posición con el título vacío
	 * 
	 * c) Sentence coverage: 71.5%
	 * 
	 * d) Data coverage: 9.1% (1 atributo incorrecto/11 atributos)
	 */

	@Test
	public void CreatePositionService() {
		final Object testingData[][] = {
			{//Positive test
				super.getEntityId("company1"), "Nuevo title", "Description", this.positionRepository.getTomorrow(), "profile", "skills", "techonology", 123.0, "poio-7654", 1, 0, null
			}, {//Negative test: title vacio
				super.getEntityId("company1"), "", "Description", this.positionRepository.getTomorrow(), "profile", "skills", "techonology", 123.0, "poio-7654", 1, 0, DataIntegrityViolationException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.CreatePositionTemplate((int) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Date) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(Double) testingData[i][7], (String) testingData[i][8], (int) testingData[i][9], (int) testingData[i][10], (Class<?>) testingData[i][11]);
	}
	protected void CreatePositionTemplate(final int CompanyId, final String title, final String description, final Date deadline, final String requiredProfile, final String skillsRequired, final String technologiesRequired, final Double salary,
		final String ticker, final int draftmode, final int isCancelled, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate("company");

			final Company c = this.companyService.findOne(CompanyId);

			final Position p = this.positionService.create();
			p.setCompany(c);
			p.setProblems(new HashSet<Problem>());

			p.setTitle(title);
			p.setDescription(description);
			p.setDeadLine(deadline);
			p.setRequiredProfile(requiredProfile);
			p.setSkillsRequired(skillsRequired);
			p.setTechnologiesRequired(technologiesRequired);
			p.setSalary(salary);
			p.setTicker(ticker);
			p.setDraftMode(draftmode);
			p.setIsCancelled(isCancelled);

			this.positionService.save(p);

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * a) Requeriment: An actor who is authenticated as a company must be able to:
	 * Manage their positions, which includes update them.
	 * 
	 * b) Broken bussines rule:
	 * Case 2: una compañía que no es propietaria de la position, intenta editarla
	 * Case 3: se intenta modificar una compañia que ya esta en save mode
	 * c) Sentence coverage: 100%
	 * 
	 * d) Data coverage: 9%
	 */

	@Test
	public void EditPositionService() {
		final Object testingData[][] = {
			{//Positive test
				"company", super.getEntityId("position1"), "Update title", null
			}, {//Negative test: Case 2
				"company1", super.getEntityId("position1"), "Update title", IllegalArgumentException.class
			}, {//Negative test: Case 3
				"company", super.getEntityId("position2"), "Update title", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.EditPositionServiceTemplate((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	protected void EditPositionServiceTemplate(final String authority, final int id, final String title, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);

			final Position p = this.positionService.findOne(id);
			p.setTitle(title);
			this.positionService.save(p);

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * a) Requeriment: An actor who is authenticated as a company must be able to:
	 * Manage their positions, which includes delete them.
	 * 
	 * b) Broken bussines rule:
	 * una compañía que no es propietaria de la position, intenta borrarla
	 * 
	 * c) Sentence coverage: 100%
	 * 
	 * d) Data coverage:--
	 */

	@Test
	public void DeletePositionService() {
		final Object testingData[][] = {
			{//Positive test
				"company", super.getEntityId("position1"), null
			}, {//Negative test: Case 2
				"company1", super.getEntityId("position2"), IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.DeletePositionServiceTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void DeletePositionServiceTemplate(final String authority, final int id, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);

			final Position p = this.positionService.findOne(id);
			this.positionService.delete(p);

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * a) Requeriment: An actor who is authenticated as a company must be able to:
	 * Manage their positions, which includes delete them.
	 * 
	 * b) Broken bussines rule:
	 * una compañía que no es propietaria de la position, intenta acceder a ella
	 * 
	 * c) Sentence coverage: 100%
	 * 
	 * d) Data coverage:--
	 */

	@Test
	public void FindOnePositionService() {
		final Object testingData[][] = {
			{//Positive test
				"company", super.getEntityId("position1"), null
			}, {//Positive test
				"company", super.getEntityId("position2"), null
			}, {//Negative test: Case 2
				"company1", super.getEntityId("position2"), IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.FindOnePositionServiceTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void FindOnePositionServiceTemplate(final String authority, final int id, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);

			final Position p = this.positionService.findOneWithCredentials(id);

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
