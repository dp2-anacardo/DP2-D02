
package forms;

import java.util.Collection;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

import datatype.Url;
import domain.InceptionRecord;

public class InceptionRecordForm {

	private int				id;
	private int				version;
	private String			title;
	private String			description;
	private Collection<Url>	photo;
	private Url				link;


	public InceptionRecordForm(final InceptionRecord iR) {
		this.id = iR.getId();
		this.version = iR.getVersion();
		this.title = iR.getTitle();
		this.description = iR.getDescription();
		this.photo = iR.getPhoto();
	}

	public InceptionRecordForm() {

	}

	public int getId() {
		return this.id;
	}

	public int getVersion() {
		return this.version;
	}

	public String getTitle() {
		return this.title;
	}

	public String getDescription() {
		return this.description;
	}

	public Collection<Url> getPhoto() {
		return this.photo;
	}

	@URL
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public Url getLink() {
		return this.link;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setPhoto(final Collection<Url> photo) {
		this.photo = photo;
	}

	public void setLink(final Url link) {
		this.link = link;
	}

}
