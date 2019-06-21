
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Company extends Actor {

	private String	nameCompany;
	private Double	totalScore;


	public Double getTotalScore() {
		return this.totalScore;
	}

	public void setTotalScore(final Double totalScore) {
		this.totalScore = totalScore;
	}
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@NotBlank
	@Length(min = 4)
	public String getNameCompany() {
		return this.nameCompany;
	}

	public void setNameCompany(final String nameCompany) {
		this.nameCompany = nameCompany;
	}

}
