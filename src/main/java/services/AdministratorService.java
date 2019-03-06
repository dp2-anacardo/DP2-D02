
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.Brotherhood;
import domain.Member;
import domain.Message;
import domain.MessageBox;
import domain.Position;
import domain.Procession;
import domain.SocialProfile;
import forms.AdministratorForm;

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
	public Collection<Double> getStatsMemberPerBrotherhood() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Collection<Double> result;
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
	public Collection<Procession> getProcessionIn30Days() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Collection<Procession> result;
		result = this.administratorRepository.getProcessionsIn30Days();

		return result;
	}

	/* Q5 */
	public Double getRatioOfRequestToProcessionPerStatus(final Procession procession, final String status) {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Double result;
		result = this.administratorRepository.getRatioOfRequestToProcessionPerStatus(procession, status);

		return result;
	}

	/* Q6 */
	public Collection<Double> getRatioOfRequestPerStatus() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Collection<Double> result;
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
	public Collection<Integer> getHistogramOfPositions() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Collection<Integer> result;
		result = new ArrayList<Integer>();

		result.add(this.administratorRepository.getNumberOfPresidents());
		result.add(this.administratorRepository.getNumberOfVicePresidents());
		result.add(this.administratorRepository.getNumberOfSecretaries());
		result.add(this.administratorRepository.getNumberOfTreasurers());
		result.add(this.administratorRepository.getNumberOfFundraisers());
		result.add(this.administratorRepository.getNumberOfHistorians());
		result.add(this.administratorRepository.getNumberOfOfficers());

		return result;
	}

	/* Q10 */
	public Collection<Double> getStatsBrotherHoodPerArea(final Integer areaId) {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Collection<Double> result;
		result = new ArrayList<Double>();

		result.add(this.administratorRepository.getCountOfBrotherhoodPerArea(areaId));
		result.add(this.administratorRepository.getMinBrotherhoodPerArea());
		result.add(this.administratorRepository.getMaxBrotherhoodPerArea());
		result.add(this.administratorRepository.getAvgBrotherhoodPerArea());
		result.add(this.administratorRepository.getStddevBrotherhoodPerArea());

		return result;
	}

	/* Q11 */
	public Collection<Double> getStatsFinders() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Collection<Double> result;
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
}
