
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AreaRepository;
import domain.Area;

@Service
@Transactional
public class AreaService {

	@Autowired
	private AreaRepository	areaRepository;

	@Autowired
	private ActorService	actorService;


	public Area create() {
		Area result;
		result = new Area();
		result.setPictures(new ArrayList<String>());
		return result;
	}

	public Area findOne(final int id) {
		final Area result = this.areaRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}

	public Collection<Area> findAll() {
		final Collection<Area> result = this.areaRepository.findAll();
		return result;
	}

	public Area save(final Area a) {
		Assert.notNull(a);
		Assert.isTrue(this.actorService.getActorLogged().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("ADMIN"));
		Area result;
		result = this.areaRepository.save(a);
		return result;
	}

	public void delete(final Area a) {
		Assert.notNull(a);
		Assert.isTrue(this.canBeDeleted(a));
		this.areaRepository.delete(a);
	}

	private boolean canBeDeleted(final Area a) {
		boolean b;
		final Collection<Area> areas = this.areaRepository.canBeDeleted();
		b = areas.contains(a);
		return b;
	}
}
