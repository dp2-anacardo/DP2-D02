
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
	private AdministratorService administratorService;


	/*
	 * Testing functional requirement : An administrator can launch a process that automatically de-activates the sponsorships whose credit cards have expired.
	 * Positive: An administrator launches the process successfully
	 * Negative: A member tries to launch the process
	 * Sentence coverage: 100%
	 * Data coverage: Not applicable
	 */

	@Test
	public void deactivateExpiredSponsorshipsDriver() {
		final Object testingData[][] = {
			{
				"admin1", null
			}, {
				"member1", IllegalArgumentException.class
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
