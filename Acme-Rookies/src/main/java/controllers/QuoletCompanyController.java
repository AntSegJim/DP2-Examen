
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.AuditService;
import services.QuoletService;
import domain.Audit;
import domain.Company;
import domain.Quolet;

@Controller
@RequestMapping("/quolet/company")
public class QuoletCompanyController {

	@Autowired
	private QuoletService	quoletService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private AuditService	auditService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int idAudit) {
		ModelAndView result;
		try {

			final Collection<Quolet> quolets;

			final UserAccount user = LoginService.getPrincipal();
			final Company c = (Company) this.actorService.getActorByUserAccount(user.getId());

			final Audit a = this.auditService.findOne(idAudit);
			Assert.isTrue(c.equals(a.getPosition().getCompany()));
			Assert.isTrue(a.getDraftMode() == 1);

			quolets = this.quoletService.getQuoletsByCompany(idAudit);
			Assert.notNull(quolets);

			result = new ModelAndView("quolet/list");
			result.addObject("quolets", quolets);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}
		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int idAudit) {
		ModelAndView result;

		final Quolet quolet = this.quoletService.create();
		final Audit audit = this.auditService.findOne(idAudit);

		result = new ModelAndView("quolet/edit");
		result.addObject("quolet", quolet);
		result.addObject("audit", audit);

		return result;

	}
}
