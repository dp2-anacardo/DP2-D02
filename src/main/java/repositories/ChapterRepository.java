
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chapter;
<<<<<<< HEAD
import domain.Parade;
=======
import domain.Procession;
import domain.Proclaim;
>>>>>>> origin/miguel

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Integer> {

	@Query("select p from Parade p join p.brotherhood b join b.area a where a.id=?1")
<<<<<<< HEAD
	Collection<Parade> getParadesOfChapter(int areaId);
=======
	Collection<Procession> getProcessionsOfChapter(int areaId);

	@Query("select p from Proclaim p join p.chapter c where c.id=?1")
	Collection<Proclaim> getProclaims(int chapterId);
>>>>>>> origin/miguel
}
