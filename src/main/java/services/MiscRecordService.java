
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MiscRecordRepository;
import security.LoginService;
import domain.Actor;
import domain.Brotherhood;
import domain.MiscRecord;

@Service
@Transactional
public class MiscRecordService {

	@Autowired
	private MiscRecordRepository	miscRecordRepository;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private BrotherhoodService		brotherhoodService;


	public MiscRecord create() {

		Assert.isTrue(this.actorService.getActorLogged().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		final MiscRecord result = new MiscRecord();
		return result;
	}

	public List<MiscRecord> findAll() {

		final List<MiscRecord> res = this.miscRecordRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public MiscRecord findOne(final int mrId) {

		Assert.notNull(mrId);
		final MiscRecord res = this.miscRecordRepository.findOne(mrId);
		Assert.notNull(res);
		return res;
	}

	public MiscRecord save(final MiscRecord mr) {

		final MiscRecord res;
		Assert.notNull(mr);
		Assert.isTrue(this.actorService.getActorLogged().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
		final Brotherhood b = this.brotherhoodService.findOne(user.getId());
		if (mr.getId() == 0) {
			mr.setBrotherhood(b);
			res = this.miscRecordRepository.save(mr);
		} else {
			Assert.isTrue(mr.getBrotherhood().equals(b));
			res = this.miscRecordRepository.save(mr);
		}
		return res;
	}

	public void delete(final MiscRecord mr) {

		Assert.notNull(mr);
		Assert.isTrue(this.actorService.getActorLogged().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		Assert.isTrue(mr.getId() != 0);
		this.miscRecordRepository.delete(mr);
	}

	public Collection<MiscRecord> getMiscRecordByBrotherhood(final int BrotherhoodId) {
		return this.miscRecordRepository.getMiscRecordByBrotherhood(BrotherhoodId);
	}

}
