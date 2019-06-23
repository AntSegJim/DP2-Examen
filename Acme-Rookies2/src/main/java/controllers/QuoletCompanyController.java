
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import repositories.AuditRepository;
import security.LoginService;
import security.UserAccount;
import services.ActorService;
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
	private AuditRepository	auditRepository;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int idAudit) {
		ModelAndView result;
		try {

			final Collection<Quolet> quolets;

			final UserAccount user = LoginService.getPrincipal();
			final Company c = (Company) this.actorService.getActorByUserAccount(user.getId());

			final Audit a = this.auditRepository.findOne(idAudit);
			Assert.isTrue(c.equals(a.getPosition().getCompany()));

			quolets = this.quoletService.getQuoletsPerAuditByCompany(idAudit);
			Assert.notNull(quolets);

			this.quoletService.updateMonths();

			final String lang = LocaleContextHolder.getLocale().getLanguage();

			result = new ModelAndView("quolet/list");
			result.addObject("quolets", quolets);
			result.addObject("audit", a);
			result.addObject("lang", lang);
			result.addObject("position", a.getPosition());

		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}
		return result;

	}
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int idAudit, @RequestParam final int idQuolet) {
		ModelAndView result;

		try {
			final Quolet q = this.quoletService.findOne(idQuolet);
			Assert.notNull(q);
			Assert.isTrue(this.quoletService.getMyQuoletsCompany().contains(q));
			result = new ModelAndView("quolet/show");
			result.addObject("quolet", q);
			result.addObject("idAudit", idAudit);
			final String lang = LocaleContextHolder.getLocale().getLanguage();
			result.addObject("lang", lang);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}

		return result;
	}

}
