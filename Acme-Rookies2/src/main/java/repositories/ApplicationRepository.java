
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	@Query("select avg(1.0*(select count(a.rookie) from Application a where a.rookie.id = h.id)), min(1.0*(select count(a.rookie) from Application a where a.rookie.id = h.id)), max(1.0*(select count(a.rookie) from Application a where a.rookie.id = h.id)), sqrt(1.0*sum(1.0*(select count (a.rookie) from Application a where a.rookie.id = h.id) * (select count(a.rookie) from Application a where a.rookie.id = h.id)) / count(h) - avg(1.0*(select count(a.rookie) from Application a where a.rookie.id = h.id)) * avg(1.0*(select count(a.rookie) from Application a where a.rookie.id = h.id))) from Rookie h")
	public List<Object[]> getAvgMinMaxDesvAppByHackers();

	@Query("select a from Application a where a.rookie.id = ?1")
	public Collection<Application> getAllMyApplicationsHacker(int id);

}
