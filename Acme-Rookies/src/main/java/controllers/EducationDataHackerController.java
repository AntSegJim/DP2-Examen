
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.CurriculaService;
import services.EducationDataService;
import services.HackerService;
import domain.Curricula;
import domain.EducationData;
import domain.Rookie;

@Controller
@RequestMapping("/educationData/rookie")
public class EducationDataHackerController extends AbstractController {

	@Autowired
	private CurriculaService		curriculaService;
	@Autowired
	private EducationDataService	educationDataService;
	@Autowired
	private ActorService			actorS;
	@Autowired
	private HackerService			hackerService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int curriculaId) {
		ModelAndView result;
		try {
			final Curricula curricula = this.curriculaService.findOne(curriculaId);
			final Collection<EducationData> educationData = curricula.getEducationData();
			result = new ModelAndView("educationData/list");
			result.addObject("educationsData", educationData);
			result.addObject("curricula", curricula);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../curricula/rookie/list.do");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createEducationData(@RequestParam final Integer curriculaId) {

		ModelAndView result;
		try {
			final EducationData educationData;
			final Curricula curricula = this.curriculaService.findOne(curriculaId);
			Assert.notNull(curricula);
			educationData = this.educationDataService.create();

			result = new ModelAndView("educationData/edit");
			result.addObject("educationData", educationData);
			result.addObject("curricula", curricula);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editEducationData(@RequestParam final Integer curriculaId, @RequestParam final Integer educationDataId) {
		ModelAndView result;
		try {
			final EducationData educationData;
			Curricula curricula;
			educationData = this.educationDataService.findOne(educationDataId);
			curricula = this.curriculaService.findOne(curriculaId);
			Assert.notNull(curricula);
			Assert.notNull(educationData);
			result = new ModelAndView("educationData/edit");
			result.addObject("educationData", educationData);
			result.addObject("curricula", curricula);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveMiscellaneousData(@Valid final EducationData educationData, final BindingResult binding, @RequestParam final Integer curriculaId) {
		ModelAndView result;
		try {
			if (!binding.hasErrors()) {
				final EducationData educationDataSave = this.educationDataService.save(educationData);
				final Curricula curricula = this.curriculaService.findOne(curriculaId);
				Assert.notNull(curricula);
				if (curricula.getEducationData().contains(educationDataSave)) {
					curricula.getEducationData().remove(educationDataSave);
					curricula.getEducationData().add(educationDataSave);
				} else
					curricula.getEducationData().add(educationDataSave);
				this.curriculaService.save(curricula);
				result = new ModelAndView("redirect:list.do?curriculaId=" + curriculaId);
			} else {
				final Curricula curricula = this.curriculaService.findOne(curriculaId);
				Assert.notNull(curricula);
				result = new ModelAndView("educationData/edit");
				result.addObject("educationData", educationData);
				result.addObject("curricula", curricula);
			}
		} catch (final Exception e) {
			final UserAccount user = this.actorS.getActorLogged().getUserAccount();
			final Rookie h = this.hackerService.hackerUserAccount(user.getId());
			final Curricula curricula = this.curriculaService.findOne(curriculaId);
			if (h.equals(curricula.getRookie())) {
				result = new ModelAndView("educationData/edit");
				result.addObject("educationData", educationData);
				result.addObject("curricula", curricula);
			} else
				result = new ModelAndView("redirect:../../");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView deleteMiscellaneousData(final EducationData educationData, final BindingResult binding, @RequestParam final Integer curriculaId) {
		ModelAndView result;

		try {
			if (!binding.hasErrors()) {
				final Curricula curricula = this.curriculaService.findOne(curriculaId);
				Assert.notNull(curricula);
				final UserAccount user = LoginService.getPrincipal();
				final Rookie h = this.hackerService.hackerUserAccount(user.getId());
				Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("ROOKIE"));
				Assert.isTrue(h.equals(curricula.getRookie()));
				this.educationDataService.delete(educationData, curriculaId);
				result = new ModelAndView("redirect:list.do?curriculaId=" + curriculaId);
			} else {
				final Curricula curricula = this.curriculaService.findOne(curriculaId);
				Assert.notNull(curricula);
				result = new ModelAndView("educationData/edit");
				result.addObject("educationData", educationData);
				result.addObject("curricula", curricula);
			}
		} catch (final Exception e) {
			final UserAccount user = LoginService.getPrincipal();
			final Rookie h = this.hackerService.hackerUserAccount(user.getId());
			final Curricula curricula = this.curriculaService.findOne(curriculaId);
			if (h.equals(curricula.getRookie())) {
				result = new ModelAndView("educationData/edit");
				result.addObject("educationData", educationData);
				result.addObject("curricula", curricula);
			} else
				result = new ModelAndView("redirect:../../");
		}
		return result;
	}

}
