
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.DataBinder;

import utilities.AbstractTest;
import domain.Sponsor;
import forms.SponsorForm;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RegisterSponsorTest extends AbstractTest {

	@Autowired
	private SponsorService	sponsorService;


	@Test
	public void registerSponsorDriver() {
		final Object testingData[][] = {
			{
				"pruab1", "123456", "123456", "prueba1", "", "prueba1", "", "prueba@prueba.com", "", "", null
			}, {
				"prueba2", "123456", "123456", "prueba2", "", "prueba2", "", "prueba@prueba.com", "600102030", "", null
			}, {
				"", "123456", "123456", "prueba2", "", "prueba2", "", "prueba@prueba.com", "", "", ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateRegisterSponsor((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (String) testingData[i][9], (Class<?>) testingData[i][10]);
	}

	@Test
	public void editSponsorDriver() {
		final Object testingData[][] = {
			{
				"sponsor1", "newName", null
			}, {
				"sponsor1", "", ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEditSponsor((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	public void templateRegisterSponsor(final String username, final String password, final String confirmPassword, final String name, final String middleName, final String surname, final String photo, final String email, final String phoneNumber,
		final String address, final Class<?> expected) {

		Class<?> caught;
		caught = null;
		try {
			final SponsorForm sForm = new SponsorForm();

			final DataBinder binding = new DataBinder(new Sponsor());

			sForm.setName(name);
			sForm.setMiddleName(middleName);
			sForm.setSurname(surname);
			sForm.setUsername(username);
			sForm.setPassword(password);
			sForm.setConfirmPass(confirmPassword);
			sForm.setEmail(email);
			sForm.setAddress(address);
			sForm.setPhoneNumber(phoneNumber);
			sForm.setPhoto(photo);

			final Sponsor sponsor = this.sponsorService.reconstruct(sForm, binding.getBindingResult());

			this.sponsorService.save(sponsor);
			this.sponsorService.flush();

		} catch (final Exception e) {
			caught = e.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	public void templateEditSponsor(final String username, final String name, final Class<?> expected) {

		Class<?> caught;
		caught = null;
		try {

			super.authenticate(username);

			final DataBinder binding = new DataBinder(new Sponsor());

			final Sponsor sponsor = this.sponsorService.findByPrincipal();

			sponsor.setName(name);

			final Sponsor result = this.sponsorService.reconstruct(sponsor, binding.getBindingResult());

			this.sponsorService.save(result);
			this.sponsorService.flush();

		} catch (final Exception e) {
			caught = e.getClass();
		}
		super.checkExceptions(expected, caught);
	}

}
