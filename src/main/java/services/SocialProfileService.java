
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SocialProfileRepository;
import domain.Actor;
import domain.SocialProfile;

@Service
@Transactional
public class SocialProfileService {

	@Autowired
	private SocialProfileRepository	socialProfileRepository;

	@Autowired
	private ActorService			actorService;


	public SocialProfile create() {

		SocialProfile result;
		result = new SocialProfile();
		return result;

	}

	public SocialProfile findOne(final Integer socialProfileId) {

		SocialProfile result;
		Assert.notNull(socialProfileId);
		result = this.socialProfileRepository.findOne(socialProfileId);
		Assert.notNull(result);
		return result;
	}

	public List<SocialProfile> findAll() {

		List<SocialProfile> result;
		result = this.socialProfileRepository.findAll();
		Assert.notEmpty(result);
		return result;
	}

	public SocialProfile save(final SocialProfile socialProfile) {

		SocialProfile result;
		Assert.notNull(socialProfile);
		final Actor user = this.actorService.getActorLogged();
		result = this.socialProfileRepository.save(socialProfile);
		if (socialProfile.getId() == 0)
			user.getSocialProfiles().add(result);
		return result;

	}

	public void delete(final SocialProfile socialProfile) {

		final Actor actor = this.actorService.getActorLogged();
		Assert.notNull(socialProfile);
		Assert.isTrue(this.socialProfileRepository.exists(socialProfile.getId()));
		final Collection<SocialProfile> result = actor.getSocialProfiles();
		Assert.notEmpty(result);
		result.remove(socialProfile);
		this.socialProfileRepository.delete(socialProfile.getId());
	}
}
