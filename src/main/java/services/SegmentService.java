
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Actor;
import domain.Brotherhood;
import domain.Parade;
import domain.Segment;
import repositories.SegmentRepository;
import security.LoginService;

@Service
@Transactional
public class SegmentService {

	@Autowired
	private SegmentRepository	segmentRepository;
	@Autowired
	private BrotherhoodService	brotherhoodService;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private ParadeService		paradeService;
	@Autowired
	private Validator			validator;


	public Segment reconstruct(final Segment s, final BindingResult binding) {
		Segment result;
		if (s.getId() == 0)
			result = s;
		else {
			result = this.findOne(s.getId());
			result.setDestinationLatitude(s.getDestinationLatitude());
			result.setDestinationLongitude(s.getDestinationLongitude());
			result.setOriginLatitude(s.getOriginLatitude());
			result.setOriginLongitude(s.getOriginLongitude());
			result.setTimeDestination(s.getTimeDestination());
			result.setTimeOrigin(s.getTimeOrigin());
		}

		this.validator.validate(result, binding);
		if (binding.hasErrors())
			throw new ValidationException();
		return result;
	}

	public Segment create(final Parade parade) {
		final Segment result = new Segment();
		result.setParade(parade);
		return result;
	}

	public Segment findOne(final int segmentId) {
		return this.segmentRepository.findOne(segmentId);
	}

	public Collection<Segment> findAll() {
		return this.segmentRepository.findAll();
	}

	public Segment save(final Segment s) {
		Segment result;
		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
		final Brotherhood b = this.brotherhoodService.findOne(user.getId());
		Assert.isTrue(this.paradeService.getParadesByBrotherhood(b).contains(s.getParade()));

		final List<Segment> segments = (List<Segment>) this.getPathByParade(s.getParade().getId());
		if (s.getId() == 0) {
			if (segments.size() > 0) {
				final Segment left = segments.get(segments.size() - 1);
				Assert.isTrue(left.getDestinationLatitude().equals(s.getOriginLatitude()) && left.getDestinationLongitude().equals(s.getOriginLongitude()));
			}
		} else if (segments.indexOf(s) != 0) {
			final Segment left = segments.get(segments.indexOf(s) - 1);
			Assert.isTrue(left.getDestinationLatitude().equals(s.getOriginLatitude()) && left.getDestinationLongitude().equals(s.getOriginLongitude()));

		}
		result = this.segmentRepository.save(s);
		return result;
	}

	public void delete(final Segment s) {
		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
		final Brotherhood b = this.brotherhoodService.findOne(user.getId());
		Assert.isTrue(this.paradeService.getParadesByBrotherhood(b).contains(s.getParade()));
		final List<Segment> segments = (List<Segment>) this.getPathByParade(s.getParade().getId());
		final int posicion = segments.indexOf(s);
		if (posicion != segments.size() - 1 && posicion != 0) {
			//Si el segment no es el primero o el ultimo le pongo al siguiente segmento como origen el destino del anterior al que estoy borrando
			segments.get(posicion + 1).setOriginLatitude(segments.get(posicion - 1).getDestinationLatitude());
			segments.get(posicion + 1).setOriginLongitude(segments.get(posicion - 1).getDestinationLongitude());
			this.segmentRepository.delete(s);
		} else
			this.segmentRepository.delete(s);
	}

	public Collection<Segment> getPathByParade(final int paradeId) {
		final Collection<Segment> result = new ArrayList<Segment>();
		result.addAll(this.segmentRepository.getPathByParade(paradeId));
		return result;
	}

}
