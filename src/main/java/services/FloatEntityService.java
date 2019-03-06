
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FloatEntityRepository;
import security.LoginService;
import datatype.Url;
import domain.Actor;
import domain.Brotherhood;
import domain.FloatEntity;
import domain.Procession;

@Service
@Transactional
public class FloatEntityService {

	@Autowired
	private FloatEntityRepository	floatRepository;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ProcessionService		processionService;

	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private Validator				validator;


	public FloatEntity reconstruct(final FloatEntity f, final BindingResult binding) {
		FloatEntity result;

		if (f.getId() == 0) {
			result = f;
			this.validator.validate(f, binding);
		} else {
			result = this.floatRepository.findOne(f.getId());

			f.setBrotherhood(result.getBrotherhood());
			f.setVersion(result.getVersion());

			result = f;
			this.validator.validate(f, binding);
		}

		return result;

	}

	public FloatEntity create() {
		FloatEntity res;

		res = new FloatEntity();
		res.setPictures(new ArrayList<Url>());

		return res;
	}

	public Collection<FloatEntity> findAll() {
		Collection<FloatEntity> res;
		res = this.floatRepository.findAll();
		return res;
	}

	public FloatEntity findOne(final int floatEntityId) {
		FloatEntity res;
		res = this.floatRepository.findOne(floatEntityId);
		return res;
	}

	public FloatEntity save(final FloatEntity floatEntity) {
		FloatEntity res;
		Assert.notNull(floatEntity);
		Assert.isTrue(this.actorService.getActorLogged().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
		final Brotherhood b = this.brotherhoodService.findOne(user.getId());
		if (floatEntity.getId() == 0) {
			floatEntity.setBrotherhood(b);
			res = this.floatRepository.save(floatEntity);
		} else {
			Assert.isTrue(floatEntity.getBrotherhood().equals(b));
			res = this.floatRepository.save(floatEntity);
		}
		return res;
	}

	public void delete(final FloatEntity floatEntity) {
		Assert.notNull(floatEntity);
		Assert.isTrue(this.actorService.getActorLogged().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));

		Collection<Procession> processions;
		processions = this.processionService.findAll();

		for (final Procession p : processions) {
			Collection<FloatEntity> floats;
			floats = p.getFloats();
			if (floats.contains(floatEntity)) {
				floats.remove(floatEntity);
				p.setFloats(floats);
			}
		}

		this.floatRepository.delete(floatEntity);

	}

	public Collection<FloatEntity> getFloatsByBrotherhood(final Brotherhood b) {
		Collection<FloatEntity> result;
		result = this.floatRepository.getFloatsByBrotherhood(b);
		return result;
	}
}
