
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.MiscRecord;

@Repository
public interface MiscRecordRepository extends JpaRepository<MiscRecord, Integer> {

	@Query("select r from MiscRecord r join r.brotherhood b where b.id=?1")
	Collection<MiscRecord> getMiscRecordByBrotherhood(final int brotherhoodId);
}
