
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.CompanyRepository;
import repositories.QuoletRepository;
import domain.Audit;
import domain.Company;
import domain.Quolet;

@Service
@Transactional
public class QuoletService {

	@Autowired
	private QuoletRepository	quoletRepository;

	@Autowired
	private CompanyRepository	companyRepository;


	public Quolet create() {
		final Quolet q = new Quolet();

		q.setTicker("");
		q.setMoment(null);
		q.setBody("");
		q.setPicture(null);
		q.setDraftMode(1);
		q.setAudit(new Audit());
		q.setCompany(new Company());

		return q;
	}
}
