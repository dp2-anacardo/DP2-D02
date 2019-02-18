
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class SocialProfile extends DomainEntity {

	private String	nick;
	private String	socialNetName;
	private String	socialNetProfLink;


	@NotBlank
	public String getNick() {
		return this.nick;
	}

	public void setNick(final String nick) {
		this.nick = nick;
	}

	@NotBlank
	public String getSocialNetName() {
		return this.socialNetName;
	}

	public void setSocialNetName(final String socialNetName) {
		this.socialNetName = socialNetName;
	}

	@URL
	public String getSocialNetProfLink() {
		return this.socialNetProfLink;
	}

	public void setSocialNetProfLink(final String socialNetProfLink) {
		this.socialNetProfLink = socialNetProfLink;
	}
}
