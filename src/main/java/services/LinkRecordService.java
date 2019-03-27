
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

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
	@Autowired
	private Validator				validator;


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
		if (lR.getId() == 0)
			res = this.linkRecordRepository.save(lR);
		else {
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
	//Reconstructs
	public LinkRecord reconstructCreate(final LinkRecord lRF, final BindingResult binding) {
		final LinkRecord result = new LinkRecord();

		result.setTitle(lRF.getTitle());
		result.setDescription(lRF.getDescription());
		result.setLinkedBH(lRF.getLinkedBH());

		this.validator.validate(result, binding);

		return result;
	}

	public LinkRecord reconstructEdit(final LinkRecord lRF, final BindingResult binding) {
		LinkRecord lR;
		final LinkRecord result = new LinkRecord();

		lR = this.linkRecordRepository.findOne(lRF.getId());

		result.setId(lR.getId());
		result.setVersion(lR.getVersion());
		result.setBrotherhood(lR.getBrotherhood());

		result.setTitle(lRF.getTitle());
		result.setDescription(lRF.getDescription());
		result.setLinkedBH(lRF.getLinkedBH());

		this.validator.validate(result, binding);

		return result;
	}

	public Collection<LinkRecord> getLinkRecordByBrotherhood(final int BrotherhoodId) {
		return this.linkRecordRepository.getLinkRecordByBrotherhood(BrotherhoodId);
	}

	public Collection<LinkRecord> getLinkRecordByLinkedBH(final int brotherhoodId) {
		Assert.notNull(brotherhoodId);
		return this.getLinkRecordByLinkedBH(brotherhoodId);
	}

	public void flush() {
		this.linkRecordRepository.flush();
	}

}
