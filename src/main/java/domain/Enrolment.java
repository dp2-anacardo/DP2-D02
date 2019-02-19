
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Enrolment extends DomainEntity {

	private Date	registerMoment;
	private Date	dropOutMoment;
	private String	status;


	@NotBlank
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getRegisterMoment() {
		return this.registerMoment;
	}

	public void setRegisterMoment(final Date RegisterMoment) {
		this.registerMoment = RegisterMoment;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getDropOutMoment() {
		return this.dropOutMoment;
	}

	public void setDropOutMoment(final Date dropOutMoment) {
		this.dropOutMoment = dropOutMoment;
	}

	@NotBlank
	@Pattern(regexp = "^ACCEPTED|PENDING|REJECTED$")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

}
