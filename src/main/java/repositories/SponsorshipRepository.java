
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsorship;

@Repository
public interface SponsorshipRepository extends JpaRepository<Sponsorship, Integer> {

	@Query("select s.sponsorships from Sponsor s where s.id = ?1")
	Collection<Sponsorship> findBySponsor(int sponsorId);

	@Query("select s from Sponsorship s where s.parade.id = ?1 and s.status = TRUE")
	List<Sponsorship> findAllByParade(int paradeId);
}
