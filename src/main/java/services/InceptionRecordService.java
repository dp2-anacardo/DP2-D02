
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

import repositories.InceptionRecordRepository;
import datatype.Url;
import domain.InceptionRecord;
import forms.InceptionRecordForm;

@Service
@Transactional
public class InceptionRecordService {

	@Autowired
	private InceptionRecordRepository	inceptionRecordRepository;
	@Autowired
	private BrotherhoodService			brotherhoodService;
	@Autowired
	private Validator					validator;


	//CRUD
	public InceptionRecord create() {

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
		res = this.inceptionRecordRepository.findOne(inceptionRecordId);
		Assert.notNull(res);
		return res;
	}

	public InceptionRecord save(final InceptionRecord inceptionRecord) {

		Assert.notNull(inceptionRecord);
		final InceptionRecord res = this.inceptionRecordRepository.save(inceptionRecord);
		return res;
	}

	public void delete(final InceptionRecord inceptionRecord) {

		Assert.notNull(inceptionRecord);
		Assert.isTrue(!this.brotherhoodService.findAll().contains(inceptionRecord.getBrotherhood()));

		Assert.isTrue(inceptionRecord.getId() != 0);
		this.inceptionRecordRepository.delete(inceptionRecord);
	}
	//Reconstructs
	public InceptionRecord reconstructCreate(final InceptionRecordForm iRF, final BindingResult binding) {
		final InceptionRecord result = new InceptionRecord();

		result.setPhoto(new ArrayList<Url>());

		result.setTitle(iRF.getTitle());
		result.setDescription(iRF.getDescription());

		this.validator.validate(result, binding);

		return result;
	}

	public InceptionRecord reconstructEdit(final InceptionRecordForm iRF, final BindingResult binding) {
		InceptionRecord iR;
		final InceptionRecord result = new InceptionRecord();

		iR = this.inceptionRecordRepository.findOne(iRF.getId());

		result.setId(iR.getId());
		result.setVersion(iR.getVersion());
		result.setBrotherhood(iR.getBrotherhood());
		result.setPhoto(iR.getPhoto());

		result.setTitle(iRF.getTitle());
		result.setDescription(iRF.getDescription());

		this.validator.validate(result, binding);

		return result;
	}

	public InceptionRecord reconstructAddPhoto(final InceptionRecordForm iRF, final BindingResult binding) {
		InceptionRecord iR;
		final InceptionRecord result = new InceptionRecord();

		iR = this.inceptionRecordRepository.findOne(iRF.getId());

		result.setTitle(iR.getTitle());
		result.setDescription(iR.getDescription());
		result.setBrotherhood(iR.getBrotherhood());

		final Collection<Url> photos = new ArrayList<Url>();

		photos.addAll(iR.getPhoto());

		result.setPhoto(photos);

		if (!iRF.getLink().equals("")) {
			final Url photo = new Url();
			photo.setLink(iRF.getLink());
			result.getPhoto().add(photo);
		}

		result.setId(iR.getId());
		result.setVersion(iR.getVersion());

		return result;
	}

	public Collection<InceptionRecord> getInceptionRecordByBrotherhood(final int BrotherhoodId) {
		Assert.isTrue(this.brotherhoodService.findAll().contains(this.brotherhoodService.findOne(BrotherhoodId)));
		return this.inceptionRecordRepository.getInceptionRecordByBrotherhood(BrotherhoodId);
	}

	public void flush() {
		this.inceptionRecordRepository.flush();
	}
}
