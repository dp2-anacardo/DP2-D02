
package controllers.brotherhood;

import java.util.Collection;

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
import services.AreaService;
import services.BrotherhoodService;
import controllers.AbstractController;
import domain.Area;
import domain.Brotherhood;
import forms.BrotherhoodForm;

@Controller
@RequestMapping("brotherhood")
public class RegisterBrotherhoodController extends AbstractController {

	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private AreaService				areaService;
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
		BrotherhoodForm broForm;
		broForm = new BrotherhoodForm();
		result = this.createEditModelAndView(broForm);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final BrotherhoodForm brotherhoodForm, final BindingResult binding) {
		ModelAndView result;
		Brotherhood bro;

		if (this.actorService.existUsername(brotherhoodForm.getUsername()) == false) {
			binding.rejectValue("username", "error.username");
			result = this.createEditModelAndView(brotherhoodForm);
		} else if (this.administratorService.checkPass(brotherhoodForm.getPassword(), brotherhoodForm.getConfirmPass()) == false) {
			binding.rejectValue("password", "error.password");
			result = this.createEditModelAndView(brotherhoodForm);
		} else if (binding.hasErrors())
			result = this.createEditModelAndView(brotherhoodForm);
		else
			try {
				bro = this.brotherhoodService.reconstruct(brotherhoodForm, binding);
				this.brotherhoodService.save(bro);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				if (binding.hasErrors())
					result = this.createEditModelAndView(brotherhoodForm, "administrator.duplicated");
				result = this.createEditModelAndView(brotherhoodForm, "administrator.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final BrotherhoodForm broForm) {
		ModelAndView result;
		result = this.createEditModelAndView(broForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final BrotherhoodForm broForm, final String messageCode) {

		ModelAndView result;
		final Collection<Area> areas = this.areaService.findAll();

		result = new ModelAndView("brotherhood/create");
		result.addObject("brotherhoodForm", broForm);
		result.addObject("areas", areas);
		result.addObject("message", messageCode);

		return result;
	}
}
