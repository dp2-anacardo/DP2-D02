
package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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


	//Para registrarse como administador, primero un admin llama al create del servicio
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
<<<<<<< HEAD
		AdministratorForm administratorForm;
		administratorForm = new AdministratorForm();
		result = this.createEditModelAndView(administratorForm);
=======

		final AdministratorForm adminForm = new AdministratorForm();
		result = this.createEditModelAndView(adminForm);
>>>>>>> origin/adrian

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
<<<<<<< HEAD
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
=======
	public ModelAndView save(@Valid final AdministratorForm adminForm, final BindingResult binding) {
		ModelAndView result;
		Administrator admin;

		admin = this.administratorService.reconstruct(adminForm, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(adminForm);
>>>>>>> origin/adrian
		else
			try {
				admin = this.administratorService.reconstruct(administratorForm, binding);
				this.administratorService.save(admin);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
<<<<<<< HEAD
				if (binding.hasErrors())
					result = this.createEditModelAndView(administratorForm, "administrator.duplicated");
				result = this.createEditModelAndView(administratorForm, "administrator.commit.error");
			}
		return result;
	}
	protected ModelAndView createEditModelAndView(final AdministratorForm adminForm) {
		ModelAndView result;
=======
				result = this.createEditModelAndView(adminForm, "administrator.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final AdministratorForm adminForm) {
		ModelAndView result;

>>>>>>> origin/adrian
		result = this.createEditModelAndView(adminForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final AdministratorForm adminForm, final String messageCode) {

		ModelAndView result;

<<<<<<< HEAD
		result = new ModelAndView("administrator/administrator/create");
		result.addObject("administratorForm", adminForm);
=======
		result = new ModelAndView("administrator/create");
		result.addObject("administrator", adminForm);
>>>>>>> origin/adrian
		result.addObject("message", messageCode);

		return result;
	}
}
