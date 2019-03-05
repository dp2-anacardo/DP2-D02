
package controllers.request;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.BrotherhoodService;
import services.MemberService;
import services.ProcessionService;
import services.RequestService;
import controllers.AbstractController;
import domain.Actor;
import domain.Brotherhood;
import domain.Member;
import domain.Procession;
import domain.Request;

@Controller
@RequestMapping("request")
public class RequestController extends AbstractController {

	@Autowired
	RequestService		requestService;
	@Autowired
	ActorService		actorService;
	@Autowired
	MemberService		memberService;
	@Autowired
	ProcessionService	processionService;
	@Autowired
	BrotherhoodService	brotherhoodService;


	@RequestMapping(value = "/member/list")
	public ModelAndView list() {
		ModelAndView result;
		Collection<Request> requests;

		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
		final Member m = this.memberService.findOne(user.getId());
		requests = this.requestService.getRequestsByMember(m);

		result = new ModelAndView("request/member/list");
		result.addObject("requests", requests);
		result.addObject("requestURI", "request/member/list.do");

		return result;

	}

	@RequestMapping(value = "/member/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Collection<Procession> processions;
		final Request request = this.requestService.create();
		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
		final Member m = this.memberService.findOne(user.getId());
		processions = this.requestService.getProcessionsByMember(m);
		result = new ModelAndView("request/member/edit");
		result.addObject("processions", processions);
		result.addObject("request", request);

		return result;
	}

	@RequestMapping(value = "/member/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("request") final Request request, final BindingResult binding) {
		ModelAndView result;
		Collection<Procession> processions;
		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
		final Member m = this.memberService.findOne(user.getId());
		processions = this.requestService.getProcessionsByMember(m);
		try {
			request.setVersion(0);
			Assert.notNull(request.getProcession());
			Collection<Request> requests;
			requests = this.requestService.getRequestsByMember(m);
			for (final Request r : requests)
				if (r.getProcession().equals(request.getProcession()))
					Assert.isTrue(false);
			this.requestService.save(request);
			result = new ModelAndView("redirect:request/member/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("request/member/edit");
			result.addObject("message", "request.commit.error");
			result.addObject("processions", processions);
		}
		return result;
	}
	@RequestMapping(value = "/member,brotherhood/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int requestId) {
		ModelAndView result;
		Request r;
		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());

		r = this.requestService.findOne(requestId);
		if (r == null)
			result = new ModelAndView("redirect:/misc/403");
		else
			try {
				if (user instanceof Member)
					Assert.isTrue(r.getMember().equals(this.memberService.findOne(user.getId())));
				else if (user instanceof Brotherhood)
					Assert.isTrue(r.getMember().equals(this.brotherhoodService.findOne(user.getId())));
				result = new ModelAndView("request/member,brotherhood/show");
				result.addObject("r", r);
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/misc/403");
			}
		return result;

	}

	//AQUI EMPIEZA LA PARTE DE BROTHERHOOD

	@RequestMapping(value = "/brotherhood/list", method = RequestMethod.GET)
	public ModelAndView listB(@RequestParam final int processionId) {
		ModelAndView result;
		Collection<Request> requests;

		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
		final Brotherhood b = this.brotherhoodService.findOne(user.getId());
		final Procession p = this.processionService.findOne(processionId);
		if (p == null || !p.getBrotherhood().equals(b))
			result = new ModelAndView("redirect:/misc/403");
		else {
			requests = this.requestService.getRequestByProcession(p);

			result = new ModelAndView("request/member/list");
			result.addObject("requests", requests);
			result.addObject("requestURI", "request/brotherhood/list.do");

		}
		return result;

	}

}
