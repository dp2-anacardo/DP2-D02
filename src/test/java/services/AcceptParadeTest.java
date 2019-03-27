
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Parade;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AcceptParadeTest extends AbstractTest {

	@Autowired
	private ParadeService	paradeService;


	@Test
	public void acceptParadeDriver() {
		final Object testingData[][] = {
			{
				"chapter2", "parade7", null
			}, {
				"chapter2", "parade9", IllegalArgumentException.class
			}, {
				"chapter2", "parade8", IllegalArgumentException.class
			}, {
				"chapter2", "parade4", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.acceptParadeTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	private void acceptParadeTemplate(final String username, final String parade, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);

			final int paradeId = super.getEntityId(parade);
			final Parade p = this.paradeService.findOne(paradeId);
			this.paradeService.acceptParade(p);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

}
