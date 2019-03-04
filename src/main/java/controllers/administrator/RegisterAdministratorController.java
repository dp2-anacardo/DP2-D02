
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
@RequestMapping("administrator/administrator")
public class RegisterAdministratorController extends AbstractController {

	@Autowired
	private AdministratorService	administratorService;


	//Para registrarse como administador, primero un admin llama al create del servicio
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		Administrator administrator;

		administrator = this.administratorService.create();
		final AdministratorForm admin = new AdministratorForm(administrator);
		result = this.createEditModelAndView(admin);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final AdministratorForm administrator, final BindingResult binding) {
		ModelAndView result;
		Administrator admin;

		admin = this.administratorService.reconstruct(administrator, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(admin);
		else
			try {
				this.administratorService.save(admin);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(administrator, "administrator.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Administrator administrator) {
		ModelAndView result;
		final AdministratorForm adminForm = new AdministratorForm(administrator);
		result = this.createEditModelAndView(adminForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final AdministratorForm administrator, final String messageCode) {

		ModelAndView result;

		result = new ModelAndView("administrator/administrator/create");
		result.addObject("administrator", administrator);
		result.addObject("message", messageCode);

		return result;
	}
}
