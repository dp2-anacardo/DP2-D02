
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CopyParadeTest extends AbstractTest {

	@Autowired
	private ParadeService paradeService;


	@Test
	public void copyParadeDriver() {
		final Object testingData[][] = {
			{
				"brotherhood1", "parade1", null
			}, {
				"member1", "parade1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.copyParadeTemplate((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}

	private void copyParadeTemplate(final String string, final int paradeId, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(string);
			this.paradeService.copyParade(paradeId);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

}
