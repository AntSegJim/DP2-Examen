
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

import repositories.CompanyRepository;
import security.LoginService;
import security.UserAccount;
import services.AuditService;
import services.AuditorService;
import services.PositionService;
import domain.Audit;
import domain.Auditor;
import domain.Position;

@Controller
@RequestMapping("/audit/auditor")
public class AuditAuditorController extends AbstractController {

	@Autowired
	private AuditService		auditService;
	@Autowired
	private AuditorService		auditorService;
	@Autowired
	private PositionService		positionService;
	@Autowired
	private CompanyRepository	companyRepository;


	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final Integer auditId) {
		ModelAndView result;
		final Audit audit;
		try {
			audit = this.auditService.findOne(auditId);
			result = new ModelAndView("audit/show");
			result.addObject("audit", audit);
			//result.addObject("position", position);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Audit> audits;

		final UserAccount user = LoginService.getPrincipal();
		final Auditor a = this.auditorService.auditorUserAccount(user.getId());

		audits = this.auditService.getAuditsByAuditor(a.getId());
		Assert.notNull(audits);

		result = new ModelAndView("audit/list");
		result.addObject("audits", audits);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Collection<Position> positions;
		positions = this.positionService.getPositionsOutDraftMode();
		final Audit audit = this.auditService.create();

		result = new ModelAndView("audit/edit");
		result.addObject("positions", positions);
		result.addObject("audit", audit);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int auditId) {
		ModelAndView result;
		final Audit audit;
		Collection<Position> positions;

		try {
			audit = this.auditService.findOne(auditId);
			positions = this.positionService.getPositionsOutDraftMode();
			Assert.notNull(audit);
			result = new ModelAndView("audit/edit");
			result.addObject("audit", audit);
			result.addObject("positions", positions);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:list.do");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(final Audit audit, final BindingResult binding) {
		ModelAndView result;
		Audit a;
		try {
			a = this.auditService.reconstruct(audit, binding);
			if (!binding.hasErrors()) {
				this.auditService.save(a);
				result = new ModelAndView("redirect:list.do");
			} else {
				Collection<Position> positions;
				positions = this.positionService.getPositionsOutDraftMode();
				result = new ModelAndView("audit/edit");
				result.addObject("positions", positions);
				result.addObject("audit", audit);
			}
		} catch (final ValidationException opps) {
			Collection<Position> positions;
			positions = this.positionService.getPositionsOutDraftMode();
			result = new ModelAndView("audit/edit");
			result.addObject("positions", positions);
			result.addObject("audit", audit);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Audit audit, final BindingResult binding) {
		ModelAndView result;
		try {
			final Audit a = this.auditService.reconstruct(audit, binding);
			if (!binding.hasErrors()) {
				this.auditService.delete(a);
				result = new ModelAndView("redirect:list.do");
			} else {
				Collection<Position> positions;
				positions = this.positionService.getPositionsOutDraftMode();
				result = new ModelAndView("audit/edit");
				result.addObject("audit", audit);
				result.addObject("positions", positions);
			}
		} catch (final Exception e) {
			result = new ModelAndView("redirect:../../");
		}

		return result;
	}

}
