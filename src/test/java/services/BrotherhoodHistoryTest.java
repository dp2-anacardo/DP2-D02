
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
import domain.InceptionRecord;
import domain.MiscRecord;
import domain.PeriodRecord;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class BrotherhoodHistoryTest extends AbstractTest {

	@Autowired
	private BrotherhoodService		brotherhoodService;
	@Autowired
	private InceptionRecordService	inceptionRecordService;
	@Autowired
	private MiscRecordService		miscRecordService;
	@Autowired
	private PeriodRecordService		periodRecordService;
	@Autowired
	private ChapterService			chapterService;
	@Autowired
	private LinkRecordService		linkRecordService;
	@Autowired
	private LegalRecordService		legalRecordService;


	@Test
	public void driver() {
		final Object testingData[][] = {
			{
				super.getEntityId("brotherhood1"), null
			}, {
				999, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateBrotherhoodHistory((int) testingData[i][0], (Class<?>) testingData[i][1]);
	}
	public void templateBrotherhoodHistory(final int brotherhoodId, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			Assert.isTrue(this.brotherhoodService.findAll().contains(this.brotherhoodService.findOne(brotherhoodId)));
			final Collection<InceptionRecord> i = this.inceptionRecordService.getInceptionRecordByBrotherhood(brotherhoodId);
			Assert.notNull(i);
			Assert.isTrue(i.size() == 1);
			final Collection<MiscRecord> m = this.miscRecordService.getMiscRecordByBrotherhood(brotherhoodId);
			Assert.notNull(m);
			Assert.isTrue(m.size() == 1);
			final Collection<PeriodRecord> p = this.periodRecordService.getPeriodRecordByBrotherhood(brotherhoodId);
			Assert.notNull(p);
			Assert.isTrue(p.size() == 1);
			//Assert.isNull(this.legalRecordService.getLegalRecordByBrotherhood(brotherhoodId));
			//			Assert.isTrue(this.linkRecordService.getLinkRecordByBrotherhood(brotherhoodId) == null);

			this.chapterService.flush();

		} catch (final Exception e) {
			caught = e.getClass();
		}
		super.checkExceptions(expected, caught);
	}
}
