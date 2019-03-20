
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.PeriodRecord;

@Repository
public interface PeriodRecordRepository extends JpaRepository<PeriodRecord, Integer> {

	@Query("select r from PeriodRecord r join r.brotherhood b where b.id=?1")
	Collection<PeriodRecord> getPeriodRecordByBrotherhood(final int brotherhoodId);
}
