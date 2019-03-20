
package controllers.chapter;

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
import services.ChapterService;
import controllers.AbstractController;
import domain.Chapter;
import forms.ChapterForm;

@Controller
@RequestMapping("chapter")
public class RegisterChapterController extends AbstractController {

	@Autowired
	private ChapterService			chapterService;

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
		ChapterForm chapterForm;
		chapterForm = new ChapterForm();
		result = this.createEditModelAndView(chapterForm);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ChapterForm chapterForm, final BindingResult binding) {
		ModelAndView result;
		final Chapter c;

		if (this.actorService.existUsername(chapterForm.getUsername()) == false) {
			binding.rejectValue("username", "error.username");
			result = this.createEditModelAndView(chapterForm);
		} else if (this.administratorService.checkPass(chapterForm.getPassword(), chapterForm.getConfirmPass()) == false) {
			binding.rejectValue("password", "error.password");
			result = this.createEditModelAndView(chapterForm);
		} else if (binding.hasErrors())
			result = this.createEditModelAndView(chapterForm);
		else
			try {
				c = this.chapterService.reconstruct(chapterForm, binding);
				this.chapterService.save(c);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				if (binding.hasErrors())
					result = this.createEditModelAndView(chapterForm, "administrator.duplicated");
				result = this.createEditModelAndView(chapterForm, "administrator.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final ChapterForm chapterForm) {
		ModelAndView result;
		result = this.createEditModelAndView(chapterForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final ChapterForm chapterForm, final String messageCode) {

		final ModelAndView result;

		result = new ModelAndView("chapter/create");
		result.addObject("chapterForm", chapterForm);
		result.addObject("message", messageCode);

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		final Collection<Chapter> chapters = this.chapterService.findAll();

		result = new ModelAndView("chapter/list");
		result.addObject("chapters", chapters);

		return result;
	}

}
