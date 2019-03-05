
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Member;
import domain.Procession;
import domain.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

	@Query("select r from Request r where r.procession = ?1")
	Collection<Request> getRequestByProcession(Procession p);

	@Query("select r from Request r group by r.status having r.member=?1")
	Collection<Request> getRequestsByMember(Member m);

}
