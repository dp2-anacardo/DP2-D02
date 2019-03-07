
package controllers.brotherhood;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.AreaService;
import services.BrotherhoodService;
import controllers.AbstractController;
import domain.Actor;
import domain.Area;
import domain.Brotherhood;

@Controller
@RequestMapping("area/brotherhood")
public class SelectAreaBrotherhoodController extends AbstractController {

	@Autowired
	private BrotherhoodService	brotherhoodService;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private AreaService			areaService;


	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		final Actor user = this.actorService.getActorLogged();
		final Brotherhood bro = this.brotherhoodService.findOne(user.getId());
		Assert.notNull(bro);
		if (bro.getArea() != null)
			return new ModelAndView("redirect:/misc/FalloArea");
		else
			result = this.editModelAndView(bro);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Brotherhood bro, final BindingResult binding) {
		ModelAndView result;

		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
		final Brotherhood b = this.brotherhoodService.findOne(user.getId());

		if (binding.hasErrors())
			result = this.editModelAndView(b);
		else
			try {
				b.setArea(bro.getArea());
				this.brotherhoodService.save(b);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				result = this.editModelAndView(b, "administrator.commit.error");
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
		final Collection<Area> lista = this.areaService.findAll();

		result = new ModelAndView("area/brotherhood/edit");
		result.addObject("bro", bro);
		result.addObject("areas", lista);
		result.addObject("messageCode", messageCode);

		return result;
	}
}
