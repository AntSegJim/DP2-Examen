
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;
import domain.Problem;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Integer> {

	@Query("SELECT p FROM Problem p WHERE ?1 MEMBER OF p.applications")
	public Problem getProblemByApplication(Application a);

	@Query("select p from Problem p where p.draftMode = 0")
	public Collection<Problem> getProblemDraftModeOut();

	@Query("select p.problems from Position p where p.company.id = ?1 and p.draftMode = 0")
	public Collection<Problem> getProblemsByCompany(int idCompany);

	@Query("select x from Position p join p.problems x where x.draftMode = 0 and p.id=?1")
	public Collection<Problem> getProblemsWithoutDraftModeByPosition(Integer positionId);

}
