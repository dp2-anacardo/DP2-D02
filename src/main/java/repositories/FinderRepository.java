
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;
import domain.Procession;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	@Query("select p from Procession p where p.ticker like %?1% or p.description like %?1% or p.title like %?1%")
	Collection<Procession> getProcessionsByKeyWord(String keyWord);

	@Query("select p from Procession p where p.moment between ?1 and ?2")
	Collection<Procession> getProcessionsByDateRange(Date dateMin, Date dateMax);

	@Query("select p from Procession p where p.brotherhood.area.name like ?1")
	Collection<Procession> getProcessionsByArea(String areaName);

}
