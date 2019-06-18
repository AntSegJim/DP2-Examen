
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PositionDataRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Curricula;
import domain.PositionData;

@Service
@Transactional
public class PositionDataService {

	@Autowired
	private PositionDataRepository	positionDataRepository;
	@Autowired
	private ActorService			actorS;
	@Autowired
	private CurriculaService		curriculaService;


	public PositionData create() {
		final PositionData res = new PositionData();
		res.setTitle("");
		res.setDescription("");
		res.setStartDate(new Date());
		res.setEndDate(new Date());
		return res;
	}

	public Collection<PositionData> findAll() {
		return this.positionDataRepository.findAll();
	}

	public PositionData findOne(final Integer positionDataId) {
		final PositionData positionData = this.positionDataRepository.findOne(positionDataId);
		final Curricula curricula = this.curriculaService.getCurriculaByPositionData(positionData.getId());
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorS.getActorByUserAccount(user.getId());
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("ROOKIE"));
		Assert.isTrue(curricula.getRookie() == a);
		return positionData;
	}

	public PositionData save(final PositionData positionData) {
		final Curricula curricula = this.curriculaService.getCurriculaByPositionData(positionData.getId());
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorS.getActorByUserAccount(user.getId());
		if (positionData.getId() != 0)
			Assert.isTrue(curricula.getRookie() == a);

		if (positionData.getEndDate() != null)
			Assert.isTrue(positionData.getStartDate().before(positionData.getEndDate()));
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("ROOKIE"));
		//Assert.isTrue(positionData != null && positionData.getEndDate().after(positionData.getStartDate()));
		return this.positionDataRepository.save(positionData);
	}

	public void delete(final PositionData positionData, final int curriculaId) {
		final Curricula curricula = this.curriculaService.findOne(curriculaId);
		final UserAccount user = this.actorS.getActorLogged().getUserAccount();
		final Actor a = this.actorS.getActorByUserAccount(user.getId());
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("ROOKIE"));
		Assert.isTrue(curricula.getRookie() == a);
		Assert.isTrue(curricula.getPositionData().contains(positionData));
		curricula.getPositionData().remove(positionData);
		this.positionDataRepository.delete(positionData);
		this.curriculaService.save(curricula);
	}
}
