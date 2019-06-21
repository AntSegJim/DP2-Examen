
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Audit;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Integer> {

	@Query("select a from Audit a where a.auditor.id=?1")
	public Collection<Audit> getAuditsByAuditor(Integer auditorId);

	//Audits de una position cuyo draftMode sea 0.
	@Query("select a from Audit a where a.position.id=?1 and a.draftMode=0")
	public Collection<Audit> getAuditsByPositionDM(Integer positionId);
	//---Cristian---
	@Query("select count(a) from Audit a where a.position.company.id = ?1")
	public Integer getNumerosAuditsByCompany(Integer id);

	@Query("select 1.0*(sum(a.score)/(10*count(a))) from Audit a where a.position.company.id = ?1")
	public Double getTotalScoreOfCompany(Integer id);
	//---Cristian---

	//DASHBOARD
	@Query("select avg(a.score), min(a.score), max(a.score), sqrt(sum(a.score * a.score)/count(a)-avg(a.score)*avg(a.score)) from Audit a")
	public List<Object[]> getAvgMinMaxDesvScoreOfAudit();

	@Query("select avg(1.0*(select sum(a.score) from Audit a where a.position.company.id=c.id)), min(1.0*(select sum(a.score) from Audit a where a.position.company.id=c.id)),max(1.0*(select sum(a.score) from Audit a where a.position.company.id=c.id)),sqrt(1.0*sum(1.0*(select sum(a.score) from Audit a where a.position.company.id=c.id)*(select sum(a.score) from Audit a where a.position.company.id=c.id))/count(c)- avg(1.0*(select sum(a.score) from Audit a where a.position.company.id=c.id))*avg(1.0*(select sum(a.score) from Audit a where a.position.company.id=c.id)))from Company c")
	public List<Object[]> getAvgMinMaxDesvScoreOfAuditByCompany();

}
