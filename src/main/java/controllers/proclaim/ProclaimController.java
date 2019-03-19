
package controllers.proclaim;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.ChapterService;
import services.ProclaimService;
import controllers.AbstractController;
import domain.Proclaim;

@Controller
@RequestMapping("proclaim")
public class ProclaimController extends AbstractController {

	@Autowired
	private ProclaimService	proclaimService;

	@Autowired
	private ChapterService	chapterService;

	@Autowired
	private ActorService	actorService;


	@ExceptionHandler(TypeMismatchException.class)
	public ModelAndView handleMismatchException(final TypeMismatchException oops) {
		if (LoginService.getPrincipal().getAuthorities().iterator().next().getAuthority().equals("CHAPTER"))
			return new ModelAndView("redirect:/proclaim/chapter/list.do");
		else
			return new ModelAndView("redirect:/proclaim/list.do");
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Proclaim> proclaims;

		proclaims = this.proclaimService.findAll();
		result = new ModelAndView("proclaim/list");
		result.addObject("proclaims", proclaims);
		result.addObject("requestURI", "proclaim/list.do");

		return result;
	}

	@RequestMapping(value = "/chapter/list", method = RequestMethod.GET)
	public ModelAndView listChapter() {
		ModelAndView result;
		Collection<Proclaim> proclaims;

		final int chapterId = this.actorService.getActorLogged().getId();
		proclaims = this.chapterService.getProclaims(chapterId);

		result = new ModelAndView("proclaim/chapter/list");
		result.addObject("proclaims", proclaims);
		result.addObject("requestURI", "proclaim/chapter/list.do");

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int proclaimId) {
		ModelAndView result;
		Proclaim proclaim;

		try {
			proclaim = this.proclaimService.findOne(proclaimId);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/misc/403");
			return result;
		}
		result = new ModelAndView("proclaim/show");
		result.addObject("proclaim", proclaim);
		result.addObject("requestURI", "proclaim/show.do");

		return result;
	}

	@RequestMapping(value = "/chapter/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Proclaim proclaim;

		proclaim = this.proclaimService.create();
		result = this.createEditModelAndView(proclaim);

		return result;
	}

	@RequestMapping(value = "/chapter/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int proclaimId) {
		ModelAndView result;
		Proclaim proclaim;

		try {
			proclaim = this.proclaimService.findOne(proclaimId);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/misc/403");
			return result;
		}
		result = this.createEditModelAndView(proclaim);
		return result;
	}

	@RequestMapping(value = "/chapter/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Proclaim proclaim, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(proclaim);
		else
			try {
				this.proclaimService.save(proclaim);
				result = new ModelAndView("redirect:list.do");
			} catch (final Exception e) {
				result = this.createEditModelAndView(proclaim, "proclaim.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Proclaim proclaim) {
		ModelAndView result;

		result = this.createEditModelAndView(proclaim, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Proclaim proclaim, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("proclaim/chapter/edit");
		result.addObject("proclaim", proclaim);
		result.addObject("message", messageCode);

		return result;
	}
}
