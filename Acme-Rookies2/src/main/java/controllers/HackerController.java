
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CreditCardService;
import services.CustomizableSystemService;
import services.HackerService;
import domain.CreditCard;
import domain.Rookie;
import forms.RegistrationFormHacker;

@Controller
@RequestMapping("/rookie")
public class HackerController extends AbstractController {

	@Autowired
	private HackerService				hackerService;
	@Autowired
	private CreditCardService			creditCardService;
	@Autowired
	private CustomizableSystemService	customizableService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createForm() {
		ModelAndView result;
		RegistrationFormHacker registrationForm = new RegistrationFormHacker();

		registrationForm = registrationForm.createToHacker();

		final String telephoneCode = this.customizableService.getTelephoneCode();
		registrationForm.setPhone(telephoneCode + " ");

		result = new ModelAndView("rookie/create");
		result.addObject("registrationForm", registrationForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("registrationForm") final RegistrationFormHacker registrationForm, final BindingResult binding) {
		ModelAndView result;
		Rookie rookie = null;
		CreditCard creditcard = null;
		CreditCard creditCardSave = null;

		try {
			creditcard = this.creditCardService.reconstruct(registrationForm, binding);
			registrationForm.setCreditCard(creditcard);
			rookie = this.hackerService.reconstruct(registrationForm, binding);
			if (!binding.hasErrors() && registrationForm.getUserAccount().getPassword().equals(registrationForm.getPassword())) {
				creditCardSave = this.creditCardService.save(creditcard);
				rookie.setCreditCard(creditCardSave);
				this.hackerService.save(rookie);
				result = new ModelAndView("redirect:/");
			} else {

				result = new ModelAndView("rookie/create");
				result.addObject("registrationForm", registrationForm);
			}
		} catch (final Exception e) {
			final Collection<String> creditCardsNumbers = this.creditCardService.getAllNumbers();
			if (creditcard != null)
				if (creditCardsNumbers.contains(creditcard.getNumber()) && creditCardSave.equals((this.creditCardService.getCreditCardByNumber(creditcard.getNumber()))))
					this.creditCardService.delete(creditCardSave);
			result = new ModelAndView("rookie/create");
			result.addObject("exception", e);
			result.addObject("registrationForm", registrationForm);

		}

		return result;
	}
}
