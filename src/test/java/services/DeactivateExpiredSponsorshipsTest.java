
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class DeactivateExpiredSponsorshipsTest extends AbstractTest {

	@Autowired
	private AdministratorService	administratorService;


	@Test
	public void deactivateExpiredSponsorshipsDriver() {
		final Object testingData[][] = {
			{
				"admin1", null
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDeactivateSponsorships((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	public void templateDeactivateSponsorships(final String username, final Class<?> expected) {

		Class<?> caught;
		caught = null;
		try {

			super.authenticate(username);

			this.administratorService.desactivateExpiredSponsorships();

		} catch (final Exception e) {
			caught = e.getClass();
		}
		super.checkExceptions(expected, caught);
	}

}
