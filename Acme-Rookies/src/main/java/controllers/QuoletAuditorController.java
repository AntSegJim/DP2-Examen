
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
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
import domain.Auditor;
import domain.Quolet;

@Controller
@RequestMapping("/quolet/auditor")
public class QuoletAuditorController {

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
			final Auditor c = (Auditor) this.actorService.getActorByUserAccount(user.getId());

			final Audit a = this.auditRepository.findOne(idAudit);
			Assert.isTrue(c.equals(a.getAuditor()));

			quolets = this.quoletService.getQuoletsByAuditor(idAudit);
			Assert.notNull(quolets);

			this.quoletService.updateMonths();

			result = new ModelAndView("quolet/list");
			result.addObject("quolets", quolets);
			result.addObject("audit", a);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}
		return result;

	}

}
