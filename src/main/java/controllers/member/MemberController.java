
package controllers.member;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.EnrolmentService;
import controllers.AbstractController;
import domain.Enrolment;
import domain.Member;

@Controller
@RequestMapping("/member")
public class MemberController extends AbstractController {

	@Autowired
	private EnrolmentService	enrolmentService;


	@ExceptionHandler(TypeMismatchException.class)
	public ModelAndView handleMismatchException(final TypeMismatchException oops) {
		return new ModelAndView("redirect:/misc/403");
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int brotherhoodId) {

		ModelAndView result;
		final Collection<Enrolment> e = this.enrolmentService.findAll();
		final Collection<Member> member = new ArrayList<Member>();
		for (final Enrolment e1 : e)
			if (e1.getBrotherhood().getId() == brotherhoodId)
				member.add(e1.getMember());
		result = new ModelAndView("member/list");
		result.addObject("member", member);
		result.addObject("requestURI", "member/list.do");

		return result;
	}
}
