
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

		result.setId(result.getId());
		result.setVersion(result.getVersion());
		result.setMaxResults(configF.getMaxResults());
		result.setMaxTime(configF.getMaxTime());
		result.setSystemName(configF.getSystemName());
		result.setBanner(configF.getBanner());
		result.setWelcomeMessageEn(configF.getWelcomeMessageEn());
		result.setWelcomeMessageEs(configF.getWelcomeMessageEs());
		result.setDefaultCC(configF.getDefaultCC());
		final Collection<String> sW = result.getSpamWords();
		final Collection<String> pW = result.getPositiveWords();
		final Collection<String> nW = result.getNegativeWords();

		if (!configF.getAddSW().equals(""))
			sW.add(configF.getAddSW());

		if (!configF.getAddPW().equals(""))
			pW.add(configF.getAddPW());

		if (!configF.getAddNW().equals(""))
			nW.add(configF.getAddNW());

		result.setSpamWords(sW);
		result.setPositiveWords(pW);
		result.setNegativeWords(nW);
		this.validator.validate(configF, binding);

		return result;
	}

	//Reconstruct
	public Configuration reconstructAdd(final ConfigurationForm configF, final BindingResult binding) {
		Configuration result;
		final Configuration c = new Configuration();

		result = this.configurationRepository.findOne(configF.getId());

		c.setId(result.getId());
		c.setVersion(result.getVersion());
		c.setMaxResults(configF.getMaxResults());
		c.setMaxTime(configF.getMaxTime());
		c.setSystemName(configF.getSystemName());
		c.setBanner(configF.getBanner());
		c.setWelcomeMessageEn(configF.getWelcomeMessageEn());
		c.setWelcomeMessageEs(configF.getWelcomeMessageEs());
		c.setDefaultCC(configF.getDefaultCC());
		final Collection<String> sW = result.getSpamWords();
		final Collection<String> pW = result.getPositiveWords();
		final Collection<String> nW = result.getNegativeWords();

		if (!configF.getAddSW().equals(""))
			sW.add(configF.getAddSW());

		if (!configF.getAddPW().equals(""))
			pW.add(configF.getAddPW());

		if (!configF.getAddNW().equals(""))
			nW.add(configF.getAddNW());

		c.setSpamWords(sW);
		c.setPositiveWords(pW);
		c.setNegativeWords(nW);
		//result = c;
		this.validator.validate(c, binding);

		return result;
	}
}
