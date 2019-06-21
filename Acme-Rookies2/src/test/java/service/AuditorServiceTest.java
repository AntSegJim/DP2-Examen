
package service;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindingResult;

import services.AdministratorService;
import services.AuditorService;
import services.CreditCardService;
import utilities.AbstractTest;
import domain.Auditor;
import domain.CreditCard;
import forms.RegistrationFormAuditor;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AuditorServiceTest extends AbstractTest {

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private AuditorService			auditorService;

	@Autowired
	private CreditCardService		creditCardService;


	/*
	 * a) Requeriment: An actor who is authenticated as an administrator
	 * must be able to create user acconunts for new auditors.
	 * 
	 * b) Broken bussines rule:
	 * Se intenta crear un nuevo auditor sin email
	 * 
	 * c) Sentence coverage:Este caso de uso engloba el recontructor y el save tando del AuditorService como de CreditCardService,
	 * el total de lineas sumando estos metodos es de 147, de las cuales este test recorrer 80 , es decir un 54'42%.
	 * 
	 * d) Data coverage: 7.69% (1 atributo incorrecto/13 atributos)
	 */

	@Test
	public void CreateAuditorService() {
		final Object testingData[][] = {
			{//Positive test
				"Nuevo Nombre", "Apellido", "ES12345678X", "prueba@email.com", "NuevoUsername", "NuevaPassWord", "NuevaPassWord", "NuevoBrandName", "NuevoholderName", "5182901911816096", 8, 2020, 876, null
			}, {//Negative test: email vacio
				"Nuevo Nombre", "Apellido", "ES12345678X", "", "NuevoUsername", "NuevaPassWord", "NuevaPassWord", "NuevoBrandName", "NuevoholderName", "5182901911816096", 8, 2020, 876, NullPointerException.class

			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.CreateAuditorTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (String) testingData[i][9], (int) testingData[i][10], (int) testingData[i][11], (int) testingData[i][12], (Class<?>) testingData[i][13]);
	}
	protected void CreateAuditorTemplate(final String name, final String surnames, final String vatNumber, final String email, final String username, final String password, final String confirmPassword, final String brandName, final String holderName,
		final String number, final int expirationMonth, final int expirationYear, final int cW, final Class<?> expected) {
		Class<?> caught;
		Auditor auditor = null;
		CreditCard creditcard = null;
		caught = null;
		try {
			super.authenticate("admin");
			RegistrationFormAuditor registrationForm = new RegistrationFormAuditor();

			registrationForm = registrationForm.createToAuditor();

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

			final BindingResult binding = null;

			creditcard = this.creditCardService.reconstruct(registrationForm, binding);
			registrationForm.setCreditCard(creditcard);
			auditor = this.auditorService.reconstruct(registrationForm, binding);
			final CreditCard creditCardSave = this.creditCardService.save(creditcard);
			auditor.setCreditCard(creditCardSave);
			this.auditorService.save(auditor);

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
	 * Un auditor intenta editar la informacion de otro.
	 * 
	 * c) Sentence coverage:Este caso de uso engloba el recontructor y el save tando del AuditorService como del CreditCardService, y al findOne de AuditorService,
	 * el total de lineas sumando estos metodos es de 153, de las cuales este test recorrer 101 , es decir un 66'01%.
	 * 
	 * d) Data coverage:7.69% (1 atributo incorrecto/13 atributos)
	 */

	@Test
	public void EditAuditorService() {
		final Object testingData[][] = {
			{
				//Positive test
				"Nuevo Nombre", "Apellido", "ES12345678X", "prueba@email.com", "NuevoUsername", "NuevaPassWord", "NuevaPassWord", "NuevoBrandName", "NuevoholderName", "5182901911816096", 8, 2020, 876, super.getEntityId("auditor1"), null
			},
			{
				//Negative test: UN auditor intenta modificar los datos de otro
				"Nuevo Nombre", "Apellido", "ES12345678X", "prueba@email.com", "NuevoUsername", "NuevaPassWord", "NuevaPassWord", "NuevoBrandName", "NuevoholderName", "5182901911816096", 8, 2020, 876, super.getEntityId("auditor2"),
				IllegalArgumentException.class

			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.EditAuditorTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (String) testingData[i][9], (int) testingData[i][10], (int) testingData[i][11], (int) testingData[i][12], (int) testingData[i][13], (Class<?>) testingData[i][14]);
	}
	protected void EditAuditorTemplate(final String name, final String surnames, final String vatNumber, final String email, final String username, final String password, final String confirmPassword, final String brandName, final String holderName,
		final String number, final int expirationMonth, final int expirationYear, final int cW, final int auditorId, final Class<?> expected) {
		Class<?> caught;
		Auditor auditor = null;
		CreditCard creditcard = null;
		caught = null;
		try {
			super.authenticate("auditor");
			RegistrationFormAuditor registrationForm = new RegistrationFormAuditor();

			registrationForm = registrationForm.createToAuditor();

			auditor = this.auditorService.findOne(auditorId);
			registrationForm.setId(auditor.getId());
			registrationForm.setVersion(auditor.getVersion());
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
			registrationForm.getUserAccount().setUsername(auditor.getUserAccount().getUsername());
			registrationForm.getUserAccount().setPassword(password);
			registrationForm.setPassword(confirmPassword);
			registrationForm.setPhone("");
			registrationForm.setAddress("");
			registrationForm.setPhoto("");

			final BindingResult binding = null;

			creditcard = this.creditCardService.reconstruct(registrationForm, binding);
			registrationForm.setCreditCard(creditcard);
			auditor = this.auditorService.reconstruct(registrationForm, binding);
			final CreditCard creditCardSave = this.creditCardService.save(creditcard);
			auditor.setCreditCard(creditCardSave);
			this.auditorService.save(auditor);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
