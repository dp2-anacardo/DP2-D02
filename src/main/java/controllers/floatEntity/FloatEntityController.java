
package controllers.floatEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.BrotherhoodService;
import services.FloatEntityService;
import controllers.AbstractController;
import domain.Actor;
import domain.Brotherhood;
import domain.FloatEntity;

@Controller
@RequestMapping("/floatEntity")
public class FloatEntityController extends AbstractController {

	@Autowired
	private FloatEntityService	floatEntityService;

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private ActorService		actorService;


	@RequestMapping(value = "/listNotRegister", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int brotherhoodId) {

		ModelAndView result;
		if (this.brotherhoodService.findOne(brotherhoodId) == null)
			return new ModelAndView("redirect:/misc/403");
		Collection<FloatEntity> f = new ArrayList<FloatEntity>();
		f = this.floatEntityService.getFloatsByBrotherhood(this.brotherhoodService.findOne(brotherhoodId));
		result = new ModelAndView("floatEntity/listNotRegister");
		result.addObject("floatEntity", f);
		result.addObject("requestURI", "floatEntity/listNotRegister.do");

		return result;
	}

	@RequestMapping(value = "/brotherhood/list", method = RequestMethod.GET)
	public ModelAndView list2() {

		ModelAndView result;
		final Collection<FloatEntity> floats;

		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
		final Brotherhood b = this.brotherhoodService.findOne(user.getId());
		floats = this.floatEntityService.getFloatsByBrotherhood(b);

		result = new ModelAndView("floatEntity/brotherhood/list");
		result.addObject("floatEntity", floats);
		result.addObject("requestURI", "floatEntity/brotherhood/list.do");
		return result;
	}

	@RequestMapping(value = "/brotherhood/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		FloatEntity floatEntity;

		floatEntity = this.floatEntityService.create();
		result = this.createEditModelAndView(floatEntity);

		return result;
	}

	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.GET)
	public ModelAndView edit(final int floatEntityId) {
		ModelAndView result;
		FloatEntity floatEntity;
		floatEntity = this.floatEntityService.findOne(floatEntityId);
		if (floatEntity == null)
			result = new ModelAndView("redirect:/misc/403");
		else
			result = this.createEditModelAndView(floatEntity);
		return result;
	}

	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(FloatEntity floatEntity, final BindingResult binding) {
		ModelAndView result;
		floatEntity = this.floatEntityService.reconstruct(floatEntity, binding);
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(floatEntity);
			final List<ObjectError> errors = binding.getAllErrors();
			for (final ObjectError e : errors)
				if (e.getDefaultMessage().equals("URL incorrecta") || e.getDefaultMessage().equals("Invalid URL"))
					result.addObject("attachmentError", e.getDefaultMessage());

		} else
			try {
				this.floatEntityService.save(floatEntity);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(floatEntity, "float.commit.error");

			}
		return result;

	}

	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@ModelAttribute("floatEntity") FloatEntity floatEntity, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(floatEntity);
		else
			try {
				floatEntity = this.floatEntityService.reconstruct(floatEntity, binding);
				this.floatEntityService.delete(floatEntity);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(floatEntity, "float.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/brotherhood/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int floatEntityId) {
		ModelAndView result;
		FloatEntity floatEntity;

		floatEntity = this.floatEntityService.findOne(floatEntityId);
		if (floatEntity == null)
			result = new ModelAndView("redirect:/misc/403");
		else {
			result = new ModelAndView("floatEntity/brotherhood/show");
			result.addObject("floatEntity", floatEntity);
		}
		return result;

	}

	private ModelAndView createEditModelAndView(final FloatEntity floatEntity) {
		ModelAndView result;
		result = this.createEditModelAndView(floatEntity, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final FloatEntity floatEntity, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("floatEntity/brotherhood/edit");
		result.addObject("floatEntity", floatEntity);
		result.addObject("message", messageCode);
		return result;
	}

}
