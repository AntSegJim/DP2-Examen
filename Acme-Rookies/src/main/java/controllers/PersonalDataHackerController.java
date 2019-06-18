
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculaService;
import services.CustomizableSystemService;
import services.PersonalDataService;
import domain.Curricula;
import domain.PersonalData;
import forms.PersonalDataForm;

@Controller
@RequestMapping("/personalData/rookie")
public class PersonalDataHackerController extends AbstractController {

	@Autowired
	private CurriculaService			curriculaService;
	@Autowired
	private PersonalDataService			personalData;
	@Autowired
	private CustomizableSystemService	customizableService;


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int curriculaId) {
		ModelAndView result;
		try {
			final Curricula curricula = this.curriculaService.findOne(curriculaId);
			//final PersonalData personalData = this.personalData.findOne(curricula.getPersonalData().getId());
			final PersonalData personalData = curricula.getPersonalData();
			result = new ModelAndView("personalData/show");
			result.addObject("personalData", personalData);
			//result.addObject("curriculaId", curriculaId);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../curricula/rookie/list.do");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;

		PersonalDataForm registrationForm = new PersonalDataForm();
		registrationForm = registrationForm.createPersonalData();

		final String telephoneCode = this.customizableService.getTelephoneCode();
		registrationForm.setPhoneNumber(telephoneCode + " ");

		result = new ModelAndView("personalData/edit");
		result.addObject("registrationForm", registrationForm);
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int personalDataId) {
		ModelAndView result;
		final PersonalDataForm registrationForm = new PersonalDataForm();
		try {
			final PersonalData personalData = this.personalData.findOne(personalDataId);
			registrationForm.setId(personalData.getId());
			registrationForm.setVersion(personalData.getVersion());
			registrationForm.setFullName(personalData.getFullName());
			registrationForm.setGithubProfile(personalData.getGithubProfile());
			registrationForm.setLinkedlnProfile(personalData.getLinkedlnProfile());
			registrationForm.setPhoneNumber(personalData.getPhoneNumber());
			registrationForm.setStatement(personalData.getStatement());
			registrationForm.setPatternPhone(false);

			result = new ModelAndView("personalData/edit");
			result.addObject("registrationForm", registrationForm);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../curricula/rookie/list.do");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("registrationForm") final PersonalDataForm registrationForm, final BindingResult binding) {
		ModelAndView result;
		PersonalData personalData = null;
		try {
			personalData = this.personalData.reconstruct(registrationForm, binding);
			if (binding.hasErrors()) {
				result = new ModelAndView("personalData/edit");
				result.addObject("registrationForm", registrationForm);
			} else {
				this.personalData.save(personalData);
				result = new ModelAndView("redirect:../../curricula/rookie/list.do");
			}
		} catch (final Exception e) {
			result = new ModelAndView("personalData/edit");
			result.addObject("registrationForm", registrationForm);
			result.addObject("exception", "e");
		}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int personalDataId) {
		ModelAndView result;
		try {
			final PersonalData personalData = this.personalData.findOne(personalDataId);
			this.personalData.delete(personalData);
			result = new ModelAndView("redirect:../../curricula/rookie/list.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../curricula/rookie/list.do");
			result.addObject("exception", e);
		}
		return result;
	}

}
