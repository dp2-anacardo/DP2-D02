
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.LinkRecord;

@Repository
public interface LinkRecordRepository extends JpaRepository<LinkRecord, Integer> {

	@Query("select r from LinkRecord r join r.brotherhood b where b.id=?1")
	Collection<LinkRecord> getLinkRecordByBrotherhood(final int brotherhoodId);

	@Query("select r from LinkRecord r join r.linkedBH b where b.id=?1")
	Collection<LinkRecord> getLinkRecordByLinkedBH(final int brotherhoodId);
}
