
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Brotherhood;
import domain.Enrolment;
import domain.Member;

@Repository
public interface BrotherhoodRepository extends JpaRepository<Brotherhood, Integer> {

	@Query("select e from Enrolment e join e.brotherhood b where b.id=?1")
	Collection<Enrolment> getEnrolments(int brotherhoodId);

	@Query("select e.member from Enrolment e where e.brotherhood = ?1 and e.status like 'ACCEPTED' and e.dropOutMoment is null")
	Collection<Member> getMembers(Brotherhood b);

}
