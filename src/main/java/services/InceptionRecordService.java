
package services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.InceptionRecordRepository;
import security.LoginService;
import datatype.Url;
import domain.Actor;
import domain.Brotherhood;
import domain.InceptionRecord;

@Service
@Transactional
public class InceptionRecordService {

	@Autowired
	private InceptionRecordRepository	inceptionRecordRepository;
	@Autowired
	private ActorService				actorService;
	@Autowired
	private BrotherhoodService			brotherhoodService;


	public InceptionRecord create() {

		Assert.isTrue(this.actorService.getActorLogged().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		final InceptionRecord result = new InceptionRecord();
		result.setPhoto(new ArrayList<Url>());
		return result;
	}

	public List<InceptionRecord> findAll() {
		final List<InceptionRecord> res = this.inceptionRecordRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public InceptionRecord findOne(final int inceptionRecordId) {

		InceptionRecord res;
		Assert.notNull(inceptionRecordId);
		res = this.findOne(inceptionRecordId);
		Assert.notNull(res);
		return res;
	}

	public InceptionRecord save(final InceptionRecord inceptionRecord) {

		final InceptionRecord res;
		Assert.notNull(inceptionRecord);
		Assert.isTrue(this.actorService.getActorLogged().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
		final Brotherhood b = this.brotherhoodService.findOne(user.getId());

		if (inceptionRecord.getId() == 0) {
			inceptionRecord.setBrotherhood(b);
			res = this.inceptionRecordRepository.save(inceptionRecord);
		} else {
			Assert.isTrue(inceptionRecord.getBrotherhood().equals(b));
			res = this.inceptionRecordRepository.save(inceptionRecord);
		}
		return res;
	}

	public void delete(final InceptionRecord inceptionRecord) {

		Assert.notNull(inceptionRecord);
		Assert.isTrue(this.actorService.getActorLogged().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));

		Assert.isTrue(inceptionRecord.getId() != 0);
		this.inceptionRecordRepository.delete(inceptionRecord);
	}
}
