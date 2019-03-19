
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.LegalRecord;

@Repository
public interface LegalRecordRepository extends JpaRepository<LegalRecord, Integer> {

	@Query("select r from LegalRecord r join r.brotherhood b where b.id=?1")
	Collection<LegalRecord> getLegalRecordByBrotherhood(final int brotherhoodId);

}
