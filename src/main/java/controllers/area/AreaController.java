
package controllers.area;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AreaService;
import controllers.AbstractController;
import domain.Area;

@Controller
@RequestMapping("area/administrator")
public class AreaController extends AbstractController {

	@Autowired
	private AreaService	areaService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Area> areas;
		areas = this.areaService.findAll();

		result = new ModelAndView("area/administrator/list");
		result.addObject("area", areas);
		result.addObject("requestURI", "area/administrator/list.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Area area;

		area = this.areaService.create();
		result = this.createEditModelAndView(area);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(final int areaId) {
		ModelAndView result;
		Area area;
		area = this.areaService.findOne(areaId);
		if (area == null)
			result = new ModelAndView("redirect:/misc/403");
		else
			result = this.createEditModelAndView(area);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Area area, final BindingResult binding) {
		ModelAndView result;
		area = this.areaService.reconstruct(area, binding);
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(area);
			for (final ObjectError e : binding.getAllErrors())
				if (e.getDefaultMessage().equals("URL incorrecta") || e.getDefaultMessage().equals("Invalid URL"))
					result.addObject("attachmentError", e.getDefaultMessage());
		} else
			try {
				this.areaService.save(area);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(area, "area.commit.error");

			}
		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@ModelAttribute("area") Area area, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(area);
		else
			try {
				area = this.areaService.reconstruct(area, binding);
				this.areaService.delete(area);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(area, "area.commit.error");
			}
		return result;
	}

	private ModelAndView createEditModelAndView(final Area area) {
		ModelAndView result;
		result = this.createEditModelAndView(area, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final Area area, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("area/administrator/edit");
		result.addObject("area", area);
		result.addObject("message", messageCode);
		return result;
	}

}
