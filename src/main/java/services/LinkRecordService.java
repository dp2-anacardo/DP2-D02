
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.LinkRecordRepository;
import security.LoginService;
import domain.Actor;
import domain.Brotherhood;
import domain.LinkRecord;

@Service
@Transactional
public class LinkRecordService {

	@Autowired
	private LinkRecordRepository	linkRecordRepository;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private BrotherhoodService		brotherhoodService;


	public LinkRecord create() {

		Assert.isTrue(this.actorService.getActorLogged().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		final LinkRecord result = new LinkRecord();
		return result;
	}

	public List<LinkRecord> findAll() {

		final List<LinkRecord> res = this.linkRecordRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public LinkRecord findOne(final int id) {

		Assert.notNull(id);
		final LinkRecord res = this.linkRecordRepository.findOne(id);
		Assert.notNull(res);
		return res;
	}

	public LinkRecord save(final LinkRecord lR) {

		final LinkRecord res;
		Assert.notNull(lR);
		Assert.isTrue(this.actorService.getActorLogged().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
		final Brotherhood b = this.brotherhoodService.findOne(user.getId());
		if (lR.getId() == 0) {
			lR.setBrotherhood(b);
			res = this.linkRecordRepository.save(lR);
		} else {
			Assert.isTrue(lR.getBrotherhood().equals(b));
			res = this.linkRecordRepository.save(lR);
		}
		return res;
	}

	public void delete(final LinkRecord lR) {

		Assert.notNull(lR);
		Assert.isTrue(this.actorService.getActorLogged().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		Assert.isTrue(lR.getId() != 0);
		this.linkRecordRepository.delete(lR);
	}

	public Collection<LinkRecord> getLinkRecordByBrotherhood(final int BrotherhoodId) {
		return this.linkRecordRepository.getLinkRecordByBrotherhood(BrotherhoodId);
	}

}
