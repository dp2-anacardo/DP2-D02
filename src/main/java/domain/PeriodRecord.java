
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

import datatype.Url;

@Entity
@Access(AccessType.PROPERTY)
public class PeriodRecord extends DomainEntity {

	private String			title;
	private String			description;
	private int				startYear;
	private int				endYear;
	private Collection<Url>	photo;

	//Relationships
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

	public int getStartYear() {
		return this.startYear;
	}

	public int getEndYear() {
		return this.endYear;
	}

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

	public void setStartYear(final int startYear) {
		this.startYear = startYear;
	}

	public void setEndYear(final int endYear) {
		this.endYear = endYear;
	}

	public void setPhoto(final Collection<Url> photo) {
		this.photo = photo;
	}

	//Relationships
	@Valid
	@ManyToOne(optional = false)
	public Brotherhood getBrotherhood() {
		return this.brotherhood;
	}

	public void setBrotherhood(final Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}
}
