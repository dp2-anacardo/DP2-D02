
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ChapterRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Area;
import domain.Chapter;
import domain.MessageBox;
import domain.Parade;
import domain.Proclaim;
import domain.SocialProfile;
import forms.ChapterForm;

@Service
@Transactional
public class ChapterService {

	@Autowired
	private ChapterRepository		chapterRepository;
	@Autowired
	private AreaService				areaService;
	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private MessageBoxService		messageBoxService;
	@Autowired
	private Validator				validator;


	public Chapter create() {
		Chapter result;
		final Authority auth;
		final UserAccount userAccount;
		final Collection<Authority> authorities;
		final Collection<SocialProfile> profiles;
		final Collection<MessageBox> boxes;

		result = new Chapter();
		userAccount = new UserAccount();
		auth = new Authority();
		boxes = new ArrayList<MessageBox>();
		authorities = new ArrayList<Authority>();
		profiles = new ArrayList<SocialProfile>();

		auth.setAuthority(Authority.CHAPTER);
		authorities.add(auth);
		userAccount.setAuthorities(authorities);
		result.setUserAccount(userAccount);
		result.setIsBanned(false);
		result.setIsSuspicious(false);
		result.setBoxes(boxes);
		result.setSocialProfiles(profiles);

		return result;
	}

	public Chapter save(final Chapter chapter) {

		Assert.notNull(chapter);
		Chapter result;
		final char[] c = chapter.getPhoneNumber().toCharArray();
		if ((!chapter.getPhoneNumber().equals(null) && !chapter.getPhoneNumber().equals("")))
			if (c[0] != '+') {
				final String i = this.configurationService.findAll().get(0).getDefaultCC();
				chapter.setPhoneNumber("+" + i + " " + chapter.getPhoneNumber());
			}
		if (chapter.getId() == 0) {
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String res = encoder.encodePassword(chapter.getUserAccount().getPassword(), null);
			chapter.getUserAccount().setPassword(res);
			chapter.setBoxes(this.messageBoxService.createSystemMessageBox());
		}
		result = this.chapterRepository.save(chapter);
		return result;
	}

	public Chapter findOne(final int chapterId) {
		Assert.notNull(chapterId);

		final Chapter result = this.chapterRepository.findOne(chapterId);

		Assert.notNull(result);

		return result;
	}

	public Collection<Chapter> findAll() {
		Collection<Chapter> result;

		result = this.chapterRepository.findAll();

		return result;
	}

	public Collection<Parade> getParadesByArea() {
		final Chapter chapter = this.chapterRepository.findOne(LoginService.getPrincipal().getId());

		final Collection<Parade> parades = this.chapterRepository.getParadesOfChapter(chapter.getArea().getId());

		return parades;
	}

	public void autoAssignateArea(final Chapter chapter, final int areaId) {
		Assert.notNull(areaId);
		Assert.notNull(chapter);

		final Area area = this.areaService.findOne(areaId);
		Assert.notNull(area);
		Assert.isTrue(this.areaService.dontHaveChapter(area));
		Assert.isNull(chapter.getArea());

		if (this.areaService.getChapter(areaId) == null)
			chapter.setArea(area);
	}
	public Chapter reconstruct(final ChapterForm c, final BindingResult binding) {

		final Chapter result = this.create();
		result.setAddress(c.getAddress());
		result.setEmail(c.getEmail());
		result.setId(c.getId());
		result.setName(c.getName());
		result.setPhoneNumber(c.getPhoneNumber());
		result.setPhoto(c.getPhoto());
		result.setTitle(c.getTitle());
		result.setMiddleName(c.getMiddleName());
		result.setSurname(c.getSurname());
		result.getUserAccount().setPassword(c.getPassword());
		result.getUserAccount().setUsername(c.getUsername());
		result.setVersion(c.getVersion());
		this.validator.validate(result, binding);

		return result;

	}

	public Collection<Proclaim> getProclaims(final int chapterId) {
		Assert.notNull(chapterId);
		Collection<Proclaim> result;

		result = this.chapterRepository.getProclaims(chapterId);

		return result;
	}

	public Chapter reconstruct(final Chapter chapter, final BindingResult binding) {
		Chapter result;
		if (chapter.getId() == 0)
			result = chapter;
		else {
			result = this.chapterRepository.findOne(chapter.getId());

			result.setName(chapter.getName());
			result.setPhoto(chapter.getPhoto());
			result.setPhoneNumber(chapter.getPhoneNumber());
			result.setEmail(chapter.getEmail());
			result.setAddress(chapter.getAddress());
			result.setTitle(chapter.getTitle());

			this.validator.validate(result, binding);
		}
		return result;
	}

	public void flush() {
		this.chapterRepository.flush();
	}
}
