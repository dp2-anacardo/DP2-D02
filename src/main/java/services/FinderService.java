
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Configuration;
import domain.Finder;
import domain.Procession;

@Service
@Transactional
public class FinderService {

	//Managed repository
	private FinderRepository		finderRepository;

	//Services
	private ConfigurationService	configurationService;


	//Simple CRUD Methods
	public Finder create() {
		Finder result;
		result = new Finder();
		final Collection<Procession> processions = new ArrayList<Procession>();
		result.setProcessions(processions);
		return result;
	}
	public List<Finder> findAll() {
		return this.finderRepository.findAll();
	}

	public Finder findOne(final Integer id) {
		return this.finderRepository.findOne(id);
	}

	public Finder save(Finder finder) {

		Assert.notNull(finder);
		final Set<Procession> allProcessions = new HashSet<Procession>();
		if (finder.getKeyWord() != null && finder.getKeyWord() != "") {
			final Collection<Procession> processions = this.finderRepository.getProcessionsByKeyWord(finder.getKeyWord());
			allProcessions.addAll(processions);
		}
		if (finder.getMinimumDate() != null && finder.getMaximumDate() != null) {
			final Collection<Procession> processions = this.finderRepository.getProcessionsByDateRange(finder.getMinimumDate(), finder.getMaximumDate());
			allProcessions.addAll(processions);
		}
		if (finder.getAreaName() != null) {
			final Collection<Procession> processions = this.finderRepository.getProcessionsByArea(finder.getAreaName());
			allProcessions.addAll(processions);
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

}
