
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Brotherhood;
import domain.FloatEntity;

@Repository
public interface FloatEntityRepository extends JpaRepository<FloatEntity, Integer> {

	@Query("select f from FloatEntity f where f.brotherhood = ?1")
	Collection<FloatEntity> getFloatsByBrotherhood(Brotherhood b);

}
