
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


	/*
	 * Testing functional requirement: Edit brotherhood's history
	 * Positive: A brotherhood edit their parades
	 * Negative: A member tries to list his parades
	 * Sentence coverage: 100%
	 * Data coverage: 100%
	 */
	@Test
	public void editRecordDriver() {
		final Object testingData[][] = {
			{
				"brotherhood1", "inceptionRecord1", "periodRecord1", "legalRecord1", "linkRecord1", "miscRecord1", null
			}, {
				"member1", "member1", "member1", "member1", "member1", "member1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.editRecords((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), super.getEntityId((String) testingData[i][2]), super.getEntityId((String) testingData[i][3]), super.getEntityId((String) testingData[i][4]),
				super.getEntityId((String) testingData[i][5]), (Class<?>) testingData[i][6]);
	}
	public void editRecords(final String bID1, final int iRID, final int pRID, final int lRID, final int liRID, final int mRID, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			final Brotherhood bh = this.brotherhoodService.findOne(super.getEntityId(bID1));

			super.authenticate(bID1);

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
				"brotherhood1", "brotherhood2", "periodRecord1", "legalRecord1", "linkRecord1", "miscRecord1", null
			}, {
				"member1", "member1", "member1", "member1", "member1", "member1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.deleteRecords(super.getEntityId((String) testingData[i][0]), super.getEntityId((String) testingData[i][1]), super.getEntityId((String) testingData[i][2]), super.getEntityId((String) testingData[i][3]),
				super.getEntityId((String) testingData[i][4]), super.getEntityId((String) testingData[i][5]), (Class<?>) testingData[i][6]);
	}
	public void deleteRecords(final int bID1, final int bID2, final int pRID, final int lRID, final int liRID, final int mRID, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			final Brotherhood bh = this.brotherhoodService.findOne(bID1);

			super.authenticate("brotherhood1");

			final MiscRecord newMR = this.miscRecordService.findOne(mRID);
			final LegalRecord newLR = this.legalRecordService.findOne(lRID);
			final LinkRecord newLiR = this.linkRecordService.findOne(liRID);
			final PeriodRecord newPR = this.periodRecordService.findOne(pRID);

			if (bh.equals(newMR.getBrotherhood()))
				this.miscRecordService.delete(newMR);

			Assert.isTrue(!this.miscRecordService.findAll().contains(newMR));

			if (bh.equals(newPR.getBrotherhood()))
				this.periodRecordService.delete(newPR);

			Assert.isTrue(!this.periodRecordService.findAll().contains(newPR));

			if (bh.equals(newLR.getBrotherhood()))
				this.legalRecordService.delete(newLR);

			Assert.isTrue(!this.legalRecordService.findAll().contains(newLR));

			if (bh.equals(newLiR.getBrotherhood()))
				this.linkRecordService.delete(newLiR);

			Assert.isTrue(!this.linkRecordService.findAll().contains(newLiR));

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
	public void createRecordDriver() {
		final Object testingData[][] = {
			{
				"brotherhood1", "brotherhood2", null
			}, {
				"member1", "member1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.createRecords((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	public void createRecords(final String bID1, final String bID2, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			super.authenticate(bID1);

			LinkRecord newLiR = this.linkRecordService.create();
			newLiR.setTitle("Title");
			newLiR.setDescription("Description");
			newLiR.setLinkedBH(this.brotherhoodService.findOne(super.getEntityId(bID2)));

			newLiR = this.linkRecordService.save(newLiR);

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

			newPR = this.periodRecordService.save(newPR);

			LegalRecord newLR = this.legalRecordService.create();
			newLR.setTitle("Title");
			newLR.setDescription("Description");
			newLR.setLegalName("Nombre");
			newLR.setVatNumber(21);
			final Collection<String> laws = new ArrayList<String>();
			laws.add("law");
			newLR.setApplicableLaws(laws);

			newLR = this.legalRecordService.save(newLR);

			MiscRecord newMR = this.miscRecordService.create();
			newMR.setTitle("title");
			newMR.setDescription("description");

			newMR = this.miscRecordService.save(newMR);

			super.unauthenticate();

		} catch (final Exception e) {
			caught = e.getClass();
		}
		super.checkExceptions(expected, caught);
	}
}
