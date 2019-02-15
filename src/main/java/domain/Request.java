
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Request extends DomainEntity {

	private int		row;
	private int		column;
	private String	status;
	private String	comments;


	public int getRow() {
		return this.row;
	}

	public void setRow(final int row) {
		this.row = row;
	}

	public int getColumn() {
		return this.column;
	}

	public void setColumn(final int column) {
		this.column = column;
	}
	@NotBlank
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}
	@Range(min = 0, max = 1)
	public String getComments() {
		return this.comments;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

}
