
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;
import domain.Member;
import domain.Position;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	@Query("select p from Position p where not exists (select p2 from Enrolment r join r.position p2 where p2 = p)")
	Collection<Position> getPositionsNotUsed();

	@Query("select m from Member m where((select count(r) from Request r where r.member.id=m.id and r.status = 'APPROVED')/(select count(r) from Request r where r.member.id=m.id))>0.1")
	Collection<Member> getMembersAtLeast10PercentOfNumberOfRequestAccepted();

	@Query("select count(e) from Enrolment e where (e.position.roleEn='President' or e.position.roleEs='Presidente') and e.status='ACCEPTED' and e.dropOutMoment is null")
	Integer getNumberOfPresidents();

	@Query("select count(e) from Enrolment e where (e.position.roleEn='Vice President' or e.position.roleEs='Vicepresidente') and e.status='ACCEPTED' and e.dropOutMoment is null")
	Integer getNumberOfVicePresidents();

	@Query("select count(e) from Enrolment e where (e.position.roleEn='Secretary' or e.position.roleEs='Secretario') and e.status='ACCEPTED' and e.dropOutMoment is null")
	Integer getNumberOfSecretaries();

	@Query("select count(e) from Enrolment e where (e.position.roleEn='Treasurer' or e.position.roleEs='Tesorero') and e.status='ACCEPTED' and e.dropOutMoment is null")
	Integer getNumberOfTreasurers();

	@Query("select count(e) from Enrolment e where (e.position.roleEn='Fundraiser' or e.position.roleEs='Promotor') and e.status='ACCEPTED' and e.dropOutMoment is null")
	Integer getNumberOfFundraisers();

	@Query("select count(e) from Enrolment e where (e.position.roleEn='Historian' or e.position.roleEs='Historiador') and e.status='ACCEPTED' and e.dropOutMoment is null")
	Integer getNumberOfHistorians();

	@Query("select count(e) from Enrolment e where (e.position.roleEn='Officer' or e.position.roleEs='Vocal') and e.status='ACCEPTED' and e.dropOutMoment is null")
	Integer getNumberOfOfficers();

	@Query("select count(b) from Brotherhood b where b.area.id=?1")
	//@Query("select count(b), b.area.name from Brotherhood b group by b.area")
	Integer getCountOfBrotherhoodPerArea(Integer AreaId);

	@Query("select min(1.0*(select count(b) from Brotherhood b where b.area.id=a.id)) from Area a")
	Double getMinBrotherhoodPerArea();

	@Query("select max(1.0*(select count(b) from Brotherhood b where b.area.id=a.id)) from Area a")
	Double getMaxBrotherhoodPerArea();

	@Query("select avg(1.0*(select count(b) from Brotherhood b where b.area.id=a.id)) from Area a")
	Double getAvgBrotherhoodPerArea();

	@Query("select stddev(1.0*(select count(b) from Brotherhood b where b.area.id=a.id)) from Area a")
	Double getStddevBrotherhoodPerArea();

	@Query("select min(f.processions.size) from Finder f")
	Double getMinResultFinder();

	@Query("select max(f.processions.size)*1.0 from Finder f")
	Double getMaxResultFinder();

	@Query("select avg(f.processions.size)*1.0 from Finder f")
	Double getAvgResultFinder();

	@Query("select stddev(f.processions.size)*1.0 from Finder f")
	Double getStddevResultFinder();

	@Query("select (count(f1)*1.0)/(select count(f2) from Finder f2) from Finder f1 where f1.processions is not empty")
	Double getRatioOfNotEmptyFinders();

	@Query("select (count(f1)*1.0)/(select count(f2) from Finder f2) from Finder f1 where f1.processions is empty")
	Double getRatioOfEmptyFinders();
}
