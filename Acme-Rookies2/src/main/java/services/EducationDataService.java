
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EducationDataRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Curricula;
import domain.EducationData;

@Service
@Transactional
public class EducationDataService {

	@Autowired
	private EducationDataRepository	educationDataRepository;
	@Autowired
	private ActorService			actorS;
	@Autowired
	private CurriculaService		curriculaService;


	public EducationData create() {
		final EducationData res = new EducationData();
		res.setDegree("");
		res.setInstitution("");
		res.setMark(0);
		res.setStartDate(new Date());
		res.setEndDate(new Date());
		return res;
	}

	public Collection<EducationData> findAll() {
		return this.educationDataRepository.findAll();
	}

	public EducationData findOne(final Integer educationDataId) {
		final EducationData educationData = this.educationDataRepository.findOne(educationDataId);
		final Curricula curricula = this.curriculaService.getCurriculaByEducationData(educationData.getId());
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorS.getActorByUserAccount(user.getId());
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("ROOKIE"));
		Assert.isTrue(curricula.getRookie() == a);
		return educationData;
	}

	public EducationData save(final EducationData educationData) {
		final Curricula curricula = this.curriculaService.getCurriculaByEducationData(educationData.getId());
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorS.getActorByUserAccount(user.getId());
		if (educationData.getId() != 0)
			Assert.isTrue(curricula.getRookie() == a);
		if (educationData.getEndDate() != null)
			Assert.isTrue(educationData.getStartDate().before(educationData.getEndDate()));

		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("ROOKIE"));
		//Assert.isTrue(educationData != null && educationData.getEndDate().after(educationData.getStartDate()));
		return this.educationDataRepository.save(educationData);
	}

	public void delete(final EducationData educationData, final int curriculaId) {
		final Curricula curricula = this.curriculaService.findOne(curriculaId);
		final UserAccount user = this.actorS.getActorLogged().getUserAccount();
		final Actor a = this.actorS.getActorByUserAccount(user.getId());
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("ROOKIE"));
		Assert.isTrue(curricula.getRookie() == a);
		Assert.isTrue(curricula.getEducationData().contains(educationData));
		curricula.getEducationData().remove(educationData);
		this.educationDataRepository.delete(educationData);
		this.curriculaService.save(curricula);
	}

}
