
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.FloatEntity;

@Repository
public interface FloatEntityRepository extends JpaRepository<FloatEntity, Integer> {

}
