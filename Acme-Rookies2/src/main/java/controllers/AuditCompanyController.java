
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuditService;
import domain.Audit;

@Controller
@RequestMapping("/audit/company")
public class AuditCompanyController extends AbstractController {

	@Autowired
	private AuditService	auditService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final Integer positionId) {
		final ModelAndView result;
		final Collection<Audit> audits;
		audits = this.auditService.getAuditsByPositionDM(positionId);
		Assert.notNull(audits);
		result = new ModelAndView("audit/list");
		result.addObject("audits", audits);
		return result;
	}

}
