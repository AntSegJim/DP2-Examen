
package service;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.FinderService;
import utilities.AbstractTest;
import domain.Finder;
import domain.Position;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FinderServiceTest extends AbstractTest {

	@Autowired
	private FinderService	finderService;


	/*
	 * a) Requeriment: Create a finder.
	 * 
	 * b) Broken bussines rule:
	 * create a finder with moment null
	 * 
	 * c) Sentence coverage: 62.4%
	 * 
	 * d) Data coverage: 14.28% (1 atributo incorrecto/7 atributos)
	 */

	@Test
	public void CreateFinderService() {
		final Object testingData[][] = {
			{//Positive test
				"", new Date(), 1., 1500.0, new HashSet<Position>(), new Date(), null
			}, {//Negative test: Moment null
				"i7", new Date(), 1., 1500.0, new HashSet<Position>(), null, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.CreateFinderTemplate((String) testingData[i][0], (Date) testingData[i][1], (double) testingData[i][2], (double) testingData[i][3], (Collection<Position>) testingData[i][4], (Date) testingData[i][5], (Class<?>) testingData[i][6]);
	}
	protected void CreateFinderTemplate(final String keyword, final Date deadline, final Double minSalary, final Double maxSalary, final Collection<Position> c, final Date moment, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate("rookie");

			final Finder f = this.finderService.create();
			f.setKeyWord(keyword);
			f.setDeadLine(deadline);
			f.setMinSalary(minSalary);
			f.setMaxSalary(maxSalary);
			f.setMoment(moment);
			f.setPositions(c);
			this.finderService.save(f);

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * a) Requeriment: Edit a finder.
	 * 
	 * b) Broken bussines rule:
	 * edit a finder with moment null
	 * 
	 * c) Sentence coverage: 82.35%
	 * 
	 * d) Data coverage: 14.28% (1 atributo incorrecto/7 atributos)
	 */
	@Test
	public void EditFinderService() {
		final Object testingData[][] = {
			{//Positive test
				super.getEntityId("finder1"), "i7", new Date(), 0., 1500.0, new HashSet<Position>(), new Date(), null
			}, {//Negative test: Moment null
				super.getEntityId("finder2"), "i7", new Date(), 0., 1500.0, new HashSet<Position>(), null, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.EditFinderTemplate((int) testingData[i][0], (String) testingData[i][1], (Date) testingData[i][2], (double) testingData[i][3], (double) testingData[i][4], (Collection<Position>) testingData[i][5], (Date) testingData[i][6],
				(Class<?>) testingData[i][7]);
	}
	protected void EditFinderTemplate(final int finderId, final String keyword, final Date deadline, final Double minSalary, final Double maxSalary, final Collection<Position> c, final Date moment, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate("rookie");

			final Finder f = this.finderService.findOne();
			f.setKeyWord(keyword);
			f.setDeadLine(deadline);
			f.setMinSalary(minSalary);
			f.setMaxSalary(maxSalary);
			f.setMoment(moment);
			f.setPositions(c);
			this.finderService.save(f);

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * a) Requeriment: Clear a finder.
	 * 
	 * b) Broken bussines rule:
	 * clear a finder with moment null
	 * 
	 * c) Sentence coverage: 62.35%
	 * 
	 * d) Data coverage: 14.28% (1 atributo incorrecto/7 atributos)
	 */
	@Test
	public void ClearFinderService() {
		final Object testingData[][] = {
			{//Positive test
				"i7", new Date(), 0., 1500.0, new HashSet<Position>(), new Date(), null
			}, {//Negative test: Moment null
				"i7", new Date(), 0., 1500.0, new HashSet<Position>(), null, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.ClearFinderTemplate((String) testingData[i][0], (Date) testingData[i][1], (double) testingData[i][2], (double) testingData[i][3], (Collection<Position>) testingData[i][4], (Date) testingData[i][5], (Class<?>) testingData[i][6]);
	}
	protected void ClearFinderTemplate(final String keyword, final Date deadline, final Double minSalary, final Double maxSalary, final Collection<Position> c, final Date moment, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate("rookie");

			final Finder f = this.finderService.findOne();
			f.setKeyWord(keyword);
			f.setDeadLine(deadline);
			f.setMinSalary(minSalary);
			f.setMaxSalary(maxSalary);
			f.setMoment(moment);
			f.setPositions(c);
			this.finderService.save(f);
			this.finderService.clearResults();

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
