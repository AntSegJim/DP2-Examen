
package service;

import java.util.ArrayList;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import repositories.ProblemRepository;
import services.PositionService;
import services.ProblemService;
import utilities.AbstractTest;
import domain.Application;
import domain.Position;
import domain.Problem;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ProblemServiceTest extends AbstractTest {

	@Autowired
	private ProblemService		problemService;

	@Autowired
	private PositionService		positionService;

	@Autowired
	private ProblemRepository	problemRepository;


	/*
	 * a) Requeriment: An actor who is authenticated as a company must be able to:
	 * Manage their problems, which includes creating them.
	 * 
	 * b) Broken bussines rule:
	 * se intenta crear un problem con el título vacío
	 * 
	 * c) Sentence coverage: 23%
	 * 
	 * d) Data coverage: 25%
	 */

	@Test
	public void CreatePositionService() {
		final Object testingData[][] = {
			{//Positive test
				"Nuevo title", "Statement", "Hint", 1, null
			}, {//Positive test
				"Nuevo title", "Statement", "", 1, null
			}, {//Negative test: title vacio
				null, "Statement", "Hint", 1, ConstraintViolationException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.CreateProblemTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (int) testingData[i][3], (Class<?>) testingData[i][4]);
	}
	protected void CreateProblemTemplate(final String title, final String statement, final String hint, final int draftMode, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate("company");

			final Problem p = this.problemService.create();

			p.setTitle(title);
			p.setStatement(statement);
			p.setHint(hint);
			p.setAttachment(new ArrayList<String>());
			p.setDraftMode(draftMode);
			p.setApplications(new ArrayList<Application>());

			this.problemService.save(p);
			this.problemRepository.flush();

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * a) Requeriment: An actor who is authenticated as a company must be able to:
	 * Manage their problems, which includes updating them.
	 * 
	 * b) Broken bussines rule:
	 * Case 2: se intenta actualizar un problem que no pertenece a su company
	 * Case 3: se intenta modificar un problem que no esta en draft mode
	 * 
	 * c) Sentence coverage: 100%
	 * 
	 * d) Data coverage: 20%
	 */

	@Test
	public void EditProblemService() {
		final Object testingData[][] = {
			{//Positive test
				"company", super.getEntityId("problem2"), "Update title", null
			}, {//Negative test: Case 2
				"company1", super.getEntityId("problem1"), "Update title", IllegalArgumentException.class
			}, {//Negative test: Case 3
				"company", super.getEntityId("problem1"), "Update title", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.EditProblemServiceTemplate((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	protected void EditProblemServiceTemplate(final String authority, final int id, final String title, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);

			final Problem p = this.problemService.findOne(id);
			p.setTitle(title);
			this.problemService.save(p);

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * a) Requeriment: An actor who is authenticated as a company must be able to:
	 * Manage their problems, which deleting updating them.
	 * 
	 * b) Broken bussines rule:
	 * se intenta eliminar un problem que no pertenece a una company
	 * 
	 * c) Sentence coverage: 100%
	 * 
	 * d) Data coverage: --
	 */

	@Test
	public void DeleteProblemService() {
		final Object testingData[][] = {
			{//Positive test
				"company", super.getEntityId("problem2"), null
			}, {//Negative test: Case 2
				"company1", super.getEntityId("problem1"), IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.DeleteProblemServiceTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void DeleteProblemServiceTemplate(final String authority, final int id, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);

			final Problem p = this.problemService.findOne(id);
			final Position position = this.positionService.getPositionByProblem(p.getId());

			this.problemService.delete(p, position.getId());

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * a) Requeriment: An actor who is authenticated as a company must be able to:
	 * Manage their problems, which showing updating them.
	 * 
	 * b) Broken bussines rule:
	 * se intenta acceder a un problem que no pertenece a una company
	 * 
	 * c) Sentence coverage: 100%
	 * 
	 * d) Data coverage: --
	 */

	@Test
	public void ShowProblemService() {
		final Object testingData[][] = {
			{//Positive test
				"company", super.getEntityId("problem1"), null
			}, {//Negative test: Case 2
				"company1", super.getEntityId("problem2"), IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.ShowProblemServiceTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void ShowProblemServiceTemplate(final String authority, final int id, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(authority);

			final Problem p = this.problemService.findOneWithCredentials(id);

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
