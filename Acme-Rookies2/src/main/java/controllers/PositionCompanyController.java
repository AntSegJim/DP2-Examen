
package controllers;

import java.util.Collection;

import javax.validation.ValidationException;

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
import services.PositionService;
import domain.Actor;
import domain.Position;

@Controller
@RequestMapping("/position/company")
public class PositionCompanyController extends AbstractController {

	@Autowired
	private PositionService	positionService;

	@Autowired
	private ActorService	actorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Position> positions;

		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());

		positions = this.positionService.getPositionsByCompany(a.getId());
		Assert.notNull(positions);

		result = new ModelAndView("position/list");
		result.addObject("positions", positions);
		return result;

	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final Integer positionId) {
		ModelAndView result;
		try {
			final Position position;

			position = this.positionService.findOne(positionId);

			final UserAccount user = LoginService.getPrincipal();
			final Actor a = this.actorService.getActorByUserAccount(user.getId());
			Assert.isTrue(position.getCompany().equals(a));
			Assert.notNull(position);

			result = new ModelAndView("position/show");
			result.addObject("position", position);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}
		return result;

	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final Position position;

		position = this.positionService.create();

		result = new ModelAndView("position/edit");
		result.addObject("position", position);
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final Integer positionId) {
		ModelAndView result;

		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());
		try {
			final Position position;

			position = this.positionService.findOne(positionId);

			Assert.isTrue(position.getCompany().equals(a));
			Assert.isTrue(position.getDraftMode() == 1);

			result = new ModelAndView("position/edit");
			result.addObject("position", position);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(final Position p, final BindingResult binding) {
		ModelAndView result;
		Position position = null;

		try {

			position = this.positionService.reconstruct(p, binding);

			if (!binding.hasErrors()) {
				this.positionService.save(position);
				result = new ModelAndView("redirect:list.do");
			} else {
				result = new ModelAndView("position/edit");
				result.addObject("position", p);
			}
		} catch (final ValidationException opps) {
			result = new ModelAndView("position/edit");
			result.addObject("position", p);
		} catch (final Exception e) {
			position = this.positionService.reconstruct(p, binding);
			final UserAccount user = LoginService.getPrincipal();
			final Actor a = this.actorService.getActorByUserAccount(user.getId());

			if (position.getCompany().equals(a)) {
				if (position.getDraftMode() == 1) {

					result = new ModelAndView("position/edit");
					result.addObject("position", p);
					result.addObject("exception", e);
				} else
					result = new ModelAndView("redirect:list.do");

			} else
				result = new ModelAndView("redirect:../../");
		}

		return result;

	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Position p, final BindingResult binding) {
		ModelAndView result;
		final Position position;

		position = this.positionService.findOne(p.getId());

		try {

			if (!binding.hasErrors()) {
				this.positionService.delete(position);
				result = new ModelAndView("redirect:list.do");
			} else {
				result = new ModelAndView("position/edit");
				result.addObject("position", p);
			}

		} catch (final Exception e) {

			final UserAccount user = LoginService.getPrincipal();
			final Actor a = this.actorService.getActorByUserAccount(user.getId());

			if (position.getCompany().equals(a)) {
				if (position.getDraftMode() == 1) {

					result = new ModelAndView("position/edit");
					result.addObject("position", p);
					result.addObject("exception", e);
				} else
					result = new ModelAndView("redirect:list.do");

			} else
				result = new ModelAndView("redirect:../../");
		}

		return result;

	}

	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView cancel(@RequestParam final Integer positionId) {
		ModelAndView result;

		try {
			final Position position;

			position = this.positionService.findOne(positionId);

			this.positionService.cancel(position);
			result = new ModelAndView("redirect:list.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}

		return result;

	}
}
