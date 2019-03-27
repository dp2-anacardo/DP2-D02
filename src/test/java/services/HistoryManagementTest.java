
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import datatype.Url;
import domain.Brotherhood;
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
public class HistoryManagementTest extends AbstractTest {

	@Autowired
	private BrotherhoodService		brotherhoodService;
	@Autowired
	private InceptionRecordService	inceptionRecordService;
	@Autowired
	private PeriodRecordService		periodRecordService;
	@Autowired
	private LegalRecordService		legalRecordService;
	@Autowired
	private LinkRecordService		linkRecordService;
	@Autowired
	private MiscRecordService		miscRecordService;


	@Test
	public void createRecordDriver() {
		final Object testingData[][] = {
			{
				756, 762, null
			}, {
				999, 998, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.createRecords((int) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	public void createRecords(final int bID1, final int bID2, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			final Brotherhood bh = this.brotherhoodService.findOne(bID1);

			super.authenticate("brotherhood1");

			LinkRecord newLiR = this.linkRecordService.create();
			newLiR.setTitle("Title");
			newLiR.setDescription("Description");
			newLiR.setLinkedBH(this.brotherhoodService.findOne(bID2));

			PeriodRecord newPR = this.periodRecordService.create();
			newPR.setTitle("Title");
			newPR.setDescription("Description");
			newPR.setStartYear(2000);
			newPR.setEndYear(2004);
			final Collection<Url> photos = new ArrayList<Url>();
			final Url photo = new Url();
			photo.setLink("https://photo");
			photos.add(photo);
			newPR.setPhoto(photos);

			LegalRecord newLR = this.legalRecordService.create();
			newLR.setTitle("Title");
			newLR.setDescription("Description");
			newLR.setLegalName("Nombre");
			newLR.setVatNumber(21);
			final Collection<String> laws = new ArrayList<String>();
			laws.add("law");
			newLR.setApplicableLaws(laws);

			MiscRecord newMR = this.miscRecordService.create();
			newMR.setTitle("title");
			newMR.setDescription("description");

			newMR = this.miscRecordService.save(newMR);
			newLR = this.legalRecordService.save(newLR);
			newLiR = this.linkRecordService.save(newLiR);
			newPR = this.periodRecordService.save(newPR);

			Assert.isTrue(this.miscRecordService.findAll().contains(newMR));
			Assert.isTrue(this.legalRecordService.findAll().contains(newLR));
			Assert.isTrue(this.linkRecordService.findAll().contains(newLiR));
			Assert.isTrue(this.periodRecordService.findAll().contains(newPR));

			Assert.isTrue(newMR.getBrotherhood().equals(bh));
			Assert.isTrue(newLR.getBrotherhood().equals(bh));
			Assert.isTrue(newLiR.getBrotherhood().equals(bh));
			Assert.isTrue(newPR.getBrotherhood().equals(bh));

			this.brotherhoodService.flush();
			this.inceptionRecordService.flush();
			this.miscRecordService.flush();
			this.periodRecordService.flush();
			this.legalRecordService.flush();
			this.linkRecordService.flush();

			super.unauthenticate();

		} catch (final Exception e) {
			caught = e.getClass();
		}
		super.checkExceptions(expected, caught);
	}
	@Test
	public void editRecordDriver() {
		final Object testingData[][] = {
			{
				756, 762, 820, null
			}, {
				999, 998, 997, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.editRecords((int) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (int) testingData[i][3], (int) testingData[i][4], (int) testingData[i][5], (int) testingData[i][6], (Class<?>) testingData[i][7]);
	}
	public void editRecords(final int bID1, final int bID2, final int iRID, final int pRID, final int lRID, final int liRID, final int mRID, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			final Brotherhood bh = this.brotherhoodService.findOne(bID1);

			super.authenticate("brotherhood1");

			MiscRecord newMR = this.miscRecordService.findOne(mRID);
			LegalRecord newLR = this.legalRecordService.findOne(lRID);
			LinkRecord newLiR = this.linkRecordService.findOne(liRID);
			PeriodRecord newPR = this.periodRecordService.findOne(pRID);
			InceptionRecord IR = this.inceptionRecordService.findOne(iRID);

			//Edit
			if (bh.equals(newMR.getBrotherhood())) {
				newMR.setTitle("new Title");
				newMR = this.miscRecordService.save(newMR);
			}

			Assert.isTrue(newMR.getTitle().equals("new Title"));

			if (bh.equals(newPR.getBrotherhood())) {
				newPR.setTitle("new Title");
				newPR = this.periodRecordService.save(newPR);
			}

			Assert.isTrue(newPR.getTitle().equals("new Title"));

			if (bh.equals(newLR.getBrotherhood())) {
				newLR.setTitle("new Title");
				newLR = this.legalRecordService.save(newLR);
			}

			Assert.isTrue(newLR.getTitle().equals("new Title"));

			if (bh.equals(newLiR.getBrotherhood())) {
				newLiR.setTitle("new Title");
				newLiR = this.linkRecordService.save(newLiR);
			}

			Assert.isTrue(newLiR.getTitle().equals("new Title"));

			if (bh.equals(IR.getBrotherhood())) {
				IR.setTitle("new Title");
				IR = this.inceptionRecordService.save(IR);
			}

			Assert.isTrue(IR.getTitle().equals("new Title"));

			this.brotherhoodService.flush();
			this.inceptionRecordService.flush();
			this.miscRecordService.flush();
			this.periodRecordService.flush();
			this.legalRecordService.flush();
			this.linkRecordService.flush();

			super.unauthenticate();

		} catch (final Exception e) {
			caught = e.getClass();
		}
		super.checkExceptions(expected, caught);
	}
	@Test
	public void deleteRecordDriver() {
		final Object testingData[][] = {
			{
				756, 762, 820, null
			}, {
				999, 998, 997, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.deleteRecords((int) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (int) testingData[i][3], (int) testingData[i][4], (int) testingData[i][5], (int) testingData[i][6], (Class<?>) testingData[i][7]);
	}
	public void deleteRecords(final int bID1, final int bID2, final int iRID, final int pRID, final int lRID, final int liRID, final int mRID, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			final Brotherhood bh = this.brotherhoodService.findOne(bID1);

			super.authenticate("brotherhood1");

			final MiscRecord newMR = this.miscRecordService.findOne(mRID);
			final LegalRecord newLR = this.legalRecordService.findOne(lRID);
			final LinkRecord newLiR = this.linkRecordService.findOne(liRID);
			final PeriodRecord newPR = this.periodRecordService.findOne(pRID);
			final InceptionRecord IR = this.inceptionRecordService.findOne(iRID);

			if (bh.equals(newMR.getBrotherhood()))
				this.miscRecordService.delete(newMR);

			Assert.isTrue(!this.miscRecordService.findAll().contains(newMR));

			if (bh.equals(newPR.getBrotherhood()))
				this.periodRecordService.delete(newPR);

			Assert.isTrue(!this.miscRecordService.findAll().contains(newPR));

			if (bh.equals(newLR.getBrotherhood()))
				this.legalRecordService.delete(newLR);

			Assert.isTrue(!this.miscRecordService.findAll().contains(newLR));

			if (bh.equals(newLiR.getBrotherhood()))
				this.linkRecordService.delete(newLiR);

			Assert.isTrue(!this.miscRecordService.findAll().contains(newLiR));

			if (bh.equals(IR.getBrotherhood()))
				this.inceptionRecordService.delete(IR);

			Assert.isTrue(!this.miscRecordService.findAll().contains(IR));

			this.brotherhoodService.flush();
			this.inceptionRecordService.flush();
			this.miscRecordService.flush();
			this.periodRecordService.flush();
			this.legalRecordService.flush();
			this.linkRecordService.flush();

			super.unauthenticate();

		} catch (final Exception e) {
			caught = e.getClass();
		}
		super.checkExceptions(expected, caught);
	}
}
