
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.InceptionRecord;

@Repository
public interface InceptionRecordRepository extends JpaRepository<InceptionRecord, Integer> {

	@Query("select r from InceptionRecord r join r.brotherhood b where b.id=?1")
	Collection<InceptionRecord> getInceptionRecordByBrotherhood(final int brotherhoodId);
}
