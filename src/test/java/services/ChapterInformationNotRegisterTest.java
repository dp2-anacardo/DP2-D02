
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Area;
import domain.Brotherhood;
import domain.Chapter;
import domain.Parade;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ChapterInformationNotRegisterTest extends AbstractTest {

	@Autowired
	private ChapterService	chapterService;
	@Autowired
	private AreaService		areaService;
	@Autowired
	private ParadeService	paradeService;


	//In this test we are testing the requirement 14.1 (chapter information not register).
	//DriverListChapter does not negative cases
	//Sentence coverage: 100%
	//Data coverage: Not applicable

	@Test
	public void driverListChapter() {
		final Object testingData[][] = {
			{
				null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateListChapter((Class<?>) testingData[i][0]);
	}
	public void templateListChapter(final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			final Collection<Chapter> c = this.chapterService.findAll();
			Assert.isTrue(c.size() == 2);

		} catch (final Exception e) {
			caught = e.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	//In this test we are testing the requirement 14.1 (chapter information not register).
	//In the negative test we are testing listing area and brotherhoods
	//Sentence coverage: 100%
	//Data coverage: Not applicable

	@Test
	public void driverListArea() {
		final Object testingData[][] = {
			{
				super.getEntityId("areaBrotherhood1"), null
			}, {
				0, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateListArea((int) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	public void templateListArea(final int areaId, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			final Collection<Area> a = this.areaService.findAll();
			final Collection<Brotherhood> b = this.areaService.getBrotherhood(areaId);
			Assert.isTrue(a.size() == 3);
			Assert.isTrue(b.size() == 1);
		} catch (final Exception e) {
			caught = e.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	//In this test we are testing the requirement 14.1 (chapter information not register).
	//In the negative test we are testing listing parades.
	//Sentence coverage: 100%
	//Data coverage: Not applicable

	@Test
	public void driverListParade() {
		final Object testingData[][] = {
			{
				super.getEntityId("brotherhood1"), null
			}, {
				0, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateListParade((int) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	public void templateListParade(final int brotherhoodId, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			final Collection<Parade> p = this.paradeService.getParadesFinalByBrotherhood(brotherhoodId);
			Assert.isTrue(p.size() == 1);

		} catch (final Exception e) {
			caught = e.getClass();
		}
		super.checkExceptions(expected, caught);
	}
}
