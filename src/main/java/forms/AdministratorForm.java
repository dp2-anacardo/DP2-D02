
package forms;

import javax.validation.constraints.NotNull;

import domain.Administrator;

public class AdministratorForm extends Administrator {

	private String	confirmPass;


	public AdministratorForm(final Administrator admin) {

		final AdministratorForm result = new AdministratorForm();
		result.setAddress(admin.getAddress());
		result.setBoxes(admin.getBoxes());
		result.setEmail(admin.getEmail());
		result.setId(admin.getId());
		result.setIsBanned(admin.getIsBanned());
		result.setIsSuspicious(admin.getIsSuspicious());
		result.setName(admin.getName());
		result.setPhoneNumber(admin.getPhoneNumber());
		result.setPhoto(admin.getPhoto());
		result.setScore(admin.getScore());
		result.setSocialProfiles(admin.getSocialProfiles());
		result.setSurname(admin.getSurname());
		result.setMiddleName(admin.getMiddleName());
		result.setUserAccount(admin.getUserAccount());
		result.setVersion(admin.getVersion());
	}
	public AdministratorForm() {

	}

	@NotNull
	public String getConfirmPass() {
		return this.confirmPass;
	}

	public void setConfirmPass(final String confirmPass) {
		this.confirmPass = confirmPass;
	}

}
