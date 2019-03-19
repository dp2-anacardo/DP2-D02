
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.SegmentRepository;
import domain.Segment;

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


	public Segment create() {
		final Segment result = new Segment();
		return result;
	}

	public Segment findOne(final int segmentId) {
		return this.segmentRepository.findOne(segmentId);
	}

	public Collection<Segment> findAll() {
		return this.segmentRepository.findAll();
	}
	//TODO Revisar el save y delete
	//	public Segment save(final Segment s) {
	//		Segment result;
	//		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
	//		final Brotherhood b = this.brotherhoodService.findOne(user.getId());
	//		Assert.isTrue(this.paradeService.getParadesByBrotherhood(b).contains(s.getParade()));
	//
	//		final List<Segment> segments = (List<Segment>) this.getPathByParade(s.getParade().getId());
	//		if (segments.size() > 0)
	//			Assert.isTrue(segments.get(segments.size() - 1).getDestination().equals(s.getOrigin()));
	//		result = this.segmentRepository.save(s);
	//		return result;
	//	}
	//
	//	public void delete(final Segment s) {
	//		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
	//		final Brotherhood b = this.brotherhoodService.findOne(user.getId());
	//		Assert.isTrue(this.paradeService.getParadesByBrotherhood(b).contains(s.getParade()));
	//		final List<Segment> segments = (List<Segment>) this.getPathByParade(s.getParade().getId());
	//		final int posicion = segments.indexOf(s);
	//		if (posicion != segments.size() - 1 && posicion != 0) {
	//			//Si el segment no es el primero o el ultimo le pongo al siguiente segmento como origen el destino del anterior al que estoy borrando
	//			segments.get(posicion + 1).setOrigin(segments.get(posicion - 1).getDestination());
	//			this.segmentRepository.delete(s);
	//		} else
	//			this.segmentRepository.delete(s);
	//	}

	public Collection<Segment> getPathByParade(final int paradeId) {
		final Collection<Segment> result = new ArrayList<Segment>();
		result.addAll(this.segmentRepository.getPathByParade(paradeId));
		return result;
	}

}
