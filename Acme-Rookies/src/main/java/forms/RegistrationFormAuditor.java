/*
 * DomainEntity.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package forms;

import java.util.Collection;
import java.util.HashSet;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import security.Authority;
import security.UserAccount;
import domain.Actor;
import domain.CreditCard;
import domain.Position;

public class RegistrationFormAuditor extends Actor {

	// Constructors -----------------------------------------------------------

	public RegistrationFormAuditor() {
		super();
	}


	// Properties -------------------------------------------------------------

	private String					password;

	private Boolean					patternPhone;

	private String					holderName;
	private String					brandName;
	private String					number;
	private int						expirationMonth;
	private int						expirationYear;
	private int						CW;

	private Collection<Position>	positions;


	@NotNull
	public Collection<Position> getPositions() {
		return this.positions;
	}

	public void setPositions(final Collection<Position> positions) {
		this.positions = positions;
	}

	@NotBlank
	@NotNull
	public String getHolderName() {
		return this.holderName;
	}

	public void setHolderName(final String holderName) {
		this.holderName = holderName;
	}

	@NotBlank
	@NotNull
	public String getBrandName() {
		return this.brandName;
	}

	public void setBrandName(final String brandName) {
		this.brandName = brandName;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	@NotNull
	public int getExpirationMonth() {
		return this.expirationMonth;
	}

	public void setExpirationMonth(final int expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	@NotNull
	public int getExpirationYear() {
		return this.expirationYear;
	}

	public void setExpirationYear(final int expirationYear) {
		this.expirationYear = expirationYear;
	}

	@NotNull
	@Range(min = 100, max = 999)
	public int getCW() {
		return this.CW;
	}

	public void setCW(final int cW) {
		this.CW = cW;
	}

	public Boolean getPatternPhone() {
		return this.patternPhone;
	}

	public void setPatternPhone(final Boolean patternPhone) {
		this.patternPhone = patternPhone;
	}

	@Size(min = 5, max = 32)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	// Business methods -------------------------------------------------------

	public RegistrationFormAuditor createToAuditor() {

		final RegistrationFormAuditor registrationForm = new RegistrationFormAuditor();

		registrationForm.setPatternPhone(false);
		registrationForm.setAddress("");
		registrationForm.setEmail("");
		registrationForm.setName("");
		registrationForm.setVatNumber("");
		registrationForm.setPhoto("");
		registrationForm.setSurnames("");
		registrationForm.setAddress("");
		registrationForm.setCreditCard(new CreditCard());
		registrationForm.setPassword("");
		registrationForm.setBrandName("");
		registrationForm.setHolderName("");
		registrationForm.setNumber("");
		registrationForm.setExpirationMonth(0);
		registrationForm.setExpirationYear(0);
		registrationForm.setCW(0);
		registrationForm.setPositions(new HashSet<Position>());

		//PREGUNTAR
		final UserAccount user = new UserAccount();
		user.setAuthorities(new HashSet<Authority>());
		final Authority ad = new Authority();
		ad.setAuthority(Authority.ADMIN);
		user.getAuthorities().add(ad);

		//NUEVO
		user.setUsername("");
		user.setPassword("");

		registrationForm.setUserAccount(user);

		return registrationForm;
	}

}
