
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RequestRepository;
import security.LoginService;
import domain.Actor;
import domain.Brotherhood;
import domain.Enrolment;
import domain.Member;
import domain.Parade;
import domain.Request;

@Service
@Transactional
public class RequestService {

	@Autowired
	private RequestRepository	requestRepository;
	@Autowired
	private MemberService		memberService;
	@Autowired
	private ParadeService	paradeService;
	@Autowired
	private ActorService		actorService;


	public Request create() {
		Request result;
		result = new Request();
		return result;
	}

	public Request findOne(final int id) {
		final Request result = this.requestRepository.findOne(id);
		return result;
	}

	public Collection<Request> findAll() {
		Collection<Request> result;
		result = this.requestRepository.findAll();
		return result;
	}

	public Request save(final Request r) {
		Assert.notNull(r);
		this.requestRepository.flush();
		Request result;
		if (r.getId() == 0) {
			final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
			final Member m = this.memberService.findOne(user.getId());
			r.setMember(m);
			r.setStatus("PENDING");
		}
		result = this.requestRepository.save(r);
		return result;
	}

	public void delete(final Request r) {
		Assert.notNull(r);
		// Meterlo en controlador cuando el usuario member la va a borrar Assert.isTrue(r.getStatus().equals("PENDING"));
		this.requestRepository.delete(r);
	}

	public Request acceptRequest(final Request r) {
		Assert.notNull(r);
		Assert.isTrue(r.getStatus().equals("PENDING"));
		r.setStatus("APPROVED");
		final Request result = this.save(r);
		return result;
	}

	public Request rejectRequest(final Request r) {
		Assert.notNull(r);
		Assert.isTrue(r.getStatus().equals("PENDING"));
		r.setStatus("REJECTED");
		final Request result = this.save(r);
		return result;
	}

	public Collection<Request> getRequestByParade(final Parade p) {
		return this.requestRepository.getRequestByParade(p);
	}
	public Collection<Request> getRequestsByMember(final Member m) {
		return this.requestRepository.getRequestsByMember(m);
	}
	public Collection<Parade> getParadesByMember(final Member m) {
		final Collection<Parade> result = new ArrayList<Parade>();
		final Collection<Enrolment> enrolments = this.memberService.getEnrolments(m.getId());
		final Collection<Brotherhood> brotherhoods = new HashSet<Brotherhood>();
		for (final Enrolment e : enrolments)
			if (e.getDropOutMoment() == null && e.getStatus().equals("ACCEPTED"))
				brotherhoods.add(e.getBrotherhood());
		for (final Brotherhood b : brotherhoods)
			result.addAll(this.paradeService.getParadesFinalByBrotherhood(b.getId()));

		return result;
	}

	public Collection<Request> getRequestAcceptedByParade(final Parade p) {
		return this.requestRepository.getRequestAcceptedByParade(p);
	}

}
