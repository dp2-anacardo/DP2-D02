
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

import repositories.BrotherhoodRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import datatype.Url;
import domain.Brotherhood;
import domain.Enrolment;
import domain.Member;
import domain.MessageBox;
import domain.SocialProfile;
import forms.BrotherhoodForm;

@Service
@Transactional
public class BrotherhoodService {

	@Autowired
	private BrotherhoodRepository	brotherhoodRepository;
	@Autowired
	private Validator				validator;

	@Autowired
	private MessageBoxService		messageBoxService;

	@Autowired
	private ConfigurationService	configurationService;


	public Brotherhood create() {

		Brotherhood result;
		final Authority auth;
		final UserAccount userAccount;
		Date d;
		final Collection<Url> pictures;
		final Collection<Authority> authorities;
		final Collection<SocialProfile> profiles;
		final Collection<MessageBox> boxes;

		result = new Brotherhood();
		d = new Date();
		pictures = new ArrayList<Url>();
		userAccount = new UserAccount();
		auth = new Authority();
		authorities = new ArrayList<Authority>();
		profiles = new ArrayList<SocialProfile>();
		boxes = new ArrayList<MessageBox>();

		auth.setAuthority(Authority.BROTHERHOOD);
		authorities.add(auth);
		userAccount.setAuthorities(authorities);
		result.setUserAccount(userAccount);
		result.setIsBanned(false);
		result.setIsSuspicious(false);
		result.setBoxes(boxes);
		result.setEstablishmentDate(d);
		result.setSocialProfiles(profiles);
		result.setPictures(pictures);

		return result;

	}

	public Brotherhood findOne(final Integer brotherhoodId) {

		Assert.notNull(brotherhoodId);
		Brotherhood result;
		result = this.brotherhoodRepository.findOne(brotherhoodId);
		Assert.notNull(result);
		return result;

	}

	public List<Brotherhood> findAll() {

		final List<Brotherhood> result = this.brotherhoodRepository.findAll();
		Assert.notNull(result);
		return result;

	}

	public Brotherhood save(final Brotherhood brotherhood) {

		Assert.notNull(brotherhood);
		final Date nuevaFecha = new Date();
		Brotherhood result;

		if ((!brotherhood.getPhoneNumber().equals(null) && !brotherhood.getPhoneNumber().equals(""))) {
			final String i = this.configurationService.findAll().get(0).getDefaultCC();
			brotherhood.setPhoneNumber("+" + i + " " + brotherhood.getPhoneNumber());
		}

		if (brotherhood.getId() == 0) {
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String res = encoder.encodePassword(brotherhood.getUserAccount().getPassword(), null);
			brotherhood.getUserAccount().setPassword(res);
			brotherhood.setEstablishmentDate(nuevaFecha);
			brotherhood.setBoxes(this.messageBoxService.createSystemMessageBox());
		}
		result = this.brotherhoodRepository.save(brotherhood);
		return result;

	}

	public void delete(final Brotherhood brotherhood) {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));

		Assert.notNull(brotherhood);
		Assert.isTrue(brotherhood.getId() != 0);
		this.brotherhoodRepository.delete(brotherhood);

	}

	public Collection<Enrolment> getEnrolments(final int brotherhoodId) {
		Collection<Enrolment> enrolments;
		Assert.notNull(brotherhoodId);
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"));

		enrolments = this.brotherhoodRepository.getEnrolments(brotherhoodId);

		return enrolments;
	}

	public Brotherhood reconstruct(final Brotherhood bro, final BindingResult binding) {

		Brotherhood result;
		if (bro.getId() == 0)
			result = bro;
		else {
			result = this.brotherhoodRepository.findOne(bro.getId());

			result.setName(bro.getName());
			result.setPhoto(bro.getPhoto());
			result.setPhoneNumber(bro.getPhoneNumber());
			result.setEmail(bro.getEmail());
			result.setAddress(bro.getAddress());
			result.setTitle(bro.getTitle());
			result.setPictures(bro.getPictures());

			this.validator.validate(result, binding);
		}
		return result;
	}

	public Brotherhood reconstruct(final BrotherhoodForm bro, final BindingResult binding) {

		final Brotherhood result = this.create();
		result.setAddress(bro.getAddress());
		result.setEmail(bro.getEmail());
		result.setId(bro.getId());
		result.setName(bro.getName());
		result.setPhoneNumber(bro.getPhoneNumber());
		result.setPhoto(bro.getPhoto());
		result.setTitle(bro.getTitle());
		result.setPictures(bro.getPictures());
		result.getUserAccount().setPassword(bro.getPassword());
		result.getUserAccount().setUsername(bro.getUsername());
		result.setVersion(bro.getVersion());
		this.validator.validate(result, binding);
		return result;
	}

	public Collection<Member> getMembersByBrotherhood(final Brotherhood b) {
		return this.brotherhoodRepository.getMembers(b);
	}

}
