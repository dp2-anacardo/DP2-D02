
package controllers.parade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Actor;
import domain.Brotherhood;
import domain.Chapter;
import domain.FloatEntity;
import domain.Message;
import domain.Parade;
import domain.Sponsorship;
import security.LoginService;
import services.ActorService;
import services.BrotherhoodService;
import services.ChapterService;
import services.ConfigurationService;
import services.FinderService;
import services.FloatEntityService;
import services.MessageService;
import services.ParadeService;
import services.PriorityService;
import services.SponsorshipService;

@Controller
@RequestMapping("parade")
public class ParadeController extends AbstractController {

	@Autowired
	private ParadeService			paradeService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private FloatEntityService		floatService;

	@Autowired
	private FinderService			finderService;

	@Autowired
	private SponsorshipService		sponsorshipService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private ChapterService			chapterService;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private PriorityService			priorityService;


	@RequestMapping(value = "/brotherhood/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Parade> parades;

		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
		final Brotherhood b = this.brotherhoodService.findOne(user.getId());
		parades = this.paradeService.getParadesByBrotherhood(b);

		result = new ModelAndView("parade/brotherhood/list");
		result.addObject("parade", parades);
		result.addObject("requestURI", "parade/brotherhood/list.do");
		return result;
	}

	@RequestMapping(value = "/brotherhood/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Parade parade;

		parade = this.paradeService.create();
		result = this.createEditModelAndView(parade);

		return result;
	}

	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int paradeId) {
		ModelAndView result;
		Parade parade;

		parade = this.paradeService.findOne(paradeId);

		if (parade == null)
			result = new ModelAndView("redirect:/misc/403");
		else
			result = this.createEditModelAndView(parade);
		return result;
	}

	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.POST, params = "saveDraft")
	public ModelAndView saveDraft(Parade parade, final BindingResult binding) {
		ModelAndView result;
		parade.setIsFinal(false);
		parade = this.paradeService.reconstruct(parade, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(parade);
		else
			try {
				this.paradeService.saveDraft(parade);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(parade, "parade.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.POST, params = "saveFinal")
	public ModelAndView saveFinal(Parade parade, final BindingResult binding) {
		ModelAndView result;
		parade.setIsFinal(false);
		parade = this.paradeService.reconstruct(parade, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(parade);
		else
			try {
				this.paradeService.saveFinal(parade);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(parade, "parade.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/listNotRegister", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int brotherhoodId) {

		ModelAndView result;
		Collection<Parade> pro;
		pro = this.paradeService.getParadesFinalByBrotherhood(brotherhoodId);
		if (pro == null || pro.size() <= 0)
			result = new ModelAndView("redirect:/misc/403");
		else {
			result = new ModelAndView("parade/listNotRegister");
			result.addObject("parade", pro);
			result.addObject("requestURI", "parade/listNotRegister.do");
		}
		return result;
	}
	@RequestMapping(value = "/listForMembers", method = RequestMethod.GET)
	public ModelAndView listMembers() {

		ModelAndView result;
		final Collection<Parade> pro = this.finderService.findAllFinal();

		if (pro == null || pro.size() <= 0)
			result = new ModelAndView("redirect:/misc/403");
		else {

			result = new ModelAndView("parade/listForMembers");
			result.addObject("parade", pro);
			result.addObject("requestURI", "parade/listForMembers.do");
		}
		return result;
	}
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int paradeId) {
		ModelAndView result;
		Parade p;

		p = this.paradeService.findOne(paradeId);
		if (p == null)
			result = new ModelAndView("redirect:/misc/403");
		else {
			result = new ModelAndView("parade/show");
			result.addObject("p", p);

			final List<Sponsorship> sponsorships = this.sponsorshipService.findAllByActiveParade(paradeId);
			if (sponsorships.size() > 0) {
				final Random rnd = new Random();
				final Sponsorship sponsorship = sponsorships.get(rnd.nextInt(sponsorships.size()));
				result.addObject("sponsorshipBanner", sponsorship.getBanner());

				final Double vat = this.configurationService.getConfiguration().getVat();
				final Double fee = this.configurationService.getConfiguration().getFlatFee();
				final Double totalAmount = (vat / 100) * fee + fee;
				final Message message = this.messageService.create();
				final Collection<Actor> recipients = new ArrayList<>();
				recipients.add(sponsorship.getSponsor());
				message.setRecipients(recipients);
				message.setPriority(this.priorityService.getHighPriority());
				message.setSubject("A sponsorship has been shown \n Se ha mostrado un anuncio");
				message.setBody("Se le cargará un importe de: " + totalAmount + "\n Se le cargará un importe de:" + totalAmount);
				this.messageService.save(message);
			}
		}
		return result;

	}
	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Parade parade) {
		ModelAndView result;
		try {
			this.paradeService.delete(this.paradeService.findOne(parade.getId()));
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(parade, "parade.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/chapter/accept", method = RequestMethod.GET)
	public ModelAndView accept(@RequestParam final int paradeId) {
		ModelAndView result;
		Parade parade;
		int chapterId;
		Chapter chapter;

		try {
			chapterId = this.actorService.getActorLogged().getId();
			chapter = this.chapterService.findOne(chapterId);
			parade = this.paradeService.findOne(paradeId);
			Assert.notNull(parade);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/misc/403");
			return result;
		}

		if (chapter == null || !parade.getBrotherhood().getArea().equals(chapter.getArea()))
			result = new ModelAndView("redirect:/misc/403");
		else {
			this.paradeService.acceptParade(parade);
			this.paradeService.saveChapter(parade);
			result = new ModelAndView("redirect:/parade/listNotRegister.do?brotherhoodId=" + parade.getBrotherhood().getId());
		}
		return result;
	}

	@RequestMapping(value = "/chapter/reject", method = RequestMethod.GET)
	public ModelAndView reject(@RequestParam final int paradeId) {
		ModelAndView result;
		Parade parade;
		int chapterId;
		Chapter chapter;

		try {
			chapterId = this.actorService.getActorLogged().getId();
			chapter = this.chapterService.findOne(chapterId);
			parade = this.paradeService.findOne(paradeId);
			Assert.notNull(parade);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/misc/403");
			return result;
		}

		if (chapter == null || !parade.getBrotherhood().getArea().equals(chapter.getArea()))
			result = new ModelAndView("redirect:/misc/403");
		else
			result = this.rejectModelAndView(parade);
		return result;
	}

	@RequestMapping(value = "/chapter/reject", method = RequestMethod.POST, params = "reject")
	public ModelAndView reject(Parade parade, final BindingResult binding) {
		ModelAndView result;
		parade = this.paradeService.reconstructChapter(parade, binding);

		if (parade.getRejectComment().equals("")) {
			binding.rejectValue("rejectComment", "error.rejectComment");
			result = this.rejectModelAndView(parade);
		} else if (binding.hasErrors())
			result = this.rejectModelAndView(parade);
		else
			try {
				this.paradeService.rejectParade(parade);
				this.paradeService.saveChapter(parade);
				result = new ModelAndView("redirect:/parade/listNotRegister.do?brotherhoodId=" + parade.getBrotherhood().getId());
			} catch (final Exception e) {
				result = this.rejectModelAndView(parade);
			}
		return result;
	}

	private ModelAndView createEditModelAndView(final Parade parade) {
		ModelAndView result;
		result = this.createEditModelAndView(parade, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final Parade parade, final String messageCode) {
		ModelAndView result;
		Collection<FloatEntity> floats;
		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
		final Brotherhood b = this.brotherhoodService.findOne(user.getId());
		floats = this.floatService.getFloatsByBrotherhood(b);

		result = new ModelAndView("parade/brotherhood/edit");
		result.addObject("parade", parade);
		result.addObject("message", messageCode);
		result.addObject("floats", floats);
		return result;
	}

	protected ModelAndView rejectModelAndView(final Parade parade) {
		ModelAndView result;

		result = this.rejectModelAndView(parade, null);

		return result;
	}

	protected ModelAndView rejectModelAndView(final Parade parade, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("parade/chapter/reject");
		result.addObject("parade", parade);
		result.addObject("message", messageCode);
		return result;
	}

	@RequestMapping(value = "/brotherhood/copy", method = RequestMethod.GET)
	public ModelAndView copy(@RequestParam final int paradeId) {
		ModelAndView result;
		try {
			final Parade newParade = this.paradeService.copyParade(paradeId);
			this.paradeService.saveDraft(newParade);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/misc/403");
		}
		return result;
	}

}
