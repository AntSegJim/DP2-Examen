
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
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
			final Auditor a = (Auditor) this.actorService.getActorByUserAccount(user.getId());

			final Audit au = this.auditRepository.findOne(idAudit);
			Assert.isTrue(a.equals(au.getAuditor()));
			Assert.isTrue(au.getDraftMode() == 0);

			quolets = this.quoletService.getQuoletsByAuditor(idAudit);
			Assert.notNull(quolets);

			this.quoletService.updateMonths();

			final String lang = LocaleContextHolder.getLocale().getLanguage();

			result = new ModelAndView("quolet/list");
			result.addObject("quolets", quolets);
			result.addObject("audit", au);
			result.addObject("lang", lang);
			//		result.addObject("position", a.getPosition());
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
			Assert.isTrue(this.quoletService.getAllMyQuolets().contains(q));
			result = new ModelAndView("quolet/show");
			result.addObject("quolet", q);
			result.addObject("idAudit", idAudit);
			final String lang = LocaleContextHolder.getLocale().getLanguage();
			result.addObject("lang", lang);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:list.do?idAudit=" + idAudit);
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
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int idQuolet) {
		ModelAndView result;
		final Quolet quolet;
		final Audit audit;

		try {
			quolet = this.quoletService.findOne(idQuolet);
			audit = quolet.getAudit();
			Assert.isTrue(quolet.getDraftMode() == 1);

			result = new ModelAndView("quolet/edit");
			result.addObject("audit", audit);
			result.addObject("quolet", quolet);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@RequestParam final Integer idAudit, final Quolet quolet, final BindingResult binding) {
		ModelAndView result;
		try {
			Quolet q = null;

			q = this.quoletService.reconstruct(quolet, idAudit, binding);

			if (!binding.hasErrors()) {
				this.quoletService.save(q);
				result = new ModelAndView("redirect:list.do?idAudit=" + idAudit);
			} else {
				result = new ModelAndView("quolet/edit");
				result.addObject("quolet", quolet);
				final Audit a = this.auditRepository.findOne(idAudit);
				result.addObject("audit", a);

			}
		} catch (final Exception e) {
			result = new ModelAndView("quolet/edit");
			result.addObject("quolet", quolet);
			final Audit a = this.auditRepository.findOne(idAudit);
			result.addObject("audit", a);
			result.addObject("exception", e);
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Quolet quolet, final BindingResult binding) {
		ModelAndView result;
		try {

			final Quolet q = this.quoletService.findOne(quolet.getId());
			final Audit audit = q.getAudit();
			final Integer idAudit = audit.getId();
			if (!binding.hasErrors()) {
				this.quoletService.delete(q);
				result = new ModelAndView("redirect:list.do?idAudit=" + idAudit);
			} else {
				result = new ModelAndView("audit/edit");
				result.addObject("quolet", quolet);
				result.addObject("audit", audit);
			}
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}

		return result;
	}
}
