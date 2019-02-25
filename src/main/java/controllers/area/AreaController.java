
package controllers.area;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AreaService;
import controllers.AbstractController;
import domain.Area;
import forms.AreaForm;

@Controller
@RequestMapping("area/administrator")
public class AreaController extends AbstractController {

	@Autowired
	private AreaService	areaService;


	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final AreaForm areaForm, final BindingResult binding) {
		final ModelAndView result;
		Area area;

		area = this.areaService.reconstruct(areaForm, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(area);
		else
			try {
				this.areaService.save(area);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(areaForm, "area.commit.error");

			}
		return result;

	}

	private ModelAndView createEditModelAndView(final Area area) {
		ModelAndView result;
		result = 
		return null;
	}
}
