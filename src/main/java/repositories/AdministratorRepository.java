
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;
import domain.Brotherhood;
import domain.Member;
import domain.Parade;
import domain.Position;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	/* Q1: The average, the minimum, the maximum, and the standard deviation of the number of members per brotherhood. */

	@Query("select avg(1.0*(select count(f) from Enrolment f where f.brotherhood.id = c.id and f.status like 'ACCEPTED' and f.dropOutMoment is null)) from Brotherhood c")
	Double getAvgOfMembersPerBrotherhood();

	@Query("select min(1.0*(select count(f) from Enrolment f where f.brotherhood.id = c.id and f.status like 'ACCEPTED' and f.dropOutMoment is null)) from Brotherhood c")
	Double getMinOfMembersPerBrotherhood();

	@Query("select max(1.0*(select count(f) from Enrolment f where f.brotherhood.id = c.id and f.status like 'ACCEPTED' and f.dropOutMoment is null)) from Brotherhood c")
	Double getMaxOfMembersPerBrotherhood();

	@Query("select stddev(1.0*(select count(f) from Enrolment f where f.brotherhood.id = c.id and f.status like 'ACCEPTED' and f.dropOutMoment is null)) from Brotherhood c")
	Double getSteddevOfMembersPerBrotherhood();

	/* Q2: The largest brotherhoods. */

	@Query("select max(f.brotherhood) from Enrolment f where f.status like 'ACCEPTED' and f.dropOutMoment is null")
	Brotherhood getLargestBrotherhood();

	/* Q3: The smallest brotherhoods. */

	@Query("select min(f.brotherhood) from Enrolment f where f.status like 'ACCEPTED' and f.dropOutMoment is null")
	Brotherhood getSmallestBrotherhood();

	/* Q4: The parades that are going to be organised in 30 days or less. */

	@Query("select p from Parade p where datediff(p.moment, current_date()) <=30")
	Collection<Parade> getParadesIn30Days();

	/* Q5: The ratio of request to march in a parade, group by their status */

	@Query("select 100*(select count(f) from Request f where f.parade = ?1 and f.status like ?2)/count(f) from Request f where f.parade = ?1")
	Double getRatioOfRequestToParadePerStatus(Parade parade, String status);

	/* Q6: The ratio of requests to march, grouped by their status. */

	@Query("select 100*(select count(f) from Request f where f.status like 'APPROVED')/count(f) from Request f")
	Double getRatioOfRequestsApproveds();

	@Query("select 100*(select count(f) from Request f where f.status like 'PENDING')/count(f) from Request f")
	Double getRatioOfRequestsPendings();

	@Query("select 100*(select count(f) from Request f where f.status like 'REJECTED')/count(f) from Request f")
	Double getRatioOfRequestsRejecteds();

	/* **************************************************************************************************************************************** */
	@Query("select p from Position p where not exists (select p2 from Enrolment r join r.position p2 where p2 = p)")
	Collection<Position> getPositionsNotUsed();

	@Query("select m from Member m where((select count(r) from Request r where r.member.id=m.id and r.status = 'APPROVED')/(select count(r) from Request r where r.member.id=m.id))>0.1")
	Collection<Member> getMembersAtLeast10PercentOfNumberOfRequestAccepted();

	@Query("select count(e) from Enrolment e where (e.position.roleEn=?1 or e.position.roleEs=?2) and e.status='ACCEPTED' and e.dropOutMoment is null")
	Integer getHistogramOfPositions(String roleEn, String roleEs);

	@Query("select 1.0 * count(b) from Brotherhood b where b.area.id=?1")
	//@Query("select count(b), b.area.name from Brotherhood b group by b.area")
	Double getCountOfBrotherhoodPerArea(Integer AreaId);

	@Query("select min(1.0*(select count(b) from Brotherhood b where b.area.id=a.id)) from Area a")
	Double getMinBrotherhoodPerArea();

	@Query("select max(1.0*(select count(b) from Brotherhood b where b.area.id=a.id)) from Area a")
	Double getMaxBrotherhoodPerArea();

	@Query("select avg(1.0*(select count(b) from Brotherhood b where b.area.id=a.id)) from Area a")
	Double getAvgBrotherhoodPerArea();

	@Query("select stddev(1.0*(select count(b) from Brotherhood b where b.area.id=a.id)) from Area a")
	Double getStddevBrotherhoodPerArea();

	@Query("select min(f.parades.size) from Finder f")
	Double getMinResultFinder();

	@Query("select max(f.parades.size)*1.0 from Finder f")
	Double getMaxResultFinder();

	@Query("select avg(f.parades.size)*1.0 from Finder f")
	Double getAvgResultFinder();

	@Query("select stddev(f.parades.size)*1.0 from Finder f")
	Double getStddevResultFinder();

	@Query("select (count(f1)*1.0)/(select count(f2) from Finder f2) from Finder f1 where f1.parades is not empty")
	Double getRatioOfNotEmptyFinders();

	@Query("select (count(f1)*1.0)/(select count(f2) from Finder f2) from Finder f1 where f1.parades is empty")
	Double getRatioOfEmptyFinders();

	//AQUI EMPIEZAN LAS QUERIES DEL DASHBOARD DE ACME PARADE

	@Query("select count(f)from Brotherhood f")
	Integer getNumOfBrotherhoods();

	@Query("select count(f) from InceptionRecord f")
	Integer getNumOfInceptionRecord();

	@Query("select count(f) from PeriodRecord f")
	Integer getNumOfPeriodRecord();

	@Query("select count(f) from LegalRecord f")
	Integer getNumOfLegalRecord();

	@Query("select count(f) from LinkRecord f")
	Integer getNumOfLinkRecord();

	@Query("select count(f) from PeriodRecord f where f.brotherhood.id = ?1")
	Integer getNumOfPeriodRecordsPerBrotherhood(Integer brotherhoodId);

	@Query("select count(f) from LegalRecord f where f.brotherhood.id = ?1")
	Integer getNumOfLegalRecordsPerBrotherhood(Integer brotherhoodId);

	@Query("select count(f) from MiscRecord f where f.brotherhood.id = ?1")
	Integer getNumOfMiscRecordsPerBrotherhood(Integer brotherhoodId);

	@Query("select count(f) from LinkRecord f where f.brotherhood.id = ?1")
	Integer getNumOfLinkRecordPerBrotherhood(Integer brotherhoodId);

	//TODO
	@Query("select (count(f1)*1.0)/(select count(f2) from Area f2) from Chapter f1 where f1.area is null")
	Double getRatioOfAreaNotCoordinateByChapter();

}
