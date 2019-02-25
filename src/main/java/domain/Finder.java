
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	private String					keyWord;
	private Date					minimumDate;
	private Date					maximumDate;
	private String					areaName;
	private Date					lastUpdate;

	//Relationships

	private Collection<Procession>	processions;


	public String getKeyWord() {
		return this.keyWord;
	}

	public void setKeyWord(final String keyWord) {
		this.keyWord = keyWord;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMinimumDate() {
		return this.minimumDate;
	}

	public void setMinimumDate(final Date minimumDate) {
		this.minimumDate = minimumDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMaximumDate() {
		return this.maximumDate;
	}

	public void setMaximumDate(final Date maximumDate) {
		this.maximumDate = maximumDate;
	}

	public String getAreaName() {
		return this.areaName;
	}

	public void setAreaName(final String areaName) {
		this.areaName = areaName;
	}

	//Relationships

	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(final Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Valid
	@OneToMany
	public Collection<Procession> getProcessions() {
		return this.processions;
	}

	public void setProcessions(final Collection<Procession> processions) {
		this.processions = processions;
	}

}
