
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MiscellaneousDataRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Curricula;
import domain.MiscellaneousData;

@Service
@Transactional
public class MiscellaneousDataService {

	@Autowired
	private MiscellaneousDataRepository	miscellaneousDataRepository;
	@Autowired
	private CurriculaService			curriculaService;
	@Autowired
	private ActorService				actorS;


	public MiscellaneousData create() {
		final MiscellaneousData res = new MiscellaneousData();
		res.setText("");
		res.setAttachment("");
		return res;
	}

	public Collection<MiscellaneousData> findAll() {
		return this.miscellaneousDataRepository.findAll();
	}

	public MiscellaneousData findOne(final Integer miscellaneousDataId) {
		final MiscellaneousData miscellaneousData = this.miscellaneousDataRepository.findOne(miscellaneousDataId);
		final Curricula curricula = this.curriculaService.getCurriculaByMiscellaneousData(miscellaneousData.getId());
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorS.getActorByUserAccount(user.getId());
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("ROOKIE"));
		Assert.isTrue(curricula.getRookie() == a);
		return miscellaneousData;
	}

	public MiscellaneousData save(final MiscellaneousData miscellaneousData) {
		final Curricula curricula = this.curriculaService.getCurriculaByMiscellaneousData(miscellaneousData.getId());
		final UserAccount user = LoginService.getPrincipal();
		final Actor a = this.actorS.getActorByUserAccount(user.getId());
		if (miscellaneousData.getId() != 0)
			Assert.isTrue(curricula.getRookie() == a);

		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("ROOKIE"));
		Assert.isTrue(miscellaneousData != null);
		return this.miscellaneousDataRepository.save(miscellaneousData);
	}

	public void delete(final MiscellaneousData miscellaneousData, final int curriculaId) {
		final Curricula curricula = this.curriculaService.findOne(curriculaId);
		final UserAccount user = this.actorS.getActorLogged().getUserAccount();
		final Actor a = this.actorS.getActorByUserAccount(user.getId());
		Assert.isTrue(user.getAuthorities().iterator().next().getAuthority().equals("ROOKIE"));
		Assert.isTrue(curricula.getRookie() == a);
		Assert.isTrue(curricula.getMiscellaneousData().contains(miscellaneousData));
		curricula.getMiscellaneousData().remove(miscellaneousData);
		this.miscellaneousDataRepository.delete(miscellaneousData);
		this.curriculaService.save(curricula);
	}
}
