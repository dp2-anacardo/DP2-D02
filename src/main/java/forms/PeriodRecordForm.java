
package forms;

import java.util.Collection;

import javax.validation.Valid;

import org.hibernate.validator.constraints.URL;

import datatype.Url;
import domain.PeriodRecord;

public class PeriodRecordForm {

	private int				id;
	private int				version;
	private String			title;
	private String			description;
	private Integer			startYear;
	private Integer			endYear;
	private Collection<Url>	photo;
	private String			link;


	public PeriodRecordForm(final PeriodRecord pR) {
		this.id = pR.getId();
		this.version = pR.getVersion();
		this.title = pR.getTitle();
		this.description = pR.getDescription();
		this.startYear = pR.getStartYear();
		this.endYear = pR.getEndYear();
		this.photo = pR.getPhoto();
	}

	public PeriodRecordForm() {

	}

	public int getId() {
		return this.id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	public String getTitle() {
		return this.title;
	}

	public String getDescription() {
		return this.description;
	}

	public Integer getStartYear() {
		return this.startYear;
	}

	public Integer getEndYear() {
		return this.endYear;
	}

	public Collection<Url> getPhoto() {
		return this.photo;
	}

	@Valid
	@URL
	public String getLink() {
		return this.link;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setStartYear(final Integer startYear) {
		this.startYear = startYear;
	}

	public void setEndYear(final Integer endYear) {
		this.endYear = endYear;
	}

	public void setPhoto(final Collection<Url> photo) {
		this.photo = photo;
	}

	public void setLink(final String link) {
		this.link = link;
	}

}
