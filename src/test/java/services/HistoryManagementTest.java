
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Brotherhood;
import domain.InceptionRecord;
import domain.MiscRecord;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class HistoryManagementTest extends AbstractTest {

	@Autowired
	private BrotherhoodService		brotherhoodService;
	@Autowired
	private InceptionRecordService	inceptionRecordService;
	@Autowired
	private MiscRecordService		miscRecordService;


	@Test
	public void driver() {
		final Object testingData[][] = {
			{
				678, 736, 738, null
			}, {
				999, 998, 997, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateHistoryManagement((int) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	public void templateHistoryManagement(final int brotherhoodId, final int iRID, final int mRID, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			final Brotherhood bh = this.brotherhoodService.findOne(brotherhoodId);
			final InceptionRecord iR = this.inceptionRecordService.findOne(iRID);
			final MiscRecord mR = this.miscRecordService.findOne(mRID);

			Assert.isTrue(this.brotherhoodService.findAll().contains(bh));
			Assert.isTrue(this.inceptionRecordService.findAll().contains(iR));
			Assert.isTrue(this.miscRecordService.findAll().contains(mR));

			Assert.notNull(bh);
			Assert.notNull(iR);
			Assert.notNull(mR);

			//Create
			super.authenticate("brotherhood1");

			MiscRecord newMR = this.miscRecordService.create();
			newMR.setTitle("title");
			newMR.setDescription("description");

			newMR = this.miscRecordService.save(newMR);

			Assert.isTrue(this.miscRecordService.findAll().contains(newMR));
			Assert.isTrue(newMR.getBrotherhood().equals(bh));

			//Edit
			if (bh.equals(newMR.getBrotherhood())) {
				newMR.setTitle("new Title");
				newMR = this.miscRecordService.save(newMR);
			}

			Assert.isTrue(newMR.getTitle().equals("new Title"));

			//Delete
			if (bh.equals(newMR.getBrotherhood()))
				this.miscRecordService.delete(newMR);

			Assert.isTrue(!this.miscRecordService.findAll().contains(newMR));

			this.brotherhoodService.flush();
			this.inceptionRecordService.flush();
			this.miscRecordService.flush();

			super.unauthenticate();

		} catch (final Exception e) {
			caught = e.getClass();
		}
		super.checkExceptions(expected, caught);
	}
}
