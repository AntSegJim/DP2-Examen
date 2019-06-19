
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
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
			Assert.isTrue(a.getDraftMode() == 0);

			quolets = this.quoletService.getQuoletsByCompany(idAudit);
			Assert.notNull(quolets);

			result = new ModelAndView("quolet/list");
			result.addObject("quolets", quolets);
			result.addObject("audit", a);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}
		return result;

	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int idAudit) {
		ModelAndView result;

		final Quolet quolet = this.quoletService.create();
		final Audit audit = this.auditRepository.findOne(idAudit);

		result = new ModelAndView("quolet/edit");
		result.addObject("quolet", quolet);
		result.addObject("audit", audit);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@RequestParam final Integer idAudit, final Quolet quolet, final BindingResult binding) {
		ModelAndView result;
		Quolet q = null;

		q = this.quoletService.reconstruct(quolet, idAudit, binding);

		if (!binding.hasErrors()) {
			this.quoletService.save(q);
			result = new ModelAndView("redirect:list.do");
		} else {
			result = new ModelAndView("quolet/edit");
			result.addObject("quolet", quolet);
			//final Audit a = this.auditRepository.findOne(idAudit);
			final Audit a = this.auditRepository.findOne(idAudit);
			result.addObject("audit", a);
			result.addObject("b", binding);

		}

		return result;
	}
}
