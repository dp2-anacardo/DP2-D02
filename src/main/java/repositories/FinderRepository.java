
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;
import domain.Parade;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	@Query("select p from Parade p where (p.ticker like %?1% or p.description like %?1% or p.title like %?1%) and p.isFinal =TRUE and p.status='ACCEPTED'")
	Collection<Parade> getParadesByKeyWord(String keyWord);

	@Query("select p from Parade p where (p.moment between ?1 and ?2) and p.isFinal =TRUE and p.status='ACCEPTED'")
	Collection<Parade> getParadesByDateRange(Date dateMin, Date dateMax);

	@Query("select p from Parade p where (p.brotherhood.area.name like ?1) and p.isFinal =TRUE and p.status='ACCEPTED'")
	Collection<Parade> getParadesByArea(String areaName);

	@Query("select p from Parade p where p.isFinal =TRUE and p.status='ACCEPTED'")
	Collection<Parade> findAllFinal();

}
