
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	private int					maxResults;
	private int					maxTime;
	private String				systemName;
	private String				banner;
	private String				welcomeMessage;
	private Collection<String>	spamWords;
	private Collection<String>	positiveWords;
	private Collection<String>	negativeWords;
	private String				defaultCC;


	@Size(min = 10, max = 100)
	public int getMaxResults() {
		return this.maxResults;
	}

	public void setMaxResults(final int maxResults) {
		this.maxResults = maxResults;
	}

	@Size(min = 1, max = 24)
	public int getMaxTime() {
		return this.maxTime;
	}

	public void setMaxTime(final int maxTime) {
		this.maxTime = maxTime;
	}

	@NotBlank
	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(final String systemName) {
		this.systemName = systemName;
	}

	@URL
	@NotBlank
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@NotBlank
	public String getWelcomeMessage() {
		return this.welcomeMessage;
	}

	public void setWelcomeMessage(final String welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
	}

	@ElementCollection
	public Collection<String> getSpamWords() {
		return this.spamWords;
	}

	public void setSpamWords(final Collection<String> spamWords) {
		this.spamWords = spamWords;
	}

	@ElementCollection
	public Collection<String> getPositiveWords() {
		return this.positiveWords;
	}

	public void setPositiveWords(final Collection<String> positiveWords) {
		this.positiveWords = positiveWords;
	}

	@ElementCollection
	public Collection<String> getNegativeWords() {
		return this.negativeWords;
	}

	public void setNegativeWords(final Collection<String> negativeWords) {
		this.negativeWords = negativeWords;
	}

	@NotBlank
	public String getDefaultCC() {
		return this.defaultCC;
	}

	public void setDefaultCC(final String defaultCC) {
		this.defaultCC = defaultCC;
	}

}
