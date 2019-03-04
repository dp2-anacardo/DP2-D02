
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class MessageBox extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private String	name;
	private Boolean	isSystem;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotNull
	public Boolean getIsSystem() {
		return this.isSystem;
	}

	public void setIsSystem(final Boolean isSystem) {
		this.isSystem = isSystem;
	}


	// Relationships -------------------------------------------------------------
	private Collection<Message>		messages;
	private Collection<MessageBox>	nestedBoxes;


	@Valid
	@ManyToMany
	public Collection<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(final Collection<Message> messages) {
		this.messages = messages;
	}

	@Valid
	@OneToMany
	public Collection<MessageBox> getNestedBoxes() {
		return this.nestedBoxes;
	}

	public void setNestedBoxes(final Collection<MessageBox> nestedBoxes) {
		this.nestedBoxes = nestedBoxes;
	}

	//Methods
	public void addMessage(final Message message) {
		this.messages.add(message);
	}

	public void deleteMessage(final Message message) {
		this.messages.remove(message);

	}

}
