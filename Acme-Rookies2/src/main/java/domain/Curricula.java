
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Curricula extends DomainEntity {

	private Rookie							rookie;
	private PersonalData					personalData;
	private Collection<PositionData>		positionData;
	private Collection<EducationData>		educationData;
	private Collection<MiscellaneousData>	miscellaneousData;
	private int								isCopy;


	@Range(min = 0, max = 1)
	public int getIsCopy() {
		return this.isCopy;
	}

	public void setIsCopy(final int isCopy) {
		this.isCopy = isCopy;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Rookie getRookie() {
		return this.rookie;
	}

	public void setRookie(final Rookie rookie) {
		this.rookie = rookie;
	}

	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.REMOVE)
	public PersonalData getPersonalData() {
		return this.personalData;
	}

	public void setPersonalData(final PersonalData personalData) {
		this.personalData = personalData;
	}

	@Valid
	@OneToMany(cascade = CascadeType.REMOVE)
	public Collection<PositionData> getPositionData() {
		return this.positionData;
	}

	public void setPositionData(final Collection<PositionData> positionData) {
		this.positionData = positionData;
	}

	@Valid
	@OneToMany(cascade = CascadeType.REMOVE)
	public Collection<EducationData> getEducationData() {
		return this.educationData;
	}

	public void setEducationData(final Collection<EducationData> educationData) {
		this.educationData = educationData;
	}

	@Valid
	@OneToMany(cascade = CascadeType.REMOVE)
	public Collection<MiscellaneousData> getMiscellaneousData() {
		return this.miscellaneousData;
	}

	public void setMiscellaneousData(final Collection<MiscellaneousData> miscellaneousData) {
		this.miscellaneousData = miscellaneousData;
	}

}
