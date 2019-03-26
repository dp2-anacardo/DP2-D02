
package controllers.brotherhood;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import services.BrotherhoodService;
import services.InceptionRecordService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.InceptionRecord;
import forms.BrotherhoodForm;

@Controller
@RequestMapping("brotherhood")
public class RegisterBrotherhoodController extends AbstractController {

	@Autowired
	private BrotherhoodService		brotherhoodService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private InceptionRecordService	inceptionRecordService;


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
		} else if (binding.hasErrors()) {
			result = this.createEditModelAndView(brotherhoodForm);
			for (final ObjectError e : binding.getAllErrors())
				if (e.getDefaultMessage().equals("URL incorrecta") || e.getDefaultMessage().equals("Invalid URL")) {
					result.addObject("attachmentError", e.getDefaultMessage());
					break;
				}
		} else
			try {
				final InceptionRecord r = this.inceptionRecordService.create();
				r.setDescription(brotherhoodForm.getDescription());
				bro = this.brotherhoodService.reconstruct(brotherhoodForm, binding);
				r.setTitle(bro.getTitle());
				r.setPhoto(bro.getPictures());
				final Brotherhood bsave = this.brotherhoodService.save(bro);
				r.setBrotherhood(bsave);
				this.inceptionRecordService.save(r);
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

		result = new ModelAndView("brotherhood/create");
		result.addObject("brotherhoodForm", broForm);
		result.addObject("message", messageCode);

		return result;
	}
}
