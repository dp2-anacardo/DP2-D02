
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class FloatEntity extends DomainEntity {

	private String	tittle;
	private String	description;
	private String	url;


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

	@URL
	public String getUrl() {
		return this.url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

}
