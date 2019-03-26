
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import datatype.Url;

@Entity
@Access(AccessType.PROPERTY)
public class PeriodRecord extends DomainEntity {

	private String			title;
	private String			description;
	private Integer			startYear;
	private Integer			endYear;
	private Collection<Url>	photo;

	private Brotherhood		brotherhood;


	//Getters
	@NotBlank
	public String getTitle() {
		return this.title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	@NotNull
	public Integer getStartYear() {
		return this.startYear;
	}
	@NotNull
	public Integer getEndYear() {
		return this.endYear;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@Valid
	@NotEmpty
	public Collection<Url> getPhoto() {
		return this.photo;
	}

	//Setters
	public void setTitle(final String title) {
		this.title = title;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setStartYear(final Integer startYear) {
		this.startYear = startYear;
	}

	public void setEndYear(final Integer endYear) {
		this.endYear = endYear;
	}

	public void setPhoto(final Collection<Url> photo) {
		this.photo = photo;
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
