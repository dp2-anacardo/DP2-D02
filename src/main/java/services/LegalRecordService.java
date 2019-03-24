
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

import repositories.LegalRecordRepository;
import security.LoginService;
import domain.Actor;
import domain.Brotherhood;
import domain.LegalRecord;
import forms.LegalRecordForm;

@Service
@Transactional
public class LegalRecordService {

	@Autowired
	private LegalRecordRepository	legalRecordRepository;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private BrotherhoodService		brotherhoodService;
	@Autowired
	private Validator				validator;


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

	//Reconstructs
	public LegalRecord reconstructCreate(final LegalRecordForm lRF, final BindingResult binding) {
		final LegalRecord result = new LegalRecord();

		result.setApplicableLaws(lRF.getApplicableLaws());

		result.setTitle(lRF.getTitle());
		result.setDescription(lRF.getDescription());
		result.setLegalName(lRF.getLegalName());
		result.setVatNumber(lRF.getVatNumber());

		this.validator.validate(result, binding);

		return result;
	}
	public LegalRecord reconstructEdit(final LegalRecordForm lRF, final BindingResult binding) {
		LegalRecord lR;
		final LegalRecord result = new LegalRecord();

		lR = this.legalRecordRepository.findOne(lRF.getId());

		result.setId(lR.getId());
		result.setVersion(lR.getVersion());
		result.setBrotherhood(lR.getBrotherhood());
		result.setApplicableLaws(lR.getApplicableLaws());

		result.setTitle(lRF.getTitle());
		result.setDescription(lRF.getDescription());
		result.setLegalName(lRF.getLegalName());
		result.setVatNumber(lRF.getVatNumber());

		this.validator.validate(result, binding);

		return result;
	}

	public LegalRecord reconstructAddLaw(final LegalRecordForm lRF, final BindingResult binding) {
		LegalRecord lR;
		final LegalRecord result = new LegalRecord();

		lR = this.legalRecordRepository.findOne(lRF.getId());

		result.setTitle(lR.getTitle());
		result.setDescription(lR.getDescription());
		result.setBrotherhood(lR.getBrotherhood());
		result.setLegalName(lR.getLegalName());
		result.setVatNumber(lR.getVatNumber());

		final Collection<String> laws = new ArrayList<String>();

		laws.addAll(lR.getApplicableLaws());

		result.setApplicableLaws(laws);

		if (!lRF.getLaw().equals(""))
			result.getApplicableLaws().add(lRF.getLaw());

		result.setId(lR.getId());
		result.setVersion(lR.getVersion());

		return result;
	}

	public Collection<LegalRecord> getLegalRecordByBrotherhood(final int BrotherhoodId) {
		return this.legalRecordRepository.getLegalRecordByBrotherhood(BrotherhoodId);
	}

}
