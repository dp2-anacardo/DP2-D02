
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import com.google.gson.annotations.Expose;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "name")
})
public class MessageBox extends DomainEntity {

	// Attributes -------------------------------------------------------------
	@Expose
	private String	name;
	@Expose
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
	private MessageBox				father;
	private Collection<MessageBox>	sons;


	@Valid
	@ManyToMany
	public Collection<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(final Collection<Message> messages) {
		this.messages = messages;
	}

	@ManyToOne(optional = true)
	public MessageBox getFather() {
		return this.father;
	}

	public void setFather(final MessageBox father) {
		this.father = father;
	}

	@Valid
	@OneToMany(mappedBy = "father")
	public Collection<MessageBox> getSons() {
		return this.sons;
	}

	public void setSons(final Collection<MessageBox> sons) {
		this.sons = sons;
	}

	public void addSon(final MessageBox son) {
		this.sons.add(son);
		son.setFather(this);
	}

	public void removeSon(final MessageBox son) {
		this.sons.remove(son);
		son.setFather(null);
	}

	//Methods
	public void addMessage(final Message message) {
		this.messages.add(message);
	}

	public void deleteMessage(final Message message) {
		this.messages.remove(message);

	}

	public boolean equals(final MessageBox obj) {
		boolean res = false;
		if (this.getName().equals(obj.getName()))
			res = true;
		return res;
	}

}
