
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ProclaimRepository;
import security.LoginService;
import domain.Chapter;
import domain.Proclaim;

@Service
@Transactional
public class ProclaimService {

	@Autowired
	private ProclaimRepository	proclaimRepository;

	@Autowired
	private ChapterService		chapterService;

	@Autowired
	private ActorService		actorService;


	public Proclaim create() {
		Proclaim result;

		Assert.isTrue(LoginService.getPrincipal().getAuthorities().iterator().next().getAuthority().equals("CHAPTER"));
		final Chapter chapter = this.chapterService.findOne(this.actorService.getActorLogged().getId());

		result = new Proclaim();
		result.setMoment(new Date());
		result.setChapter(chapter);
		result.setDescription("");

		return result;
	}

	public Proclaim save(final Proclaim proclaim) {
		Proclaim result;
		Assert.notNull(proclaim);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().iterator().next().getAuthority().equals("CHAPTER"));

		result = this.proclaimRepository.save(proclaim);

		return result;
	}

	public Proclaim findOne(final int proclaimId) {
		Proclaim result;
		Assert.notNull(proclaimId);

		result = this.proclaimRepository.findOne(proclaimId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Proclaim> findAll() {
		Collection<Proclaim> result;

		result = this.proclaimRepository.findAll();

		return result;
	}

	public void flush() {
		this.proclaimRepository.flush();
	}

}
