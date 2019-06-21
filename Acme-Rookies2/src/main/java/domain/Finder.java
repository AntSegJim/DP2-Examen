
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "keyWord, deadLine, minSalary, maxSalary")
})
public class Finder extends DomainEntity {

	private String					keyWord;
	private Date					deadLine;
	private Double					minSalary;
	private Double					maxSalary;
	private Collection<Position>	positions;
	private Date					moment;


	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getKeyWord() {
		return this.keyWord;
	}

	public void setKeyWord(final String keyWord) {
		this.keyWord = keyWord;
	}

	@Valid
	@ManyToMany
	public Collection<Position> getPositions() {
		return this.positions;
	}

	public void setPositions(final Collection<Position> positions) {
		this.positions = positions;
	}

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDeadLine() {
		return this.deadLine;
	}

	public void setDeadLine(final Date deadLine) {
		this.deadLine = deadLine;
	}

	public Double getMinSalary() {
		return this.minSalary;
	}

	public void setMinSalary(final Double minSalary) {
		this.minSalary = minSalary;
	}

	public Double getMaxSalary() {
		return this.maxSalary;
	}

	public void setMaxSalary(final Double maxSalary) {
		this.maxSalary = maxSalary;
	}

	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

}
