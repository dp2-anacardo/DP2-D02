
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;
import domain.Administrator;
import domain.Brotherhood;
import domain.Chapter;
import domain.Enrolment;
import domain.Finder;
import domain.FloatEntity;
import domain.LegalRecord;
import domain.LinkRecord;
import domain.Member;
import domain.MiscRecord;
import domain.Parade;
import domain.PeriodRecord;
import domain.Proclaim;
import domain.Request;
import domain.Segment;
import domain.SocialProfile;
import domain.Sponsor;
import domain.Sponsorship;

@Service
@Transactional
public class ActorService {

	//Managed Repositories
	@Autowired
	private ActorRepository			actorRepository;

	//Supporting services
	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private SocialProfileService	socialProfileService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private ChapterService			chapterService;

	@Autowired
	private ProclaimService			proclaimService;

	@Autowired
	private AreaService				areaService;

	@Autowired
	private SponsorService			sponsorService;

	@Autowired
	private SponsorshipService		sponsorShipService;

	@Autowired
	private MemberService			memberService;

	@Autowired
	private FinderService			finderService;

	@Autowired
	private EnrolmentService		enrolmentService;

	@Autowired
	private PositionService			positionService;

	@Autowired
	private RequestService			requestService;

	@Autowired
	private InceptionRecordService	inceptionRecordService;

	@Autowired
	private LinkRecordService		linkRecordService;

	@Autowired
	private MiscRecordService		miscRecordService;

	@Autowired
	private PeriodRecordService		periodRecordService;

	@Autowired
	private LegalRecordService		legalRecordService;

	@Autowired
	private FloatEntityService		floatEntityService;

	@Autowired
	private ParadeService			paradeService;

	@Autowired
	private SegmentService			segmentService;


	public Collection<Actor> findAll() {
		Collection<Actor> result;

		result = this.actorRepository.findAll();

		return result;
	}

	public Actor findOne(final int actorId) {
		Assert.isTrue(actorId != 0);

		Actor result;

		result = this.actorRepository.findOne(actorId);

		return result;
	}

	public void delete(final Actor actor) {
		Assert.notNull(actor);
		Assert.isTrue(actor.getId() != 0);
		Assert.isTrue(this.actorRepository.exists(actor.getId()));

		this.actorRepository.delete(actor);
	}

	public UserAccount findUserAccount(final Actor actor) {
		Assert.notNull(actor);

		UserAccount result;

		result = this.userAccountService.findByActor(actor);

		return result;
	}

	public Actor findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Actor result;

		result = this.actorRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	public Actor getActorLogged() {
		UserAccount userAccount;
		Actor actor;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		actor = this.findByUserAccount(userAccount);
		Assert.notNull(actor);

		return actor;
	}

	public Actor save(final Actor actor) {
		Assert.notNull(actor);

		Actor result;

		result = this.actorRepository.save(actor);

		return result;
	}

	public Actor findByUsername(final String username) {
		Assert.notNull(username);

		Actor result;
		result = this.actorRepository.findByUsername(username);
		return result;
	}

	public Collection<Actor> findSuspiciousActors() {
		Collection<Actor> result;
		result = this.actorRepository.findSuspiciousActors();
		return result;
	}

	public Collection<Actor> findBannedActors() {
		Collection<Actor> result;
		result = this.actorRepository.findBannedActors();
		return result;
	}

	public Boolean existUsername(final String username) {
		Boolean res = false;
		final List<String> lista = new ArrayList<String>();
		for (final Actor a : this.actorRepository.findAll())
			lista.add(a.getUserAccount().getUsername());
		if (!(lista.contains(username)))
			res = true;
		return res;
	}

	public Boolean existIdSocialProfile(final Integer id) {
		Boolean res = false;
		final List<Integer> lista = new ArrayList<Integer>();
		for (final SocialProfile s : this.socialProfileService.findAll())
			lista.add(s.getId());
		if (lista.contains(id))
			res = true;
		return res;
	}

