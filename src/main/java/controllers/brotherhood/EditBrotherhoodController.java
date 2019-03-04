
package controllers.brotherhood;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.BrotherhoodService;
import controllers.AbstractController;
import domain.Actor;
import domain.Brotherhood;

@Controller
@RequestMapping("brotherhood/brotherhood")
public class EditBrotherhoodController extends AbstractController {

	@Autowired
	private ActorService		actorService;
	@Autowired
	private BrotherhoodService	brotherhoodService;


	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		final Actor user = this.actorService.getActorLogged();
		final Brotherhood bro = this.brotherhoodService.findOne(user.getId());
		Assert.notNull(bro);
		result = this.editModelAndView(bro);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "update")
	public ModelAndView update(@ModelAttribute("bro") @Valid Brotherhood bro, final BindingResult binding) {
		ModelAndView result;
		bro = this.brotherhoodService.reconstruct(bro, binding);

		if (binding.hasErrors())
			result = this.editModelAndView(bro);
		else
			try {
				this.brotherhoodService.save(bro);
				result = new ModelAndView("redirect:/profile/action-1.do");
			} catch (final Throwable oops) {
				result = this.editModelAndView(bro, "Administrator.commit.error");
			}
		return result;
	}

	protected ModelAndView editModelAndView(final Brotherhood bro) {
		ModelAndView result;
		result = this.editModelAndView(bro, null);
		return result;
	}

	protected ModelAndView editModelAndView(final Brotherhood bro, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("brotherhood/brotherhood/edit");
		result.addObject("bro", bro);
		result.addObject("messageCode", messageCode);

		return result;
	}
}
