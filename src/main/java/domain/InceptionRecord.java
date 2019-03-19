
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import datatype.Url;

@Entity
@Access(AccessType.PROPERTY)
public class InceptionRecord extends DomainEntity {

	private String			title;
	private String			description;
	private Collection<Url>	photo;


	//Relaciones

	@SafeHtml(whitelistType = WhiteListType.NONE)
	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@Valid
	@NotEmpty
	public Collection<Url> getPhoto() {
		return this.photo;
	}

	public void setPhoto(final Collection<Url> photo) {
		this.photo = photo;
	}

}
