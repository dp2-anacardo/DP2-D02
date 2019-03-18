
package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

public class SegmentForm {

	private int		version;
	private int		id;
	private Double	originLatitude;
	private Double	originLongitude;
	private Double	destinationLatitude;
	private Double	destinationLongitude;
	private Date	timeOrigin;
	private Date	timeDestination;


	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
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
