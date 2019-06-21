
package service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import services.CreditCardService;
import services.ItemService;
import services.ProviderService;
import utilities.AbstractTest;
import domain.CreditCard;
import domain.Item;
import domain.Provider;
import forms.RegistrationFormProviderAndCreditCard;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ProviderServiceTest extends AbstractTest {

	@Autowired
	private ProviderService		providerService;

	@Autowired
	private ItemService			itemService;

	@Autowired
	private CreditCardService	creditCardService;


	/*
	 * a) Requeriment: An actor who is not authenticated must be able to
	 * register to the system as provider
	 * 
	 * b) Broken bussines rule:
	 * Se intenta registratar un usuario como provider con el make vacio
	 * 
	 * c) Sentence coverage:Este caso de uso engloba el recontructor y el save tanto del ProviderService como de CreditCardService,
	 * el total de lineas sumando estos metodos es de 142, de las cuales este test recorrer 77 , es decir un 54'23%.
	 * 
	 * d) Data coverage: 7.14% (1 atributo incorrecto/14atributos)
	 */

	@Test
	public void CreateProviderService() {
		final Object testingData[][] = {
			{//Positive test
				"Nuevo Nombre", "Apellido", "ES12345678X", "prueba@email.com", "NuevoUsername", "NuevaPassWord", "NuevaPassWord", "NuevoBrandName", "NuevoholderName", "5182901911816096", 8, 2020, 876, "NuevoMake", null
			}, {//Negative test: make vacio
				"Nuevo Nombre", "Apellido", "ES12345678X", "prueba2@email.com", "NuevoUsername", "NuevaPassWord", "NuevaPassWord", "NuevoBrandName", "NuevoholderName", "5182901911816096", 8, 2020, 876, "", NullPointerException.class

			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.CreateProviderTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (String) testingData[i][9], (int) testingData[i][10], (int) testingData[i][11], (int) testingData[i][12], (String) testingData[i][13], (Class<?>) testingData[i][14]);
	}
	protected void CreateProviderTemplate(final String name, final String surnames, final String vatNumber, final String email, final String username, final String password, final String confirmPassword, final String brandName, final String holderName,
		final String number, final int expirationMonth, final int expirationYear, final int cW, final String make, final Class<?> expected) {
		Class<?> caught;
		Provider provider = null;
		CreditCard creditcard = null;
		caught = null;
		try {

			RegistrationFormProviderAndCreditCard registrationForm = new RegistrationFormProviderAndCreditCard();

			registrationForm = registrationForm.createToProviderAndCreditCard();

			registrationForm.setBrandName(brandName);
			registrationForm.setHolderName(holderName);
			registrationForm.setNumber(number);
			registrationForm.setExpirationMonth(expirationMonth);
			registrationForm.setExpirationYear(expirationYear);
			registrationForm.setCW(cW);

			registrationForm.setName(name);
			registrationForm.setSurnames(surnames);
			registrationForm.setVatNumber(vatNumber);
			registrationForm.setEmail(email);
			registrationForm.getUserAccount().setUsername(username);
			registrationForm.getUserAccount().setPassword(password);
			registrationForm.setPassword(confirmPassword);
			registrationForm.setPhone("");
			registrationForm.setAddress("");
			registrationForm.setPhoto("");
			registrationForm.setMake(make);
			registrationForm.setCheck(true);

			final BindingResult binding = null;

			creditcard = this.creditCardService.reconstruct(registrationForm, binding);
			registrationForm.setCreditCard(creditcard);
			provider = this.providerService.reconstruct(registrationForm, binding);
			final CreditCard creditCardSave = this.creditCardService.save(creditcard);
			provider.setCreditCard(creditCardSave);
			this.providerService.save(provider);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * a) Requeriment: An actor who is authenticated
	 * must be able to edit his or her personal data.
	 * 
	 * b) Broken bussines rule:
	 * Un provider intenta editar la informacion de otro.
	 * 
	 * c) Sentence coverage:Este caso de uso engloba el recontructor y el save tanto del ProviderService como del CreditCardService, y al findOne de ProviderService,
	 * el total de lineas sumando estos metodos es de 148, de las cuales este test recorrer 96 , es decir un 63'38%.
	 * 
	 * d) Data coverage:7.14% (1 atributo incorrecto/14atributos)
	 */

	@Test
	public void EditProviderService() {
		final Object testingData[][] = {
			{
				//Positive test
				"Nuevo Nombre", "Apellido", "ES12345678X", "prueba@email.com", "NuevaPassWord", "NuevaPassWord", "NuevoBrandName", "NuevoholderName", "5182901911816096", 8, 2020, 876, super.getEntityId("provider1"), "NombreProvider", null
			},
			{
				//Negative test: Un Provider  intenta modificar los datos de otra
				"Nuevo Nombre", "Apellido", "ES12345678X", "prueba@email.com", "NuevaPassWord", "NuevaPassWord", "NuevoBrandName", "NuevoholderName", "5182901911816096", 8, 2020, 876, super.getEntityId("provider2"), "NombreProvider",
				IllegalArgumentException.class

			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.EditProviderTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (int) testingData[i][9], (int) testingData[i][10], (int) testingData[i][11], (int) testingData[i][12], (String) testingData[i][13], (Class<?>) testingData[i][14]);
	}
	protected void EditProviderTemplate(final String name, final String surnames, final String vatNumber, final String email, final String password, final String confirmPassword, final String brandName, final String holderName, final String number,
		final int expirationMonth, final int expirationYear, final int cW, final int providerId, final String make, final Class<?> expected) {
		Class<?> caught;
		Provider provider = null;
		CreditCard creditcard = null;
		caught = null;
		try {
			super.authenticate("provider");
			RegistrationFormProviderAndCreditCard registrationForm = new RegistrationFormProviderAndCreditCard();

			registrationForm = registrationForm.createToProviderAndCreditCard();

			provider = this.providerService.findOne(providerId);
			registrationForm.setId(provider.getId());
			registrationForm.setVersion(provider.getVersion());
			registrationForm.setBrandName(brandName);
			registrationForm.setHolderName(holderName);
			registrationForm.setNumber(number);
			registrationForm.setExpirationMonth(expirationMonth);
			registrationForm.setExpirationYear(expirationYear);
			registrationForm.setCW(cW);

			registrationForm.setName(name);
			registrationForm.setSurnames(surnames);
			registrationForm.setVatNumber(vatNumber);
			registrationForm.setEmail(email);
			registrationForm.getUserAccount().setUsername(provider.getUserAccount().getUsername());
			registrationForm.getUserAccount().setPassword(password);
			registrationForm.setPassword(confirmPassword);
			registrationForm.setPhone("");
			registrationForm.setAddress("");
			registrationForm.setPhoto("");
			registrationForm.setMake(make);

			final BindingResult binding = null;

			creditcard = this.creditCardService.reconstruct(registrationForm, binding);
			registrationForm.setCreditCard(creditcard);
			provider = this.providerService.reconstruct(registrationForm, binding);
			final CreditCard creditCardSave = this.creditCardService.save(creditcard);
			provider.setCreditCard(creditCardSave);
			this.providerService.save(provider);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * a) Requeriment: 9. An actor who is not authenticated must be able to:
	 * Browse the list of providers and navigate to their items.
	 * 
	 * b) Broken bussines rule:
	 * casoNegativo : Se le pasa un id de una provider que no existe
	 * 
	 * c) Sentence coverage:Compruebo el findOne de Provider y de item , cubriendo el 100% de sus lineas de código.
	 * 
	 * d) Data coverage:-
	 */
	@Test
	public void listProviderAndItems() {
		final Object testingData[][] = {
			{
				super.getEntityId("provider1"), null
			}, {
				1000, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.templateList((Integer) testingData[i][0], (Class<?>) testingData[i][1]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}
	// Ancillary methods ------------------------------------------------------

	protected void templateList(final Integer providerId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			final Provider provider = this.providerService.findOneAnonymous(providerId);
			Assert.notNull(provider);
			final Collection<Item> items = this.itemService.itemsByProviderId(provider.getUserAccount().getId());
			Assert.notNull(items);

			super.flushTransaction();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

}
