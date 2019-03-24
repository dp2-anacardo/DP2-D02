
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Actor;
import domain.Administrator;
import domain.Brotherhood;
import domain.Member;
import domain.Message;
import domain.MessageBox;
import domain.Parade;
import domain.Position;
import domain.SocialProfile;
import domain.Sponsorship;
import forms.AdministratorForm;
import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class AdministratorService {

	@Autowired
	private AdministratorRepository	administratorRepository;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private MemberService			memberService;

	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private Validator				validator;

	@Autowired
	private MessageBoxService		messageBoxService;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private SponsorshipService		sponsorshipService;


	public Administrator create() {

		Administrator result;
		final Authority auth;
		final UserAccount userAccount;
		UserAccount userAccount1;
		final Collection<Authority> authorities;
		final Collection<SocialProfile> profiles;
		final Collection<MessageBox> boxes;
		userAccount1 = LoginService.getPrincipal();
		Assert.isTrue(userAccount1.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		result = new Administrator();
		userAccount = new UserAccount();
		auth = new Authority();
		boxes = new ArrayList<MessageBox>();
		authorities = new ArrayList<Authority>();
		profiles = new ArrayList<SocialProfile>();

		auth.setAuthority(Authority.ADMIN);
		authorities.add(auth);
		userAccount.setAuthorities(authorities);
		result.setUserAccount(userAccount);
		result.setIsBanned(false);
		result.setIsSuspicious(false);
		result.setBoxes(boxes);
		result.setSocialProfiles(profiles);

		return result;
	}

	public Administrator findOne(final Integer administratorId) {

		Assert.notNull(administratorId);
		Administrator result;
		result = this.administratorRepository.findOne(administratorId);
		Assert.notNull(result);
		return result;
	}

	public List<Administrator> findAll() {

		List<Administrator> result;
		result = this.administratorRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Administrator save(final Administrator administrator) {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));
		Assert.notNull(administrator);
		Administrator result;
		final char[] c = administrator.getPhoneNumber().toCharArray();
		if ((!administrator.getPhoneNumber().equals(null) && !administrator.getPhoneNumber().equals("")))
			if (c[0] != '+') {
				final String i = this.configurationService.findAll().get(0).getDefaultCC();
				administrator.setPhoneNumber("+" + i + " " + administrator.getPhoneNumber());
			}
		if (administrator.getId() == 0) {
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String res = encoder.encodePassword(administrator.getUserAccount().getPassword(), null);
			administrator.getUserAccount().setPassword(res);
			administrator.setBoxes(this.messageBoxService.createSystemMessageBox());
		}
		result = this.administratorRepository.save(administrator);
		return result;
	}
	public void delete(final Administrator administrator) {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Assert.notNull(administrator);
		Assert.isTrue(administrator.getId() != 0);
		this.administratorRepository.delete(administrator.getId());
	}

	public Administrator reconstruct(final Administrator admin, final BindingResult binding) {

		Administrator result;
		if (admin.getId() == 0) {
			this.validator.validate(admin, binding);
			result = admin;
		} else {
			result = this.administratorRepository.findOne(admin.getId());

			result.setName(admin.getName());
			result.setMiddleName(admin.getMiddleName());
			result.setSurname(admin.getSurname());
			result.setPhoto(admin.getPhoto());
			result.setPhoneNumber(admin.getPhoneNumber());
			result.setEmail(admin.getEmail());
			result.setAddress(admin.getAddress());

			//			result.setIsBanned(admin.getIsBanned());
			//			result.setIsSuspicious(admin.getIsSuspicious());
			//			result.setScore(admin.getScore());
			//			result.setBoxes(admin.getBoxes());
			//			result.setUserAccount(admin.getUserAccount());
			//			result.setSocialProfiles(admin.getSocialProfiles());
			//			result = admin;

			this.validator.validate(admin, binding);
		}
		return result;
	}

	/* Q1 */
	public List<Double> getStatsMemberPerBrotherhood() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		List<Double> result;
		result = new ArrayList<Double>();

		result.add(this.administratorRepository.getAvgOfMembersPerBrotherhood());
		result.add(this.administratorRepository.getMinOfMembersPerBrotherhood());
		result.add(this.administratorRepository.getMaxOfMembersPerBrotherhood());
		result.add(this.administratorRepository.getSteddevOfMembersPerBrotherhood());

		return result;
	}

	/* Q2 */
	public Brotherhood getLargestBrotherhood() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Brotherhood result;
		result = this.administratorRepository.getLargestBrotherhood();

		return result;
	}

	/* Q3 */
	public Brotherhood getSmallestBrotherhoood() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Brotherhood result;
		result = this.administratorRepository.getSmallestBrotherhood();

		return result;
	}

	/* Q4 */
	public Collection<Parade> getParadeIn30Days() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Collection<Parade> result;
		result = this.administratorRepository.getParadesIn30Days();

		return result;
	}

	/* Q5 */
	public Double getRatioOfRequestToParadePerStatus(final Parade parade, final String status) {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Double result;
		result = this.administratorRepository.getRatioOfRequestToParadePerStatus(parade, status);

		return result;
	}

	/* Q6 */
	public List<Double> getRatioOfRequestPerStatus() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		List<Double> result;
		result = new ArrayList<Double>();

		result.add(this.administratorRepository.getRatioOfRequestsApproveds());
		result.add(this.administratorRepository.getRatioOfRequestsPendings());
		result.add(this.administratorRepository.getRatioOfRequestsRejecteds());

		return result;
	}

	/* Q7 */
	public Collection<Position> getPositionsNotUsed() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Collection<Position> result;
		result = this.administratorRepository.getPositionsNotUsed();

		return result;
	}

	/* Q8 */
	public Collection<Member> getMembersAtLeast10PercentOfNumberOfRequestAccepted() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Collection<Member> result;
		result = this.administratorRepository.getMembersAtLeast10PercentOfNumberOfRequestAccepted();

		return result;
	}

	/* Q9 */
	public Integer getHistogramOfPositions(final String roleEn, final String roleEs) {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));
		Integer result;

		result = this.administratorRepository.getHistogramOfPositions(roleEn, roleEs);

		return result;
	}
	/* Q10 */

	public Double getCountOfBrotherhoodPerArea(final Integer areaId) {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Double result;
		result = this.administratorRepository.getCountOfBrotherhoodPerArea(areaId);
		return result;
	}

	public Double getMinBrotherhoodPerArea() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Double result;
		result = this.administratorRepository.getMinBrotherhoodPerArea();
		return result;
	}

	public Double getMaxBrotherhoodPerArea() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Double result;
		result = this.administratorRepository.getMaxBrotherhoodPerArea();
		return result;
	}

	public Double getAvgBrotherhoodPerArea() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Double result;
		result = this.administratorRepository.getAvgBrotherhoodPerArea();
		return result;
	}

	public Double getStddevBrotherhoodPerArea() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Double result;
		result = this.administratorRepository.getStddevBrotherhoodPerArea();
		return result;
	}

	/* Q11 */
	public List<Double> getStatsFinders() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		List<Double> result;
		result = new ArrayList<Double>();

		result.add(this.administratorRepository.getMinResultFinder());
		result.add(this.administratorRepository.getMaxResultFinder());
		result.add(this.administratorRepository.getAvgResultFinder());
		result.add(this.administratorRepository.getStddevResultFinder());

		return result;
	}

	/* Q12 */
	public Double getRatioOfNotEmptyFinders() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Double res;

		res = this.administratorRepository.getRatioOfNotEmptyFinders();

		return res;
	}

	public Double getRatioOfEmptyFinders() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Double res;

		res = this.administratorRepository.getRatioOfEmptyFinders();

		return res;
	}

	/* Q13: The average, the minimum, the maximum, and the standard deviation of the number of records per history. */

	public Double getAvgRecordsPerHistory() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		final Double records = (this.administratorRepository.getNumOfInceptionRecord() + this.administratorRepository.getNumOfLegalRecord() + this.administratorRepository.getNumOfLinkRecord() + this.administratorRepository.getNumOfPeriodRecord()) * 1.;
		final Double brotherhoods = this.administratorRepository.getNumOfBrotherhoods() * 1.;
		final Double result = records / brotherhoods;

		return result;
	}

	public Double getMaxRecordsPerHistory() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Double max = 0.;
		final List<Brotherhood> brotherhoods = this.brotherhoodService.findAll();
		for (final Brotherhood b : brotherhoods) {
			final Integer id = b.getId();
			final Double res = this.getNumRecordsPerBrotherhoods(id);
			if (res > max)
				max = res;
		}
		return max;
	}

	public Double getMinRecordsPerHistory() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Double min = 0.;
		final List<Brotherhood> brotherhoods = this.brotherhoodService.findAll();
		for (final Brotherhood b : brotherhoods) {
			final Integer id = b.getId();
			final Double res = this.getNumRecordsPerBrotherhoods(id);
			if (min == 0.)
				min = res;
			else if (res < min)
				min = res;
		}
		return min;
	}

	public Double getStddevRecordsPerHistory() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		final Double avg = this.getAvgRecordsPerHistory();
		Double sum = 0.;
		final Double stddev = Math.sqrt(sum / avg);
		final List<Brotherhood> brotherhoods = this.brotherhoodService.findAll();
		for (final Brotherhood b : brotherhoods) {
			final Integer brotherhoodId = b.getId();
			final Double res = this.getNumRecordsPerBrotherhoods(brotherhoodId);
			sum += Math.pow(res - avg, 2);
		}
		return stddev;
	}

	private Double getNumRecordsPerBrotherhoods(final Integer brotherhoodId) {
		Integer res = 0;
		res++;
		res += this.administratorRepository.getNumOfPeriodRecordsPerBrotherhood(brotherhoodId);
		res += this.administratorRepository.getNumOfLegalRecordsPerBrotherhood(brotherhoodId);
		res += this.administratorRepository.getNumOfMiscRecordsPerBrotherhood(brotherhoodId);
		res += this.administratorRepository.getNumOfLegalRecordsPerBrotherhood(brotherhoodId);
		return res * 1.;
	}

	/* TODO Q14: The brotherhood with the largest history. */

	/* Q15: The brotherhoods whose history is larger than the average. */

	public List<Brotherhood> getBrotherhoodHistoryLargerThanAvg() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		final List<Brotherhood> res = new ArrayList<Brotherhood>();
		final List<Brotherhood> brotherhoods = this.brotherhoodService.findAll();
		final Double avg = this.getAvgRecordsPerHistory();
		for (final Brotherhood b : brotherhoods) {
			final Integer id = b.getId();
			final Double numRecords = this.getNumRecordsPerBrotherhoods(id);
			if (numRecords > avg)
				res.add(b);
		}
		return res;
	}

	/* Q16: The ratio of areas that are not co-ordinated by any chapters. */
	public Double getRatioAreaNotCoordinatesByChapter() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Double res;
		res = this.administratorRepository.getRatioAreaNotCoordinatesByChapter();
		return res;
	}

	/* TODO Q17: The average, the minimum, the maximum, and the standard deviation of the number of parades co-ordinated by the chapters. */
	public Double getAvgParadesCoordinatesByChapters() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Double res;
		res = this.administratorRepository.getAvgParadesCoordinatesByChapters();
		return res;
	}

	public Double getMinParadesCoordinatesByChapters() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Double res;
		res = this.administratorRepository.getMinParadesCoordinatesByChapters();
		return res;
	}

	public Double getMaxParadesCoordinatesByChapters() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Double res;
		res = this.administratorRepository.getMaxParadesCoordinatesByChapters();
		return res;
	}

	/* TODO Q18: The chapters that co-ordinate at least 10% more parades than the average. */

	/* Q19: The ratio of parades in draft mode versus parades in final mode. */
	public Double getRatioParadeDraftVsFinal() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Double res;
		res = this.administratorRepository.getRatioParadeDraftVsFinal();
		return res;
	}

	/* Q20: The ratio of parades in final mode grouped by status. */
	public Double getRatioParadeFinalModeAccepted() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Double res;
		res = this.administratorRepository.getRatioParadeFinalModeAccepted();
		return res;
	}

	public Double getRatioParadeFinalModeSubmitted() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Double res;
		res = this.administratorRepository.getRatioParadeFinalModeSubmitted();
		return res;
	}

	public Double getRatioParadeFinalModeRejected() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Double res;
		res = this.administratorRepository.getRatioParadeFinalModeRejected();
		return res;
	}

	/* Q21: The ratio of active sponsorships */
	public Double getRatioActiveSponsorships() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Double res;
		res = this.administratorRepository.getRatioActiveSponsorships();
		return res;
	}

	/* Q22: The average, the minimum, the maximum, and the standard deviation of ac-tive sponsorships per sponsor. */
	public Double getAvgSponsorshipsPerSponsor() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Double res;
		res = this.administratorRepository.getAvgSponsorshipsActivesPerSponsor();
		return res;
	}

	public Double getMinSponsorshipsActivesPerSponsor() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Double res;
		res = this.administratorRepository.getMinSponsorshipsActivesPerSponsor();
		return res;
	}

	public Double getMaxSponsorshipsActivesPerSponsor() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Double res;
		res = this.administratorRepository.getMaxSponsorshipsActivesPerSponsor();
		return res;
	}

	public Double getStddevSponsorshipsActivesPerSponsor() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Double res;
		res = this.administratorRepository.getStddevSponsorshipsActivesPerSponsor();
		return res;
	}

	/* TODO Q23: The top-5 sponsors in terms of number of active sponsorships. */

	//Validador de contraseñas
	public Boolean checkPass(final String pass, final String confirmPass) {
		Boolean res = false;
		if (pass.compareTo(confirmPass) == 0)
			res = true;
		return res;
	}

	public Administrator reconstruct(final AdministratorForm admin, final BindingResult binding) {

		final Administrator result = this.create();
		result.setAddress(admin.getAddress());
		result.setEmail(admin.getEmail());
		result.setId(admin.getId());
		result.setName(admin.getName());
		result.setPhoneNumber(admin.getPhoneNumber());
		result.setPhoto(admin.getPhoto());
		result.setSurname(admin.getSurname());
		result.setMiddleName(admin.getMiddleName());
		result.getUserAccount().setPassword(admin.getPassword());
		result.getUserAccount().setUsername(admin.getUsername());
		result.setVersion(admin.getVersion());

		this.validator.validate(result, binding);
		return result;
	}

	// SKERE

	public void computeAllScores() {

		Collection<Member> members;
		Collection<Brotherhood> brotherhoods;

		// Make sure that the principal is an Admin
		final Actor principal = this.actorService.getActorLogged();
		Assert.isInstanceOf(Administrator.class, principal);

		members = this.memberService.findAll();
		for (final Member member : members) {
			member.setScore(this.computeScore(this.messageService.findAllByActor(member.getId())));
			this.memberService.save(member);
		}

		brotherhoods = this.brotherhoodService.findAll();
		for (final Brotherhood brotherhood : brotherhoods) {
			brotherhood.setScore(this.computeScore(this.messageService.findAllByActor(brotherhood.getId())));
			this.brotherhoodService.save(brotherhood);
		}
	}

	private Double computeScore(final Collection<Message> messages) {
		final Collection<String> positiveWords = this.configurationService.getConfiguration().getPositiveWords();

		final Collection<String> negativeWords = this.configurationService.getConfiguration().getNegativeWords();

		Double positiveWordsValue = 0.0;
		Double negativeWordsValue = 0.0;

		for (final Message message : messages) {
			final String body = message.getBody();
			final String subject = message.getSubject();

			for (final String positiveWord : positiveWords) {
				System.out.println(positiveWord);
				if (body.contains(positiveWord)) {
					positiveWordsValue += 1.0;
					System.out.println(positiveWordsValue);
				}
			}
			for (final String negativeWord : negativeWords) {
				System.out.println(negativeWord);
				if (body.contains(negativeWord)) {
					negativeWordsValue += 1.0;
					System.out.println(negativeWordsValue);
				}
			}

			for (final String positiveWord : positiveWords) {
				System.out.println(positiveWord);
				if (subject.contains(positiveWord)) {
					positiveWordsValue += 1.0;
					System.out.println(positiveWordsValue);
				}
			}
			for (final String negativeWord : negativeWords) {
				System.out.println(negativeWord);
				if (subject.contains(negativeWord)) {
					negativeWordsValue += 1.0;
					System.out.println(negativeWordsValue);
				}
			}
		}

		// check for NaN values
		if (positiveWordsValue + negativeWordsValue == 0)
			return 0.0;
		else if (positiveWordsValue - negativeWordsValue == 0)
			return 0.0;
		else
			return (positiveWordsValue - negativeWordsValue) / (positiveWordsValue + negativeWordsValue);

	}

	public void computeAllSpam() {

		Collection<Member> members;
		Collection<Brotherhood> brotherhoods;

		// Make sure that the principal is an Admin
		final Actor principal = this.actorService.getActorLogged();
		Assert.isInstanceOf(Administrator.class, principal);

		members = this.memberService.findAll();
		for (final Member member : members) {
			member.setIsSuspicious(this.messageService.findSpamRatioByActor(member.getId()) > .10);
			this.memberService.save(member);
		}

		brotherhoods = this.brotherhoodService.findAll();
		for (final Brotherhood brotherhood : brotherhoods) {
			brotherhood.setIsSuspicious(this.messageService.findSpamRatioByActor(brotherhood.getId()) > .10);
			this.brotherhoodService.save(brotherhood);
		}
	}

	public void desactivateExpiredSponsorships() {
		final Collection<Sponsorship> sponsorships = this.sponsorshipService.findAllActive();
		final Date date = new Date();

		for (final Sponsorship s : sponsorships)
			if (s.getCreditCard().getExpiration().before(date))
				this.sponsorshipService.desactivate(s);

	}
}
