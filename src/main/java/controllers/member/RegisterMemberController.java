
package controllers.member;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import services.MemberService;
import controllers.AbstractController;
import domain.Member;
import forms.MemberForm;

@Controller
@RequestMapping("member")
public class RegisterMemberController extends AbstractController {

	@Autowired
	private MemberService			memberService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private AdministratorService	administratorService;


	//Para registrarse como administador, primero un admin llama al create del servicio
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		MemberForm mForm;
		mForm = new MemberForm();
		result = this.createEditModelAndView(mForm);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final MemberForm mForm, final BindingResult binding) {
		ModelAndView result;
		Member member;

		if (this.actorService.existUsername(mForm.getUsername()) == false) {
			binding.rejectValue("username", "error.username");
			result = this.createEditModelAndView(mForm);
		} else if (this.administratorService.checkPass(mForm.getPassword(), mForm.getConfirmPass()) == false) {
			binding.rejectValue("password", "error.password");
			result = this.createEditModelAndView(mForm);
		} else if (binding.hasErrors())
			result = this.createEditModelAndView(mForm);
		else
			try {
				member = this.memberService.reconstruct(mForm, binding);
				this.memberService.save(member);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				oops.printStackTrace();
				if (binding.hasErrors())
					result = this.createEditModelAndView(mForm, "administrator.duplicated");
				result = this.createEditModelAndView(mForm, "administrator.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final MemberForm mForm) {
		ModelAndView result;
		result = this.createEditModelAndView(mForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final MemberForm mForm, final String messageCode) {

		ModelAndView result;

		result = new ModelAndView("member/create");
		result.addObject("memberForm", mForm);
		result.addObject("message", messageCode);

		return result;
	}
}
