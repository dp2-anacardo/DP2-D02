
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
import domain.Parade;

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
		final Collection<Parade> parades = new ArrayList<Parade>();
		result.setLastUpdate(new Date());
		result.setParades(parades);
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
		Collection<Parade> result = Collections.emptyList();
		List<Parade> pro1 = null;
		List<Parade> pro2 = null;
		List<Parade> pro3 = null;
		if (!(finder.getKeyWord() == null || finder.getKeyWord() == ""))
			pro1 = (List<Parade>) this.finderRepository.getParadesByKeyWord(finder.getKeyWord());
		if (finder.getMinimumDate() != null && finder.getMaximumDate() != null)
			pro2 = (List<Parade>) this.finderRepository.getParadesByDateRange(finder.getMinimumDate(), finder.getMaximumDate());
		if (!(finder.getAreaName() == null || finder.getAreaName() == ""))
			pro3 = (List<Parade>) this.finderRepository.getParadesByArea(finder.getAreaName());
		if (!(pro1 == null && pro2 == null && pro3 == null)) {
			if (pro1 == null)
				pro1 = (List<Parade>) this.finderRepository.findAllFinal();
			if (pro2 == null)
				pro2 = (List<Parade>) this.finderRepository.findAllFinal();
			if (pro3 == null)
				pro3 = (List<Parade>) this.finderRepository.findAllFinal();
			pro1.retainAll(pro2);
			pro1.retainAll(pro3);

			result = pro1;
		}

		Configuration conf;
		conf = this.configurationService.getConfiguration();

		if (result.size() > conf.getMaxResults()) {

			final List<Parade> copy = (List<Parade>) result;

			final List<Parade> paradesLim = new ArrayList<Parade>();
			for (int i = 0; i < conf.getMaxResults(); i++)
				paradesLim.add(copy.get(i));
			result = paradesLim;

		}

		finder.setParades(result);
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
	public Collection<Parade> findAllFinal() {
		return this.finderRepository.findAllFinal();
	}

}
