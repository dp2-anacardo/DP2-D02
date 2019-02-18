
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Request extends DomainEntity {

	private int			positionRow;
	private int			positionColumn;
	private String		status;
	private String		comments;
	private Procession	procession;
	private Member		member;


	@NotBlank
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

	public int getPositionRow() {
		return this.positionRow;
	}

	public void setPositionRow(final int positionRow) {
		this.positionRow = positionRow;
	}

	public int getPositionColumn() {
		return this.positionColumn;
	}

	public void setPositionColumn(final int positionColumn) {
		this.positionColumn = positionColumn;
	}

	//Relationships

	@ManyToOne(optional = false)
	public Procession getProcession() {
		return this.procession;
	}

	public void setProcession(final Procession procession) {
		this.procession = procession;
	}

	public Member getMember() {
		return this.member;
	}

	@ManyToOne(optional = false)
	public void setMember(final Member member) {
		this.member = member;
	}

}
