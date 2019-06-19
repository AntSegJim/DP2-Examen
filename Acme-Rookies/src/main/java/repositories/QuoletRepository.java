
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Quolet;

@Repository
public interface QuoletRepository extends JpaRepository<Quolet, Integer> {

	@Query("select q from Quolet q where q.audit.id = ?1")
	public Collection<Quolet> getQuoletsByCompany(Integer idAudit);

	@Query("select q from Quolet q where q.company.id = ?1")
	public Collection<Quolet> getQuoletsByMyCompany(Integer idCompany);
}
