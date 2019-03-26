
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

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
	@Autowired
	private Validator				validator;


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

	//Reconstructs
	public MiscRecord reconstructCreate(final MiscRecord mRF, final BindingResult binding) {
		final MiscRecord result = new MiscRecord();

		result.setTitle(mRF.getTitle());
		result.setDescription(mRF.getDescription());

		this.validator.validate(result, binding);

		return result;
	}

	public MiscRecord reconstructEdit(final MiscRecord mRF, final BindingResult binding) {
		MiscRecord mR;
		final MiscRecord result = new MiscRecord();

		mR = this.miscRecordRepository.findOne(mRF.getId());

		result.setId(mR.getId());
		result.setVersion(mR.getVersion());
		result.setBrotherhood(mR.getBrotherhood());

		result.setTitle(mRF.getTitle());
		result.setDescription(mRF.getDescription());

		this.validator.validate(result, binding);

		return result;
	}

	public Collection<MiscRecord> getMiscRecordByBrotherhood(final int BrotherhoodId) {
		return this.miscRecordRepository.getMiscRecordByBrotherhood(BrotherhoodId);
	}

	public void flush() {
		this.miscRecordRepository.flush();
	}

}
