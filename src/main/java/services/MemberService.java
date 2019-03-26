
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

import repositories.MemberRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Enrolment;
import domain.Finder;
import domain.Member;
import domain.MessageBox;
import domain.SocialProfile;
import forms.MemberForm;

@Service
@Transactional
public class MemberService {

	@Autowired
	private MemberRepository		memberRepository;

	@Autowired
	private Validator				validator;

	@Autowired
	private FinderService			finderService;

	@Autowired
	private MessageBoxService		messageBoxService;

	@Autowired
	private ConfigurationService	configurationService;


	public Member create() {

		Member result;
		Authority auth;
		UserAccount userAccount;
		Collection<Authority> authorities;
		Collection<SocialProfile> profiles;
		Collection<MessageBox> boxes;

		result = new Member();
		userAccount = new UserAccount();
		auth = new Authority();
		authorities = new ArrayList<Authority>();
		profiles = new ArrayList<SocialProfile>();
		boxes = new ArrayList<MessageBox>();

		auth.setAuthority(Authority.MEMBER);
		authorities.add(auth);
		userAccount.setAuthorities(authorities);
		result.setUserAccount(userAccount);
		result.setIsBanned(false);
		result.setIsSuspicious(false);
		result.setBoxes(boxes);
		result.setSocialProfiles(profiles);

		return result;

	}

	public Member findOne(final Integer memberId) {

		Assert.isTrue(memberId != 0);
		Member result;
		result = this.memberRepository.findOne(memberId);
		return result;

	}

	public List<Member> findAll() {

		List<Member> result;
		result = this.memberRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Member save(final Member member) {

		Assert.notNull(member);
		Member result;

		final char[] c = member.getPhoneNumber().toCharArray();
		if ((!member.getPhoneNumber().equals(null) && !member.getPhoneNumber().equals("")))
			if (c[0] != '+') {
				final String i = this.configurationService.findAll().get(0).getDefaultCC();
				member.setPhoneNumber("+" + i + " " + member.getPhoneNumber());
			}

		if (member.getId() == 0) {
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String res = encoder.encodePassword(member.getUserAccount().getPassword(), null);
			member.getUserAccount().setPassword(res);
			Finder finderCreate;
			final Finder finder;
			finderCreate = this.finderService.create();
			finder = this.finderService.save(finderCreate);
			member.setFinder(finder);
			member.setBoxes(this.messageBoxService.createSystemMessageBox());
		}
		result = this.memberRepository.save(member);
		return result;

	}

	public Member update(final Member member) {
		Member result;

		result = this.memberRepository.save(member);

		return result;
	}
	public void delete(final Member member) {

		Assert.notNull(member);
		Assert.isTrue(member.getId() != 0);

		this.memberRepository.delete(member.getId());

	}

	public Member reconstruct(final Member member, final BindingResult binding) {

		Member result;
		if (member.getId() == 0)
			result = member;
		else {
			result = this.memberRepository.findOne(member.getId());

			result.setName(member.getName());
			result.setMiddleName(member.getMiddleName());
			result.setSurname(member.getSurname());
			result.setPhoto(member.getPhoto());
			result.setPhoneNumber(member.getPhoneNumber());
			result.setEmail(member.getEmail());
			result.setAddress(member.getAddress());

			this.validator.validate(result, binding);
		}
		return result;
	}

	public Collection<Enrolment> getEnrolments(final int memberId) {
		Collection<Enrolment> enrolments;
		Assert.notNull(memberId);
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("MEMBER"));

		enrolments = this.memberRepository.getEnrolments(memberId);

		return enrolments;
	}

	public Member reconstruct(final MemberForm m, final BindingResult binding) {

		final Member result = this.create();
		result.setAddress(m.getAddress());
		result.setEmail(m.getEmail());
		result.setId(m.getId());
		result.setName(m.getName());
		result.setPhoneNumber(m.getPhoneNumber());
		result.setPhoto(m.getPhoto());
		result.setSurname(m.getSurname());
		result.setMiddleName(m.getMiddleName());
		result.getUserAccount().setPassword(m.getPassword());
		result.getUserAccount().setUsername(m.getUsername());
		result.setVersion(m.getVersion());
		this.validator.validate(result, binding);
		return result;
	}

}
