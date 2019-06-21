
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Provider;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Integer> {

	@Query("select c from Provider c where c.userAccount.id = ?1")
	public Provider providerUserAccount(Integer id);

	@Query(value = "select provider from `acme-rookies`.item i group by i.provider order by count(i.provider) desc LIMIT 5", nativeQuery = true)
	public Collection<Integer> getTop5Providers();
}
