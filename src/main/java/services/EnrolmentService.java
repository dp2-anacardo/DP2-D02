
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EnrolmentRepository;
import security.LoginService;
import security.UserAccount;
import domain.Brotherhood;
import domain.Enrolment;
import domain.Member;
import domain.Position;

@Service
@Transactional
public class EnrolmentService {

	@Autowired
	private EnrolmentRepository	enrolmentRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private MemberService		memberService;


	public Enrolment create() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		Enrolment enrolment;
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("MEMBER"));

		enrolment = new Enrolment();
		enrolment.setPosition(new Position());
		final Member member = this.memberService.findOne(this.actorService.getActorLogged().getId());
		enrolment.setMember(member);
		enrolment.setStatus("PENDING");
		enrolment.setRegisterMoment(new Date());
		enrolment.setBrotherhood(new Brotherhood());
		enrolment.setDropOutMoment(null);

		return enrolment;
	}

	public void dropOut(final Enrolment enrolment) {
		Assert.notNull(enrolment);
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("MEMBER"));
		Assert.isTrue(enrolment.getStatus().equals("ACCEPTED"));
		enrolment.setDropOutMoment(new Date());
	}

	public void acceptEnrolment(final Enrolment enrolment) {
		Assert.notNull(enrolment);
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		Assert.isTrue(enrolment.getStatus().equals("PENDING"));
		enrolment.setStatus("ACCEPTED");
	}

	public void rejectEnrolment(final Enrolment enrolment) {
		Assert.notNull(enrolment);
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		Assert.isTrue(enrolment.getStatus().equals("PENDING"));
		enrolment.setStatus("REJECTED");
	}

	public Enrolment save(final Enrolment enrolment) {
		Enrolment result;
		Assert.notNull(enrolment);

		result = this.enrolmentRepository.save(enrolment);
		Assert.notNull(result);

		return result;
	}

	public Enrolment findOne(final Integer enrolmentId) {
		Assert.notNull(enrolmentId);

		final Enrolment enrolment = this.enrolmentRepository.findOne(enrolmentId);
		Assert.notNull(enrolment);

		return enrolment;
	}

	public Collection<Enrolment> findAll() {
		Collection<Enrolment> enrolments;

		enrolments = this.enrolmentRepository.findAll();

		return enrolments;
	}

}
