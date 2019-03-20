
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Area;
import domain.Brotherhood;
import domain.Chapter;

@Repository
public interface AreaRepository extends JpaRepository<Area, Integer> {

	@Query("select p from Area p where not exists (select p2 from Brotherhood r join r.area p2 where p2 = p)")
	Collection<Area> canBeDeleted();

	@Query("select c from Chapter c join c.area a where a.id=?1")
	Chapter getChapter(int areaId);

	@Query("select b from Brotherhood b join b.area a where a.id=?1")
	Collection<Brotherhood> getBrotherhood(int areaId);

}
