
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PriorityRepository;
import domain.Actor;
import domain.Priority;

@Service
@Transactional
public class PriorityService {

	@Autowired
	private PriorityRepository	priorityRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;


	public Priority create() {

		Priority result;
		result = new Priority();
		return result;

	}

	public Priority findOne(final Integer priorityId) {

		Priority result;
		Assert.notNull(priorityId);
		result = this.priorityRepository.findOne(priorityId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Priority> findAll() {

		Collection<Priority> result;
		result = this.priorityRepository.findAll();
		Assert.notEmpty(result);
		return result;
	}

	public Priority save(final Priority priority) {

		Priority result;
		Assert.notNull(priority);
		final Actor actor = this.actorService.getActorLogged();
		Assert.isTrue(actor.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("ADMINISTRATOR"));
		result = this.priorityRepository.save(priority);
		return result;

	}

	public void delete(final Priority priority) {
		Assert.notNull(priority);
		final Actor actor = this.actorService.getActorLogged();
		Assert.isTrue(actor.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("ADMINISTRATOR"));
		this.priorityRepository.delete(priority);
	}

	public Priority reconstruct(final Priority priority, final BindingResult binding) {
		Priority result;

		if (priority.getId() == 0)
			result = priority;
		else {
			result = this.priorityRepository.findOne(priority.getId());

			result.setName(priority.getName());

			this.validator.validate(result, binding);
		}

		return result;

	}

	public Priority getHighPriority() {
		return this.priorityRepository.getHighPriority();
	}
}
