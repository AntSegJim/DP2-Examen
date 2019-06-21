
package service;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindingResult;

import services.CompanyService;
import services.CreditCardService;
import utilities.AbstractTest;
import domain.Company;
import domain.CreditCard;
import forms.RegistrationFormCompanyAndCreditCard;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CompanyServiceTest extends AbstractTest {

	@Autowired
	private CompanyService		companyService;

	@Autowired
	private CreditCardService	creditCardService;


	/*
	 * a) Requeriment: An actor who is not authenticated must be able to
	 * register to the system as company.
	 * 
	 * b) Broken bussines rule:
	 * Se intenta registratar un usuario como company con el nameCompany vacio
	 * 
	 * c) Sentence coverage:Este caso de uso engloba el recontructor y el save tando del CompanyService como de CreditCardService,
	 * el total de lineas sumando estos metodos es de 128, de las cuales este test recorrer 75 , es decir un 58'59%.
	 * 
	 * d) Data coverage: 7.14% (1 atributo incorrecto/14atributos)
	 */

	@Test
	public void CreateCompanyService() {
		final Object testingData[][] = {
			{//Positive test
				"Nuevo Nombre", "Apellido", "ES12345678X", "prueba@email.com", "NuevoUsername", "NuevaPassWord", "NuevaPassWord", "NuevoBrandName", "NuevoholderName", "5182901911816096", 8, 2020, 876, "NombreCompañia", null
			}, {//Negative test: nameCompany vacio
				"Nuevo Nombre", "Apellido", "ES12345678X", "prueba2@email.com", "NuevoUsername", "NuevaPassWord", "NuevaPassWord", "NuevoBrandName", "NuevoholderName", "5182901911816096", 8, 2020, 876, "", NullPointerException.class

			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.CreateCompanyTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (String) testingData[i][9], (int) testingData[i][10], (int) testingData[i][11], (int) testingData[i][12], (String) testingData[i][13], (Class<?>) testingData[i][14]);
	}
	protected void CreateCompanyTemplate(final String name, final String surnames, final String vatNumber, final String email, final String username, final String password, final String confirmPassword, final String brandName, final String holderName,
		final String number, final int expirationMonth, final int expirationYear, final int cW, final String nameCompany, final Class<?> expected) {
		Class<?> caught;
		Company company = null;
		CreditCard creditcard = null;
		caught = null;
		try {

			RegistrationFormCompanyAndCreditCard registrationForm = new RegistrationFormCompanyAndCreditCard();

			registrationForm = registrationForm.createToCompanyAndCreditCard();

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
			registrationForm.setNameCompany(nameCompany);
			registrationForm.setCheck(true);

			final BindingResult binding = null;

			creditcard = this.creditCardService.reconstruct(registrationForm, binding);
			registrationForm.setCreditCard(creditcard);
			company = this.companyService.reconstruct(registrationForm, binding);
			final CreditCard creditCardSave = this.creditCardService.save(creditcard);
			company.setCreditCard(creditCardSave);
			this.companyService.save(company);

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
	 * Una company intenta editar la informacion de otro.
	 * 
	 * c) Sentence coverage:Este caso de uso engloba el recontructor y el save tando del CompanyService como del CreditCardService, y al findOne de CompanyService,
	 * el total de lineas sumando estos metodos es de 134, de las cuales este test recorrer 81 , es decir un 60'45%.
	 * 
	 * d) Data coverage:
	 */

	@Test
	public void EditCompanyService() {
		final Object testingData[][] = {
			{
				//Positive test
				"Nuevo Nombre", "Apellido", "ES12345678X", "prueba@email.com", "NuevaPassWord", "NuevaPassWord", "NuevoBrandName", "NuevoholderName", "5182901911816096", 8, 2020, 876, super.getEntityId("company1"), "NombreCompañia", null
			},
			{
				//Negative test: Una company intenta modificar los datos de otra
				"Nuevo Nombre", "Apellido", "ES12345678X", "prueba@email.com", "NuevaPassWord", "NuevaPassWord", "NuevoBrandName", "NuevoholderName", "5182901911816096", 8, 2020, 876, super.getEntityId("company2"), "NombreCompañia",
				IllegalArgumentException.class

			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.EditCompanyTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (int) testingData[i][9], (int) testingData[i][10], (int) testingData[i][11], (int) testingData[i][12], (String) testingData[i][13], (Class<?>) testingData[i][14]);
	}
	protected void EditCompanyTemplate(final String name, final String surnames, final String vatNumber, final String email, final String password, final String confirmPassword, final String brandName, final String holderName, final String number,
		final int expirationMonth, final int expirationYear, final int cW, final int companyId, final String nameCompany, final Class<?> expected) {
		Class<?> caught;
		Company company = null;
		CreditCard creditcard = null;
		caught = null;
		try {
			super.authenticate("company");
			RegistrationFormCompanyAndCreditCard registrationForm = new RegistrationFormCompanyAndCreditCard();

			registrationForm = registrationForm.createToCompanyAndCreditCard();

			company = this.companyService.findOne(companyId);
			registrationForm.setId(company.getId());
			registrationForm.setVersion(company.getVersion());
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
			registrationForm.getUserAccount().setUsername(company.getUserAccount().getUsername());
			registrationForm.getUserAccount().setPassword(password);
			registrationForm.setPassword(confirmPassword);
			registrationForm.setPhone("");
			registrationForm.setAddress("");
			registrationForm.setPhoto("");
			registrationForm.setNameCompany(nameCompany);

			final BindingResult binding = null;

			creditcard = this.creditCardService.reconstruct(registrationForm, binding);
			registrationForm.setCreditCard(creditcard);
			company = this.companyService.reconstruct(registrationForm, binding);
			final CreditCard creditCardSave = this.creditCardService.save(creditcard);
			company.setCreditCard(creditCardSave);
			this.companyService.save(company);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
