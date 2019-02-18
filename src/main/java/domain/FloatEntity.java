
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class FloatEntity extends DomainEntity {

	private String				tittle;
	private String				description;
	private Collection<String>	pictures;


	@NotBlank
	public String getTittle() {
		return this.tittle;
	}

	public void setTittle(final String tittle) {
		this.tittle = tittle;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Collection<String> getPictures() {
		return this.pictures;
	}

	public void setPictures(final Collection<String> pictures) {
		this.pictures = pictures;
	}

}
