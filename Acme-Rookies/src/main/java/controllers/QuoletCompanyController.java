
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.QuoletService;
import domain.Quolet;

@Controller
@RequestMapping("/quolet/company")
public class QuoletCompanyController {

	@Autowired
	private QuoletService	quoletService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int idAudit) {
		final ModelAndView result;
		final Collection<Quolet> quolets;

		//		final UserAccount user = LoginService.getPrincipal();
		//		final Restaurant r = (Restaurant) this.actorService.getActorByUserAccount(user.getId());

		quolets = this.quoletService.getQuoletsByCompany(idAudit);
		Assert.notNull(quolets);

		result = new ModelAndView("quolet/list");
		result.addObject("quolets", quolets);
		return result;

	}

}
