
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.MessageBox;

@Repository
public interface MessageBoxRepository extends JpaRepository<MessageBox, Integer> {

	@Query("select a.boxes from Actor a where a.id=?1")
	Collection<MessageBox> findAllByActor(int actorID);

	@Query("select box from Actor a join a.boxes box where a.id=?1 and box.name=?2")
	MessageBox findOneByActorAndName(int actorID, String name);

}
