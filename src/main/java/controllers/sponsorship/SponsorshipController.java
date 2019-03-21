
package controllers.sponsorship;

import java.util.Collection;

import javax.swing.JOptionPane;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.ParadeService;
import services.SponsorService;
import services.SponsorshipService;
import controllers.AbstractController;
import domain.Parade;
import domain.Sponsor;
import domain.Sponsorship;

@Controller
@RequestMapping("sponsorship")
public class SponsorshipController extends AbstractController {

	@Autowired
	private SponsorshipService		sponsorshipService;

	@Autowired
	private SponsorService			sponsorService;

	@Autowired
	private ParadeService			paradeService;

	@Autowired
	private ConfigurationService	configurationService;


	@ExceptionHandler(TypeMismatchException.class)
	public ModelAndView handleMismatchException(final TypeMismatchException oops) {
		return new ModelAndView("redirect:/");
	}

	// List -------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Sponsorship> sponsorships;

		final Sponsor principal = this.sponsorService.findByPrincipal();

		sponsorships = this.sponsorshipService.findBySponsor(principal.getId());

		result = new ModelAndView("sponsorship/list");
		result.addObject("sponsorships", sponsorships);
		result.addObject("requestURI", "sponsorship/list.do");

		return result;
	}

	// Create ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Sponsorship sponsorship;

		sponsorship = this.sponsorshipService.create();
		result = this.createEditModelAndView(sponsorship);

		return result;
	}

	// Edition -------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int sponsorshipID) {
		ModelAndView result;
		final Sponsorship sponsorship;

		try {
			final Sponsor principal = this.sponsorService.findByPrincipal();
			sponsorship = this.sponsorshipService.findOne(sponsorshipID);

		} catch (final Exception e) {
			result = this.forbiddenOpperation();
			return result;
		}

		result = this.createEditModelAndView(sponsorship);

		return result;
	}
	// Save -------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Sponsorship sponsorship, final BindingResult binding) {
		ModelAndView result;
		Sponsorship spship;
		Sponsorship security;

		try {
			spship = this.sponsorshipService.reconstruct(sponsorship, binding);

			if (binding.hasErrors())
				result = this.createEditModelAndView(spship);

			else {
				try {
					final Sponsor principal = this.sponsorService.findByPrincipal();
					if (sponsorship.getId() != 0)
						security = this.sponsorshipService.findOne(sponsorship.getId());
				} catch (final Exception e) {
					result = this.forbiddenOpperation();
					return result;
				}

				this.sponsorshipService.save(spship);
				result = new ModelAndView("redirect:list.do");
			}
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(sponsorship, "sponsorship.commit.error");
		}

		return result;
	}

	// Delete ------------------------------------------------------
	@RequestMapping(value = "/remove", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int sponsorshipId) {
		ModelAndView result;
		Sponsorship sponsorship;

		sponsorship = this.sponsorshipService.findOne(sponsorshipId);

		try {
			this.sponsorshipService.delete(sponsorship);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(sponsorship, "profile.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/activate", method = RequestMethod.GET)
	public ModelAndView activate(@RequestParam final int sponsorshipId) {
		ModelAndView result;

		final Sponsorship sponsorship = this.sponsorshipService.findOne(sponsorshipId);
		this.sponsorshipService.activate(sponsorship);

		result = new ModelAndView("redirect:/sponsorship/list.do");

		return result;
	}

	@RequestMapping(value = "/desactivate", method = RequestMethod.GET)
	public ModelAndView desactivate(@RequestParam final int sponsorshipId) {
		ModelAndView result;

		final Sponsorship sponsorship = this.sponsorshipService.findOne(sponsorshipId);
		this.sponsorshipService.desactivate(sponsorship);

		result = new ModelAndView("redirect:/sponsorship/list.do");

		return result;
	}

	// Ancillary methods ------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship) {
		ModelAndView result;

		result = this.createEditModelAndView(sponsorship, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship, final String message) {
		ModelAndView result;

		if (sponsorship.getId() == 0)
			result = new ModelAndView("sponsorship/create");
		else
			result = new ModelAndView("sponsorship/update");

		//final Collection<Parade> paradeList = this.paradeService.findAll();
		final Collection<Parade> paradeList = this.paradeService.getParadesFinal();
		final Collection<String> brandList = this.configurationService.getConfiguration().getBrandName();

		result.addObject("sponsorship", sponsorship);
		result.addObject("paradeList", paradeList);
		result.addObject("brandList", brandList);
		result.addObject("message", message);

		return result;
	}
	private ModelAndView forbiddenOpperation() {
		JOptionPane.showMessageDialog(null, "Forbidden operation");
		return new ModelAndView("redirect:list.do");
	}

}
