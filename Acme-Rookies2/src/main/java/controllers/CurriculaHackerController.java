
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.CurriculaService;
import services.HackerService;
import domain.Curricula;
import domain.Rookie;

@Controller
@RequestMapping("/curricula/rookie")
public class CurriculaHackerController extends AbstractController {

	@Autowired
	private CurriculaService	curriculaService;
	@Autowired
	private HackerService		hackerService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Curricula> curriculas;

		final UserAccount user = LoginService.getPrincipal();
		final Rookie h = this.hackerService.hackerUserAccount(user.getId());

		curriculas = this.curriculaService.getCurriculasByHacker(h.getId());
		Assert.notNull(curriculas);

		result = new ModelAndView("curricula/list");
		result.addObject("curriculas", curriculas);

		return result;

	}

}
