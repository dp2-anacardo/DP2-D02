
package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import controllers.AbstractController;
import domain.Administrator;
import forms.AdministratorForm;

@Controller
@RequestMapping("administrator")
public class RegisterAdministratorController extends AbstractController {

	@Autowired
	private AdministratorService	administratorService;


	//Para registrarse como administador, primero un admin llama al create del servicio
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;

		final AdministratorForm adminForm = new AdministratorForm();
		result = this.createEditModelAndView(adminForm);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final AdministratorForm adminForm, final BindingResult binding) {
		ModelAndView result;
		Administrator admin;

		admin = this.administratorService.reconstruct(adminForm, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(adminForm);
		else
			try {
				this.administratorService.save(admin);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(adminForm, "administrator.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final AdministratorForm adminForm) {
		ModelAndView result;

		result = this.createEditModelAndView(adminForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final AdministratorForm adminForm, final String messageCode) {

		ModelAndView result;

		result = new ModelAndView("administrator/create");
		result.addObject("administrator", adminForm);
		result.addObject("message", messageCode);

		return result;
	}
}
