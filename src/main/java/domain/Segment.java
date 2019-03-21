
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class Segment extends DomainEntity {

	private Double	originLatitude;
	private Double	originLongitude;
	private Double	destinationLatitude;
	private Double	destinationLongitude;
	private String	timeOrigin;
	private String	timeDestination;
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

	@NotBlank
	@Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d$")
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTimeOrigin() {
		return this.timeOrigin;
	}

	public void setTimeOrigin(final String timeOrigin) {
		this.timeOrigin = timeOrigin;
	}

	@NotBlank
	@Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d$")
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTimeDestination() {
		return this.timeDestination;
	}

	public void setTimeDestination(final String timeDestination) {
		this.timeDestination = timeDestination;
	}

}
