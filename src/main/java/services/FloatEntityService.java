
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FloatEntityRepository;
import datatype.Url;
import domain.Brotherhood;
import domain.FloatEntity;

@Service
@Transactional
public class FloatEntityService {

	@Autowired
	private FloatEntityRepository	floatRepository;

	@Autowired
	private ActorService			actorService;


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
		Assert.notNull(res);
		return res;
	}

	public FloatEntity save(final FloatEntity floatEntity) {
		FloatEntity res;
		Assert.notNull(floatEntity);
		Assert.isTrue(this.actorService.getActorLogged().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		res = this.floatRepository.save(floatEntity);
		return res;
	}

	//TODO: Queda borrar Float de las Procession---- > Falta Collection en Dominio Procession
	public void delete(final FloatEntity floatEntity) {
		Assert.notNull(floatEntity);
		Assert.isTrue(this.actorService.getActorLogged().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		this.floatRepository.delete(floatEntity);

	}

	public Collection<FloatEntity> getFloatsByBrotherhood(final Brotherhood b) {
		Collection<FloatEntity> result;
		result = this.floatRepository.getFloatsByBrotherhood(b);
		return result;
	}
}
