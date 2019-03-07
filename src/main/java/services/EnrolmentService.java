
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.EnrolmentRepository;
import security.LoginService;
import security.UserAccount;
import domain.Brotherhood;
import domain.Enrolment;
import domain.Member;

@Service
@Transactional
public class EnrolmentService {

	@Autowired
	private EnrolmentRepository	enrolmentRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private MemberService		memberService;

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private PositionService		positionService;

	@Autowired
	private Validator			validator;


	public Enrolment create(final int brotherhoodId) {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Enrolment enrolment;

		Assert.notNull(brotherhoodId);
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("MEMBER"));

		enrolment = new Enrolment();
		final Brotherhood brotherhood = this.brotherhoodService.findOne(brotherhoodId);
		enrolment.setPosition(this.positionService.getDefaultPosition());
		final Member member = this.memberService.findOne(this.actorService.getActorLogged().getId());
		enrolment.setMember(member);
		enrolment.setStatus("PENDING");
		enrolment.setRegisterMoment(new Date());
		enrolment.setBrotherhood(brotherhood);
		enrolment.setDropOutMoment(null);

		return enrolment;
	}

	public void dropOut(final Enrolment enrolment) {
		Assert.notNull(enrolment);
		Assert.isTrue(enrolment.getStatus().equals("ACCEPTED"));
		enrolment.setDropOutMoment(new Date());
	}

	public void acceptEnrolment(final Enrolment enrolment) {
		Assert.notNull(enrolment);
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		Assert.isTrue(enrolment.getStatus().equals("PENDING"));
		Assert.notNull(enrolment.getBrotherhood().getArea());
		enrolment.setStatus("ACCEPTED");
	}

	public void rejectEnrolment(final Enrolment enrolment) {
		Assert.notNull(enrolment);
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));
		Assert.isTrue(enrolment.getStatus().equals("PENDING"));
		Assert.notNull(enrolment.getBrotherhood().getArea());
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

	public Enrolment reconstruct(final Enrolment enrolment, final BindingResult binding) {
		Enrolment result;

		if (enrolment.getId() == 0)
			result = enrolment;
		else {
			result = this.enrolmentRepository.findOne(enrolment.getId());
			enrolment.setRegisterMoment(result.getRegisterMoment());
			enrolment.setDropOutMoment(result.getDropOutMoment());
			enrolment.setBrotherhood(result.getBrotherhood());
			enrolment.setStatus(result.getStatus());
			enrolment.setMember(result.getMember());
			enrolment.setVersion(result.getVersion());

			result = enrolment;

			this.validator.validate(result, binding);
		}

		return result;
	}

}
