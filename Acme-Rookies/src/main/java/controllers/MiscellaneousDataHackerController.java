
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
import services.MiscellaneousDataService;
import domain.Curricula;
import domain.MiscellaneousData;
import domain.Rookie;

@Controller
@RequestMapping("/miscellaneousData/rookie")
public class MiscellaneousDataHackerController extends AbstractController {

	@Autowired
	private CurriculaService			curriculaService;
	@Autowired
	private MiscellaneousDataService	miscellaneousDataService;
	@Autowired
	private ActorService				actorS;
	@Autowired
	private HackerService				hackerService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int curriculaId) {
		ModelAndView result;
		try {
			final Curricula curricula = this.curriculaService.findOne(curriculaId);
			final Collection<MiscellaneousData> miscellaneousData = curricula.getMiscellaneousData();
			result = new ModelAndView("miscellaneousData/list");
			result.addObject("miscellaneousesData", miscellaneousData);
			result.addObject("curricula", curricula);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../curricula/rookie/list.do");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createMiscellaneousData(@RequestParam final Integer curriculaId) {

		ModelAndView result;
		try {
			final MiscellaneousData miscellaneousData;
			final Curricula curricula = this.curriculaService.findOne(curriculaId);
			Assert.notNull(curricula);
			miscellaneousData = this.miscellaneousDataService.create();

			result = new ModelAndView("miscellaneousData/edit");
			result.addObject("miscellaneousData", miscellaneousData);
			result.addObject("curricula", curricula);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editMiscellaneousData(@RequestParam final Integer curriculaId, @RequestParam final Integer miscellaneousDataId) {
		ModelAndView result;
		try {
			MiscellaneousData miscellaneousData;
			Curricula curricula;
			miscellaneousData = this.miscellaneousDataService.findOne(miscellaneousDataId);
			curricula = this.curriculaService.findOne(curriculaId);
			Assert.notNull(curricula);
			Assert.notNull(miscellaneousData);
			result = new ModelAndView("miscellaneousData/edit");
			result.addObject("miscellaneousData", miscellaneousData);
			result.addObject("curricula", curricula);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveMiscellaneousData(@Valid final MiscellaneousData miscellaneousData, final BindingResult binding, @RequestParam final Integer curriculaId) {
		ModelAndView result;
		try {
			if (!binding.hasErrors()) {
				final MiscellaneousData miscellaneousDataSave = this.miscellaneousDataService.save(miscellaneousData);
				final Curricula curricula = this.curriculaService.findOne(curriculaId);
				Assert.notNull(curricula);
				if (curricula.getMiscellaneousData().contains(miscellaneousDataSave)) {
					curricula.getMiscellaneousData().remove(miscellaneousDataSave);
					curricula.getMiscellaneousData().add(miscellaneousDataSave);
				} else
					curricula.getMiscellaneousData().add(miscellaneousDataSave);
				this.curriculaService.save(curricula);
				result = new ModelAndView("redirect:list.do?curriculaId=" + curriculaId);
			} else {
				final Curricula curricula = this.curriculaService.findOne(curriculaId);
				Assert.notNull(curricula);
				result = new ModelAndView("miscellaneousData/edit");
				result.addObject("miscellaneousData", miscellaneousData);
				result.addObject("curricula", curricula);
			}
		} catch (final Exception e) {
			final UserAccount user = this.actorS.getActorLogged().getUserAccount();
			final Rookie h = this.hackerService.hackerUserAccount(user.getId());
			final Curricula curricula = this.curriculaService.findOne(curriculaId);
			if (h.equals(curricula.getRookie())) {
				result = new ModelAndView("miscellaneousData/edit");
				result.addObject("miscellaneousData", miscellaneousData);
				result.addObject("curricula", curricula);
			} else
				result = new ModelAndView("redirect:../../");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView deleteMiscellaneousData(final MiscellaneousData miscellaneousData, final BindingResult binding, @RequestParam final Integer curriculaId) {
		ModelAndView result;

		try {
			if (!binding.hasErrors()) {
				final Curricula curricula = this.curriculaService.findOne(curriculaId);
				Assert.notNull(curricula);
				final UserAccount user = LoginService.getPrincipal();
				final Rookie h = this.hackerService.hackerUserAccount(user.getId());
				Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("ROOKIE"));
				Assert.isTrue(h.equals(curricula.getRookie()));
				this.miscellaneousDataService.delete(miscellaneousData, curriculaId);
				result = new ModelAndView("redirect:list.do?curriculaId=" + curriculaId);
			} else {
				final Curricula curricula = this.curriculaService.findOne(curriculaId);
				Assert.notNull(curricula);
				result = new ModelAndView("miscellaneousData/edit");
				result.addObject("miscellaneousData", miscellaneousData);
				result.addObject("curricula", curricula);
			}
		} catch (final Exception e) {
			final UserAccount user = LoginService.getPrincipal();
			final Rookie h = this.hackerService.hackerUserAccount(user.getId());
			final Curricula curricula = this.curriculaService.findOne(curriculaId);
			if (h.equals(curricula.getRookie())) {
				result = new ModelAndView("miscellaneousData/edit");
				result.addObject("miscellaneousData", miscellaneousData);
				result.addObject("curricula", curricula);
			} else
				result = new ModelAndView("redirect:../../");
		}
		return result;
	}

}
