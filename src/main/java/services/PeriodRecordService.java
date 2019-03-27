
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PeriodRecordRepository;
import security.LoginService;
import datatype.Url;
import domain.Actor;
import domain.Brotherhood;
import domain.PeriodRecord;
import forms.PeriodRecordForm;

@Service
@Transactional
public class PeriodRecordService {

	@Autowired
	private PeriodRecordRepository	periodRecordRepository;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private BrotherhoodService		brotherhoodService;
	@Autowired
	private Validator				validator;


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

	//Reconstructs
	public PeriodRecord reconstructCreate(final PeriodRecordForm pRF, final BindingResult binding) {
		final PeriodRecord result = new PeriodRecord();

		result.setPhoto(new ArrayList<Url>());

		result.setTitle(pRF.getTitle());
		result.setDescription(pRF.getDescription());
		if (pRF.getStartYear() == 0)
			result.setStartYear(null);
		else
			result.setStartYear(pRF.getStartYear());
		if (pRF.getEndYear() == 0)
			result.setEndYear(null);
		else
			result.setEndYear(pRF.getEndYear());
		result.setPhoto(pRF.getPhoto());

		this.validator.validate(result, binding);

		return result;
	}

	public PeriodRecord reconstructEdit(final PeriodRecordForm pRF, final BindingResult binding) {
		PeriodRecord pR;
		final PeriodRecord result = new PeriodRecord();

		pR = this.periodRecordRepository.findOne(pRF.getId());

		result.setId(pR.getId());
		result.setVersion(pR.getVersion());
		result.setBrotherhood(pR.getBrotherhood());
		result.setPhoto(pR.getPhoto());

		result.setTitle(pRF.getTitle());
		result.setDescription(pRF.getDescription());
		if (pRF.getStartYear() == 0)
			result.setStartYear(null);
		else
			result.setStartYear(pRF.getStartYear());
		if (pRF.getEndYear() == 0)
			result.setEndYear(null);
		else
			result.setEndYear(pRF.getEndYear());

		this.validator.validate(result, binding);

		return result;
	}

	public PeriodRecord reconstructAddPhoto(final PeriodRecordForm pRF, final BindingResult binding) {
		PeriodRecord pR;
		final PeriodRecord result = new PeriodRecord();

		pR = this.periodRecordRepository.findOne(pRF.getId());

		result.setTitle(pR.getTitle());
		result.setDescription(pR.getDescription());
		result.setStartYear(pR.getStartYear());
		result.setEndYear(pR.getEndYear());
		result.setBrotherhood(pR.getBrotherhood());

		final Collection<Url> photos = new ArrayList<Url>();

		photos.addAll(pR.getPhoto());

		result.setPhoto(photos);

		if (!pRF.getLink().equals("")) {
			final Url photo = new Url();
			photo.setLink(pRF.getLink());
			result.getPhoto().add(photo);
		}
		result.setId(pR.getId());
		result.setVersion(pR.getVersion());

		return result;
	}

	public Collection<PeriodRecord> getPeriodRecordByBrotherhood(final int BrotherhoodId) {
		Assert.isTrue(this.brotherhoodService.findAll().contains(this.brotherhoodService.findOne(BrotherhoodId)));
		return this.periodRecordRepository.getPeriodRecordByBrotherhood(BrotherhoodId);
	}

}
