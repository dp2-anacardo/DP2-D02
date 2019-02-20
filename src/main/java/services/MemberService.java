
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MemberRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Member;
import domain.MessageBox;
import domain.SocialProfile;

@Service
@Transactional
public class MemberService {

	@Autowired
	private MemberRepository	memberRepository;


	//	@Autowired 
	//	private FinderService		finderService;

	//	@Autowired 
	//	private MessageBoxService		messageBoxService;

	public Member create() {

		Member result;
		Authority auth;
		//		Finder finder;
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
		//		finder = this.finderService.create();

		auth.setAuthority(Authority.MEMBER);
		authorities.add(auth);
		userAccount.setAuthorities(authorities);
		result.setUserAccount(userAccount);
		result.setIsBanned(false);
		result.setIsSuspicious(false);
		result.setBoxes(boxes);
		result.setSocialProfiles(profiles);
		//		result.setFinder(finder);

		return result;

	}

	public Member findOne(final Integer memberId) {

		Assert.isTrue(memberId != 0);
		Member result;
		result = this.findOne(memberId);
		return result;

	}

	public List<Member> findAll() {

		List<Member> result;
		result = this.memberRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Member save(final Member member) {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("MEMBER"));
		Assert.notNull(member);
		final Member result;
		if (member.getId() == 0) {
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String res = encoder.encodePassword(member.getUserAccount().getPassword(), null);
			member.getUserAccount().setPassword(res);
			//	member.setBoxes(this.messageBoxService.createSystemMessageBox);
		}
		result = this.memberRepository.save(member);
		return result;

	}
	public void delete(final Member member) {

		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Assert.notNull(member);
		Assert.isTrue(member.getId() != 0);
		this.memberRepository.delete(member.getId());

	}

}
