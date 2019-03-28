
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Actor;
import domain.Brotherhood;
import security.LoginService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ListParadeTest extends AbstractTest {

	@Autowired
	private ParadeService		paradeService;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private BrotherhoodService	brotherhoodService;


	/*
	 * Testing functional requirement :List their parades
	 * Positive: A brotherhood lists its parades
	 * Negative: A member tries to list his parades
	 * Sentence coverage: 100%
	 * Data coverage: not applicable
	 */
	@Test
	public void listParadesDriver() {
		final Object testingData[][] = {
			{
				"brotherhood1", null
			}, {
				"member1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.listParadesTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	private void listParadesTemplate(final String string, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(string);
			final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
			final Brotherhood b = this.brotherhoodService.findOne(user.getId());
			this.paradeService.getParadesByBrotherhood(b);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

}
