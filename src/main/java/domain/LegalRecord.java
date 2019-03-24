
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Access(AccessType.PROPERTY)
public class LegalRecord extends DomainEntity {

	private String				title;
	private String				description;
	private String				legalName;
	private int					vatNumber;
	private Collection<String>	applicableLaws;

	private Brotherhood			brotherhood;


	//Getters
	@NotBlank
	public String getTitle() {
		return this.title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	@NotBlank
	public String getLegalName() {
		return this.legalName;
	}

	public int getVatNumber() {
		return this.vatNumber;
	}

	@NotEmpty
	@ElementCollection(fetch = FetchType.EAGER)
	@Valid
	public Collection<String> getApplicableLaws() {
		return this.applicableLaws;
	}

	//Setters
	public void setTitle(final String title) {
		this.title = title;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setLegalName(final String legalName) {
		this.legalName = legalName;
	}

	public void setVatNumber(final int vatNumber) {
		this.vatNumber = vatNumber;
	}

	public void setApplicableLaws(final Collection<String> applicableLaws) {
		this.applicableLaws = applicableLaws;
	}

	@Valid
	@ManyToOne
	public Brotherhood getBrotherhood() {
		return this.brotherhood;
	}

	public void setBrotherhood(final Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}

}
