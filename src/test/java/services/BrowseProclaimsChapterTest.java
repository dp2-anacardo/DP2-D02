
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Proclaim;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class BrowseProclaimsChapterTest extends AbstractTest {

	@Autowired
	private ChapterService	chapterService;


	/*
	 * Testing functional requirement : requirement 14.2 (browse proclaims chapter)
	 * Positive:An actor who is not authenticated can see all proclaims of chapters
	 * Negative:An actor who is not authenticated can't see all proclaims of chapters
	 * Sentence coverage: 100%
	 * Data coverage: Not applicable
	 */

	@Test
	public void driver() {
		final Object testingData[][] = {
			{
				super.getEntityId("chapter1"), null
			}, {
				0, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateProclaimsChapter((int) testingData[i][0], (Class<?>) testingData[i][1]);
	}
	public void templateProclaimsChapter(final int chapterId, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			final Collection<Proclaim> c = this.chapterService.getProclaims(chapterId);
			Assert.isTrue(c.size() == 1);

		} catch (final Exception e) {
			caught = e.getClass();
		}
		super.checkExceptions(expected, caught);
	}
}
