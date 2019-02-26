
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FloatEntityRepository;
import domain.FloatEntity;

@Service
@Transactional
public class FloatEntityService {

	@Autowired
	private FloatEntityRepository	floatRepository;


	public FloatEntity create() {
		FloatEntity res;

		res = new FloatEntity();

		return res;
	}

	public Collection<FloatEntity> findAll() {
		Collection<FloatEntity> res;
		res = this.floatRepository.findAll();
		return res;
	}

	public FloatEntity findOne(final Integer floatEntityId) {
		Assert.notNull(floatEntityId);
		FloatEntity res;
		res = this.floatRepository.findOne(floatEntityId);
		Assert.notNull(res);
		return res;
	}

	public FloatEntity save(final FloatEntity floatEntity) {
		FloatEntity res;
		Assert.notNull(floatEntity);
		res = this.floatRepository.save(floatEntity);
		return res;
	}
}
