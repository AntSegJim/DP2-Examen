
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CompanyService;
import services.CreditCardService;
import services.CustomizableSystemService;
import domain.Company;
import domain.CreditCard;
import forms.RegistrationFormCompanyAndCreditCard;

@Controller
@RequestMapping("/company")
public class CompanyController extends AbstractController {

	@Autowired
	private CompanyService				companyService;
	@Autowired
	private CreditCardService			creditCardService;
	@Autowired
	private CustomizableSystemService	customizableService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView company() {
		final ModelAndView result;
		final Collection<Company> companies;

		companies = this.companyService.findAll();
		Assert.notNull(companies);

		result = new ModelAndView("company/list");
		result.addObject("companies", companies);
		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createForm() {
		ModelAndView result;
		RegistrationFormCompanyAndCreditCard registrationForm = new RegistrationFormCompanyAndCreditCard();

		registrationForm = registrationForm.createToCompanyAndCreditCard();

		final String telephoneCode = this.customizableService.getTelephoneCode();
		registrationForm.setPhone(telephoneCode + " ");

		result = new ModelAndView("company/create");
		result.addObject("registrationForm", registrationForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("registrationForm") final RegistrationFormCompanyAndCreditCard registrationForm, final BindingResult binding) {
		ModelAndView result;
		Company company = null;
		CreditCard creditcard = null;
		CreditCard creditCardSave = null;

		try {
			creditcard = this.creditCardService.reconstruct(registrationForm, binding);
			registrationForm.setCreditCard(creditcard);
			company = this.companyService.reconstruct(registrationForm, binding);

			if (!binding.hasErrors() && registrationForm.getUserAccount().getPassword().equals(registrationForm.getPassword())) {
				creditCardSave = this.creditCardService.save(creditcard);
				company.setCreditCard(creditCardSave);
				this.companyService.save(company);
				result = new ModelAndView("redirect:/");
			} else {

				result = new ModelAndView("company/create");
				result.addObject("registrationForm", registrationForm);
			}
		} catch (final Exception e) {
			final Collection<String> creditCardsNumbers = this.creditCardService.getAllNumbers();
			if (creditcard != null)
				if (creditCardsNumbers.contains(creditcard.getNumber()) && creditCardSave.equals((this.creditCardService.getCreditCardByNumber(creditcard.getNumber()))))
					this.creditCardService.delete(creditCardSave);
			result = new ModelAndView("company/create");
			result.addObject("exception", e);
			result.addObject("registrationForm", registrationForm);

		}

		return result;
	}
}
