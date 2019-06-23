
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Quolet;

@Repository
public interface QuoletRepository extends JpaRepository<Quolet, Integer> {

	@Query("select q from Quolet q where q.audit.id = ?1")
	public Collection<Quolet> getQuoletsPerAuditByCompany(Integer idAudit);

	@Query("select q from Quolet q where q.audit.id = ?1 and q.draftMode=0")
	public Collection<Quolet> getQuoletsPerAuditByAuditor(Integer idAudit);

	@Query("select q from Quolet q where q.company.id = ?1")
	public Collection<Quolet> getQuoletsByMyCompany(Integer idCompany);

	@Query("select q from Quolet q where q.audit.auditor.id = ?1 and q.draftMode=0")
	public Collection<Quolet> getMyQuoletsAuditor(int idAuditor);

}
