
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "title, description, moment, isFinal")
}, uniqueConstraints = {
	@UniqueConstraint(columnNames = "ticker")
})
public class Parade extends DomainEntity {

	private String					title;
	private String					description;
	private Date					moment;
	private String					ticker;
	private Boolean					isFinal;
	private Integer					maxRow;
	private Integer					maxColumn;
	private Brotherhood				brotherhood;
	private Collection<FloatEntity>	floats;
	private String					status;
	private String					rejectComment;


	@Valid
	@ManyToMany
	public Collection<FloatEntity> getFloats() {
		return this.floats;
	}

	public void setFloats(final Collection<FloatEntity> floats) {
		this.floats = floats;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	@Future
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
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
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

	@Pattern(regexp = "^ACCEPTED|SUBMITTED|REJECTED$")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getRejectComment() {
		return this.rejectComment;
	}

	public void setRejectComment(final String rejectComment) {
		this.rejectComment = rejectComment;
	}

	public void setBrotherhood(final Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}

}
