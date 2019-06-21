
package controllers;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import repositories.CompanyRepository;
import services.AuditService;
import services.CompanyService;
import domain.Company;

@Controller
@RequestMapping("/company/administrator/")
public class CompanyAdministratorController extends AbstractController {

	@Autowired
	private CompanyService		companyService;

	@Autowired
	private AuditService		auditService;

	@Autowired
	private CompanyRepository	companyRepository;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listCompanies() {
		ModelAndView result;

		final Collection<Company> companies = this.companyService.findAll();

		result = new ModelAndView("company/list");
		result.addObject("companies", companies);

		return result;
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView updateScore() {
		ModelAndView result;

		final List<Company> companies = (List<Company>) this.companyService.findAll();
		for (int i = 0; i < companies.size(); i++) {
			this.auditService.updateTotalScoreOfCompany(companies.get(i).getId());
			this.companyRepository.save(companies.get(i));
		}

		result = new ModelAndView("redirect:list.do");

		return result;
	}

}
