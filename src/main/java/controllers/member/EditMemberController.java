
package controllers.member;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.MemberService;
import controllers.AbstractController;
import domain.Actor;
import domain.Member;

@Controller
@RequestMapping("member/member")
public class EditMemberController extends AbstractController {

	@Autowired
	private ActorService	actorService;
	@Autowired
	private MemberService	memberService;


	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		final Actor user = this.actorService.getActorLogged();
		final Member member = this.memberService.findOne(user.getId());
		Assert.notNull(member);
		result = this.editModelAndView(member);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "update")
	public ModelAndView update(@Valid Member member, final BindingResult binding) {

		ModelAndView result;
		member = this.memberService.reconstruct(member, binding);

		if (binding.hasErrors())
			result = this.editModelAndView(member);
		else
			try {
				this.memberService.save(member);
				result = new ModelAndView("redirect:/profile/action-1.do");
			} catch (final Throwable oops) {
				result = this.editModelAndView(member, "member.commit.error");
			}
		return result;
	}

	protected ModelAndView editModelAndView(final Member member) {
		ModelAndView result;
		result = this.editModelAndView(member, null);
		return result;
	}

	protected ModelAndView editModelAndView(final Member member, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("member/member/edit");
		result.addObject("member", member);
		result.addObject("messageCode", messageCode);

		return result;
	}
}
