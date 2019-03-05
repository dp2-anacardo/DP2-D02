
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ConfigurationRepository;
import security.UserAccount;
import domain.Configuration;
import forms.ConfigurationForm;

@Service
@Transactional
public class ConfigurationService {

	//Managed repository
	@Autowired
	private ConfigurationRepository	configurationRepository;
	//Services
	@Autowired
	private ActorService			actorService;
	//Validator
	@Autowired
	private Validator				validator;


	//CRUD
	public List<Configuration> findAll() {
		return this.configurationRepository.findAll();
	}

	public Configuration findOne(final Integer arg0) {
		return this.configurationRepository.findOne(arg0);
	}

	public Configuration save(final Configuration configuration) {
		Assert.notNull(configuration);

		UserAccount userAccount;
		userAccount = this.actorService.getActorLogged().getUserAccount();

		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Configuration result;
		result = this.configurationRepository.save(configuration);

		return result;
	}

	//Reconstructs
	public Configuration reconstructEdit(final ConfigurationForm configF, final BindingResult binding) {
		Configuration config;
		final Configuration result = new Configuration();

		config = this.configurationRepository.findOne(configF.getId());

		result.setId(config.getId());
		result.setVersion(config.getVersion());
		result.setSpamWords(config.getSpamWords());
		result.setPositiveWords(config.getPositiveWords());
		result.setNegativeWords(config.getNegativeWords());

		result.setMaxResults(configF.getMaxResults());
		result.setMaxTime(configF.getMaxTime());
		result.setSystemName(configF.getSystemName());
		result.setBanner(configF.getBanner());
		result.setWelcomeMessageEn(configF.getWelcomeMessageEn());
		result.setWelcomeMessageEs(configF.getWelcomeMessageEs());
		result.setDefaultCC(configF.getDefaultCC());

		this.validator.validate(result, binding);

		return result;
	}

	public Configuration reconstructAddWord(final ConfigurationForm configF, final BindingResult binding) {
		Configuration config;
		final Configuration result = new Configuration();

		config = this.configurationRepository.findOne(configF.getId());

		result.setMaxResults(config.getMaxResults());
		result.setMaxTime(config.getMaxTime());
		result.setSystemName(config.getSystemName());
		result.setBanner(config.getBanner());
		result.setWelcomeMessageEn(config.getWelcomeMessageEn());
		result.setWelcomeMessageEs(config.getWelcomeMessageEs());
		result.setDefaultCC(config.getDefaultCC());

		final Collection<String> sW = new ArrayList<String>();
		final Collection<String> pW = new ArrayList<String>();
		final Collection<String> nW = new ArrayList<String>();

		sW.addAll(config.getSpamWords());
		pW.addAll(config.getPositiveWords());
		nW.addAll(config.getNegativeWords());

		result.setSpamWords(sW);
		result.setPositiveWords(pW);
		result.setNegativeWords(nW);

		if (!configF.getAddSW().equals(""))
			result.getSpamWords().add(configF.getAddSW());

		if (!configF.getAddPW().equals(""))
			result.getPositiveWords().add(configF.getAddPW());

		if (!configF.getAddNW().equals(""))
			result.getNegativeWords().add(configF.getAddNW());

		result.setId(config.getId());
		result.setVersion(config.getVersion());

		return result;
	}
}
