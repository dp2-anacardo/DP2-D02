
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Chapter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AutoAssignateAreaTest extends AbstractTest {

	@Autowired
	private ChapterService	chapterService;


	//In this test we are testing the requirement 7.2.1(self-assign area).
	//In the negative case we are testing that a chapter without an area can not
	//self-assign an area assigned to another chapter.

	@Test
	public void autoAssignateAreaDriver() {
		final Object testingData[][] = {
			{
				"chapter1", "areaBrotherhood1", null
			}, {
				"chapter1", "area2", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.autoAssignateAreaTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	private void autoAssignateAreaTemplate(final String username, final String area, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);

			final int chapterId = super.getEntityId(username);
			final Chapter chapter = this.chapterService.findOne(chapterId);
			final int areaId = super.getEntityId(area);

			this.chapterService.autoAssignateArea(chapter, areaId);
			this.chapterService.save(chapter);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

}
