
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FloatEntityRepository;
import datatype.Url;
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
}
