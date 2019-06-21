
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Rookie;

@Repository
public interface HackerRepository extends JpaRepository<Rookie, Integer> {

	@Query("select h from Rookie h where h.userAccount.id = ?1")
	public Rookie hackerUserAccount(Integer id);

	//DASHBOARD
	@Query("select h.name from Rookie h where (select max(1.0*( select count(a.rookie) from Application a where a.rookie.id=x.id)) from Rookie x)=(1.0*(select count(b.rookie) from Application b where b.rookie.id=h.id))")
	public List<String> getHackersWithMoreApplications();

}
