
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.PersonalData;

@Repository
public interface PersonalDataRepository extends JpaRepository<PersonalData, Integer> {

	@Query("select c.personalData from Curricula c where c.id=?1")
	public Collection<PersonalData> getProfileDatasByCurricula(Integer curriculaId);
}
