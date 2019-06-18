
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

	@Query("select i from Item i where i.provider.userAccount.id = ?1")
	public Collection<Item> getItemsByProvider(int providerId);

	@Query("select avg(1.0*(select count(i.provider) from Item i where i.provider.id = p.id)), min(1.0*(select count(i.provider) from Item i where i.provider.id = p.id)), max(1.0*(select count(i.provider) from Item i where i.provider.id = p.id)), sqrt(1.0*sum(1.0*(select count (i.provider) from Item i where i.provider.id = p.id) * (select count(i.provider) from Item i where i.provider.id = p.id)) / count(p) - avg(1.0*(select count(i.provider) from Item i where i.provider.id = p.id)) * avg(1.0*(select count(i.provider) from Item i where i.provider.id = p.id))) from Provider p")
	public List<Object[]> getAvgMinMaxDesvNumberItemByProvider();
}
