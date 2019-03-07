
package controllers.brotherhood;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.BrotherhoodService;
import services.MemberService;
import controllers.AbstractController;
import domain.Actor;
import domain.Brotherhood;
import domain.Member;

@Controller
@RequestMapping("/brotherhood")
public class BrotherhoodController extends AbstractController {

	@Autowired
	private BrotherhoodService	brotherhoodService;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private MemberService		memberService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		final Collection<Brotherhood> bros = this.brotherhoodService.findAll();

		result = new ModelAndView("brotherhood/list");
		result.addObject("brotherhood", bros);
		result.addObject("requestURI", "brotherhood/list.do");

		return result;
	}

	@RequestMapping(value = "/listMember", method = RequestMethod.GET)
	public ModelAndView listMembers() {
		final ModelAndView result;

		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
		final Brotherhood b = this.brotherhoodService.findOne(user.getId());
		final Collection<Member> members = this.brotherhoodService.getMembersByBrotherhood(b);

		result = new ModelAndView("brotherhood/listMembers");

		result.addObject("members", members);
		result.addObject("requestURI", "brotherhood/list.do");

		return result;

	}

	@RequestMapping(value = "/showMember", method = RequestMethod.GET)
	public ModelAndView showMember(@RequestParam final int memberId) {
		ModelAndView result;
		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
		final Brotherhood b = this.brotherhoodService.findOne(user.getId());
		final Collection<Member> members = this.brotherhoodService.getMembersByBrotherhood(b);
		final Member m = this.memberService.findOne(memberId);
		if (m == null || !members.contains(m))
			result = new ModelAndView("redirect:/misc/403");
		else {
			result = new ModelAndView("brotherhood/showMember");
			result.addObject("m", m);
		}
		return result;

	}
}
