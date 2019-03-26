
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SponsorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.MessageBox;
import domain.SocialProfile;
import domain.Sponsor;
import domain.Sponsorship;
import forms.SponsorForm;

@Service
@Transactional
public class SponsorService {

	// Manage Repository
	@Autowired
	private SponsorRepository		sponsorRepository;

	// Supporting services
	@Autowired
	MessageBoxService				messageBoxService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private Validator				validator;


	// CRUD methods
	public Sponsor create() {
		Sponsor result;
		final Authority auth;
		final UserAccount userAccount;

		final Collection<Authority> authorities;
		final Collection<SocialProfile> profiles;
		final Collection<MessageBox> boxes;
		final Collection<Sponsorship> sponsorships;

		result = new Sponsor();
		userAccount = new UserAccount();
		auth = new Authority();
		authorities = new ArrayList<Authority>();
		profiles = new ArrayList<SocialProfile>();
		boxes = new ArrayList<MessageBox>();
		sponsorships = new ArrayList<Sponsorship>();

		auth.setAuthority(Authority.SPONSOR);
		authorities.add(auth);
		userAccount.setAuthorities(authorities);
		result.setUserAccount(userAccount);
		result.setIsBanned(false);
		result.setIsSuspicious(false);
		result.setBoxes(boxes);
		result.setSocialProfiles(profiles);
		result.setSponsorships(sponsorships);

		return result;
	}

	public Sponsor findOne(final int sponsorID) {
		final Sponsor result = this.sponsorRepository.findOne(sponsorID);
		Assert.notNull(result);

		return result;
	}

	public Collection<Sponsor> findAll() {
		final Collection<Sponsor> result = this.sponsorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Sponsor save(final Sponsor sponsor) {
		Assert.notNull(sponsor);
		Sponsor result;

		final char[] c = sponsor.getPhoneNumber().toCharArray();
		if ((!sponsor.getPhoneNumber().equals(null) && !sponsor.getPhoneNumber().equals("")))
			if (c[0] != '+') {
				final String i = this.configurationService.findAll().get(0).getDefaultCC();
				sponsor.setPhoneNumber("+" + i + " " + sponsor.getPhoneNumber());
			}

		if (sponsor.getId() == 0) {
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String res = encoder.encodePassword(sponsor.getUserAccount().getPassword(), null);
			sponsor.getUserAccount().setPassword(res);
			sponsor.setBoxes(this.messageBoxService.createSystemMessageBox());
		}
		result = this.sponsorRepository.save(sponsor);
		return result;
	}

	public void delete(final Sponsor sponsor) {
		Assert.notNull(sponsor);

		this.sponsorRepository.delete(sponsor);
	}

	public Sponsor reconstruct(final Sponsor member, final BindingResult binding) {

		final Sponsor result;

		result = this.sponsorRepository.findOne(member.getId());

		result.setName(member.getName());
		result.setMiddleName(member.getMiddleName());
		result.setSurname(member.getSurname());
		result.setPhoto(member.getPhoto());
		result.setPhoneNumber(member.getPhoneNumber());
		result.setEmail(member.getEmail());
		result.setAddress(member.getAddress());

		this.validator.validate(result, binding);

		return result;
	}

	public Sponsor reconstruct(final SponsorForm m, final BindingResult binding) {

		final Sponsor result = this.create();
		result.setAddress(m.getAddress());
		result.setEmail(m.getEmail());
		result.setId(m.getId());
		result.setName(m.getName());
		result.setPhoneNumber(m.getPhoneNumber());
		result.setPhoto(m.getPhoto());
		result.getUserAccount().setPassword(m.getPassword());
		result.getUserAccount().setUsername(m.getUsername());
		result.setVersion(m.getVersion());
		result.setMiddleName(m.getMiddleName());
		result.setSurname(m.getSurname());
		this.validator.validate(result, binding);
		return result;
	}

	// Other business methods
	public Sponsor findByPrincipal() {
		Sponsor result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Sponsor findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Sponsor result;

		result = this.sponsorRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	public void flush() {
		this.sponsorRepository.flush();
	}
}
