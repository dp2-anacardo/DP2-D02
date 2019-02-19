
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private Date				sendingMoment;
	private String				subject;
	private String				body;
	private Priority			priority;
	private Collection<String>	tags;


	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getSendingMoment() {
		return this.sendingMoment;
	}

	public void setSendingMoment(final Date sendingMoment) {
		this.sendingMoment = sendingMoment;
	}

	@NotBlank
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	@NotBlank
	public String getBody() {
		return this.body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	@NotNull
	@Valid
	@ManyToOne
	public Priority getPriority() {
		return this.priority;
	}

	public void setPriority(final Priority priority) {
		this.priority = priority;
	}

	@ElementCollection
	public Collection<String> getTags() {
		return this.tags;
	}

	public void setTags(final Collection<String> tags) {
		this.tags = tags;
	}


	// Relationships ----------------------------------------------------------
	private Actor					sender;
	private Collection<Actor>		recipients;
	private Collection<MessageBox>	messageBoxes;


	@Valid
	@OneToOne(optional = true)
	public Actor getSender() {
		return this.sender;
	}

	public void setSender(final Actor sender) {
		this.sender = sender;
	}

	@Valid
	@NotNull
	@ManyToMany
	public Collection<Actor> getRecipients() {
		return this.recipients;
	}

	public void setRecipients(final Collection<Actor> recipients) {
		this.recipients = recipients;
	}

	@NotNull
	@Valid
	@ManyToMany
	public Collection<MessageBox> getMessageBoxes() {
		return this.messageBoxes;
	}

	public void setMessageBoxes(final Collection<MessageBox> messageBoxes) {
		this.messageBoxes = messageBoxes;
	}

}
