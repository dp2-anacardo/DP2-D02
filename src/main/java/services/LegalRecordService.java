
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.LegalRecordRepository;
import security.LoginService;
import domain.Actor;
import domain.Brotherhood;
import domain.LegalRecord;

@Service
@Transactional
public class LegalRecordService {

	@Autowired
	private LegalRecordRepository	legalRecordRepository;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private BrotherhoodService		brotherhoodService;


	public LegalRecord create() {

		Assert.isTrue(this.actorService.getActorLogged().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		final LegalRecord result = new LegalRecord();
		final Collection<String> aL = new ArrayList<String>();
		result.setApplicableLaws(aL);
		return result;
	}

	public List<LegalRecord> findAll() {

		final List<LegalRecord> res = this.legalRecordRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public LegalRecord findOne(final int id) {

		Assert.notNull(id);
		final LegalRecord res = this.legalRecordRepository.findOne(id);
		Assert.notNull(res);
		return res;
	}

	public LegalRecord save(final LegalRecord lR) {

		final LegalRecord res;
		Assert.notNull(lR);
		Assert.isTrue(this.actorService.getActorLogged().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
		final Brotherhood b = this.brotherhoodService.findOne(user.getId());
		if (lR.getId() == 0) {
			lR.setBrotherhood(b);
			res = this.legalRecordRepository.save(lR);
		} else {
			Assert.isTrue(lR.getBrotherhood().equals(b));
			res = this.legalRecordRepository.save(lR);
		}
		return res;
	}

	public void delete(final LegalRecord lR) {

		Assert.notNull(lR);
		Assert.isTrue(this.actorService.getActorLogged().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		Assert.isTrue(lR.getId() != 0);
		this.legalRecordRepository.delete(lR);
	}

	public Collection<LegalRecord> getLegalRecordByBrotherhood(final int BrotherhoodId) {
		return this.legalRecordRepository.getLegalRecordByBrotherhood(BrotherhoodId);
	}

}
