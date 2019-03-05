
package forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
	private int		id;
	private int		version;


	public AdministratorForm(final Administrator admin) {
		final AdministratorForm result = new AdministratorForm();
		result.setAddress(admin.getAddress());
		result.setEmail(admin.getEmail());
		result.setId(admin.getId());
		result.setName(admin.getName());
		result.setPhoneNumber(admin.getPhoneNumber());
		result.setPhoto(admin.getPhoto());
		result.setSurname(admin.getSurname());
		result.setMiddleName(admin.getMiddleName());
		result.setVersion(admin.getVersion());
	}

	public AdministratorForm() {

	}

	@Size(min = 5, max = 32)
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

	@Size(min = 5, max = 32)
	@NotBlank
	public String getPassword() {
		return this.password;
	}
	public void setPassword(final String password) {
		this.password = password;
	}

	@Size(min = 5, max = 32)
	@NotBlank
	public String getUsername() {
		return this.username;
	}
	public void setUsername(final String username) {
		this.username = username;
	}

}
