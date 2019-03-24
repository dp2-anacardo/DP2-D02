
package forms;

import java.util.Collection;

import domain.LegalRecord;

public class LegalRecordForm {

	private int					id;
	private int					version;
	private String				title;
	private String				description;
	private String				legalName;
	private int					vatNumber;
	private Collection<String>	applicableLaws;
	private String				law;


	public LegalRecordForm(final LegalRecord lR) {
		this.id = lR.getId();
		this.version = lR.getVersion();
		this.title = lR.getTitle();
		this.description = lR.getDescription();
		this.legalName = lR.getLegalName();
		this.vatNumber = lR.getVatNumber();
		this.applicableLaws = lR.getApplicableLaws();
	}

	public LegalRecordForm() {

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

	public String getLegalName() {
		return this.legalName;
	}

	public int getVatNumber() {
		return this.vatNumber;
	}

	public Collection<String> getApplicableLaws() {
		return this.applicableLaws;
	}

	public String getLaw() {
		return this.law;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setLegalName(final String legalName) {
		this.legalName = legalName;
	}

	public void setVatNumber(final int vatNumber) {
		this.vatNumber = vatNumber;
	}

	public void setApplicableLaws(final Collection<String> applicableLaws) {
		this.applicableLaws = applicableLaws;
	}

	public void setLaw(final String law) {
		this.law = law;
	}

}
