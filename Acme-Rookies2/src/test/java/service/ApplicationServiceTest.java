
package service;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.ApplicationService;
import services.CurriculaService;
import services.HackerService;
import services.PositionService;
import utilities.AbstractTest;
import domain.Application;
import domain.Position;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	@Autowired
	private ApplicationService	applicationService;
	@Autowired
	private CurriculaService	curriculaService;
	@Autowired
	private HackerService		hackerService;
	@Autowired
	private PositionService		positionService;


	/*
	 * a) Requeriment: Create application.
	 * 
	 * b) Broken bussines rule:
	 * curricula of other hacker
	 * 
	 * c) Sentence coverage: 68.75 %
	 * 
	 * d) Data coverage: 33.33% (1 atributo incorrecto/3 atributos)
	 */

	@Test
	public void CreateApplicationService() {
		final Object testingData[][] = {
			{//Positive test
				super.getEntityId("rookie1"), super.getEntityId("curricula1"), null
			}, {//Negative test: hacker
				super.getEntityId("rookie2"), super.getEntityId("curricula1"), IllegalArgumentException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.CreateApplicationTemplate((int) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void CreateApplicationTemplate(final int hackerId, final int curriculaId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate("rookie");

			final Application a = this.applicationService.create();
			a.setMoment(new Date());
			a.setRookie(this.hackerService.findOne(hackerId));
			a.setCurricula(this.curriculaService.findOne(curriculaId));
			final int idPosition = super.getEntityId("position1");
			final Position position = this.positionService.findOne(idPosition);
			this.applicationService.save(a, position);

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * a) Requeriment: Edit application.
	 * 
	 * b) Broken bussines rule:
	 * curricula of other hacker
	 * 
	 * c) Sentence coverage: 54.54%
	 * 
	 * d) Data coverage: 50% (1 atributo incorrecto/2 atributos)
	 */
	@Test
	public void EditApplicationService() {
		final Object testingData[][] = {
			{//Positive test
				super.getEntityId("application1"), null
			}, {//Negative test: hacker
				super.getEntityId("application4"), IllegalArgumentException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.EditApplicationTemplate((int) testingData[i][0], (Class<?>) testingData[i][1]);
	}
	protected void EditApplicationTemplate(final int applicationId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate("rookie");

			final Application a = this.applicationService.findOne(applicationId);
			a.setExplication("hola");
			final int idPosition = super.getEntityId("position1");
			final Position position = this.positionService.findOne(idPosition);
			this.applicationService.save(a, position);

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
