
package controllers.enrolment;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.BrotherhoodService;
import services.EnrolmentService;
import services.MemberService;
import services.MessageService;
import services.PositionService;
import services.PriorityService;
import controllers.AbstractController;
import domain.Actor;
import domain.Brotherhood;
import domain.Enrolment;
import domain.Member;
import domain.Message;
import domain.Position;

@Controller
@RequestMapping("enrolment")
public class EnrolmentController extends AbstractController {

	@Autowired
	private EnrolmentService	enrolmentService;

	@Autowired
	private PositionService		positionService;

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private MemberService		memberService;

	@Autowired
	private MessageService		messageService;

	@Autowired
	private PriorityService		priorityService;


	@ExceptionHandler(TypeMismatchException.class)
	public ModelAndView handleMismatchException(final TypeMismatchException oops) {
		ModelAndView result;

		if (LoginService.getPrincipal().getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD"))
			result = new ModelAndView("redirect:/enrolment/brotherhood/list.do");
		else if (LoginService.getPrincipal().getAuthorities().iterator().next().getAuthority().equals("MEMBER"))
			result = new ModelAndView("redirect:/enrolment/member/list.do");
		else
			result = new ModelAndView("redirect:/");

		return result;
	}

	//AQUÍ EMPIEZA LA PARTE DE BROTHERHOOD-----------------------------------
	@RequestMapping(value = "/brotherhood/list", method = RequestMethod.GET)
	public ModelAndView listBrotherhood() {
		ModelAndView result;
		Collection<Enrolment> enrolments;
		int brotherhoodId;

		brotherhoodId = this.actorService.getActorLogged().getId();
		enrolments = this.brotherhoodService.getEnrolments(brotherhoodId);
		final String language = LocaleContextHolder.getLocale().getLanguage();

		result = new ModelAndView("enrolment/brotherhood/list");
		result.addObject("enrolments", enrolments);
		result.addObject("requestURI", "enrolment/brotherhood/list.do");
		result.addObject("lang", language);

		return result;
	}

	@RequestMapping(value = "/brotherhood/accept", method = RequestMethod.GET)
	public ModelAndView accept(@RequestParam final int enrolmentId) {
		final ModelAndView result;
		Enrolment enrolment;
		int brotherhoodId;
		Brotherhood brotherhood;

		try {
			enrolment = this.enrolmentService.findOne(enrolmentId);
			brotherhoodId = this.actorService.getActorLogged().getId();
			brotherhood = this.brotherhoodService.findOne(brotherhoodId);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/misc/403");
			return result;
		}

		if (enrolment == null || !enrolment.getBrotherhood().equals(brotherhood))
			result = new ModelAndView("redirect:/misc/403");
		else
			result = this.acceptModelAndView(enrolment);
		return result;
	}
	@RequestMapping(value = "/brotherhood/accept", method = RequestMethod.POST, params = "accept")
	public ModelAndView accept(Enrolment enrolment, final BindingResult binding) {
		ModelAndView result;

		enrolment = this.enrolmentService.reconstruct(enrolment, binding);

		if (binding.hasErrors())
			result = this.acceptModelAndView(enrolment);
		else
			try {
				this.enrolmentService.acceptEnrolment(enrolment);
				this.enrolmentService.save(enrolment);
				final Message message = this.messageService.create();
				final Collection<Actor> recipients = new ArrayList<>();
				recipients.add(enrolment.getMember());
				recipients.add(enrolment.getBrotherhood());
				message.setRecipients(recipients);
				message.setPriority(this.priorityService.getHighPriority());
				message.setSubject("Enrolment accepted \n Inscripción aceptada");
				message.setBody("An enrolment has been accepted \n Una inscripción ha sido aceptada");
				this.messageService.save(message);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.acceptModelAndView(enrolment);
			}
		return result;
	}
	@RequestMapping(value = "/brotherhood/reject", method = RequestMethod.GET)
	public ModelAndView reject(@RequestParam final int enrolmentId) {
		ModelAndView result;
		Enrolment enrolment;
		int brotherhoodId;
		Brotherhood brotherhood;

		try {
			brotherhoodId = this.actorService.getActorLogged().getId();
			brotherhood = this.brotherhoodService.findOne(brotherhoodId);
			enrolment = this.enrolmentService.findOne(enrolmentId);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/misc/403");
			return result;
		}

		if (enrolment == null || !enrolment.getBrotherhood().equals(brotherhood))
			result = new ModelAndView("redirect:/misc/403");
		else {
			this.enrolmentService.rejectEnrolment(enrolment);
			this.enrolmentService.save(enrolment);
			result = new ModelAndView("redirect:list.do");
		}
		return result;
	}

	protected ModelAndView acceptModelAndView(final Enrolment enrolment) {
		ModelAndView result;

		result = this.acceptModelAndView(enrolment, null);

		return result;
	}

	protected ModelAndView acceptModelAndView(final Enrolment enrolment, final String messageCode) {
		ModelAndView result;
		final Collection<Position> positions = this.positionService.findAll();
		final Position defaultPosition = this.positionService.getDefaultPosition();
		positions.remove(defaultPosition);
		final String language = LocaleContextHolder.getLocale().getLanguage();

		result = new ModelAndView("enrolment/brotherhood/accept");
		result.addObject("enrolment", enrolment);
		result.addObject("message", messageCode);
		result.addObject("positions", positions);
		result.addObject("lang", language);

		return result;
	}
	//AQUí EMPIEZA LA PARTE DE MEMBER----------------------------------------
	@RequestMapping(value = "/member/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int brotherhoodId) {
		ModelAndView result;
		Enrolment enrolment;

		try {
			Assert.notNull(this.brotherhoodService.findOne(brotherhoodId));
			enrolment = this.enrolmentService.create(brotherhoodId);
			this.enrolmentService.save(enrolment);
			result = new ModelAndView("redirect:list.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/misc/403");
			return result;
		}
		return result;
	}

	@RequestMapping(value = "/member/list", method = RequestMethod.GET)
	public ModelAndView listMember() {
		ModelAndView result;
		Collection<Enrolment> enrolments;
		int memberId;

		memberId = this.actorService.getActorLogged().getId();
		enrolments = this.memberService.getEnrolments(memberId);
		final String language = LocaleContextHolder.getLocale().getLanguage();

		result = new ModelAndView("enrolment/member/list");
		result.addObject("enrolments", enrolments);
		result.addObject("requestURI", "enrolment/member/list.do");
		result.addObject("lang", language);

		return result;
	}

	@RequestMapping(value = "/member/dropOut", method = RequestMethod.GET)
	public ModelAndView dropOut(@RequestParam final int enrolmentId) {
		ModelAndView result;
		Enrolment enrolment;
		int memberId;
		Member member;

		try {
			memberId = this.actorService.getActorLogged().getId();
			member = this.memberService.findOne(memberId);
			enrolment = this.enrolmentService.findOne(enrolmentId);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/misc/403");
			return result;
		}

		if (enrolment == null || !enrolment.getMember().equals(member))
			result = new ModelAndView("redirect:/misc/403");
		else {
			final Message message = this.messageService.create();
			final Collection<Actor> recipients = new ArrayList<>();
			recipients.add(enrolment.getMember());
			recipients.add(enrolment.getBrotherhood());
			message.setRecipients(recipients);
			message.setSubject("Drop out brotherhood \n Salida de hermandad");
			message.setBody("A drop out happened \n Se ha dejado la hermandad");
			message.setPriority(this.priorityService.getHighPriority());
			this.messageService.save(message);
			this.enrolmentService.dropOut(enrolment);
			this.enrolmentService.save(enrolment);
			result = new ModelAndView("redirect:list.do");
		}
		return result;
	}
	protected ModelAndView createEditModelAndViewMember(final Enrolment enrolment) {
		ModelAndView result;

		result = this.createEditModelAndViewMember(enrolment, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewMember(final Enrolment enrolment, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("enrolment/member/edit");
		result.addObject("enrolment", enrolment);
		result.addObject("message", messageCode);

		return result;
	}
}
