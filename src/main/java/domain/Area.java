
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

import datatype.Url;

@Entity
@Access(AccessType.PROPERTY)
public class Area extends DomainEntity {

	private String			name;
	private Collection<Url>	pictures;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@Valid
	public Collection<Url> getPictures() {
		return this.pictures;
	}

	public void setPictures(final Collection<Url> pictures) {
		this.pictures = pictures;
	}

}
