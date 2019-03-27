
package services;

import java.util.ArrayList;
import java.util.Collection;
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
import domain.Message;
import domain.MessageBox;
import domain.MiscRecord;
import domain.Parade;
import domain.PeriodRecord;
import domain.Proclaim;
import domain.Request;
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
	private MessageService			messageService;


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
			final List<SocialProfile> a = new ArrayList<SocialProfile>();
			final Collection<SocialProfile> ad = varActor.getSocialProfiles();
			a.addAll(ad);
			for (final SocialProfile i : a)
				this.socialProfileService.delete(i);
		}

		//Borrado de los mensajes que recibes
		final Collection<Message> msgs = this.messageService.findAllReceivedByActor(varActor.getId());

		if (msgs.size() > 0)
			for (final Message m : msgs)
				for (final MessageBox b : varActor.getBoxes())
					if (b.getMessages().contains(m)) {
						b.deleteMessage(m);
						m.getMessageBoxes().remove(b);
					}

		//Borrado de los mensajes que envias
		final Collection<Message> sent = this.messageService.findAllByActor(varActor.getId());

		if (sent.size() > 0)
			for (final Message m : sent) {
				for (final MessageBox b : varActor.getBoxes())
					if (b.getMessages().contains(m)) {
						b.deleteMessage(m);
						m.getMessageBoxes().remove(b);
					}
				m.setSender(null);
			}

		if (userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN")) {

			final Administrator a = this.administratorService.findOne(this.getActorLogged().getId());
			//Borrado de la informacion del administrador
			this.administratorService.delete(a);

		}
		if (userAccount.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD")) {

			//Faltan las relaciones de brotherhood
			final Brotherhood b = this.brotherhoodService.findOne(this.getActorLogged().getId());
			//Borrado de todos los record de la brotherhood
			this.inceptionRecordService.delete(this.inceptionRecordService.getInceptionRecordByBrotherhood(b.getId()).iterator().next());
			if (!(this.linkRecordService.getLinkRecordByBrotherhood(b.getId())).isEmpty()) {
				final List<LinkRecord> linkList = new ArrayList<LinkRecord>();
				final Collection<LinkRecord> it1 = this.linkRecordService.getLinkRecordByBrotherhood(b.getId());
				linkList.addAll(it1);
				for (final LinkRecord ls1 : linkList)
					this.linkRecordService.delete(ls1);
			}
			final Collection<LinkRecord> lbh = this.linkRecordService.findAll();
			for (final LinkRecord lr1 : lbh)
				if (lr1.getLinkedBH().equals(b))
					this.linkRecordService.delete(lr1);
			if (!(this.miscRecordService.getMiscRecordByBrotherhood(b.getId())).isEmpty()) {
				final List<MiscRecord> miscList = new ArrayList<MiscRecord>();
				final Collection<MiscRecord> it2 = this.miscRecordService.getMiscRecordByBrotherhood(b.getId());
				miscList.addAll(it2);
				for (final MiscRecord mr : miscList)
					this.miscRecordService.delete(mr);
			}
			if (!(this.periodRecordService.getPeriodRecordByBrotherhood(b.getId())).isEmpty()) {
				final List<PeriodRecord> periodList = new ArrayList<PeriodRecord>();
				final Collection<PeriodRecord> it3 = this.periodRecordService.getPeriodRecordByBrotherhood(b.getId());
				periodList.addAll(it3);
				for (final PeriodRecord pr : periodList)
					this.periodRecordService.delete(pr);
			}
			if (!(this.legalRecordService.getLegalRecordByBrotherhood(b.getId())).isEmpty()) {
				final List<LegalRecord> legalList = new ArrayList<LegalRecord>();
				final Collection<LegalRecord> it4 = this.legalRecordService.getLegalRecordByBrotherhood(b.getId());
				legalList.addAll(it4);
				for (final LegalRecord lr : legalList)
					this.legalRecordService.delete(lr);
			}
			//Borrado de enrolments de una brotherhood
			final List<Enrolment> enrolmentList = new ArrayList<Enrolment>();
			final Collection<Enrolment> it = this.brotherhoodService.getEnrolments(b.getId());
			enrolmentList.addAll(it);
			for (final Enrolment e : enrolmentList)
				this.enrolmentService.delete(e);
			//Borrado de FloatEntity
			if (!(this.floatEntityService.getFloatsByBrotherhood(b)).isEmpty()) {
				final List<FloatEntity> fList = new ArrayList<FloatEntity>();
				final Collection<FloatEntity> it5 = this.floatEntityService.getFloatsByBrotherhood(b);
				fList.addAll(it5);
				for (final FloatEntity f : fList)
					this.floatEntityService.delete(f);

			}
			//Borrado de parade y de todas sus relaciones
			if (!(this.paradeService.getParadesByBrotherhood(b)).isEmpty()) {
				final List<Parade> paradeList = new ArrayList<Parade>();
				final Collection<Parade> it6 = this.paradeService.getParadesByBrotherhood(b);
				paradeList.addAll(it6);
				for (final Parade p : paradeList) {
					if (!(this.sponsorShipService.findAllByParade(p.getId())).isEmpty()) {
						final List<Sponsorship> sponsorSList = new ArrayList<Sponsorship>();
						final Collection<Sponsorship> it9 = this.sponsorShipService.findAllByParade(p.getId());
						sponsorSList.addAll(it9);
						for (final Sponsorship sp : sponsorSList)
							this.sponsorShipService.delete(sp);
					}
					this.paradeService.delete(p);
				}

			}
			this.brotherhoodService.delete(b);

		}
		if (userAccount.getAuthorities().iterator().next().getAuthority().equals("SPONSOR")) {

			final Sponsor s = this.sponsorService.findOne(this.getActorLogged().getId());
			//Borrado de las sponsorships
			if (!(this.sponsorShipService.findBySponsor(s.getId()).isEmpty())) {
				final List<Sponsorship> sponsorship = new ArrayList<Sponsorship>();
				final Collection<Sponsorship> it = this.sponsorShipService.findBySponsor(s.getId());
				sponsorship.addAll(it);
				for (final Sponsorship sp : sponsorship)
					this.sponsorShipService.delete(sp);
			}
			//Borrado de la informacion del sponsor
			this.sponsorService.delete(s);

		}
		if (userAccount.getAuthorities().iterator().next().getAuthority().equals("CHAPTER")) {

			final Chapter chapter = this.chapterService.findOne(this.getActorLogged().getId());
			//Borrado de las proclaims del chapter logueado
			if (!(this.chapterService.getProclaims(chapter.getId()).isEmpty())) {
				final List<Proclaim> proclaimList = new ArrayList<Proclaim>();
				final Collection<Proclaim> it = this.chapterService.getProclaims(chapter.getId());
				proclaimList.addAll(it);
				for (final Proclaim p : proclaimList)
					this.proclaimService.delete(p);
			}

			//Borrado de la informacion del chapter
			this.chapterService.delete(chapter);

		}
		if (userAccount.getAuthorities().iterator().next().getAuthority().equals("MEMBER")) {
			final Member member = this.memberService.findOne(this.getActorLogged().getId());
			//Borrando su finder
			final Finder f = member.getFinder();
			//Borrando sus enrolment y sus positions
			final List<Enrolment> enrolmentList = new ArrayList<Enrolment>();
			final Collection<Enrolment> e = this.memberService.getEnrolments(member.getId());
			enrolmentList.addAll(e);
			for (final Enrolment enrol : enrolmentList)
				this.enrolmentService.delete(enrol);
			//Borrando las request
			final List<Request> requestList = new ArrayList<Request>();
			final Collection<Request> r = this.requestService.getRequestsByMember(member);
			requestList.addAll(r);
			for (final Request requ : requestList)
				this.requestService.delete(requ);

			this.memberService.delete(member);
			this.finderService.Delete(f);

		}
	}
}
