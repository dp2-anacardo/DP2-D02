
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

import repositories.BrotherhoodRepository;
import services.BrotherhoodService;
import controllers.AbstractController;
import domain.Member;

@Controller
@RequestMapping("/member")
public class MemberController extends AbstractController {

	@Autowired
	private BrotherhoodRepository	brotherhoodRepository;
	@Autowired
	private BrotherhoodService		brotherhoodService;


	@ExceptionHandler(TypeMismatchException.class)
	public ModelAndView handleMismatchException(final TypeMismatchException oops) {
		return new ModelAndView("redirect:/misc/403");
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int brotherhoodId) {

		ModelAndView result;
		if (this.brotherhoodService.findOne(brotherhoodId) == null)
			return new ModelAndView("redirect:/misc/403");
		Collection<Member> member = new ArrayList<Member>();
		member = this.brotherhoodRepository.getMembers(this.brotherhoodService.findOne(brotherhoodId));
		result = new ModelAndView("member/list");
		result.addObject("member", member);
		result.addObject("requestURI", "member/list.do");

		return result;
	}
}
