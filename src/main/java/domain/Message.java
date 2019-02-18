
package domain;

<<<<<<< HEAD
=======
import java.util.Collection;
>>>>>>> origin/adrian
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
<<<<<<< HEAD
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
=======
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
>>>>>>> origin/adrian

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity {

<<<<<<< HEAD
	//Attributes
	private Date	sendingMoment;
	private String	subject;
	private String	body;
	private int		priority;
	private String	tags;


	//Getters and Setters
	@Temporal(TemporalType.TIMESTAMP)
=======
	// Attributes -------------------------------------------------------------
	private Date				sendingMoment;
	private String				subject;
	private String				body;
	private Priority			priority;
	private Collection<String>	tags;


	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
>>>>>>> origin/adrian
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getSendingMoment() {
		return this.sendingMoment;
	}

	public void setSendingMoment(final Date sendingMoment) {
		this.sendingMoment = sendingMoment;
	}
<<<<<<< HEAD
=======

>>>>>>> origin/adrian
	@NotBlank
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}
<<<<<<< HEAD
=======

>>>>>>> origin/adrian
	@NotBlank
	public String getBody() {
		return this.body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

<<<<<<< HEAD
	@NotBlank
	public int getPriority() {
		return this.priority;
	}

	public void setPriority(final int priority) {
		this.priority = priority;
	}

	public String getTags() {
		return this.tags;
	}

	public void setTags(final String tags) {
		this.tags = tags;
	}

=======
	@NotNull
	@Valid
	public Priority getPriority() {
		return this.priority;
	}

	public void setPriority(final Priority priority) {
		this.priority = priority;
	}

	@ElementCollection(targetClass = String.class)
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


	@OneToOne(optional = true)
	public Actor getSender() {
		return this.sender;
	}

	public void setSender(final Actor sender) {
		this.sender = sender;
	}

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

>>>>>>> origin/adrian
}
