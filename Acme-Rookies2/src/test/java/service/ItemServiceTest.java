
package service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import repositories.ItemRepository;
import repositories.ProviderRepository;
import security.LoginService;
import services.ItemService;
import services.ProviderService;
import utilities.AbstractTest;
import domain.Item;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ItemServiceTest extends AbstractTest {

	@Autowired
	private ItemService			itemService;
	@Autowired
	private ProviderService		providerService;
	@Autowired
	private ItemRepository		itemRepository;
	@Autowired
	private ProviderRepository	providerRepository;


	/*
	 * a) Requeriment: An actor who is authenticated as a company must be able to:
	 * Manage their items, which includes creating them.
	 * 
	 * b) Broken bussines rule:
	 * se intenta crear un item con el nombre vacío
	 * 
	 * c) Sentence coverage: 71.5%
	 * 
	 * d) Data coverage: 9.1% (1 atributo incorrecto/11 atributos)
	 */

	@Test
	public void CreateItemService() {
		final Object testingData[][] = {
			{//Positive test
				super.getEntityId("provider1"), "Name", "Description", "https://www.google.com", null
			}, {//Negative test: name vacio
				super.getEntityId("provider1"), "", "Description", "https://www.marca.es", ConstraintViolationException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.CreateItemTemplate((int) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}
	protected void CreateItemTemplate(final int providerId, final String name, final String description, final String link, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate("provider1");

			final Item item = this.itemService.create();
			item.setDescription(description);
			item.setName(name);
			item.setProvider(this.providerService.providerUserAccount(LoginService.getPrincipal().getId()));
			item.getLink().add(link);

			this.itemRepository.saveAndFlush(item);

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * a) Requeriment: An actor who is authenticated as a company must be able to:
	 * Manage their items, which includes edit them.
	 * 
	 * b) Broken bussines rule:
	 * se intenta crear un item con el nombre vacío
	 * 
	 * c) Sentence coverage: 82.6%
	 * 
	 * d) Data coverage: 9.1% (1 atributo incorrecto/11 atributos)
	 */

	@Test
	public void EditItemService() {
		final Object testingData[][] = {
			{//Positive test
				super.getEntityId("item1"), "Name", "Description", "https://www.google.com", null
			}, {//Negative test: bad provider
				super.getEntityId("item4"), "Name", "Description", "https://www.marca.es", IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.EditItemServiceTemplate((int) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	protected void EditItemServiceTemplate(final int itemId, final String name, final String description, final String link, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate("provider");
			final Item item = this.itemService.findOne(itemId);
			item.setDescription(description);
			item.setName(name);
			item.getLink().add(link);

			this.itemService.save(item);

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * a) Requeriment: An actor who is authenticated as a company must be able to:
	 * Manage their items, which includes deleting them.
	 * 
	 * b) Broken bussines rule:
	 * se intenta crear un item con el nombre vacío
	 * 
	 * c) Sentence coverage: 100%
	 * 
	 * d) Data coverage: --
	 */

	@Test
	public void DeleteItemService() {
		final Object testingData[][] = {
			{//Positive test
				super.getEntityId("item1"), null
			}, {//Negative test: bad provider
				super.getEntityId("item4"), IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.DeleteItemServiceTemplate((int) testingData[i][0], (Class<?>) testingData[i][1]);
	}
	protected void DeleteItemServiceTemplate(final int itemId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate("provider");
			final Item item = this.itemService.findOne(itemId);
			this.itemService.delete(item);

			super.authenticate(null);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
