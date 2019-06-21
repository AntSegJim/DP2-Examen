
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
import services.HackerService;
import services.PositionDataService;
import domain.Curricula;
import domain.PositionData;
import domain.Rookie;

@Controller
@RequestMapping("/positionData/rookie")
public class PositionDataHackerController extends AbstractController {

	@Autowired
	private CurriculaService	curriculaService;
	@Autowired
	private PositionDataService	positionDataService;
	private ActorService		actorS;
	@Autowired
	private HackerService		hackerService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int curriculaId) {
		ModelAndView result;
		try {
			final Curricula curricula = this.curriculaService.findOne(curriculaId);
			final Collection<PositionData> positionData = curricula.getPositionData();
			result = new ModelAndView("positionData/list");
			result.addObject("positionsData", positionData);
			result.addObject("curricula", curricula);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../curricula/rookie/list.do");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createPositionData(@RequestParam final Integer curriculaId) {

		ModelAndView result;
		try {
			final PositionData positionData;
			final Curricula curricula = this.curriculaService.findOne(curriculaId);
			Assert.notNull(curricula);
			positionData = this.positionDataService.create();

			result = new ModelAndView("positionData/edit");
			result.addObject("positionData", positionData);
			result.addObject("curricula", curricula);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editPositionData(@RequestParam final Integer curriculaId, @RequestParam final Integer positionDataId) {
		ModelAndView result;
		try {
			final PositionData positionData;
			Curricula curricula;
			positionData = this.positionDataService.findOne(positionDataId);
			curricula = this.curriculaService.findOne(curriculaId);
			Assert.notNull(curricula);
			Assert.notNull(positionData);
			result = new ModelAndView("positionData/edit");
			result.addObject("positionData", positionData);
			result.addObject("curricula", curricula);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView savePositionData(@Valid final PositionData positionData, final BindingResult binding, @RequestParam final Integer curriculaId) {
		ModelAndView result;
		try {
			if (!binding.hasErrors()) {
				final PositionData positionDataSave = this.positionDataService.save(positionData);
				final Curricula curricula = this.curriculaService.findOne(curriculaId);
				Assert.notNull(curricula);
				if (curricula.getPositionData().contains(positionDataSave)) {
					curricula.getPositionData().remove(positionDataSave);
					curricula.getPositionData().add(positionDataSave);
				} else
					curricula.getPositionData().add(positionDataSave);
				this.curriculaService.save(curricula);
				result = new ModelAndView("redirect:list.do?curriculaId=" + curriculaId);
			} else {
				final Curricula curricula = this.curriculaService.findOne(curriculaId);
				Assert.notNull(curricula);
				result = new ModelAndView("positionData/edit");
				result.addObject("positionData", positionData);
				result.addObject("curricula", curricula);
			}
		} catch (final Exception e) {
			final UserAccount user = LoginService.getPrincipal();
			final Rookie h = this.hackerService.hackerUserAccount(user.getId());
			final Curricula curricula = this.curriculaService.findOne(curriculaId);
			if (h.equals(curricula.getRookie())) {
				result = new ModelAndView("positionData/edit");
				result.addObject("positionData", positionData);
				result.addObject("curricula", curricula);
			} else
				result = new ModelAndView("redirect:../../");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView deletePositionData(final PositionData positionData, final BindingResult binding, @RequestParam final Integer curriculaId) {
		ModelAndView result;

		try {
			if (!binding.hasErrors()) {
				final Curricula curricula = this.curriculaService.findOne(curriculaId);
				Assert.notNull(curricula);
				final UserAccount user = LoginService.getPrincipal();
				final Rookie h = this.hackerService.hackerUserAccount(user.getId());
				Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("ROOKIE"));
				Assert.isTrue(h.equals(curricula.getRookie()));
				this.positionDataService.delete(positionData, curriculaId);
				result = new ModelAndView("redirect:list.do?curriculaId=" + curriculaId);
			} else {
				final Curricula curricula = this.curriculaService.findOne(curriculaId);
				Assert.notNull(curricula);
				result = new ModelAndView("positionData/edit");
				result.addObject("positionData", positionData);
				result.addObject("curricula", curricula);
			}
		} catch (final Exception e) {
			final UserAccount user = LoginService.getPrincipal();
			final Rookie h = this.hackerService.hackerUserAccount(user.getId());
			final Curricula curricula = this.curriculaService.findOne(curriculaId);
			if (h.equals(curricula.getRookie())) {
				result = new ModelAndView("positionData/edit");
				result.addObject("positionData", positionData);
				result.addObject("curricula", curricula);
			} else
				result = new ModelAndView("redirect:../../");
		}
		return result;
	}
}
