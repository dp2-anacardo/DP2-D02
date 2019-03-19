
package controllers.sponsor;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.SponsorService;
import controllers.AbstractController;
import domain.Actor;
import domain.Sponsor;

@Controller
@RequestMapping("sponsor")
public class EditSponsorController extends AbstractController {

	@Autowired
	private ActorService	actorService;
	@Autowired
	private SponsorService	sponsorService;


	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		final Actor user = this.actorService.getActorLogged();
		final Sponsor sponsor = this.sponsorService.findOne(user.getId());
		Assert.notNull(sponsor);
		result = this.editModelAndView(sponsor);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "update")
	public ModelAndView update(@Valid Sponsor sponsor, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.editModelAndView(sponsor);
		else
			try {
				sponsor = this.sponsorService.reconstruct(sponsor, binding);
				this.sponsorService.save(sponsor);
				result = new ModelAndView("redirect:/profile/action-1.do");
			} catch (final Throwable oops) {
				result = this.editModelAndView(sponsor, "sponsor.commit.error");
			}
		return result;
	}

	protected ModelAndView editModelAndView(final Sponsor sponsor) {
		ModelAndView result;
		result = this.editModelAndView(sponsor, null);
		return result;
	}

	protected ModelAndView editModelAndView(final Sponsor sponsor, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("sponsor/edit");
		result.addObject("sponsor", sponsor);
		result.addObject("messageCode", messageCode);

		return result;
	}
}
