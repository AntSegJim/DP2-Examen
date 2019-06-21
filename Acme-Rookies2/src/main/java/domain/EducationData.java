
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class EducationData extends DomainEntity {

	private String	degree;
	private String	institution;
	private int		mark;
	private Date	startDate;
	private Date	endDate;


	@NotNull
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getDegree() {
		return this.degree;
	}

	public void setDegree(final String degree) {
		this.degree = degree;
	}

	@NotNull
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getInstitution() {
		return this.institution;
	}

	public void setInstitution(final String institution) {
		this.institution = institution;
	}

	public int getMark() {
		return this.mark;
	}

	public void setMark(final int mark) {
		this.mark = mark;
	}

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}
}
