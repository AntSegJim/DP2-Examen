
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class PersonalData extends DomainEntity {

	private String	fullName;
	private String	statement;
	private String	phoneNumber;
	private String	githubProfile;
	private String	linkedlnProfile;


	//	private Boolean	patternPhone;

	//	public Boolean getPatternPhone() {
	//		return this.patternPhone;
	//	}
	//
	//	public void setPatternPhone(final Boolean patternPhone) {
	//		this.patternPhone = patternPhone;
	//	}

	@NotNull
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}

	@NotNull
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getStatement() {
		return this.statement;
	}

	public void setStatement(final String statement) {
		this.statement = statement;
	}

	@NotNull
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@NotNull
	@URL
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getGithubProfile() {
		return this.githubProfile;
	}

	public void setGithubProfile(final String githubProfile) {
		this.githubProfile = githubProfile;
	}

	@NotNull
	@URL
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getLinkedlnProfile() {
		return this.linkedlnProfile;
	}

	public void setLinkedlnProfile(final String linkedlnProfile) {
		this.linkedlnProfile = linkedlnProfile;
	}
}
