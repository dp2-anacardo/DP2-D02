
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Parade;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class RejectParadeTest extends AbstractTest {

	@Autowired
	private ParadeService paradeService;


	//In this test we are testing the requirement 7.2.2(rejected parades).
	//In the negative cases we are testing that accepted parades, rejected parades
	//and parades that are co-ordinated by another chapter can not be rejected.
	//Also we are testing that the reject comment that the chapter must write can not
	//be blank.
	//Sequence coverage: 100%
	//Data coverage: 100%

	@Test
	public void rejectParadeDriver() {
		final Object testingData[][] = {
			{
				"chapter2", "parade7", "rejectComment", null
			}, {
				"chapter2", "parade7", "", IllegalArgumentException.class
			}, {
				"chapter2", "parade9", "rejectComment", IllegalArgumentException.class
			}, {
				"chapter2", "parade8", "rejectComment", IllegalArgumentException.class
			}, {
				"chapter2", "parade4", "rejectComment", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.rejectParadeTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	private void rejectParadeTemplate(final String username, final String parade, final String rejectComment, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);

			final int paradeId = super.getEntityId(parade);
			final Parade p = this.paradeService.findOne(paradeId);
			p.setRejectComment(rejectComment);
			this.paradeService.rejectParade(p);
			this.paradeService.saveChapter(p);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

}
