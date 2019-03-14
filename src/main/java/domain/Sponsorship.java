
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import datatype.Url;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsorship extends DomainEntity {

	private Url			banner;
	private Url			targetURL;
	private String		status;
	private Sponsor		sponsor;
	private CreditCard	creditCard;
	private Procession	parade;


	@NotNull
	@Valid
	public Url getBanner() {
		return this.banner;
	}

	public void setBanner(final Url banner) {
		this.banner = banner;
	}

	@NotNull
	@Valid
	public Url getTargetURL() {
		return this.targetURL;
	}

	public void setTargetURL(final Url targetURL) {
		this.targetURL = targetURL;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	@Valid
	@ManyToOne(optional = false)
	public Sponsor getSponsor() {
		return this.sponsor;
	}

	public void setSponsor(final Sponsor sponsor) {
		this.sponsor = sponsor;
	}

	@Valid
	@OneToOne(optional = false)
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@Valid
	@ManyToOne(optional = false)
	public Procession getParade() {
		return this.parade;
	}

	public void setParade(final Procession parade) {
		this.parade = parade;
	}

}
