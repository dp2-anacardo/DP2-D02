
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FinderRepository;
import domain.Configuration;
import domain.Finder;
import domain.Procession;

@Service
@Transactional
public class FinderService {

	//Managed repository
	@Autowired
	private FinderRepository		finderRepository;

	//Services
	@Autowired
	private ConfigurationService	configurationService;

	//Validator
	@Autowired
	private Validator				validator;


	//Simple CRUD Methods
	public Finder create() {
		Finder result;
		result = new Finder();
		final Collection<Procession> processions = new ArrayList<Procession>();
		result.setLastUpdate(new Date());
		result.setProcessions(processions);
		return result;
	}
	public Collection<Finder> findAll() {
		return this.finderRepository.findAll();
	}

	public Finder findOne(final Integer id) {
		return this.finderRepository.findOne(id);
	}

	public Finder save(Finder finder) {

		Assert.notNull(finder);
		Collection<Procession> result = Collections.emptyList();
		List<Procession> pro1 = null;
		List<Procession> pro2 = null;
		List<Procession> pro3 = null;
		if (!(finder.getKeyWord() == null || finder.getKeyWord() == ""))
			pro1 = (List<Procession>) this.finderRepository.getProcessionsByKeyWord(finder.getKeyWord());
		if (finder.getMinimumDate() != null && finder.getMaximumDate() != null)
			pro2 = (List<Procession>) this.finderRepository.getProcessionsByDateRange(finder.getMinimumDate(), finder.getMaximumDate());
		if (!(finder.getAreaName() == null || finder.getAreaName() == ""))
			pro3 = (List<Procession>) this.finderRepository.getProcessionsByArea(finder.getAreaName());
		if (!(pro1 == null && pro2 == null && pro3 == null)) {
			if (pro1 == null)
				pro1 = (List<Procession>) this.finderRepository.findAllFinal();
			if (pro2 == null)
				pro2 = (List<Procession>) this.finderRepository.findAllFinal();
			if (pro3 == null)
				pro3 = (List<Procession>) this.finderRepository.findAllFinal();
			pro1.retainAll(pro2);
			pro1.retainAll(pro3);

			result = pro1;
		}

		Configuration conf;
		conf = this.configurationService.getConfiguration();

		if (result.size() > conf.getMaxResults()) {

			final List<Procession> copy = (List<Procession>) result;

			final List<Procession> processionsLim = new ArrayList<Procession>();
			for (int i = 0; i < conf.getMaxResults(); i++)
				processionsLim.add(copy.get(i));
			result = processionsLim;

		}

		finder.setProcessions(result);
		final Date moment = new Date();
		finder.setLastUpdate(moment);

		finder = this.finderRepository.save(finder);
		return finder;
	}

	//Reconstruct
	public Finder reconstruct(final Finder finder, final BindingResult binding) {
		Finder result;

		result = this.finderRepository.findOne(finder.getId());

		finder.setVersion(result.getVersion());

		result = finder;
		this.validator.validate(finder, binding);

		return result;
	}

	//Another methods
	public Collection<Procession> findAllFinal() {
		return this.finderRepository.findAllFinal();
	}

}
