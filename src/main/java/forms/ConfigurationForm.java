
package forms;

import java.util.Collection;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

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
	private Collection<String>	brandName;
	private String				addBN;
	private Double				vat;
	private Double				flatFee;


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
		this.brandName = config.getBrandName();
		this.vat = config.getVat();
		this.flatFee = config.getFlatFee();
	}

	public Collection<String> getBrandName() {
		return this.brandName;
	}

	public Double getVat() {
		return this.vat;
	}

	public Double getFlatFee() {
		return this.flatFee;
	}

	public void setBrandName(final Collection<String> brandName) {
		this.brandName = brandName;
	}

	public void setVat(final Double vat) {
		this.vat = vat;
	}

	public void setFlatFee(final Double flatFee) {
		this.flatFee = flatFee;
	}

	public ConfigurationForm() {

	}

	public int getId() {
		return this.id;
	}

	public int getVersion() {
		return this.version;
	}

	public int getMaxResults() {
		return this.maxResults;
	}

	public int getMaxTime() {
		return this.maxTime;
	}

	public String getSystemName() {
		return this.systemName;
	}

	public String getBanner() {
		return this.banner;
	}

	public String getWelcomeMessageEn() {
		return this.welcomeMessageEn;
	}

	public String getWelcomeMessageEs() {
		return this.welcomeMessageEs;
	}

	public Collection<String> getSpamWords() {
		return this.spamWords;
	}

	public Collection<String> getPositiveWords() {
		return this.positiveWords;
	}

	public Collection<String> getNegativeWords() {
		return this.negativeWords;
	}

	public String getDefaultCC() {
		return this.defaultCC;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getAddSW() {
		return this.addSW;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getAddPW() {
		return this.addPW;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
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

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getAddBN() {
		return this.addBN;
	}

	public void setAddBN(final String addBN) {
		this.addBN = addBN;
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
