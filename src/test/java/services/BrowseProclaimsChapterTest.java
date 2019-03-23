
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


	@Test
	public void driver() {
		final Object testingData[][] = {
			{
				699, null
			}, {
				999, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateProclaimsChapter((int) testingData[i][0], (Class<?>) testingData[i][1]);
	}
	public void templateProclaimsChapter(final int chapterId, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			Assert.isTrue(this.chapterService.findAll().contains(this.chapterService.findOne(chapterId)));
			final Collection<Proclaim> c = this.chapterService.getProclaims(chapterId);
			Assert.notNull(c);
			Assert.isTrue(c.size() == 1);

		} catch (final Exception e) {
			caught = e.getClass();
		}
		super.checkExceptions(expected, caught);
	}
}
