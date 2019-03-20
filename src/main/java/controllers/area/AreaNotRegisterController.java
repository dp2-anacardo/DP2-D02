
package controllers.area;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChapterService;
import controllers.AbstractController;
import domain.Area;

@Controller
@RequestMapping("/area")
public class AreaNotRegisterController extends AbstractController {

	@Autowired
	private ChapterService	chapterService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int chapterId) {

		ModelAndView result;
		try {
			Assert.isTrue(this.chapterService.findAll().contains(this.chapterService.findOne(chapterId)));
			Assert.notNull(chapterId);
		} catch (final Exception e) {
			result = this.forbiddenOperation();
			return result;
		}
		final Area a = this.chapterService.findOne(chapterId).getArea();
		result = new ModelAndView("area/list");
		result.addObject("area", a);

		return result;
	}

	private ModelAndView forbiddenOperation() {
		return new ModelAndView("redirect:/");
	}
}
