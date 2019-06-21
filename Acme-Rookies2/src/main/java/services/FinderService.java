
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FinderRepository;
import security.LoginService;
import domain.Finder;
import domain.Position;

@Service
@Transactional
public class FinderService {

	@Autowired
	private FinderRepository			finderRepository;

	@Autowired
	private Validator					validator;

	@Autowired
	private CustomizableSystemService	customizableSystemService;


	public Finder create() {
		final Finder res = new Finder();
		res.setDeadLine(new Date());
		res.setMaxSalary(0.);
		res.setKeyWord("");
		res.setMinSalary(0.);
		res.setPositions(new HashSet<Position>());
		res.setMoment(new Date());
		return res;

	}

	public Collection<Finder> findAll() {
		return this.finderRepository.findAll();
	}

	public Finder findOne() {
		return this.finderRepository.getMyFinder(this.hackerUserAccountId());
	}

	public Finder save(final Finder f) {
		Finder saved;
		if (f.getId() == 0) {
			Assert.isTrue(f.getKeyWord() == "" && f.getDeadLine() != null && f.getMaxSalary() >= 0. && f.getMinSalary() >= 0. && f.getPositions().isEmpty());
			saved = this.finderRepository.save(f);
		} else {
			final Finder savedFinder = this.findOne();
			savedFinder.setDeadLine(f.getDeadLine());
			savedFinder.setMaxSalary(f.getMaxSalary());
			savedFinder.setKeyWord(f.getKeyWord());
			savedFinder.setMinSalary(f.getMinSalary());
			savedFinder.setPositions(f.getPositions());
			savedFinder.setMoment(f.getMoment());
			Assert.isTrue(f.getMoment() != null);
			saved = this.finderRepository.save(savedFinder);
		}
		return saved;
	}

	//RECONSTRUCT
	public Finder reconstruct(final Finder finder, final BindingResult binding) {
		final Finder res = this.findOne();

		final Finder copy = new Finder();
		copy.setId(res.getId());
		copy.setVersion(res.getVersion());
		copy.setMoment(res.getMoment());
		copy.setDeadLine(finder.getDeadLine());
		copy.setMaxSalary(finder.getMaxSalary());
		copy.setKeyWord(finder.getKeyWord());
		copy.setMinSalary(finder.getMinSalary());

		if (finder.getMinSalary() == null)
			copy.setMinSalary(0.);
		if (finder.getMaxSalary() == null)
			copy.setMaxSalary(Double.MAX_VALUE);
		if (finder.getKeyWord() == null)
			copy.setKeyWord("");
		if (res.getKeyWord() == null)
			res.setKeyWord("");

		Boolean noHaCambiadoFecha;
		if (res.getDeadLine() == null && finder.getDeadLine() == null)
			noHaCambiadoFecha = true;
		else if ((res.getDeadLine() == null && finder.getDeadLine() != null) || (res.getDeadLine() != null && finder.getDeadLine() == null))
			noHaCambiadoFecha = false;
		else if (res.getDeadLine().getDate() == finder.getDeadLine().getDate() && res.getDeadLine().getMonth() == finder.getDeadLine().getMonth() && res.getDeadLine().getYear() == finder.getDeadLine().getYear())
			noHaCambiadoFecha = true;
		else
			noHaCambiadoFecha = false;

		final Boolean noHaCambiadoFinder = res.getMinSalary().equals(copy.getMinSalary()) && res.getMaxSalary().equals(copy.getMaxSalary()) && res.getKeyWord().equals(copy.getKeyWord()) && noHaCambiadoFecha;

		final long a = (new Date().getTime() - res.getMoment().getTime()) / 3600000;
		final long b = this.customizableSystemService.getTimeCache();

		if (a > b || !noHaCambiadoFinder) {
			String fecha;
			if (finder.getDeadLine() == null)
				fecha = "";
			else
				fecha = this.getStringToDate(finder.getDeadLine());
			final Collection<Position> c = this.finderRepository.filterPositions2(copy.getKeyWord(), copy.getMinSalary(), copy.getMaxSalary(), fecha);
			copy.setPositions(c);
			copy.setMoment(new Date());
		} else
			copy.setPositions(res.getPositions());

		this.validator.validate(copy, binding);
		return copy;

	}
	private int hackerUserAccountId() {
		return LoginService.getPrincipal().getId();
	}

	private String getStringToDate(final Date date) {
		String cadena = "";
		cadena += date.toString().substring(24, 28);

		final Integer month = date.getMonth() + 1;
		if (month < 10)
			cadena += "-" + "0" + month;
		else
			cadena += "-" + month;

		if (date.getDate() < 10)
			cadena += "-" + "0" + date.getDate();
		else
			cadena += "-" + date.getDate();

		return cadena;
	}

	public List<Finder> getFinderByPosition(final Integer id) {
		return this.finderRepository.getFinderByPosition(id);
	}

	public void clearResults() {
		final Finder finder = this.findOne();
		String fecha;
		final Date a = finder.getDeadLine();
		if (finder.getDeadLine() == null)
			fecha = "";
		else
			fecha = this.getStringToDate(new Date(a.getYear(), a.getMonth(), a.getDate()));

		if (finder.getMinSalary() == null)
			finder.setMinSalary(0.);
		if (finder.getMaxSalary() == null)
			finder.setMaxSalary(Double.MAX_VALUE);

		final Collection<Position> c = this.finderRepository.filterPositions2(finder.getKeyWord(), finder.getMinSalary(), finder.getMaxSalary(), fecha);
		finder.setPositions(c);
		this.save(finder);
	}

	public List<Double> getMinMaxAvgDesvResultsFinder() {
		return this.finderRepository.getMinMaxAvgDesvResultsFinder();
	}

	public Double ratioEmptyNotEmtpyFinder() {
		return this.finderRepository.ratioEmptyNotEmtpyFinder();
	}

}
