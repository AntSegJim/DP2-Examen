
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;
import domain.Position;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	@Query("select h.finder from Rookie h where h.userAccount.id = ?1")
	public Finder getMyFinder(int id);

	//	@Query("select p from Position p where (locate(?1,p.title) != 0 or locate(?1,p.ticker) != 0 or locate(?1,p.description) != 0 or locate(?1,p.skillsRequired) != 0 or locate(?1,p.technologiesRequired) != 0 or locate(?1,p.requiredProfile) != 0) and p.salary between ?2 and ?3 or locate(?4,p.deadLine) != 0 and p.draftMode = 1")
	//	public Collection<Position> filterPositions(String keyWord, Double minSalary, Double maxSalary, Date deadline);

	@Query("select p from Position p where ((locate(?1,p.title) != 0 or locate(?1,p.ticker) != 0 or locate(?1,p.description) != 0 or locate(?1,p.skillsRequired) != 0 or locate(?1,p.technologiesRequired) != 0 or locate(?1,p.requiredProfile) != 0) and p.salary between ?2 and ?3 and locate(?4,p.deadLine) != 0) and p.draftMode = 0 and p.isCancelled = 0")
	public Collection<Position> filterPositions2(String keyWord, Double minSalary, Double maxSalary, String fecha);

	@Query("select f from Finder f where ?1 member of f.positions")
	public List<Finder> getFinderByPosition(Integer id);

	@Query("select min(f.positions.size),max(f.positions.size),avg(f.positions.size), sqrt(1.0*sum(f.positions.size * f.positions.size) / count(f) - avg(f.positions.size) * avg(f.positions.size)) from Finder f")
	public List<Double> getMinMaxAvgDesvResultsFinder();

	@Query("select count(f) from Finder f where f.positions.size = 0 / 1.0 * (select count(f) from Finder f where f.positions.size > 0)")
	public Double ratioEmptyNotEmtpyFinder();

}
