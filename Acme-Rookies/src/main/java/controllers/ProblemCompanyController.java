
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

import repositories.PositionRepository;
import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.PositionService;
import services.ProblemService;
import domain.Actor;
import domain.Position;
import domain.Problem;

@Controller
@RequestMapping("/problem/company")
public class ProblemCompanyController extends AbstractController {

	@Autowired
	private PositionService		positionService;

	@Autowired
	private PositionRepository	positionRepository;

	@Autowired
	private ProblemService		problemService;

	@Autowired
	private ActorService		actorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final Integer positionId) {
		ModelAndView result;
		try {
			final Position position = this.positionService.findOne(positionId);
			final Collection<Problem> problems = position.getProblems();

			final UserAccount user = LoginService.getPrincipal();
			final Actor a = this.actorService.getActorByUserAccount(user.getId());

			Assert.isTrue(position.getCompany().equals(a));

			result = new ModelAndView("problem/list");
			result.addObject("problems", problems);
			result.addObject("position", position);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final Integer positionId, @RequestParam final Integer problemId) {
		ModelAndView result;

		try {
			final Problem problem;
			final Position position;
			position = this.positionService.findOne(positionId);

			final UserAccount user = LoginService.getPrincipal();
			final Actor a = this.actorService.getActorByUserAccount(user.getId());

			Assert.isTrue(position.getCompany().equals(a));

			problem = this.problemService.findOne(problemId);

			Assert.isTrue(position.getProblems().contains(problem));

			result = new ModelAndView("problem/show");
			result.addObject("problem", problem);
			result.addObject("position", position);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final Integer positionId) {
		ModelAndView result;

		try {
			final Problem problem;
			final Position position;
			position = this.positionService.findOne(positionId);
			Assert.isTrue(position.getIsCancelled() == 0);

			final UserAccount user = LoginService.getPrincipal();
			final Actor a = this.actorService.getActorByUserAccount(user.getId());

			Assert.isTrue(position.getCompany().equals(a));

			problem = this.problemService.create();

			result = new ModelAndView("problem/edit");
			result.addObject("problem", problem);
			result.addObject("position", position);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final Integer positionId, @RequestParam final Integer problemId) {
		ModelAndView result;

		try {
			final Problem problem;
			final Position position;
			position = this.positionService.findOne(positionId);
			Assert.isTrue(position.getIsCancelled() == 0);

			final UserAccount user = LoginService.getPrincipal();
			final Actor a = this.actorService.getActorByUserAccount(user.getId());

			Assert.isTrue(position.getCompany().equals(a));

			problem = this.problemService.findOne(problemId);

			Assert.isTrue(position.getProblems().contains(problem));
			Assert.isTrue(problem.getDraftMode() == 1);

			result = new ModelAndView("problem/edit");
			result.addObject("problem", problem);
			result.addObject("position", position);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(final Problem problem, final BindingResult binding, @RequestParam final int positionId) {
		ModelAndView result;
		Problem p = null;
		final Position position = this.positionService.findOne(positionId);

		try {
			Assert.isTrue(position.getIsCancelled() == 0);
			p = this.problemService.reconstruct(problem, binding);

			if (!binding.hasErrors()) {
				final Problem saved = this.problemService.save(p);
				if (!position.getProblems().contains(saved)) {
					position.getProblems().add(saved);
					this.positionRepository.save(position);
				}
				result = new ModelAndView("redirect:list.do?positionId=" + positionId);

			} else {
				result = new ModelAndView("problem/edit");
				result.addObject("problem", problem);
				result.addObject("position", position);
			}
		} catch (final ValidationException opps) {
			result = new ModelAndView("problem/edit");
			result.addObject("problem", problem);
			result.addObject("position", position);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Problem problem, final BindingResult binding, @RequestParam final int positionId) {
		ModelAndView result;
		final Problem p = this.problemService.reconstruct(problem, binding);

		try {

			final Position position = this.positionService.findOne(positionId);
			Assert.isTrue(position.getIsCancelled() == 0);

			if (!binding.hasErrors()) {
				this.problemService.delete(p, positionId);
				result = new ModelAndView("redirect:list.do?positionId=" + positionId);

			} else {
				result = new ModelAndView("problem/edit");
				result.addObject("problem", problem);
				result.addObject("position", position);
			}
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}

		return result;
	}

}
