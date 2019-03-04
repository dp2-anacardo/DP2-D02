
package controllers.enrolment;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.BrotherhoodService;
import services.EnrolmentService;
import services.MemberService;
import services.PositionService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.Enrolment;
import domain.Member;
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

		enrolment = this.enrolmentService.findOne(enrolmentId);
		brotherhoodId = this.actorService.getActorLogged().getId();
		brotherhood = this.brotherhoodService.findOne(brotherhoodId);

		if (enrolment == null || !enrolment.getBrotherhood().equals(brotherhood))
			result = new ModelAndView("redirect:/misc/403");
		else
			result = this.acceptModelAndView(enrolment);
		return result;
	}

	@RequestMapping(value = "/brotherhood/accept", method = RequestMethod.POST, params = "accept")
	public ModelAndView accept(@Valid final Enrolment enrolment, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.acceptModelAndView(enrolment);
		else
			try {
				this.enrolmentService.acceptEnrolment(enrolment);
				this.enrolmentService.save(enrolment);
				result = new ModelAndView("enrolment/brotherhood/list");
			} catch (final Throwable oops) {
				result = this.acceptModelAndView(enrolment);
			}
		return result;
	}

	@RequestMapping(value = "/brotherhood/reject", method = RequestMethod.POST, params = "reject")
	public ModelAndView reject(@RequestParam final int enrolmentId) {
		ModelAndView result;
		Enrolment enrolment;
		int brotherhoodId;
		Brotherhood brotherhood;

		enrolment = this.enrolmentService.findOne(enrolmentId);
		brotherhoodId = this.actorService.getActorLogged().getId();
		brotherhood = this.brotherhoodService.findOne(brotherhoodId);

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

		result = new ModelAndView("enrolment/brotherhood/accept");
		result.addObject("enrolment", enrolment);
		result.addObject("message", messageCode);
		result.addObject("positions", positions);

		return result;
	}

	//AQUí EMPIEZA LA PARTE DE MEMBER----------------------------------------
	@RequestMapping(value = "/member/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int brotherhoodId) {
		ModelAndView result;
		Enrolment enrolment;

		enrolment = this.enrolmentService.create(brotherhoodId);
		this.enrolmentService.save(enrolment);
		result = new ModelAndView("redirect:list.do");

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

	@RequestMapping(value = "/member/dropOut", method = RequestMethod.POST)
	public ModelAndView dropOut(@RequestParam final int enrolmentId) {
		ModelAndView result;
		Enrolment enrolment;
		int memberId;
		Member member;

		memberId = this.actorService.getActorLogged().getId();
		member = this.memberService.findOne(memberId);
		enrolment = this.enrolmentService.findOne(enrolmentId);

		if (enrolment == null || !enrolment.getMember().equals(member))
			result = new ModelAndView("redirect:/misc/403");
		else {
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
