
package services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ProcessionRepository;
import security.LoginService;
import domain.Actor;
import domain.Brotherhood;
import domain.Finder;
import domain.Procession;
import domain.Request;
import domain.Segment;

@Service
@Transactional
public class ProcessionService {

	@Autowired
	ProcessionRepository	processionRepository;
	@Autowired
	Validator				validator;
	@Autowired
	ActorService			actorService;
	@Autowired
	BrotherhoodService		brotherhoodService;
	@Autowired
	RequestService			requestService;
	@Autowired
	FinderService			finderService;
	@Autowired
	SegmentService			segmentService;


	public Procession reconstruct(final Procession p, final BindingResult binding) {
		Procession result;
		if (p.getId() == 0) {
			p.setTicker(this.tickerGenerator());
			result = p;
			this.validator.validate(p, binding);
		} else {
			result = this.processionRepository.findOne(p.getId());
			p.setBrotherhood(result.getBrotherhood());
			p.setTicker(result.getTicker());
			p.setVersion(result.getVersion());
			p.setIsFinal(result.getIsFinal());
			this.validator.validate(p, binding);
			result = p;
		}
		return result;

	}

	public Procession create() {
		Procession result;
		result = new Procession();
		result.setIsFinal(false);
		return result;
	}

	public Procession findOne(final int id) {
		Procession result;
		result = this.processionRepository.findOne(id);
		return result;
	}

	public Collection<Procession> findAll() {
		final Collection<Procession> result = this.processionRepository.findAll();
		return result;
	}

	public Procession save(final Procession p) {
		Assert.notNull(p);
		Assert.isTrue(this.actorService.getActorLogged().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
		final Brotherhood b = this.brotherhoodService.findOne(user.getId());
		Procession result;
		if (p.getId() == 0) {
			p.setTicker(this.tickerGenerator());
			p.setBrotherhood(b);
			result = this.processionRepository.save(p);
		} else {
			Assert.isTrue(p.getBrotherhood().equals(b));
			result = this.processionRepository.save(p);
		}
		return result;
	}

	public Procession saveDraft(final Procession p) {
		Assert.notNull(p);
		p.setIsFinal(false);
		final Procession result = this.save(p);
		return result;
	}

	public Procession saveFinal(final Procession p) {
		Assert.notNull(p);
		p.setIsFinal(true);
		p.setStatus("SUBMITTED");
		final Procession result = this.save(p);
		return result;
	}

	public void delete(final Procession p) {
		Assert.notNull(p);
		Assert.isTrue(this.actorService.getActorLogged().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
		final Brotherhood b = this.brotherhoodService.findOne(user.getId());
		Assert.isTrue(p.getBrotherhood().equals(b));
		final Collection<Request> requests = this.requestService.getRequestByProcession(p);
		for (final Request r : requests)
			this.requestService.delete(r);
		final Collection<Finder> finders = this.getFinders();
		for (final Finder f : finders)
			if (f.getProcessions().contains(p))
				f.getProcessions().remove(p);
		final Collection<Segment> segments = this.segmentService.getPathByParade(p.getId());
		for (final Segment s : segments)
			this.segmentService.delete(s);
		this.processionRepository.delete(p);
	}

	public Collection<Procession> getProcessionsByBrotherhood(final Brotherhood b) {
		Collection<Procession> result;
		result = this.processionRepository.getProcessionsByBrotherhood(b);
		return result;
	}

	public Collection<Finder> getFinders() {
		return this.processionRepository.getFinders();
	}

	public Collection<Procession> getProcessionsFinalByBrotherhood(final int id) {
		Collection<Procession> result;
		result = this.processionRepository.getProcessionsFinalByBrotherhood(id);
		return result;
	}

	private String tickerGenerator() {
		String dateRes = "";
		String numericRes = "";
		final String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		dateRes = new SimpleDateFormat("yyMMdd").format(Calendar.getInstance().getTime());

		for (int i = 0; i < 5; i++) {
			final Random random = new Random();
			numericRes = numericRes + alphanumeric.charAt(random.nextInt(alphanumeric.length() - 1));
		}

		return dateRes + "-" + numericRes;
	}

	public Procession copyParade(final int paradeId) {
		Procession result = this.create();
		final Procession p1 = this.findOne(paradeId);
		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
		final Brotherhood b = this.brotherhoodService.findOne(user.getId());
		Assert.isTrue(p1.getBrotherhood().equals(b));
		result.setBrotherhood(b);
		result.setDescription(p1.getDescription());
		result.setFloats(p1.getFloats());
		result.setMaxColumn(p1.getMaxColumn());
		result.setMaxRow(p1.getMaxRow());
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 1);
		final Date nextYear = cal.getTime();
		result.setMoment(nextYear);
		result.setTicker(this.tickerGenerator());
		result.setTitle(p1.getTitle());
		result = this.saveDraft(result);

		final Collection<Segment> segments = this.segmentService.getPathByParade(p1.getId());
		for (final Segment s : segments) {
			final Segment newSegment = this.segmentService.create();
			newSegment.setParade(result);
			newSegment.setOrigin(s.getOrigin());
			newSegment.setDestination(s.getDestination());
			newSegment.setTimeOrigin(s.getTimeOrigin());
			newSegment.setTimeDestination(s.getTimeDestination());
			this.segmentService.save(newSegment);
		}
		return result;
	}
}
