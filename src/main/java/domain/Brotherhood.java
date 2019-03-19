
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

import datatype.Url;

@Entity
@Access(AccessType.PROPERTY)
public class Brotherhood extends Actor {

	private String			title;
	private Date			establishmentDate;
	private Collection<Url>	pictures;

	//Relationships

	private Area			area;


	//	private InceptionRecord				inceptionRecord;
	//	private Collection<PeriodRecord>	periodsRecords;
	//	private Collection<LegalRecord>		legalsRecords;
	//	private Collection<MiscRecord>		miscsRecords;
	//	private Collection<LinkRecord>		linksRecords;
	//	private Collection<LinkRecord>		linkedsRecordsBrotherhood;

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEstablishmentDate() {
		return this.establishmentDate;
	}

	public void setEstablishmentDate(final Date establishmentDate) {
		this.establishmentDate = establishmentDate;
	}

	@NotEmpty
	@ElementCollection(fetch = FetchType.EAGER)
	@Valid
	public Collection<Url> getPictures() {
		return this.pictures;
	}

	public void setPictures(final Collection<Url> pictures) {
		this.pictures = pictures;
	}

	//Relationships

	@Valid
	@ManyToOne(optional = true)
	public Area getArea() {
		return this.area;
	}

	public void setArea(final Area area) {
		this.area = area;
	}
	//
	//	@Valid
	//	@OneToOne(optional = false, mappedBy = "brotherhood")
	//	public InceptionRecord getInceptionRecord() {
	//		return this.inceptionRecord;
	//	}
	//
	//	public void setInceptionRecord(final InceptionRecord inceptionRecord) {
	//		this.inceptionRecord = inceptionRecord;
	//	}
	//
	//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "brotherhood")
	//	public Collection<PeriodRecord> getPeriodsRecords() {
	//		return this.periodsRecords;
	//	}
	//
	//	public void setPeriodsRecords(final Collection<PeriodRecord> periodsRecords) {
	//		this.periodsRecords = periodsRecords;
	//	}
	//
	//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "brotherhood")
	//	public Collection<LegalRecord> getLegalsRecords() {
	//		return this.legalsRecords;
	//	}
	//
	//	public void setLegalsRecords(final Collection<LegalRecord> legalsRecords) {
	//		this.legalsRecords = legalsRecords;
	//	}
	//
	//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "brotherhood")
	//	public Collection<MiscRecord> getMiscsRecords() {
	//		return this.miscsRecords;
	//	}
	//
	//	public void setMiscsRecords(final Collection<MiscRecord> miscsRecords) {
	//		this.miscsRecords = miscsRecords;
	//	}
	//
	//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "brotherhood")
	//	public Collection<LinkRecord> getLinksRecords() {
	//		return this.linksRecords;
	//	}
	//
	//	public void setLinksRecords(final Collection<LinkRecord> linksRecords) {
	//		this.linksRecords = linksRecords;
	//	}
	//
	//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "linkedBH")
	//	public Collection<LinkRecord> getLinkedsRecordsBrotherhood() {
	//		return this.linkedsRecordsBrotherhood;
	//	}
	//
	//	public void setLinkedsRecordsBrotherhood(final Collection<LinkRecord> linkedsRecordsBrotherhood) {
	//		this.linkedsRecordsBrotherhood = linkedsRecordsBrotherhood;
	//	}
}
