
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
import domain.Brotherhood;
import domain.Chapter;
import domain.Parade;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ChapterInformationNotRegisterTest extends AbstractTest {

	@Autowired
	private ChapterService		chapterService;
	@Autowired
	private AreaService			areaService;
	@Autowired
	private ParadeService		paradeService;
	@Autowired
	private BrotherhoodService	brotherhoodService;


	@Test
	public void driver() {
		final Object testingData[][] = {
			{
				699, 644, 638, null
			}, {
				999, 998, 997, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateChapterInformation((int) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	public void templateChapterInformation(final int chapterId, final int areaId, final int brotherhoodId, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			Assert.isTrue(this.chapterService.findAll().contains(this.chapterService.findOne(chapterId)));
			Assert.isTrue(this.areaService.findAll().contains(this.areaService.findOne(areaId)));
			Assert.isTrue(this.brotherhoodService.findAll().contains(this.brotherhoodService.findOne(brotherhoodId)));
			final Collection<Chapter> c = this.chapterService.findAll();
			Assert.notNull(c);
			Assert.isTrue(c.size() == 1);
			Assert.isNull(this.chapterService.findOne(chapterId).getArea());
			final Collection<Brotherhood> b = this.areaService.getBrotherhood(areaId);
			Assert.notNull(b);
			Assert.isTrue(b.size() == 1);
			final Collection<Parade> p = this.paradeService.getParadesFinalByBrotherhood(brotherhoodId);
			Assert.notNull(p);
			Assert.isTrue(p.size() == 15);

			this.chapterService.flush();

		} catch (final Exception e) {
			caught = e.getClass();
		}
		super.checkExceptions(expected, caught);
	}
}
