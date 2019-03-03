
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import services.ConfigurationService;
import domain.Actor;
import domain.Administrator;
import domain.Configuration;
import forms.ConfigurationForm;

@Controller
@RequestMapping("configuration/administrator")
public class ConfigurationController extends AbstractController {

	@Autowired
	private ActorService			actorService;
	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private ConfigurationService	configurationService;


	// WORD DELETES
	@RequestMapping(value = "/deleteSWord", method = RequestMethod.GET)
	public ModelAndView deleteSWord(final String spamWord) {
		final ModelAndView result;
		final Configuration config = this.configurationService.findAll().get(0);

		final Actor user = this.actorService.getActorLogged();
		final Administrator admin = this.administratorService.findOne(user.getId());
		Assert.notNull(admin);

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
		final Administrator admin = this.administratorService.findOne(user.getId());
		Assert.notNull(admin);

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
		final Administrator admin = this.administratorService.findOne(user.getId());
		Assert.notNull(admin);

		final Collection<String> nW = config.getNegativeWords();
		nW.remove(negWord);
		config.setNegativeWords(nW);

		this.configurationService.save(config);
		result = new ModelAndView("redirect:/configuration/administrator/edit.do");

		return result;
	}
	// WORD ADDS
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "addSW")
	public ModelAndView addSWord(@Valid final ConfigurationForm configF, final BindingResult binding) {
		ModelAndView result;
		final Configuration config = this.configurationService.findAll().get(0);

		final Actor user = this.actorService.getActorLogged();
		final Administrator admin = this.administratorService.findOne(user.getId());
		Assert.notNull(admin);

		if (binding.hasErrors())
			result = this.editModelAndView(config);
		else
			try {
				final Collection<String> sW = config.getSpamWords();
				sW.add(configF.getAddSW());
				config.setNegativeWords(sW);

				this.configurationService.save(config);
				result = new ModelAndView("redirect:/configuration/administrator/edit.do");

			} catch (final Throwable oops) {
				result = this.editModelAndView(config, "configuration.edit.error"); //"Administrator.commit.error"
			}
		return result;
	}

	// EDIT GET & POST
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		final Actor user = this.actorService.getActorLogged();
		final Administrator admin = this.administratorService.findOne(user.getId());
		Assert.notNull(admin);

		result = this.editModelAndView(this.configurationService.findAll().get(0));

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView update(@Valid final Configuration config, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.editModelAndView(config);
		else
			try {
				this.configurationService.save(config);
				result = new ModelAndView("redirect:/configuration/administrator/show.do");
			} catch (final Throwable oops) {
				result = this.editModelAndView(config, "configuration.edit.error"); //"Administrator.commit.error"
			}
		return result;
	}

	// SHOW/DISPLAY
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView result;

		final Configuration config = this.configurationService.findAll().get(0);

		result = new ModelAndView("configuration/administrator/show");
		result.addObject("config", config);

		return result;
	}

	// MODEL&VIEW
	protected ModelAndView editModelAndView(final Configuration config) {
		ModelAndView result;
		result = this.editModelAndView(config, null);
		return result;
	}

	protected ModelAndView editModelAndView(final Configuration config, final String messageCode) {
		ModelAndView result;

		final ConfigurationForm configF = new ConfigurationForm();

		result = new ModelAndView("configuration/administrator/edit");
		result.addObject("config", config);
		result.addObject("configF", configF);
		result.addObject("messageCode", messageCode);

		return result;
	}

}
