
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ApplicationRepository;
import repositories.PersonalDataRepository;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.Curricula;
import domain.EducationData;
import domain.MiscellaneousData;
import domain.PersonalData;
import domain.Position;
import domain.PositionData;
import domain.Problem;
import domain.Rookie;

@Service
@Transactional
public class ApplicationService {

	@Autowired
	private ApplicationRepository		applicationRepository;
	@Autowired
	private Validator					validator;
	@Autowired
	private HackerService				hackerService;
	@Autowired
	private CurriculaService			curriculaService;
	@Autowired
	private ProblemService				problemService;
	@Autowired
	private EducationDataService		educationDataService;
	@Autowired
	private MiscellaneousDataService	miscellaneousDataService;
	@Autowired
	private PersonalDataRepository		personalDataRepository;
	@Autowired
	private PositionDataService			positionDataService;


	public Application create() {
		final Application a = new Application();
		a.setCurricula(null);
		a.setExplication("");
		a.setRookie(null);
		a.setMoment(new Date());
		a.setStatus(0);
		a.setSubmitMoment(null);
		a.setUrlCode("");
		return a;
	}

	public Application findOne(final Integer id) {
		return this.applicationRepository.findOne(id);
	}

	public Collection<Application> findAll() {
		return this.applicationRepository.findAll();
	}

	public Application save(final Application application, final Position position) {
		Application savedApplication;

		if (application.getId() != 0) {
			Assert.isTrue(this.getAllMyApplicationsHacker().contains(application));
			Assert.isTrue(this.applicationRepository.findOne(application.getId()).getStatus() == 0);
		} else {
			final Collection<Curricula> c = this.curriculaService.getCurriculasByHacker(this.hackerService.hackerUserAccount(LoginService.getPrincipal().getId()).getId());
			Assert.isTrue(c.contains(application.getCurricula()));
		}

		if (application.getId() == 0 && this.problemService.getProblemDraftModeOut().size() > 0) {
			final Problem p = this.problemService.getAleatoryProblem(position);
			final Curricula copia = this.clonarCurricula(application);
			final Curricula savedCopia = this.curriculaService.save(copia);
			application.setCurricula(savedCopia);
			savedApplication = this.applicationRepository.save(application);
			p.getApplications().add(savedApplication);
			this.problemService.saveApplication(p);

		} else if (application.getId() != 0) {
			Assert.isTrue(true);
			savedApplication = this.applicationRepository.save(application);
		} else {
			Assert.isTrue(false);
			savedApplication = this.applicationRepository.save(application);
		}
		return savedApplication;
	}
	//RECONSTRUCT
	public Application reconstruct(final Application application, final BindingResult binding) {
		Application res;

		if (application.getId() == 0) {
			res = application;

			final UserAccount user = LoginService.getPrincipal();
			final Rookie h = this.hackerService.hackerUserAccount(user.getId());

			res.setRookie(h);
			res.setStatus(0);
			res.setMoment(new Date());
			res.setExplication("");
			res.setUrlCode("");

			this.validator.validate(res, binding);

		} else {
			res = this.applicationRepository.findOne(application.getId());
			final Application a = new Application();
			a.setId(application.getId());
			a.setVersion(application.getVersion());
			a.setCurricula(res.getCurricula());
			a.setExplication(application.getExplication());
			a.setUrlCode(application.getUrlCode());
			a.setStatus(application.getStatus());
			a.setMoment(res.getMoment());
			a.setRookie(res.getRookie());
			a.setSubmitMoment(new Date());
			if (application.getExplication() == null || application.getExplication() == "")
				binding.rejectValue("explication", "notBlank");
			if (application.getUrlCode() == null || application.getUrlCode() == "")
				binding.rejectValue("urlCode", "notBlank");
			this.validator.validate(a, binding);
			if (binding.hasErrors())
				throw new ValidationException();
			res = a;

		}
		return res;
	}
	//DASHBOARD
	public List<Object[]> getAvgMinMaxDesvAppByHackers() {
		return this.applicationRepository.getAvgMinMaxDesvAppByHackers();
	}

	public Collection<Application> getAllMyApplicationsHacker() {
		final int hackerId = this.hackerService.hackerUserAccount(LoginService.getPrincipal().getId()).getId();
		return this.applicationRepository.getAllMyApplicationsHacker(hackerId);
	}

	public Collection<Curricula> getCurriculaHacker() {
		final Integer hackerId = this.hackerService.hackerUserAccount(LoginService.getPrincipal().getId()).getId();
		return this.curriculaService.getCurriculasByHacker(hackerId);
	}

	public void delete(final Application application) {
		Assert.isTrue(this.getAllMyApplicationsHacker().contains(application));
		this.applicationRepository.delete(application);
	}

	public Application saveCompany(final Application a) {
		return this.applicationRepository.save(a);
	}

	public Curricula clonarCurricula(final Application application) {
		final Curricula curricula = new Curricula();
		final Collection<EducationData> es = new HashSet<EducationData>();
		for (final EducationData e : application.getCurricula().getEducationData()) {
			final EducationData newE = new EducationData();
			newE.setDegree(e.getDegree());
			newE.setEndDate(e.getEndDate());
			newE.setInstitution(e.getInstitution());
			newE.setMark(e.getMark());
			newE.setStartDate(e.getStartDate());
			EducationData savedE = new EducationData();
			savedE = this.educationDataService.save(newE);
			es.add(savedE);

		}
		final Collection<MiscellaneousData> ms = new HashSet<MiscellaneousData>();
		for (final MiscellaneousData m : application.getCurricula().getMiscellaneousData()) {
			final MiscellaneousData newM = new MiscellaneousData();
			newM.setAttachment(m.getAttachment());
			newM.setText(m.getText());
			MiscellaneousData savedM = new MiscellaneousData();
			savedM = this.miscellaneousDataService.save(newM);
			ms.add(savedM);

		}

		final Collection<PositionData> ps = new HashSet<PositionData>();
		for (final PositionData p : application.getCurricula().getPositionData()) {
			final PositionData newP = new PositionData();
			newP.setDescription(p.getDescription());
			newP.setEndDate(p.getEndDate());
			newP.setStartDate(p.getStartDate());
			newP.setTitle(p.getTitle());

			PositionData savedP = new PositionData();
			savedP = this.positionDataService.save(newP);
			ps.add(savedP);

		}

		final PersonalData pd = new PersonalData();
		pd.setFullName(application.getCurricula().getPersonalData().getFullName());
		pd.setGithubProfile(application.getCurricula().getPersonalData().getGithubProfile());
		pd.setLinkedlnProfile(application.getCurricula().getPersonalData().getLinkedlnProfile());
		pd.setPhoneNumber(application.getCurricula().getPersonalData().getPhoneNumber());
		pd.setStatement(application.getCurricula().getPersonalData().getStatement());
		final PersonalData savedpd = this.personalDataRepository.save(pd);

		curricula.setPersonalData(savedpd);
		curricula.setIsCopy(1);
		curricula.setRookie(application.getCurricula().getRookie());
		curricula.setEducationData(es);
		curricula.setMiscellaneousData(ms);
		curricula.setPositionData(ps);

		return curricula;
	}
}
