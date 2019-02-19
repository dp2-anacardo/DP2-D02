
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Request extends DomainEntity {

	private int			positionRow;
	private int			positionColumn;
	private String		status;
	private String		comment;
	private Procession	procession;
	private Member		member;


	@NotBlank
	@Pattern(regexp = "^APPROVED|PENDING|REJECTED$")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
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
	@Valid
	@ManyToOne(optional = false)
	public Procession getProcession() {
		return this.procession;
	}

	public void setProcession(final Procession procession) {
		this.procession = procession;
	}

	@Valid
	@ManyToOne(optional = false)
	public Member getMember() {
		return this.member;
	}

	public void setMember(final Member member) {
		this.member = member;
	}

}
