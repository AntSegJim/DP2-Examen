
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Auditor;

@Repository
public interface AuditorRepository extends JpaRepository<Auditor, Integer> {

	@Query("select c from Auditor c where c.userAccount.id = ?1")
	public Auditor auditorUserAccount(Integer id);

	@Query("select a from Auditor a where ?1 member of a.positions")
	public List<Auditor> getAuditorByPosition(Integer id);

}
