
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigurationService;
import services.FinderService;
import services.MemberService;
import services.ProcessionService;
import domain.Actor;
import domain.Finder;
import domain.Member;
import domain.Procession;

@Controller
@RequestMapping("finder/member")
public class FinderController extends AbstractController {

	@Autowired
	private FinderService			finderService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private MemberService			memberService;
	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private ProcessionService		processionService;


	//LIST RESULTS
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Procession> processions;
		result = new ModelAndView("finder/member/list");

		//Sacar finder del Member
		final Actor user = this.actorService.getActorLogged();
		final Member member = this.memberService.findOne(user.getId());

		Finder finder;
		finder = member.getFinder();

		//Borrar si fechaLimite < fechaActual
		final Date fechaActual = new Date();
		final Date lastUpdate = finder.getLastUpdate();
		final int horasDeGuardado = this.configurationService.findAll().get(0).getMaxTime();
		final Date fechaLimite = new Date(lastUpdate.getTime() + (horasDeGuardado * 3600000L));

		if (fechaActual.after(fechaLimite)) {
			finder.setProcessions(new ArrayList<Procession>());
			this.finderService.save(finder);
		}

		if (finder.getProcessions().isEmpty()) {
			result = new ModelAndView("procession/listNotRegister");
			result.addObject("procession", this.processionService.findAll());
			result.addObject("requestURI", "procession/listNotRegister.do");
		} else {

			processions = finder.getProcessions();

			result.addObject("processions", processions);
			result.addObject("finder", finder);
			result.addObject("requestURI", "finder/member/list.do");
		}

		return result;
	}
	//EDIT FINDER
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Finder finder;

		final Actor user = this.actorService.getActorLogged();
		final Member member = this.memberService.findOne(user.getId());

		finder = member.getFinder();
		Assert.notNull(finder);
		result = this.createEditModelAndView(finder);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("finder") Finder finder, final BindingResult binding) {
		ModelAndView result;

		finder = this.finderService.reconstruct(finder, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(finder);
		else
			try {
				this.finderService.save(finder);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(finder, oops.getMessage()/* "finder.update.error" */);
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Finder finder) {
		ModelAndView result;

		result = this.createEditModelAndView(finder, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Finder finder, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("finder/member/edit");
		result.addObject("finder", finder);
		result.addObject("messageCode", messageCode);

		return result;
	}
}
