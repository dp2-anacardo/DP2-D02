
package services;

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

	//Reconstruct
	public Configuration reconstruct(final Configuration config, final BindingResult binding) {
		Configuration result;

		if (config.getId() == 0)
			result = config;
		else {
			result = this.configurationRepository.findOne(config.getId());

			result.setMaxResults(config.getMaxResults());
			result.setMaxTime(config.getMaxTime());
			result.setBanner(config.getBanner());
			result.setSystemName(config.getSystemName());
			result.setWelcomeMessageEn(config.getWelcomeMessageEn());
			result.setWelcomeMessageEs(config.getWelcomeMessageEs());
			result.setDefaultCC(config.getDefaultCC());
			//result.setSpamWords(config.getSpamWords());

			this.validator.validate(result, binding);
		}
		return result;
	}

}
