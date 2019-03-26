
package controllers.chapter;

import javax.validation.Valid;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ChapterService;
import controllers.AbstractController;
import domain.Actor;
import domain.Chapter;

@Controller
@RequestMapping("chapter/chapter")
public class EditChapterController extends AbstractController {

	@Autowired
	private ActorService	actorService;
	@Autowired
	private ChapterService	chapterService;


	@ExceptionHandler(TypeMismatchException.class)
	public ModelAndView handleMismatchException(final TypeMismatchException oops) {
		return new ModelAndView("redirect:/misc/403");
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		final Actor user = this.actorService.getActorLogged();
		final Chapter chapter = this.chapterService.findOne(user.getId());
		Assert.notNull(chapter);
		result = this.editModelAndView(chapter);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "update")
	public ModelAndView update(@ModelAttribute("chapter") @Valid Chapter chapter, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.editModelAndView(chapter);
		else
			try {
				chapter = this.chapterService.reconstruct(chapter, binding);
				this.chapterService.save(chapter);
				result = new ModelAndView("redirect:/profile/action-1.do");
			} catch (final Throwable oops) {
				result = this.editModelAndView(chapter, "Administrator.commit.error");
			}
		return result;
	}

	protected ModelAndView editModelAndView(final Chapter chapter) {
		ModelAndView result;
		result = this.editModelAndView(chapter, null);
		return result;
	}

	protected ModelAndView editModelAndView(final Chapter chapter, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("chapter/chapter/edit");
		result.addObject("chapter", chapter);
		result.addObject("messageCode", messageCode);

		return result;
	}
}
