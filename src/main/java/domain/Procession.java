
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Procession extends DomainEntity {

	private String					title;
	private String					description;
	private Date					moment;
	private String					ticker;
	private Member[][]				formation;
	private Boolean					isFinal;
	private Integer					maxRow;
	private Integer					maxColumn;
	private Brotherhood				brotherhood;
	private Collection<FloatEntity>	floats;


	@Valid
	@ManyToMany
	public Collection<FloatEntity> getFloats() {
		return this.floats;
	}

	public void setFloats(final Collection<FloatEntity> floats) {
		this.floats = floats;
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^(\\d{6}(-)\\w{5})$")
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	public Member[][] getFormation() {
		return this.formation;
	}

	public void setFormation(final Member[][] formation) {
		this.formation = formation;
	}

	public Boolean getIsFinal() {
		return this.isFinal;
	}

	public void setIsFinal(final Boolean isFinal) {
		this.isFinal = isFinal;
	}

	@Range(min = 1, max = 10000)
	public Integer getMaxRow() {
		return this.maxRow;
	}

	public void setMaxRow(final int maxRow) {
		this.maxRow = maxRow;
	}
	@Range(min = 1, max = 10000)
	public Integer getMaxColumn() {
		return this.maxColumn;
	}

	public void setMaxColumn(final int maxColumn) {
		this.maxColumn = maxColumn;
	}

	@Valid
	@ManyToOne(optional = false)
	public Brotherhood getBrotherhood() {
		return this.brotherhood;
	}

	public void setBrotherhood(final Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}

}
