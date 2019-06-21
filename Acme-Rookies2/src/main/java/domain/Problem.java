
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Problem extends DomainEntity {

	private String					title;
	private String					statement;
	private String					hint;
	private Collection<String>		attachment;
	private int						draftMode;
	private Collection<Application>	applications;


	@NotNull
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
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

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getHint() {
		return this.hint;
	}

	public void setHint(final String hint) {
		this.hint = hint;
	}

	@ElementCollection
	public Collection<String> getAttachment() {
		return this.attachment;
	}

	public void setAttachment(final Collection<String> attachment) {
		this.attachment = attachment;
	}

	@Range(min = 0, max = 1)
	public int getDraftMode() {
		return this.draftMode;
	}

	public void setDraftMode(final int draftMode) {
		this.draftMode = draftMode;
	}

	@Valid
	@ElementCollection
	@NotNull
	@OneToMany(cascade = {
		CascadeType.REMOVE
	})
	public Collection<Application> getApplications() {
		return this.applications;
	}

	public void setApplications(final Collection<Application> applications) {
		this.applications = applications;
	}

}
