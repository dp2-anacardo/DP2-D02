
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Index;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "roleEs, roleEn")
})
public class Position extends DomainEntity {

	private String	roleEs;
	private String	roleEn;


	@NotBlank
	@Column(unique = true)
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getRoleEs() {
		return this.roleEs;
	}

	public void setRoleEs(final String roleEs) {
		this.roleEs = roleEs;
	}

	@NotBlank
	@Column(unique = true)
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getRoleEn() {
		return this.roleEn;
	}

	public void setRoleEn(final String roleEn) {
		this.roleEn = roleEn;
	}

}
