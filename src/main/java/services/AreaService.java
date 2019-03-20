
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AreaRepository;
import datatype.Url;
import domain.Area;
import domain.Brotherhood;
import domain.Chapter;

@Service
@Transactional
public class AreaService {

	@Autowired
	private AreaRepository	areaRepository;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private Validator		validator;


	public Area reconstruct(final Area a, final BindingResult binding) {
		Area result;

		if (a.getId() == 0) {
			this.validator.validate(a, binding);
			result = a;
		} else {
			result = this.areaRepository.findOne(a.getId());

			//result.setName(a.getName());
			//result.setPictures(a.getPictures());

			a.setVersion(result.getVersion());
			result = a;
			this.validator.validate(a, binding);
		}

		return result;

	}

	public Area create() {
		Area result;
		result = new Area();
		result.setPictures(new ArrayList<Url>());
		return result;
	}

	public Area findOne(final int id) {
		final Area result = this.areaRepository.findOne(id);
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
		Assert.isTrue(this.actorService.getActorLogged().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("ADMIN"));
		this.areaRepository.delete(a);
	}

	private boolean canBeDeleted(final Area a) {
		boolean b;
		final Collection<Area> areas = this.areaRepository.canBeDeleted();
		b = areas.contains(a);
		return b;
	}

	public Chapter getChapter(final int areaId) {
		Assert.notNull(areaId);

		final Chapter result = this.areaRepository.getChapter(areaId);

		return result;
	}

	public Collection<Brotherhood> getBrotherhood(final int areaId) {
		Assert.notNull(areaId);
		final Collection<Brotherhood> result = this.areaRepository.getBrotherhood(areaId);
		return result;
	}
}
