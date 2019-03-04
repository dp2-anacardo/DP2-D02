
package services;

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

	//Reconstruct
	public Configuration reconstruct(final ConfigurationForm configF, final BindingResult binding) {
		Configuration result;

		result = this.configurationRepository.findOne(configF.getId());

		result.setVersion(configF.getVersion());
		final Collection<String> sW = result.getSpamWords();
		final Collection<String> pW = result.getPositiveWords();
		final Collection<String> nW = result.getNegativeWords();

		if (!configF.getAddSW().equals(""))
			sW.add(configF.getAddSW());

		if (!configF.getAddPW().equals(""))
			pW.add(configF.getAddNW());

		if (!configF.getAddNW().equals(""))
			nW.add(configF.getAddNW());

		result.setSpamWords(sW);
		result.setPositiveWords(pW);
		result.setSpamWords(nW);

		this.validator.validate(result, binding);

		return result;
	}
}
