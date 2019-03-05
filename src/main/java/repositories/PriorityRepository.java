
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Priority;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Integer> {

	@Query("select count(m) from Message m where m.priority.id = ?1")
	Integer getPriorityCount(int id);
}
