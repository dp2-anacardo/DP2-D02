
package controllers.request;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.MemberService;
import services.RequestService;
import controllers.AbstractController;
import domain.Actor;
import domain.Member;
import domain.Request;

@Controller
@RequestMapping("request")
public class RequestController extends AbstractController {

	@Autowired
	RequestService	requestService;
	@Autowired
	ActorService	actorService;
	@Autowired
	MemberService	memberService;


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

}
