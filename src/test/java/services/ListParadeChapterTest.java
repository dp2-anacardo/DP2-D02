
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Actor;
import domain.Chapter;
import security.LoginService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ListParadeChapterTest extends AbstractTest {

	@Autowired
	private ChapterService	chapterService;

	@Autowired
	private ActorService	actorService;


	//In this test we are testing the requirement 7.2.2(list parades).
	//In the negative case we are testing that an user not authenticated as a chapter
	//In this case, member can not manage the parades of a chapter.
	//Sequence coverage: 100%
	//Data coverage: Not applicable

	@Test
	public void listParadeChapterDriver() {
		final Object testingData[][] = {
			{
				"chapter2", null
			}, {
				"member1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.listParadeChapterTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	private void listParadeChapterTemplate(final String string, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(string);
			final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
			final Chapter chapter = this.chapterService.findOne(user.getId());
			this.chapterService.getParadesByArea(chapter);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

}
