
package controllers.administrator;

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
import controllers.AbstractController;
import domain.Administrator;
import forms.AdministratorForm;

@Controller
@RequestMapping("administrator/administrator")
public class RegisterAdministratorController extends AbstractController {

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ActorService			actorService;


	@ExceptionHandler(TypeMismatchException.class)
	public ModelAndView handleMismatchException(final TypeMismatchException oops) {
		return new ModelAndView("redirect:/misc/403");
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		AdministratorForm administratorForm;
		administratorForm = new AdministratorForm();
		result = this.createEditModelAndView(administratorForm);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView save(@Valid final AdministratorForm administratorForm, final BindingResult binding) {
		ModelAndView result;
		Administrator admin;

		if (this.actorService.existUsername(administratorForm.getUsername()) == false) {
			binding.rejectValue("username", "error.username");
			result = this.createEditModelAndView(administratorForm);
		} else if (this.administratorService.checkPass(administratorForm.getPassword(), administratorForm.getConfirmPass()) == false) {
			binding.rejectValue("password", "error.password");
			result = this.createEditModelAndView(administratorForm);
		} else if (binding.hasErrors())
			result = this.createEditModelAndView(administratorForm);
		else
			try {
				admin = this.administratorService.reconstruct(administratorForm, binding);
				this.administratorService.save(admin);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				if (binding.hasErrors())
					result = this.createEditModelAndView(administratorForm, "administrator.duplicated");
				result = this.createEditModelAndView(administratorForm, "administrator.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final AdministratorForm adminForm) {
		ModelAndView result;
		result = this.createEditModelAndView(adminForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final AdministratorForm adminForm, final String messageCode) {

		final ModelAndView result;

		result = new ModelAndView("administrator/administrator/create");
		result.addObject("administratorForm", adminForm);
		result.addObject("message", messageCode);

		return result;
	}
}
