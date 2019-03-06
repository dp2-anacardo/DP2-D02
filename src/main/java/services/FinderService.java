
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		final Set<Procession> allProcessions = new HashSet<Procession>();
		if (!(finder.getKeyWord() == null || finder.getKeyWord() == "")) {
			final Collection<Procession> pro = this.finderRepository.getProcessionsByKeyWord(finder.getKeyWord());
			allProcessions.addAll(pro);
		}
		if (finder.getMinimumDate() != null && finder.getMaximumDate() != null) {
			final Collection<Procession> pro = this.finderRepository.getProcessionsByDateRange(finder.getMinimumDate(), finder.getMaximumDate());
			allProcessions.addAll(pro);
		}
		if (!(finder.getAreaName() == null || finder.getAreaName() == "")) {
			final Collection<Procession> pro = this.finderRepository.getProcessionsByArea(finder.getAreaName());
			allProcessions.addAll(pro);
		}

		Configuration conf;
		conf = this.configurationService.getConfiguration();

		List<Procession> result = new ArrayList<Procession>(allProcessions);

		if (allProcessions.size() > conf.getMaxResults()) {

			final List<Procession> processionsLim = new ArrayList<Procession>();
			for (int i = 0; i < conf.getMaxResults(); i++)
				processionsLim.add(result.get(i));
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

}
