
package forms;

import java.util.Collection;

import javax.persistence.ElementCollection;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import domain.Configuration;

public class ConfigurationForm {

	private int					id;
	private int					version;
	private int					maxResults;
	private int					maxTime;
	private String				systemName;
	private String				banner;
	private String				welcomeMessageEn;
	private String				welcomeMessageEs;
	private Collection<String>	spamWords;
	private Collection<String>	positiveWords;
	private Collection<String>	negativeWords;
	private String				defaultCC;
	private String				addSW;
	private String				addPW;
	private String				addNW;


	public ConfigurationForm(final Configuration config) {
		this.id = config.getId();
		this.version = config.getVersion();
		this.maxResults = config.getMaxResults();
		this.maxTime = config.getMaxTime();
		this.systemName = config.getSystemName();
		this.banner = config.getBanner();
		this.welcomeMessageEn = config.getWelcomeMessageEn();
		this.welcomeMessageEs = config.getWelcomeMessageEs();
		this.spamWords = config.getSpamWords();
		this.positiveWords = config.getPositiveWords();
		this.negativeWords = config.getNegativeWords();
		this.defaultCC = config.getDefaultCC();
	}

	public ConfigurationForm() {

	}

	public int getId() {
		return this.id;
	}

	public int getVersion() {
		return this.version;
	}

	//@Range(min = 10, max = 100)
	public int getMaxResults() {
		return this.maxResults;
	}

	@Range(min = 1, max = 24)
	public int getMaxTime() {
		return this.maxTime;
	}

	@NotBlank
	public String getSystemName() {
		return this.systemName;
	}

	@NotBlank
	@URL
	public String getBanner() {
		return this.banner;
	}

	@NotBlank
	public String getWelcomeMessageEn() {
		return this.welcomeMessageEn;
	}

	@NotBlank
	public String getWelcomeMessageEs() {
		return this.welcomeMessageEs;
	}

	@ElementCollection
	public Collection<String> getSpamWords() {
		return this.spamWords;
	}

	@ElementCollection
	public Collection<String> getPositiveWords() {
		return this.positiveWords;
	}

	@ElementCollection
	public Collection<String> getNegativeWords() {
		return this.negativeWords;
	}

	@NotBlank
	public String getDefaultCC() {
		return this.defaultCC;
	}

	public String getAddSW() {
		return this.addSW;
	}

	public String getAddPW() {
		return this.addPW;
	}

	public String getAddNW() {
		return this.addNW;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	public void setMaxResults(final int maxResults) {
		this.maxResults = maxResults;
	}

	public void setMaxTime(final int maxTime) {
		this.maxTime = maxTime;
	}

	public void setSystemName(final String systemName) {
		this.systemName = systemName;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	public void setWelcomeMessageEn(final String welcomeMessageEn) {
		this.welcomeMessageEn = welcomeMessageEn;
	}

	public void setWelcomeMessageEs(final String welcomeMessageEs) {
		this.welcomeMessageEs = welcomeMessageEs;
	}

	public void setSpamWords(final Collection<String> spamWords) {
		this.spamWords = spamWords;
	}

	public void setPositiveWords(final Collection<String> positiveWords) {
		this.positiveWords = positiveWords;
	}

	public void setNegativeWords(final Collection<String> negativeWords) {
		this.negativeWords = negativeWords;
	}

	public void setDefaultCC(final String defaultCC) {
		this.defaultCC = defaultCC;
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
