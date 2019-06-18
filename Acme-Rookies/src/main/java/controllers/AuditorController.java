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

import security.LoginService;
import services.AuditorService;
import services.CreditCardService;
import services.CustomizableSystemService;
import services.PositionService;
import domain.Auditor;
import domain.CreditCard;
import domain.Position;
import forms.RegistrationFormAuditor;

@Controller
@RequestMapping("/auditor")
public class AuditorController extends AbstractController {

	@Autowired
	private AuditorService				auditorService;

	@Autowired
	private CreditCardService			creditCardService;

	@Autowired
	private CustomizableSystemService	customizableService;

	@Autowired
	private PositionService				positionService;


	// Constructors -----------------------------------------------------------

	public AuditorController() {
		super();
	}
	@RequestMapping(value = "/assing-position", method = RequestMethod.GET)
	public ModelAndView editAuditor() {
		ModelAndView result;
		final RegistrationFormAuditor registrationForm = new RegistrationFormAuditor();
		Auditor auditor;
		final CreditCard creditCard;
		Collection<Position> allPositions;
		Collection<Position> positionAssing;
		Collection<Position> positionsMe;

		try {
			allPositions = this.positionService.findAll();
			positionAssing = this.positionService.getAllPositionAssing();
			final boolean eliminar = allPositions.removeAll(positionAssing);

			auditor = this.auditorService.findOne(this.auditorService.auditorUserAccount(LoginService.getPrincipal().getId()).getId());
			positionsMe = auditor.getPositions();
			final boolean añadir = allPositions.addAll(positionsMe);

			result = new ModelAndView("auditor/assingPositions");
			result.addObject("auditor", auditor);
			result.addObject("positions", allPositions);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}
		return result;

	}

	@RequestMapping(value = "/assing-position", method = RequestMethod.POST, params = "save")
	public ModelAndView editAuditor(@ModelAttribute("auditor") final Auditor a, final BindingResult binding) {
		ModelAndView result;
		Auditor auditor = null;

		Collection<Position> allPositions;
		Collection<Position> positionAssing;
		Collection<Position> positionsMe;

		allPositions = this.positionService.findAll();
		positionAssing = this.positionService.getAllPositionAssing();
		final boolean eliminar = allPositions.removeAll(positionAssing);

		auditor = this.auditorService.findOne(this.auditorService.auditorUserAccount(LoginService.getPrincipal().getId()).getId());
		positionsMe = auditor.getPositions();
		final boolean añadir = allPositions.addAll(positionsMe);
		try {
			final Auditor auditor2 = this.auditorService.reconstruct(a, binding);
			if (!binding.hasErrors()) {
				this.auditorService.save(auditor2);
				result = new ModelAndView("redirect:../");

			} else {
				result = new ModelAndView("auditor/assingPositions");
				result.addObject("auditor", a);
				result.addObject("positions", allPositions);
			}

		} catch (final Exception e) {

			result = new ModelAndView("auditor/assingPositions");

			result.addObject("exception", e);
			result.addObject("auditor", a);
			result.addObject("positions", allPositions);

		}
		return result;

	}

}
