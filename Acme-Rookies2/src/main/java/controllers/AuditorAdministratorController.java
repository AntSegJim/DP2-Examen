/*
 * AdministratorController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AuditorService;
import services.CreditCardService;
import services.CustomizableSystemService;
import domain.Auditor;
import domain.CreditCard;
import forms.RegistrationFormAuditor;

@Controller
@RequestMapping("/auditor/administrator/")
public class AuditorAdministratorController extends AbstractController {

	@Autowired
	private AuditorService				auditorService;

	@Autowired
	private CreditCardService			creditCardService;

	@Autowired
	private CustomizableSystemService	customizableService;


	// Constructors -----------------------------------------------------------

	public AuditorAdministratorController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createForm() {
		ModelAndView result;
		RegistrationFormAuditor registrationForm = new RegistrationFormAuditor();

		registrationForm = registrationForm.createToAuditor();

		final String telephoneCode = this.customizableService.getTelephoneCode();
		registrationForm.setPhone(telephoneCode + " ");

		result = new ModelAndView("auditor/create");
		result.addObject("registrationForm", registrationForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("registrationForm") final RegistrationFormAuditor registrationForm, final BindingResult binding) {
		ModelAndView result;
		Auditor auditor = null;
		CreditCard creditcard = null;
		CreditCard creditCardSave = null;

		try {
			creditcard = this.creditCardService.reconstruct(registrationForm, binding);
			registrationForm.setCreditCard(creditcard);
			auditor = this.auditorService.reconstruct(registrationForm, binding);
			if (!binding.hasErrors() && registrationForm.getUserAccount().getPassword().equals(registrationForm.getPassword())) {
				creditCardSave = this.creditCardService.save(creditcard);
				auditor.setCreditCard(creditCardSave);
				this.auditorService.save(auditor);
				result = new ModelAndView("redirect:/");
			} else {

				result = new ModelAndView("auditor/create");
				result.addObject("registrationForm", registrationForm);
			}
		} catch (final Exception e) {
			final Collection<String> creditCardsNumbers = this.creditCardService.getAllNumbers();
			if (creditcard != null)
				if (creditCardsNumbers.contains(creditcard.getNumber()) && creditCardSave.equals((this.creditCardService.getCreditCardByNumber(creditcard.getNumber()))))
					this.creditCardService.delete(creditCardSave);
			result = new ModelAndView("auditor/create");
			result.addObject("exception", e);
			result.addObject("registrationForm", registrationForm);

		}

		return result;
	}

}
