
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Sponsor;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RegisterSponsorTest extends AbstractTest {

	@Autowired
	private SponsorService	sponsorService;


	@Test
	public void driver() {
		final Object testingData[][] = {
			{
				"prueba2", "123456", "123456", "prueba2", "", "prueba2", "", "prueba@prueba.com", "", "", null
			}, {
				"", "123456", "123456", "prueba2", "", "prueba2", "", "prueba@prueba.com", "", "", ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateRegisterSponsor((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (String) testingData[i][9], (Class<?>) testingData[i][10]);
	}

	public void templateRegisterSponsor(final String username, final String password, final String confirmPassword, final String name, final String middleName, final String surname, final String photo, final String email, final String phoneNumber,
		final String address, final Class<?> expected) {

		Class<?> caught;
		caught = null;
		try {
			final Sponsor c = this.sponsorService.create();

			c.getUserAccount().setUsername(username);
			c.getUserAccount().setPassword(password);
			c.setName(name);
			c.setSurname(surname);
			c.setMiddleName(middleName);
			c.setPhoneNumber(phoneNumber);
			c.setPhoto(photo);
			c.setEmail(email);
			c.setAddress(address);

			this.sponsorService.save(c);
			this.sponsorService.flush();

		} catch (final Exception e) {
			caught = e.getClass();
		}
		super.checkExceptions(expected, caught);
	}
}