
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PositionService;
import domain.Position;

@Controller
@RequestMapping("position")
public class PositionController extends AbstractController {

	@Autowired
	private PositionService	positionService;


	@RequestMapping(value = "/administrator/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Position> positions;

		positions = this.positionService.findAll();

		result = new ModelAndView("position/administrator/list");
		result.addObject("positions", positions);
		result.addObject("requestURI", "postion/administrator/list.do");

		return result;
	}

	@RequestMapping(value = "/administrator/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int positionId) {
		ModelAndView result;
		Position position;

		position = this.positionService.findOne(positionId);

		result = new ModelAndView("position/administrator/show");
		result.addObject("position", position);
		result.addObject("requestURI", "position/administrator/show.do");

		return result;
	}

	@RequestMapping(value = "/administrator/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Position position;

		position = this.positionService.create();
		result = this.createEditModelAndView(position);

		return result;
	}

	@RequestMapping(value = "/administrator/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int positionId) {
		final ModelAndView result;
		Position position;

		position = this.positionService.findOne(positionId);
		result = this.createEditModelAndView(position);

		return result;
	}

	@RequestMapping(value = "/administrator/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Position position, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(position);
		else
			try {
				this.positionService.save(position);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(position, "position.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/administrator/delete", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Position position, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(position);
		else
			try {
				this.positionService.delete(position);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(position, "position.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Position position) {
		ModelAndView result;

		result = this.createEditModelAndView(position, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Position position, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("position/administrator/edit");
		result.addObject("position", position);
		result.addObject("message", messageCode);

		return result;
	}

}
