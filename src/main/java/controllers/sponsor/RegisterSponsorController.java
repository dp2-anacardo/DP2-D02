
package controllers.sponsor;

import javax.validation.Valid;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import services.SponsorService;
import controllers.AbstractController;
import domain.Sponsor;
import forms.SponsorForm;

@Controller
@RequestMapping("sponsor")
public class RegisterSponsorController extends AbstractController {

	@Autowired
	private SponsorService			sponsorService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private AdministratorService	administratorService;


	@ExceptionHandler(TypeMismatchException.class)
	public ModelAndView handleMismatchException(final TypeMismatchException oops) {
		return new ModelAndView("redirect:/misc/403");
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		SponsorForm mForm;
		mForm = new SponsorForm();
		result = this.createEditModelAndView(mForm);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final SponsorForm mForm, final BindingResult binding) {
		ModelAndView result;
		Sponsor sponsor;

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
				sponsor = this.sponsorService.reconstruct(mForm, binding);
				this.sponsorService.save(sponsor);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				oops.printStackTrace();
				if (binding.hasErrors())
					result = this.createEditModelAndView(mForm, "administrator.duplicated");
				result = this.createEditModelAndView(mForm, "administrator.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final SponsorForm mForm) {
		ModelAndView result;
		result = this.createEditModelAndView(mForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final SponsorForm mForm, final String messageCode) {

		ModelAndView result;

		result = new ModelAndView("sponsor/create");
		result.addObject("sponsorForm", mForm);
		result.addObject("message", messageCode);

		return result;
	}
}
