
package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ParadeRepository;
import repositories.SponsorshipRepository;
import domain.Sponsor;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorshipService {

	// Manage Repository
	@Autowired
	private SponsorshipRepository	sponsorshipRepository;

	@Autowired
	private SponsorService			sponsorService;

	@Autowired
	private Validator				validator;

	@Autowired
	private ParadeRepository		paradeRepository;


	// CRUD methods
	public Sponsorship create() {
		final Sponsorship result = new Sponsorship();

		final Sponsor principal = this.sponsorService.findByPrincipal();

		result.setSponsor(principal);

		return result;
	}

	public Sponsorship findOne(final int sponsorshipID) {
		final Sponsorship result = this.sponsorshipRepository.findOne(sponsorshipID);
		Assert.notNull(result);

		return result;
	}

	public Collection<Sponsorship> findAll() {
		final Collection<Sponsorship> result = this.sponsorshipRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Sponsorship save(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship);
		final Sponsorship result = this.sponsorshipRepository.save(sponsorship);

		return result;
	}

	public void delete(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship);

		this.sponsorshipRepository.delete(sponsorship);
	}

	// Other business methods
	public Collection<Sponsorship> findBySponsor(final int sponsorId) {
		return this.sponsorshipRepository.findBySponsor(sponsorId);
	}

	public void activate(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship);
		Assert.isTrue(sponsorship.getStatus() == false);

		sponsorship.setStatus(true);

		this.sponsorshipRepository.save(sponsorship);
	}

	public void desactivate(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship);
		Assert.isTrue(sponsorship.getStatus() == true);

		sponsorship.setStatus(false);

		this.sponsorshipRepository.save(sponsorship);
	}

	public Sponsorship reconstruct(final Sponsorship sponsorship, final BindingResult binding) {
		Sponsorship result;

		if (sponsorship.getId() == 0) {
			result = sponsorship;
			result.setSponsor(this.sponsorService.findByPrincipal());
			result.setTargetURL("http://localhost:8080/Acme-Madruga/parade/show.do?paradeId=" + result.getParade().getId() + "");

			this.validator.validate(result, binding);

		} else {
			result = this.sponsorshipRepository.findOne(sponsorship.getId());

			//sponsorship.setBanner(result.getBanner());
			//sponsorship.setCreditCard(result.getCreditCard());
			//sponsorship.setStatus(result.getStatus());
			sponsorship.setTargetURL(result.getTargetURL());
			sponsorship.setParade(result.getParade());
			sponsorship.setSponsor(result.getSponsor());
			sponsorship.setVersion(result.getVersion());

			result = sponsorship;

			this.validator.validate(result, binding);

		}

		return result;

	}

	public List<Sponsorship> findAllByParade(final int paradeId) {
		Assert.notNull(paradeId);

		final List<Sponsorship> sponsorships = this.sponsorshipRepository.findAllByParade(paradeId);

		return sponsorships;
	}

	public Collection<Sponsorship> findAllActive() {
		final Collection<Sponsorship> sponsorships = this.sponsorshipRepository.findAllActive();

		return sponsorships;
	}

	public Collection<Sponsorship> getSponsorshipByParade(final int id) {
		return this.paradeRepository.getParadesBySponsorship(id);
	}
}
