
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Segment extends DomainEntity {

	private Double	originLatitude;
	private Double	originLongitude;
	private Double	destinationLatitude;
	private Double	destinationLongitude;
	private Date	timeOrigin;
	private Date	timeDestination;
	private Parade	parade;


	@Valid
	@ManyToOne(optional = true)
	public Parade getParade() {
		return this.parade;
	}

	public void setParade(final Parade parade) {
		this.parade = parade;
	}

	@NotNull
	@Range(min = -90, max = 90)
	public Double getOriginLatitude() {
		return this.originLatitude;
	}

	public void setOriginLatitude(final Double originLatitude) {
		this.originLatitude = originLatitude;
	}
	@NotNull
	@Range(min = -180, max = 180)
	public Double getOriginLongitude() {
		return this.originLongitude;
	}

	public void setOriginLongitude(final Double originLongitude) {
		this.originLongitude = originLongitude;
	}
	@NotNull
	@Range(min = -90, max = 90)
	public Double getDestinationLatitude() {
		return this.destinationLatitude;
	}

	public void setDestinationLatitude(final Double destinationLatitude) {
		this.destinationLatitude = destinationLatitude;
	}
	@NotNull
	@Range(min = -180, max = 180)
	public Double getDestinationLongitude() {
		return this.destinationLongitude;
	}

	public void setDestinationLongitude(final Double destinationLongitude) {
		this.destinationLongitude = destinationLongitude;
	}
	@NotNull
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "dd/MM/yyy HH:mm")
	public Date getTimeOrigin() {
		return this.timeOrigin;
	}

	public void setTimeOrigin(final Date timeOrigin) {
		this.timeOrigin = timeOrigin;
	}
	@NotNull
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getTimeDestination() {
		return this.timeDestination;
	}

	public void setTimeDestination(final Date timeDestination) {
		this.timeDestination = timeDestination;
	}

}
