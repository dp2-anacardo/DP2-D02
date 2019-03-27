
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
import domain.LegalRecord;
import domain.LinkRecord;
import domain.MiscRecord;
import domain.PeriodRecord;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class BrotherhoodHistoryTest extends AbstractTest {

	@Autowired
	private InceptionRecordService	inceptionRecordService;
	@Autowired
	private MiscRecordService		miscRecordService;
	@Autowired
	private PeriodRecordService		periodRecordService;
	@Autowired
	private LinkRecordService		linkRecordService;
	@Autowired
	private LegalRecordService		legalRecordService;


	@Test
	public void driverListInceptionRecord() {
		final Object testingData[][] = {
			{
				super.getEntityId("brotherhood1"), null
			}, {
				999, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateListInceptionRecord((int) testingData[i][0], (Class<?>) testingData[i][1]);
	}
	public void templateListInceptionRecord(final int brotherhoodId, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			final Collection<InceptionRecord> i = this.inceptionRecordService.getInceptionRecordByBrotherhood(brotherhoodId);
			Assert.isTrue(i.size() == 1);

		} catch (final Exception e) {
			caught = e.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	@Test
	public void driverListMiscRecord() {
		final Object testingData[][] = {
			{
				super.getEntityId("brotherhood1"), null
			}, {
				999, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateListMiscRecord((int) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	public void templateListMiscRecord(final int brotherhoodId, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			final Collection<MiscRecord> m = this.miscRecordService.getMiscRecordByBrotherhood(brotherhoodId);
			Assert.isTrue(m.size() == 1);

		} catch (final Exception e) {
			caught = e.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	@Test
	public void driverListPeriodRecord() {
		final Object testingData[][] = {
			{
				super.getEntityId("brotherhood1"), null
			}, {
				999, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateListPeriodRecord((int) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	public void templateListPeriodRecord(final int brotherhoodId, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			final Collection<PeriodRecord> p = this.periodRecordService.getPeriodRecordByBrotherhood(brotherhoodId);
			Assert.isTrue(p.size() == 1);

			//Assert.isNull(this.legalRecordService.getLegalRecordByBrotherhood(brotherhoodId));
			//			Assert.isTrue(this.linkRecordService.getLinkRecordByBrotherhood(brotherhoodId) == null);

		} catch (final Exception e) {
			caught = e.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	@Test
	public void driverListLegalRecord() {
		final Object testingData[][] = {
			{
				super.getEntityId("brotherhood1"), null
			}, {
				999, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateListLegalRecord((int) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	public void templateListLegalRecord(final int brotherhoodId, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			final Collection<LegalRecord> f = this.legalRecordService.getLegalRecordByBrotherhood(brotherhoodId);
			Assert.isTrue(f.size() == 1);
			//			Assert.isTrue(this.linkRecordService.getLinkRecordByBrotherhood(brotherhoodId) == null);

		} catch (final Exception e) {
			caught = e.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	@Test
	public void driverListLinkRecord() {
		final Object testingData[][] = {
			{
				super.getEntityId("brotherhood1"), null
			}, {
				999, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateListLinkRecord((int) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	public void templateListLinkRecord(final int brotherhoodId, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			final Collection<LinkRecord> l = this.linkRecordService.getLinkRecordByBrotherhood(brotherhoodId);
			Assert.isTrue(l.size() == 1);

		} catch (final Exception e) {
			caught = e.getClass();
		}
		super.checkExceptions(expected, caught);
	}
}
