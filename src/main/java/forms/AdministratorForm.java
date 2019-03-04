
package forms;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import domain.Administrator;

public class AdministratorForm {

	private String	confirmPass;
	private String	password;
	private String	username;
	private String	middleName;
	private String	surname;
	private String	name;
	private String	photo;
	private String	email;
	private String	phoneNumber;
	private String	address;
	//	private Boolean						isSuspicious;
	//	private Boolean						isBanned;
	//	private Double						score;
	//	private Collection<MessageBox>		boxes;
	//	private UserAccount					userAccount;
	//	private Collection<SocialProfile>	socialProfiles;
	private int		id;
	private int		version;


	public AdministratorForm(final Administrator admin) {

		final AdministratorForm result = new AdministratorForm();
		result.setAddress(admin.getAddress());
		//		result.setBoxes(admin.getBoxes());
		result.setEmail(admin.getEmail());
		result.setId(admin.getId());
		//		result.setIsBanned(admin.getIsBanned());
		//		result.setIsSuspicious(admin.getIsSuspicious());
		result.setName(admin.getName());
		result.setPhoneNumber(admin.getPhoneNumber());
		result.setPhoto(admin.getPhoto());
		//		result.setScore(admin.getScore());
		//		result.setSocialProfiles(admin.getSocialProfiles());
		result.setSurname(admin.getSurname());
		result.setMiddleName(admin.getMiddleName());
		//		result.setUserAccount(admin.getUserAccount());
		result.setVersion(admin.getVersion());
	}
	public AdministratorForm() {

	}

	@NotNull
	@NotBlank
	public String getConfirmPass() {
		return this.confirmPass;
	}

	public void setConfirmPass(final String confirmPass) {
		this.confirmPass = confirmPass;
	}

	public String getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(final String middleName) {
		this.middleName = middleName;
	}

	@NotBlank
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
	}

	@NotBlank
	@Email
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	//	public Boolean getIsSuspicious() {
	//		return this.isSuspicious;
	//	}
	//
	//	public void setIsSuspicious(final Boolean isSuspicious) {
	//		this.isSuspicious = isSuspicious;
	//	}
	//
	//	public Boolean getIsBanned() {
	//		return this.isBanned;
	//	}
	//
	//	public void setIsBanned(final Boolean isBanned) {
	//		this.isBanned = isBanned;
	//	}
	//
	//	public Double getScore() {
	//		return this.score;
	//	}
	//
	//	public void setScore(final Double score) {
	//		this.score = score;
	//	}
	//
	//	public Collection<MessageBox> getBoxes() {
	//		return this.boxes;
	//	}
	//
	//	public void setBoxes(final Collection<MessageBox> boxes) {
	//		this.boxes = boxes;
	//	}
	//
	//	public UserAccount getUserAccount() {
	//		return this.userAccount;
	//	}
	//
	//	public void setUserAccount(final UserAccount userAccount) {
	//		this.userAccount = userAccount;
	//	}
	//
	//	public Collection<SocialProfile> getSocialProfiles() {
	//		return this.socialProfiles;
	//	}
	//
	//	public void setSocialProfiles(final Collection<SocialProfile> socialProfiles) {
	//		this.socialProfiles = socialProfiles;
	//	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	@NotBlank
	public String getPassword() {
		return this.password;
	}
	public void setPassword(final String password) {
		this.password = password;
	}

	@NotBlank
	public String getUsername() {
		return this.username;
	}
	public void setUsername(final String username) {
		this.username = username;
	}

}
