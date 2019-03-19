
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chapter;
import domain.Parade;
import domain.Proclaim;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Integer> {

	@Query("select p from Parade p join p.brotherhood b join b.area a where a.id=?1")
	Collection<Parade> getParadesOfChapter(int areaId);

	@Query("select p from Proclaim p join p.chapter c where c.id=?1")
	Collection<Proclaim> getProclaims(int chapterId);

}
