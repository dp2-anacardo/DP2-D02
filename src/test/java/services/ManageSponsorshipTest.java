
package services;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import org.springframework.validation.DataBinder;

import utilities.AbstractTest;
import datatype.CreditCard;
import domain.Parade;
import domain.Sponsor;
import domain.Sponsorship;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ManageSponsorshipTest extends AbstractTest {

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private SponsorService		sponsorService;

	@Autowired
	private ParadeService		paradeService;

	@Autowired
	private ActorService		actorService;


	@Test
	public void createSponsorShipDriver() {
		final Object testingData[][] = {
			{
				"sponsor1", "http://www.test.es", true, "Skere", "MCARD", "5105105105105100", "25/02/2022", 666, "parade2", null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateSponsorship((String) testingData[i][0], (String) testingData[i][1], (Boolean) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(int) testingData[i][7], (String) testingData[i][8], (Class<?>) testingData[i][9]);

	}

	@Test
	public void editSponsorShipDriver() {
		final Object testingData[][] = {
			{
				"sponsor1", "sponsorship1", "http://www.testEdit.es", null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEditSponsorship((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);

	}

	@Test
	public void desactivateSponsorshipDriver() {
		final Object testingData[][] = {
			{
				"sponsor1", "sponsorship1", null
			}, {
				"sponsor1", "sponsorship4", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateDesactivateSponsorship((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}

	@Test
	public void activateSponsorshipDriver() {
		final Object testingData[][] = {
			{
				"sponsor1", "sponsorship4", null
			}, {
				"sponsor1", "sponsorship1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateActivateSponsorship((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}

	@Test
	public void listSponsorshipBySponsorDriver() {
		final Object testingData[][] = {
			{
				"sponsor1", 2, null
			}, {
				"sponsor2", 2, null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateListSponsorshipBySponsor((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);

	}

	public void templateCreateSponsorship(final String sponsor, final String banner, final Boolean status, final String holderName, final String brandName, final String number, final String expiration, final Integer cvvCode, final String paradeBean,
		final Class<?> expected) {

		Class<?> caught;
		caught = null;
		try {

			super.authenticate(sponsor);

			final Sponsorship sp = this.sponsorshipService.create();

			final DataBinder bind = new DataBinder(sp);

			final Parade parade = this.paradeService.findOne(super.getEntityId(paradeBean));

			sp.setBanner(banner);
			sp.setParade(parade);
			sp.setStatus(status);

			final CreditCard creditCard = new CreditCard();
			final Date date = new SimpleDateFormat("dd/MM/yyyy").parse(expiration);

			creditCard.setBrandName(brandName);
			creditCard.setCvvCode(cvvCode);
			creditCard.setExpiration(date);
			creditCard.setHolderName(holderName);
			creditCard.setNumber(number);
			sp.setCreditCard(creditCard);

			final Sponsorship result = this.sponsorshipService.reconstruct(sp, bind.getBindingResult());

			this.sponsorshipService.save(result);
			this.sponsorService.flush();

		} catch (final Exception e) {
			caught = e.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	public void templateEditSponsorship(final String sponsor, final String sponsorship, final String banner, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {

			super.authenticate(sponsor);

			final Sponsorship sp = this.sponsorshipService.findOne(super.getEntityId(sponsorship));

			final DataBinder bind = new DataBinder(sp);

			sp.setBanner(banner);

			final Sponsorship result = this.sponsorshipService.reconstruct(sp, bind.getBindingResult());

			this.sponsorshipService.save(result);
			this.sponsorService.flush();

		} catch (final Exception e) {
			caught = e.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	public void templateDesactivateSponsorship(final String sponsor, final String sponsorship, final Class<?> expected) {

		Class<?> caught;
		caught = null;
		try {

			super.authenticate(sponsor);

			final Sponsorship sp = this.sponsorshipService.findOne(super.getEntityId(sponsorship));

			this.sponsorshipService.desactivate(sp);

			this.sponsorService.flush();

		} catch (final Exception e) {
			caught = e.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	public void templateActivateSponsorship(final String sponsor, final String sponsorship, final Class<?> expected) {

		Class<?> caught;
		caught = null;
		try {

			super.authenticate(sponsor);

			final Sponsorship sp = this.sponsorshipService.findOne(super.getEntityId(sponsorship));

			this.sponsorshipService.activate(sp);

			this.sponsorService.flush();

		} catch (final Exception e) {
			caught = e.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	public void templateListSponsorshipBySponsor(final String sponsor, final int numSponsorships, final Class<?> expected) {

		Class<?> caught;
		caught = null;
		try {

			super.authenticate(sponsor);

			final Sponsor sp = this.sponsorService.findOne(this.actorService.getActorLogged().getId());

			final Collection<Sponsorship> sponsorships = this.sponsorshipService.findBySponsor(sp.getId());

			Assert.isTrue(sponsorships.size() == numSponsorships);

		} catch (final Exception e) {
			caught = e.getClass();
		}
		super.checkExceptions(expected, caught);
	}
}