	public void deleteInformation() {

		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		final Actor varActor = this.findByUserAccount(userAccount);

		//Borrado de los socialProfiles de los actores
		if (!(varActor.getSocialProfiles().isEmpty())) {
			final Iterator<SocialProfile> it = varActor.getSocialProfiles().iterator();
			while (it.hasNext())
				this.socialProfileService.delete(it.next());
		}

		if (userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN")) {

			final Administrator a = this.administratorService.findOne(this.getActorLogged().getId());
			//Borrado de la informacion del administrador
			this.administratorService.delete(a);

		} else if (userAccount.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD")) {

			//Faltan las relaciones de brotherhood
			final Brotherhood b = this.brotherhoodService.findOne(this.getActorLogged().getId());
			//Borrado de todos los record de la brotherhood
			this.inceptionRecordService.delete(this.inceptionRecordService.getInceptionRecordByBrotherhood(b.getId()).iterator().next());
			if (!(this.linkRecordService.getLinkRecordByLinkedBH(b.getId())).isEmpty()) {
				final Iterator<LinkRecord> itRecord = this.linkRecordService.getLinkRecordByLinkedBH(b.getId()).iterator();
				while (itRecord.hasNext())
					this.linkRecordService.delete(itRecord.next());
			}
			if (!(this.linkRecordService.getLinkRecordByBrotherhood(b.getId())).isEmpty()) {
				final Iterator<LinkRecord> it1 = this.linkRecordService.getLinkRecordByBrotherhood(b.getId()).iterator();
				while (it1.hasNext())
					this.linkRecordService.delete(it1.next());
			}
			if (!(this.miscRecordService.getMiscRecordByBrotherhood(b.getId())).isEmpty()) {
				final Iterator<MiscRecord> it2 = this.miscRecordService.getMiscRecordByBrotherhood(b.getId()).iterator();
				while (it2.hasNext())
					this.miscRecordService.delete(it2.next());
			}
			if (!(this.periodRecordService.getPeriodRecordByBrotherhood(b.getId())).isEmpty()) {
				final Iterator<PeriodRecord> it3 = this.periodRecordService.getPeriodRecordByBrotherhood(b.getId()).iterator();
				while (it3.hasNext())
					this.periodRecordService.delete(it3.next());
			}
			if (!(this.legalRecordService.getLegalRecordByBrotherhood(b.getId())).isEmpty()) {
				final Iterator<LegalRecord> it4 = this.legalRecordService.getLegalRecordByBrotherhood(b.getId()).iterator();
				while (it4.hasNext())
					this.legalRecordService.delete(it4.next());
			}
			//Borrado de enrolments de una brotherhood
			final Iterator<Enrolment> it = this.brotherhoodService.getEnrolments(b.getId()).iterator();
			while (it.hasNext()) {
				this.positionService.delete(it.next().getPosition());
				this.enrolmentService.delete(it.next());
			}
			//Borrado de FloatEntity
			if (!(this.floatEntityService.getFloatsByBrotherhood(b)).isEmpty()) {
				final Iterator<FloatEntity> it5 = this.floatEntityService.getFloatsByBrotherhood(b).iterator();
				while (it5.hasNext())
					this.floatEntityService.delete(it5.next());
			}
			//Borrado de parade y de todas sus relaciones
			if (!(this.paradeService.getParadesByBrotherhood(b)).isEmpty()) {
				final Iterator<Parade> it6 = this.paradeService.getParadesByBrotherhood(b).iterator();
				while (it6.hasNext()) {
					final Iterator<Request> it7 = this.requestService.getRequestByParade(it6.next()).iterator();
					while (it7.hasNext())
						this.requestService.delete(it7.next());
					if (!(this.segmentService.getPathByParade(it6.next().getId())).isEmpty()) {
						final Iterator<Segment> it8 = this.segmentService.getPathByParade(it6.next().getId()).iterator();
						while (it8.hasNext())
							this.segmentService.delete(it8.next());
					}
					if (!(this.sponsorShipService.findAllByParade(it6.next().getId())).isEmpty()) {
						final Iterator<Sponsorship> it9 = this.sponsorShipService.findAllByParade(it6.next().getId()).iterator();
						while (it9.hasNext())
							this.sponsorShipService.delete(it9.next());
					}
					final Iterator<Finder> itFinder = this.paradeService.getFinderByParade(it6.next().getId()).iterator();
					while (itFinder.hasNext())
						if (itFinder.next().getParades().contains(it6.next()))
							itFinder.next().getParades().remove(it6.next());
				}
				this.paradeService.delete(it6.next());
			}
			this.brotherhoodService.delete(b);

		} else if (userAccount.getAuthorities().iterator().next().getAuthority().equals("SPONSOR")) {

			final Sponsor s = this.sponsorService.findOne(this.getActorLogged().getId());
			//Borrado de las sponsorships
			if (!(this.sponsorShipService.findBySponsor(s.getId()).isEmpty())) {
				final Iterator<Sponsorship> it = this.sponsorShipService.findBySponsor(s.getId()).iterator();
				while (it.hasNext())
					this.sponsorShipService.delete(it.next());
			}
			//Borrado de la informacion del sponsor
			this.sponsorService.delete(s);

		} else if (userAccount.getAuthorities().iterator().next().getAuthority().equals("CHAPTER")) {

			final Chapter chapter = this.chapterService.findOne(this.getActorLogged().getId());
			//Borrado de las proclaims del chapter logueado
			if (!(this.chapterService.getProclaims(chapter.getId()).isEmpty())) {
				final Iterator<Proclaim> it = this.chapterService.getProclaims(chapter.getId()).iterator();
				while (it.hasNext())
					this.proclaimService.delete(it.next());
			}
			//Borrado del area del chapter si es que la tiene
			if (chapter.getArea() != null)
				this.areaService.delete(chapter.getArea());

			//Borrado de la informacion del chapter
			this.chapterService.delete(chapter);

		} else if (userAccount.getAuthorities().iterator().next().getAuthority().equals("MEMBER")) {
			final Member member = this.memberService.findOne(this.getActorLogged().getId());
			//Borrando su finder
			final Finder f = member.getFinder();
			this.finderService.Delete(f);
			//Borrando sus enrolment y sus positions
			final Iterator<Enrolment> e = this.memberService.getEnrolments(member.getId()).iterator();
			while (e.hasNext()) {
				this.positionService.delete(e.next().getPosition());
				this.enrolmentService.delete(e.next());
			}
			//Borrando las request
			final Iterator<Request> r = this.requestService.getRequestsByMember(member).iterator();
			while (r.hasNext())
				this.requestService.delete(r.next());

			this.memberService.delete(member);
		}
	}
}
