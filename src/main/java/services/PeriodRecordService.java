
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PeriodRecordRepository;
import security.LoginService;
import datatype.Url;
import domain.Actor;
import domain.Brotherhood;
import domain.PeriodRecord;

@Service
@Transactional
public class PeriodRecordService {

	@Autowired
	private PeriodRecordRepository	periodRecordRepository;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private BrotherhoodService		brotherhoodService;


	public PeriodRecord create() {

		Assert.isTrue(this.actorService.getActorLogged().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		final PeriodRecord result = new PeriodRecord();
		final Collection<Url> photo = new ArrayList<Url>();
		result.setPhoto(photo);
		return result;
	}

	public List<PeriodRecord> findAll() {

		final List<PeriodRecord> res = this.periodRecordRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public PeriodRecord findOne(final int id) {

		Assert.notNull(id);
		final PeriodRecord res = this.periodRecordRepository.findOne(id);
		Assert.notNull(res);
		return res;
	}

	public PeriodRecord save(final PeriodRecord pR) {

		final PeriodRecord res;
		Assert.notNull(pR);
		Assert.isTrue(this.actorService.getActorLogged().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
		final Brotherhood b = this.brotherhoodService.findOne(user.getId());
		if (pR.getId() == 0) {
			pR.setBrotherhood(b);
			res = this.periodRecordRepository.save(pR);
		} else {
			Assert.isTrue(pR.getBrotherhood().equals(b));
			res = this.periodRecordRepository.save(pR);
		}
		return res;
	}

	public void delete(final PeriodRecord pR) {

		Assert.notNull(pR);
		Assert.isTrue(this.actorService.getActorLogged().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		Assert.isTrue(pR.getId() != 0);
		this.periodRecordRepository.delete(pR);
	}

	public Collection<PeriodRecord> getPeriodRecordByBrotherhood(final int BrotherhoodId) {
		return this.periodRecordRepository.getPeriodRecordByBrotherhood(BrotherhoodId);
	}

}
