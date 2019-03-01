
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigurationService;
import domain.Actor;
import domain.Configuration;

@Controller
@RequestMapping("configuration/administrator")
public class ConfigurationController extends AbstractController {

	@Autowired
	private ActorService			actorService;
	//@Autowired
	//private AdministratorService	administratorService;
	@Autowired
	private ConfigurationService	configurationService;


	@RequestMapping(value = "/deleteSWord", method = RequestMethod.GET)
	public ModelAndView deleteSWord(final String spamWord) {
		final ModelAndView result;
		final Configuration config = this.configurationService.findAll().get(0);

		final Actor user = this.actorService.getActorLogged();
		//final Administrator admin = this.administratorService.findOne(user.getId());
		//Assert.notNull(admin);

		final Collection<String> sW = config.getSpamWords();
		sW.remove(spamWord);
		config.setSpamWords(sW);

		this.configurationService.save(config);
		result = new ModelAndView("redirect:/configuration/administrator/edit.do");

		return result;
	}

	@RequestMapping(value = "/deletePWord", method = RequestMethod.GET)
	public ModelAndView deletePWord(final String posWord) {
		final ModelAndView result;
		final Configuration config = this.configurationService.findAll().get(0);

		final Actor user = this.actorService.getActorLogged();
		//final Administrator admin = this.administratorService.findOne(user.getId());
		//Assert.notNull(admin);

		final Collection<String> pW = config.getPositiveWords();
		pW.remove(posWord);
		config.setPositiveWords(pW);

		this.configurationService.save(config);
		result = new ModelAndView("redirect:/configuration/administrator/edit.do");

		return result;
	}

	@RequestMapping(value = "/deleteNWord", method = RequestMethod.GET)
	public ModelAndView deleteNWord(final String negWord) {
		final ModelAndView result;
		final Configuration config = this.configurationService.findAll().get(0);

		final Actor user = this.actorService.getActorLogged();
		//final Administrator admin = this.administratorService.findOne(user.getId());
		//Assert.notNull(admin);

		final Collection<String> nW = config.getNegativeWords();
		nW.remove(negWord);
		config.setNegativeWords(nW);

		this.configurationService.save(config);
		result = new ModelAndView("redirect:/configuration/administrator/edit.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		final Actor user = this.actorService.getActorLogged();
		//final Administrator admin = this.administratorService.findOne(user.getId());
		//Assert.notNull(admin);

		result = this.editModelAndView(this.configurationService.findAll().get(0));

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView update(@Valid Configuration config, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.editModelAndView(config);
		else
			try {
				config = this.configurationService.reconstruct(config, binding);
				this.configurationService.save(config);
				result = new ModelAndView("redirect:/configuration/administrator/show.do");
			} catch (final Throwable oops) {
				result = this.editModelAndView(config, "configuration.edit.error"); //"Administrator.commit.error"
			}
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView result;

		final Configuration config = this.configurationService.findAll().get(0);

		result = new ModelAndView("configuration/administrator/show");
		result.addObject("config", config);

		return result;
	}

	protected ModelAndView editModelAndView(final Configuration config) {
		ModelAndView result;
		result = this.editModelAndView(config, null);
		return result;
	}

	protected ModelAndView editModelAndView(final Configuration config, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("configuration/administrator/edit");
		result.addObject("configuration", config);
		result.addObject("messageCode", messageCode);

		return result;
	}

}
