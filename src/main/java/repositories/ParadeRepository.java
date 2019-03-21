
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Brotherhood;
import domain.Finder;
import domain.Parade;

@Repository
public interface ParadeRepository extends JpaRepository<Parade, Integer> {

	@Query("select p from Parade p where p.brotherhood = ?1")
	Collection<Parade> getParadesByBrotherhood(Brotherhood b);

	@Query("select f from Finder f")
	Collection<Finder> getFinders();

	@Query("select p from Parade p where p.brotherhood.id =?1 and p.isFinal =TRUE")
	Collection<Parade> getParadesFinalByBrotherhood(final int id);

	@Query("select p from Parade p where p.isFinal = TRUE")
	Collection<Parade> getParadesFinal();

}
