
package controllers.socialProfile;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.SocialProfileService;
import controllers.AbstractController;
import domain.Actor;
import domain.SocialProfile;

@Controller
@RequestMapping("/socialProfile/brotherhood,member,admin")
public class SocialProfileController extends AbstractController {

	@Autowired
	private SocialProfileService	socialProfileService;

	@Autowired
	private ActorService			actorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<SocialProfile> profiles;
		final Actor user = this.actorService.getActorLogged();
		profiles = user.getSocialProfiles();

		result = new ModelAndView("socialProfile/brotherhood,member,admin/list");
		result.addObject("socialProfiles", profiles);
		result.addObject("requestURI", "socialProfile/brotherhood,member,admin/list.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		SocialProfile profile;

		profile = this.socialProfileService.create();
		result = this.createEditModelAndView(profile);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int socialProfileId) {

		ModelAndView result;
		SocialProfile profile;
		Actor user;

		user = this.actorService.getActorLogged();
		profile = this.socialProfileService.findOne(socialProfileId);
		Assert.notNull(profile);
		if (user.getSocialProfiles().contains(profile))
			result = this.createEditModelAndView(profile);
		else
			result = new ModelAndView("redirect:/misc/403");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("socialProfile") @Valid SocialProfile profile, final BindingResult binding) {

		ModelAndView result;
		profile = this.socialProfileService.reconstruct(profile, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(profile);
		else
			try {
				this.socialProfileService.save(profile);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(profile, "profile.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(SocialProfile profile, final BindingResult binding) {

		ModelAndView result;
		profile = this.socialProfileService.reconstruct(profile, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(profile);
		else
			try {
				this.socialProfileService.delete(profile);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(profile, "profile.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final SocialProfile profile) {

		ModelAndView result;
		result = this.createEditModelAndView(profile, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final SocialProfile profile, final String messageCode) {

		ModelAndView result;
		result = new ModelAndView("socialProfile/brotherhood,member,admin/edit");
		result.addObject("socialProfile", profile);
		result.addObject("message", messageCode);
		return result;
	}
}
