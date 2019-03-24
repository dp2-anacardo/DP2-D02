
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

import repositories.ParadeRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Brotherhood;
import domain.Chapter;
import domain.Finder;
import domain.Parade;
import domain.Request;
import domain.Segment;

@Service
@Transactional
public class ParadeService {

	@Autowired
	ParadeRepository	paradeRepository;
	@Autowired
	Validator			validator;
	@Autowired
	ActorService		actorService;
	@Autowired
	BrotherhoodService	brotherhoodService;
	@Autowired
	RequestService		requestService;
	@Autowired
	FinderService		finderService;
	@Autowired
	SegmentService		segmentService;
	@Autowired
	ChapterService		chapterService;


	public Parade reconstruct(final Parade p, final BindingResult binding) {
		Parade result;
		if (p.getId() == 0) {
			p.setTicker(this.tickerGenerator());
			result = p;
			this.validator.validate(p, binding);
		} else {
			result = this.paradeRepository.findOne(p.getId());
			p.setBrotherhood(result.getBrotherhood());
			p.setTicker(result.getTicker());
			p.setVersion(result.getVersion());
			p.setIsFinal(result.getIsFinal());
			this.validator.validate(p, binding);
			result = p;
		}
		return result;
	}

	public Parade reconstructChapter(final Parade p, final BindingResult binding) {
		Parade result;
		if (p.getId() == 0) {
			p.setTicker(this.tickerGenerator());
			result = p;
			this.validator.validate(p, binding);
		} else {
			result = this.paradeRepository.findOne(p.getId());
			p.setBrotherhood(result.getBrotherhood());
			p.setDescription(result.getDescription());
			p.setFloats(result.getFloats());
			p.setMaxColumn(result.getMaxColumn());
			p.setMaxRow(result.getMaxRow());
			p.setTitle(result.getTitle());
			p.setMoment(result.getMoment());
			p.setTicker(result.getTicker());
			p.setStatus(result.getStatus());
			p.setVersion(result.getVersion());
			p.setIsFinal(result.getIsFinal());
			this.validator.validate(p, binding);
			result = p;
		}
		return result;
	}

	public Parade create() {
		Parade result;
		result = new Parade();
		result.setIsFinal(false);
		result.setRejectComment("");
		return result;
	}

	public Parade findOne(final int id) {
		Parade result;
		result = this.paradeRepository.findOne(id);
		return result;
	}

	public Collection<Parade> findAll() {
		final Collection<Parade> result = this.paradeRepository.findAll();
		return result;
	}

	public Parade save(final Parade p) {
		Assert.notNull(p);
		Assert.isTrue(this.actorService.getActorLogged().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
		final Brotherhood b = this.brotherhoodService.findOne(user.getId());
		Parade result;
		if (p.getId() == 0) {
			p.setTicker(this.tickerGenerator());
			p.setBrotherhood(b);
			result = this.paradeRepository.save(p);
		} else {
			Assert.isTrue(p.getBrotherhood().equals(b));
			result = this.paradeRepository.save(p);
		}
		return result;
	}

	public Parade saveChapter(final Parade p) {
		Assert.notNull(p);
		Assert.isTrue(this.actorService.getActorLogged().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("CHAPTER"));
		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
		final Chapter c = this.chapterService.findOne(user.getId());
		Parade result;
		Assert.isTrue(p.getBrotherhood().getArea().equals(c.getArea()));
		result = this.paradeRepository.save(p);
		return result;
	}

	public Parade saveDraft(final Parade p) {
		Assert.notNull(p);
		p.setIsFinal(false);
		final Parade result = this.save(p);
		return result;
	}

	public Parade saveFinal(final Parade p) {
		Assert.notNull(p);
		p.setIsFinal(true);
		p.setStatus("SUBMITTED");
		final Parade result = this.save(p);
		return result;
	}

	public void delete(final Parade p) {
		Assert.notNull(p);
		Assert.isTrue(this.actorService.getActorLogged().getUserAccount().getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
		final Brotherhood b = this.brotherhoodService.findOne(user.getId());
		Assert.isTrue(p.getBrotherhood().equals(b));
		final Collection<Request> requests = this.requestService.getRequestByParade(p);
		for (final Request r : requests)
			this.requestService.delete(r);
		final Collection<Finder> finders = this.getFinders();
		for (final Finder f : finders)
			if (f.getParades().contains(p))
				f.getParades().remove(p);
		final Collection<Segment> segments = this.segmentService.getPathByParade(p.getId());
		for (final Segment s : segments)
			this.segmentService.delete(s);

		this.paradeRepository.delete(p);
	}

	public Collection<Parade> getParadesByBrotherhood(final Brotherhood b) {
		Collection<Parade> result;
		result = this.paradeRepository.getParadesByBrotherhood(b);
		return result;
	}

	public Collection<Finder> getFinders() {
		return this.paradeRepository.getFinders();
	}

	public Collection<Parade> getParadesFinalByBrotherhood(final int id) {
		Collection<Parade> result;
		result = this.paradeRepository.getParadesFinalByBrotherhood(id);
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

	public Parade copyParade(final int paradeId) {
		Parade result = this.create();
		final Parade p1 = this.findOne(paradeId);
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
			final Segment newSegment = this.segmentService.create(result);
			newSegment.setOriginLatitude(s.getOriginLatitude());
			newSegment.setOriginLongitude(s.getOriginLongitude());
			newSegment.setDestinationLatitude(s.getDestinationLatitude());
			newSegment.setDestinationLongitude(s.getDestinationLongitude());
			newSegment.setTimeOrigin(s.getTimeOrigin());
			newSegment.setTimeDestination(s.getTimeDestination());
			this.segmentService.save(newSegment);
		}
		return result;
	}

	public Collection<Parade> getParadesFinal() {
		Collection<Parade> result;
		result = this.paradeRepository.getParadesFinal();
		return result;
	}

	public void acceptParade(final Parade parade) {
		Assert.notNull(parade);
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("CHAPTER"));
		Assert.isTrue(parade.getStatus().equals("SUBMITTED"));
		parade.setStatus("ACCEPTED");
	}

	public void rejectParade(final Parade parade) {
		Assert.notNull(parade);
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("CHAPTER"));
		Assert.isTrue(parade.getStatus().equals("SUBMITTED"));
		parade.setStatus("REJECTED");
	}

}
