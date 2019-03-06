
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Position extends DomainEntity {

	private String	roleEs;
	private String	roleEn;


	@NotBlank
	@Column(unique = true)
	public String getRoleEs() {
		return this.roleEs;
	}

	public void setRoleEs(final String roleEs) {
		this.roleEs = roleEs;
	}

	@NotBlank
	@Column(unique = true)
	public String getRoleEn() {
		return this.roleEn;
	}

	public void setRoleEn(final String roleEn) {
		this.roleEn = roleEn;
	}

}
