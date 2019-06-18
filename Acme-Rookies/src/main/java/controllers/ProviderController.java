
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

import services.CreditCardService;
import services.CustomizableSystemService;
import services.ProviderService;
import domain.CreditCard;
import domain.Provider;
import forms.RegistrationFormProviderAndCreditCard;

@Controller
@RequestMapping("/provider")
public class ProviderController extends AbstractController {

	@Autowired
	private ProviderService				providerService;
	@Autowired
	private CreditCardService			creditCardService;
	@Autowired
	private CustomizableSystemService	customizableService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView provider() {
		final ModelAndView result;
		final Collection<Provider> providers;

		providers = this.providerService.findAll();
		Assert.notNull(providers);

		result = new ModelAndView("provider/list");
		result.addObject("providers", providers);
		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createForm() {
		ModelAndView result;
		RegistrationFormProviderAndCreditCard registrationForm = new RegistrationFormProviderAndCreditCard();

		registrationForm = registrationForm.createToProviderAndCreditCard();

		final String telephoneCode = this.customizableService.getTelephoneCode();
		registrationForm.setPhone(telephoneCode + " ");

		result = new ModelAndView("provider/create");
		result.addObject("registrationForm", registrationForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("registrationForm") final RegistrationFormProviderAndCreditCard registrationForm, final BindingResult binding) {
		ModelAndView result;
		Provider provider = null;
		CreditCard creditcard = null;
		CreditCard creditCardSave = null;

		try {
			creditcard = this.creditCardService.reconstruct(registrationForm, binding);
			registrationForm.setCreditCard(creditcard);
			provider = this.providerService.reconstruct(registrationForm, binding);

			if (!binding.hasErrors() && registrationForm.getUserAccount().getPassword().equals(registrationForm.getPassword())) {
				creditCardSave = this.creditCardService.save(creditcard);
				provider.setCreditCard(creditCardSave);
				this.providerService.save(provider);
				result = new ModelAndView("redirect:/");
			} else {

				result = new ModelAndView("provider/create");
				result.addObject("registrationForm", registrationForm);
			}
		} catch (final Exception e) {
			final Collection<String> creditCardsNumbers = this.creditCardService.getAllNumbers();
			if (creditcard != null)
				if (creditCardsNumbers.contains(creditcard.getNumber()) && creditCardSave.equals((this.creditCardService.getCreditCardByNumber(creditcard.getNumber()))))
					this.creditCardService.delete(creditCardSave);
			result = new ModelAndView("provider/create");
			result.addObject("exception", e);
			result.addObject("registrationForm", registrationForm);

		}

		return result;
	}
}
