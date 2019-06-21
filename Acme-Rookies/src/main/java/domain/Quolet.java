
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Quolet extends DomainEntity {

	private String	ticker;
	private Date	moment;
	private String	body;
	private String	picture;
	private int		draftMode;
	private Audit	audit;
	private Integer	numMonth;
	private Company	company;


	@NotNull
	@Min(0)
	public Integer getNumMonth() {
		return this.numMonth;
	}

	public void setNumMonth(final Integer numMonth) {
		this.numMonth = numMonth;
	}

	//Pattern para Letras x4 - Numero x6
	//"^[A-z]{4}\\-[0-9]{6}$"
	@Pattern(regexp = "^[0-9]{6}\\-[A-z]{4}$")
	@Column(unique = true)
	@NotNull
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	@NotBlank
	@NotNull
	@Length(max = 100)
	public String getBody() {
		return this.body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	@URL
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getPicture() {
		return this.picture;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}

	@Range(min = 0, max = 1)
	public int getDraftMode() {
		return this.draftMode;
	}

	public void setDraftMode(final int draftMode) {
		this.draftMode = draftMode;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Audit getAudit() {
		return this.audit;
	}

	public void setAudit(final Audit audit) {
		this.audit = audit;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Company getCompany() {
		return this.company;
	}

	public void setCompany(final Company company) {
		this.company = company;
	}
}
