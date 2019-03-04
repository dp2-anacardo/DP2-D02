
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Brotherhood;
import domain.Finder;
import domain.Procession;

@Repository
public interface ProcessionRepository extends JpaRepository<Procession, Integer> {

	@Query("select p from Procession p where p.brotherhood = ?1")
	Collection<Procession> getProcessionsByBrotherhood(Brotherhood b);

	@Query("select f from Finder f")
	Collection<Finder> getFinders();

	@Query("select p from Procession p where p.brotherhood.id =?1 and p.isFinal =TRUE")
	Collection<Procession> getProcessionsFinalByBrotherhood(final int id);

}
