
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Quolet;

@Repository
public interface QuoletRepository extends JpaRepository<Quolet, Integer> {

	@Query("select q from Quolet q where q.audit.id = ?1")
	public Collection<Quolet> getQuoletsPerAuditByAuditor(Integer idAudit);

	@Query("select q from Quolet q where q.audit.id = ?1 and q.draftMode=0")
	public Collection<Quolet> getQuoletsPerAuditByCompany(Integer idAudit);

	@Query("select q from Quolet q where q.auditor.id = ?1")
	public Collection<Quolet> getQuoletsByMyAuditor(Integer idAuditor);
	//*
	@Query("select q from Quolet q join q.audit a join a.position p  where p.company.id =?1 and q.draftMode=0")
	public Collection<Quolet> getMyQuoletsCompany(int idCompany);

}
