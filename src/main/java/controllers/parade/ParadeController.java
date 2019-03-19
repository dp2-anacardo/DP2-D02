
package controllers.parade;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.BrotherhoodService;
import services.FinderService;
import services.FloatEntityService;
import services.ParadeService;
import controllers.AbstractController;
import domain.Actor;
import domain.Brotherhood;
import domain.FloatEntity;
import domain.Parade;

@Controller
@RequestMapping("parade")
public class ParadeController extends AbstractController {

	@Autowired
	private ParadeService	paradeService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private FloatEntityService	floatService;

	@Autowired
	private FinderService		finderService;


	@RequestMapping(value = "/brotherhood/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Parade> parades;

		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
		final Brotherhood b = this.brotherhoodService.findOne(user.getId());
		parades = this.paradeService.getParadesByBrotherhood(b);

		result = new ModelAndView("parade/brotherhood/list");
		result.addObject("parade", parades);
		result.addObject("requestURI", "parade/brotherhood/list.do");
		return result;
	}

	@RequestMapping(value = "/brotherhood/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Parade parade;

		parade = this.paradeService.create();
		result = this.createEditModelAndView(parade);

		return result;
	}

	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int paradeId) {
		ModelAndView result;
		Parade parade;

		parade = this.paradeService.findOne(paradeId);

		if (parade == null)
			result = new ModelAndView("redirect:/misc/403");
		else
			result = this.createEditModelAndView(parade);
		return result;
	}

	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.POST, params = "saveDraft")
	public ModelAndView saveDraft(Parade parade, final BindingResult binding) {
		ModelAndView result;
		parade.setIsFinal(false);
		parade = this.paradeService.reconstruct(parade, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(parade);
		else
			try {
				this.paradeService.saveDraft(parade);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(parade, "parade.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.POST, params = "saveFinal")
	public ModelAndView saveFinal(Parade parade, final BindingResult binding) {
		ModelAndView result;
		parade.setIsFinal(false);
		parade = this.paradeService.reconstruct(parade, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(parade);
		else
			try {
				this.paradeService.saveFinal(parade);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(parade, "parade.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/listNotRegister", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int brotherhoodId) {

		ModelAndView result;
		Collection<Parade> pro;
		pro = this.paradeService.getParadesFinalByBrotherhood(brotherhoodId);
		if (pro == null || pro.size() <= 0)
			result = new ModelAndView("redirect:/misc/403");
		else {
			result = new ModelAndView("parade/listNotRegister");
			result.addObject("parade", pro);
			result.addObject("requestURI", "parade/listNotRegister.do");
		}
		return result;
	}
	@RequestMapping(value = "/listForMembers", method = RequestMethod.GET)
	public ModelAndView listMembers() {

		ModelAndView result;
		final Collection<Parade> pro = this.finderService.findAllFinal();

		if (pro == null || pro.size() <= 0)
			result = new ModelAndView("redirect:/misc/403");
		else {

			result = new ModelAndView("parade/listForMembers");
			result.addObject("parade", pro);
			result.addObject("requestURI", "parade/listForMembers.do");
		}
		return result;
	}
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int paradeId) {
		ModelAndView result;
		Parade p;

		p = this.paradeService.findOne(paradeId);
		if (p == null)
			result = new ModelAndView("redirect:/misc/403");
		else {
			result = new ModelAndView("parade/show");
			result.addObject("p", p);
		}
		return result;

	}

	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Parade parade) {
		ModelAndView result;
		try {
			this.paradeService.delete(this.paradeService.findOne(parade.getId()));
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(parade, "parade.commit.error");
		}

		return result;
	}
	private ModelAndView createEditModelAndView(final Parade parade) {
		ModelAndView result;
		result = this.createEditModelAndView(parade, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final Parade parade, final String messageCode) {
		ModelAndView result;
		Collection<FloatEntity> floats;
		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
		final Brotherhood b = this.brotherhoodService.findOne(user.getId());
		floats = this.floatService.getFloatsByBrotherhood(b);

		result = new ModelAndView("parade/brotherhood/edit");
		result.addObject("parade", parade);
		result.addObject("message", messageCode);
		result.addObject("floats", floats);
		return result;
	}

}
