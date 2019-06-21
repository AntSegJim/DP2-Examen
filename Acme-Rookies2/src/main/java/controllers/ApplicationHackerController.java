
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

import services.ApplicationService;
import services.PositionService;
import services.ProblemService;
import domain.Application;
import domain.Position;
import domain.Problem;

@Controller
@RequestMapping("/application/rookie")
public class ApplicationHackerController extends AbstractController {

	@Autowired
	private ApplicationService	applicationService;
	@Autowired
	private ProblemService		problemService;
	@Autowired
	private PositionService		positionService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Application> applications;
		applications = this.applicationService.getAllMyApplicationsHacker();
		result = new ModelAndView("application/list");
		result.addObject("applications", applications);
		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final Application application;

		application = this.applicationService.create();

		result = new ModelAndView("application/create");
		result.addObject("application", application);
		result.addObject("curriculas", this.applicationService.getCurriculaHacker());
		result.addObject("positions", this.positionService.getAllPositionToCreateApplication());
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int applicationId) {
		ModelAndView result;
		final Application application;

		try {
			application = this.applicationService.findOne(applicationId);
			Assert.notNull(application);
			Assert.isTrue(application.getStatus() == 0);
			result = new ModelAndView("application/edit");
			result.addObject("application", application);
			result.addObject("curriculas", this.applicationService.getCurriculaHacker());
		} catch (final Exception e) {
			result = new ModelAndView("redirect:list.do");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(Application newApplication, final BindingResult binding, @RequestParam(value = "status", defaultValue = "0") final int status, @RequestParam(value = "positionId", defaultValue = "-1") final int positionId) {
		ModelAndView result;
		if (status == 1)
			newApplication.setStatus(1);
		try {
			Position position = null;
			if (positionId != -1) {
				position = this.positionService.findOne(positionId);
				Assert.notNull(position);
			}
			newApplication = this.applicationService.reconstruct(newApplication, binding);
			if (!binding.hasErrors()) {
				this.applicationService.save(newApplication, position);
				result = new ModelAndView("redirect:list.do");
			} else if (newApplication.getId() != 0) {
				result = new ModelAndView("application/edit");
				result.addObject("application", newApplication);
				result.addObject("curriculas", this.applicationService.getCurriculaHacker());
			} else {
				result = new ModelAndView("application/create");
				result.addObject("positions", this.positionService.getAllPositionToCreateApplication());
				result.addObject("curriculas", this.applicationService.getCurriculaHacker());
			}
		} catch (final ValidationException v) {
			result = new ModelAndView("application/edit");
			result.addObject("application", newApplication);
		} catch (final Exception e) {
			if (newApplication.getId() == 0) {
				result = new ModelAndView("application/create");
				result.addObject("positions", this.positionService.getAllPositionToCreateApplication());
				result.addObject("curriculas", this.applicationService.getCurriculaHacker());
			} else
				result = new ModelAndView("application/edit");
			result.addObject("exception", "e");
			result.addObject("application", newApplication);
		}
		return result;

	}
	//	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	//	public ModelAndView delete(@RequestParam final int applicationId) {
	//		ModelAndView result;
	//
	//		try {
	//			final Application application = this.applicationService.findOne(applicationId);
	//			Assert.notNull(application);
	//			this.applicationService.delete(application);
	//			result = new ModelAndView("redirect:list.do");
	//		} catch (final Exception e) {
	//			result = new ModelAndView("application/list");
	//			result.addObject("exception", "e");
	//			result.addObject("applications", this.applicationService.getAllMyApplicationsHacker());
	//		}
	//		return result;
	//	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int applicationId) {
		ModelAndView result;
		Application application;
		try {
			application = this.applicationService.findOne(applicationId);
			Assert.notNull(application);
			final Collection<Application> applications = this.applicationService.getAllMyApplicationsHacker();
			Assert.isTrue(applications.contains(application));

			final Problem p = this.problemService.getProblemByApplication(application);

			result = new ModelAndView("application/show");
			result.addObject("application", application);
			result.addObject("problem", p);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:list.do");
		}
		return result;
	}
}
