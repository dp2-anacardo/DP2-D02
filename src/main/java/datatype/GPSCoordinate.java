
package datatype;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
@Access(AccessType.PROPERTY)
public class GPSCoordinate {

	private Double	latitude;
	private Double	longitude;


	@NotNull
	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(final Double latitude) {
		this.latitude = latitude;
	}

	@NotNull
	public Double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(final Double longitude) {
		this.longitude = longitude;
	}

}
