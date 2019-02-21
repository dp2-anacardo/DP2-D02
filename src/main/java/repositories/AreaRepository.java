
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Area;

@Repository
public interface AreaRepository extends JpaRepository<Area, Integer> {

	@Query("select p from Area p where not exists (select p2 from Brotherhood r join r.area p2 where p2 = p)")
	Collection<Area> canBeDeleted();

}
