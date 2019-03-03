
package forms;

import org.hibernate.validator.constraints.NotBlank;

public class ConfigurationForm {

	private String	addSW;
	private String	addPW;
	private String	addNW;


	@NotBlank
	public String getAddSW() {
		return this.addSW;
	}

	@NotBlank
	public String getAddPW() {
		return this.addPW;
	}

	@NotBlank
	public String getAddNW() {
		return this.addNW;
	}

	public void setAddSW(final String addSW) {
		this.addSW = addSW;
	}

	public void setAddPW(final String addPW) {
		this.addPW = addPW;
	}

	public void setAddNW(final String addNW) {
		this.addNW = addNW;
	}

}
