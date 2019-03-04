
package controllers.procession;

import java.util.ArrayList;
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
import services.FloatEntityService;
import services.ProcessionService;
import controllers.AbstractController;
import domain.Actor;
import domain.Brotherhood;
import domain.FloatEntity;
import domain.Procession;

@Controller
@RequestMapping("procession")
public class ProcessionController extends AbstractController {

	@Autowired
	private ProcessionService	processionService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private FloatEntityService	floatService;


	@RequestMapping(value = "/brotherhood/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Procession> processions;

		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
		final Brotherhood b = this.brotherhoodService.findOne(user.getId());
		processions = this.processionService.getProcessionsByBrotherhood(b);

		result = new ModelAndView("procession/brotherhood/list");
		result.addObject("procession", processions);
		result.addObject("requestURI", "procession/brotherhood/list.do");
		return result;
	}

	@RequestMapping(value = "/brotherhood/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Procession procession;

		procession = this.processionService.create();
		result = this.createEditModelAndView(procession);

		return result;
	}

	@RequestMapping(value = "/brotherhood/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int processionId) {
		ModelAndView result;
		Procession procession;

		procession = this.processionService.findOne(processionId);

		if (procession == null)
			result = new ModelAndView("redirect:/misc/403");
		else
			result = this.createEditModelAndView(procession);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveDraft")
	public ModelAndView saveDraft(Procession procession, final BindingResult binding) {
		ModelAndView result;
		procession.setIsFinal(false);
		procession = this.processionService.reconstruct(procession, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(procession);
		else
			try {
				this.processionService.saveDraft(procession);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(procession, "procession.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveFinal")
	public ModelAndView saveFinal(Procession procession, final BindingResult binding) {
		ModelAndView result;
		procession.setIsFinal(false);
		procession = this.processionService.reconstruct(procession, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(procession);
		else
			try {
				this.processionService.saveFinal(procession);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(procession, "procession.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/listNotRegister", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int brotherhoodId) {

		ModelAndView result;
		Collection<Procession> pro = new ArrayList<Procession>();
		pro = this.processionService.findAll();
		for (final Procession p : pro)
			if (p.getBrotherhood().getId() == brotherhoodId)
				pro.remove(p);
		result = new ModelAndView("procession/listNotRegister");
		result.addObject("procession", pro);
		result.addObject("requestURI", "procession/listNotRegister.do");

		return result;
	}
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int processionId) {
		ModelAndView result;
		Procession p;

		p = this.processionService.findOne(processionId);

		result = new ModelAndView("procession/brotherhood/show");
		result.addObject("p", p);

		return result;

	}

	private ModelAndView createEditModelAndView(final Procession procession) {
		ModelAndView result;
		result = this.createEditModelAndView(procession, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final Procession procession, final String messageCode) {
		ModelAndView result;
		Collection<FloatEntity> floats;
		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
		final Brotherhood b = this.brotherhoodService.findOne(user.getId());
		floats = this.floatService.getFloatsByBrotherhood(b);

		result = new ModelAndView("procession/brotherhood/edit");
		result.addObject("procession", procession);
		result.addObject("message", messageCode);
		result.addObject("floats", floats);
		return result;
	}

}
