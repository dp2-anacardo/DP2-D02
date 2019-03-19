
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import datatype.GPSCoordinate;

@Entity
@Access(AccessType.PROPERTY)
public class Segment extends DomainEntity {

	private GPSCoordinate	origin;
	private GPSCoordinate	destination;
	private Date			timeOrigin;
	private Date			timeDestination;
	private Parade		parade;


	@Valid
	@ManyToOne(optional = true)
	public Parade getParade() {
		return this.parade;
	}

	public void setParade(final Parade parade) {
		this.parade = parade;
	}
	@Valid
	@AttributeOverrides({
		@AttributeOverride(name = "latitude", column = @Column(name = "originLatitude")), @AttributeOverride(name = "longitude", column = @Column(name = "originLongitude"))
	})
	public GPSCoordinate getOrigin() {
		return this.origin;
	}

	public void setOrigin(final GPSCoordinate origin) {
		this.origin = origin;
	}
	@Valid
	@AttributeOverrides({
		@AttributeOverride(name = "latitude", column = @Column(name = "destinationLatitude")), @AttributeOverride(name = "longitude", column = @Column(name = "destinationLongitude"))
	})
	public GPSCoordinate getDestination() {
		return this.destination;
	}

	public void setDestination(final GPSCoordinate destination) {
		this.destination = destination;
	}
	@Temporal(TemporalType.TIME)
	@NotNull
	public Date getTimeOrigin() {
		return this.timeOrigin;
	}

	public void setTimeOrigin(final Date timeOrigin) {
		this.timeOrigin = timeOrigin;
	}
	@Temporal(TemporalType.TIME)
	@NotNull
	public Date getTimeDestination() {
		return this.timeDestination;
	}

	public void setTimeDestination(final Date timeDestination) {
		this.timeDestination = timeDestination;
	}

}
