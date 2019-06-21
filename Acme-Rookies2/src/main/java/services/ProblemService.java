
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PositionRepository;
import repositories.ProblemRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Application;
import domain.Company;
import domain.Position;
import domain.Problem;

@Transactional
@Service
public class ProblemService {

	@Autowired
	private ProblemRepository	problemRepository;

	@Autowired
	private PositionRepository	positionRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;

	@Autowired
	private CompanyService		companyService;


	public Problem create() {
		final Problem p = new Problem();

		p.setTitle("");
		p.setStatement("");
		p.setHint("");
		p.setAttachment(new HashSet<String>());
		p.setDraftMode(1);
		p.setApplications(new HashSet<Application>());

		return p;
	}

	public Collection<Problem> findAll() {
		return this.problemRepository.findAll();
	}

	public Problem findOne(final Integer id) {
		return this.problemRepository.findOne(id);
	}

	public Problem findOneWithCredentials(final Integer id) {
		Problem res;
		res = this.problemRepository.findOne(id);
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());
		final Position p = this.positionRepository.getPositionByProblem(res.getId());
		Assert.isTrue(p.getCompany().equals(a));
		Assert.isTrue(p.getProblems().contains(res));

		return res;
	}

	public Problem save(final Problem problem) {

		if (problem.getId() != 0) {
			final UserAccount user = LoginService.getPrincipal();
			final Actor a = this.actorService.getActorByUserAccount(user.getId());
			final Position p = this.positionRepository.getPositionByProblem(problem.getId());
			Assert.isTrue(p.getCompany().equals(a));
			final Problem old = this.findOne(problem.getId());
			Assert.isTrue(old.getDraftMode() == 1);
		}

		final Problem saved = this.problemRepository.save(problem);
		return saved;
	}

	public Problem reconstruct(final Problem problem, final BindingResult binding) {
		final Problem res;

		if (problem.getId() == 0) {

			res = problem;
			problem.setDraftMode(1);
			problem.setApplications(new HashSet<Application>());

			this.validator.validate(res, binding);
			return res;
		} else {
			res = this.problemRepository.findOne(problem.getId());
			final Problem copy = new Problem();

			copy.setId(res.getId());
			copy.setVersion(res.getVersion());
			copy.setApplications(res.getApplications());
			copy.setTitle(problem.getTitle());
			copy.setStatement(problem.getStatement());
			copy.setHint(problem.getHint());
			copy.setAttachment(problem.getAttachment());
			copy.setDraftMode(problem.getDraftMode());

			this.validator.validate(copy, binding);
			if (binding.hasErrors())
				throw new ValidationException();
			return copy;
		}
	}

	public void delete(final Problem problem, final Integer positionId) {
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(user.getId());
		final Position p = this.positionRepository.findOne(positionId);
		Assert.isTrue(p.getCompany().equals(a));
		Assert.isTrue(p.getProblems().contains(problem));
		Assert.isTrue(problem.getDraftMode() == 1);
		p.getProblems().remove(problem);
		this.problemRepository.delete(problem);
		this.positionRepository.save(p);

	}

	public Problem getProblemByApplication(final Application a) {
		return this.problemRepository.getProblemByApplication(a);
	}

	public Problem getAleatoryProblem(final Position position) {
		final Collection<Problem> ps = this.problemRepository.getProblemsWithoutDraftModeByPosition(position.getId());
		final List<Problem> list = new ArrayList<Problem>(ps);
		final int number = (int) Math.floor(Math.random() * ((list.size() - 1) - 0 + 1) + (0));
		final Problem aleatory = list.get(number);
		return aleatory;
	}

	public Problem saveApplication(final Problem p) {
		return this.problemRepository.save(p);
	}

	public Collection<Problem> getProblemDraftModeOut() {
		return this.problemRepository.getProblemDraftModeOut();
	}

	public Collection<Problem> getProblemsByCompany() {
		final int idUser = LoginService.getPrincipal().getId();
		final Company c = this.companyService.companyUserAccount(idUser);
		return this.problemRepository.getProblemsByCompany(c.getId());
	}
}
