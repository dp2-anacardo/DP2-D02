
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.jsoup.select.Evaluator.Class;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.LinkRecord;
import domain.MiscRecord;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class HistoryManagementTest extends AbstractTest {

	@Autowired
	private MiscRecordService	miscRecordService;
	@Autowired
	private LinkRecordService	linkRecordService;


	/*
	 * For every test in this class we are testing : 3. An actor who is authenticated as a brotherhood must be able to: 1. Manage their history
	 * Positive: A brotherhood creates a miscRecord
	 * Negative: A brotherhood tries to create a miscRecord with invalid data //// A member tries to create a miscRecord
	 * Sentence coverage: 100%
	 * Data coverage: 100%
	 */
	@Test
	public void createMiscRecordDriver() {
		final Object testingData[][] = {
			{
				"title", "description", "brotherhood1", null
			}, {
				"", "description", "brotherhood1", ConstraintViolationException.class
			}, {
				"title", "", "brotherhood1", ConstraintViolationException.class
			}, {
				"title", "description", "member1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.createMiscRecordTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	private void createMiscRecordTemplate(final String string, final String string2, final String brotherhood, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(brotherhood);
			final MiscRecord m = this.miscRecordService.create();
			m.setTitle(string);
			m.setDescription(string2);
			this.miscRecordService.save(m);
			this.miscRecordService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}/*
		 * Positive: A brotherhood edits a miscRecord
		 * Negative: A brotherhood tries to edit a miscRecord with invalid data
		 * Sentence coverage: 100%
		 * Data coverage: 100%
		 */
	@Test
	public void editMiscRecordDriver() {
		final Object testingData[][] = {
			{
				"titlePruebita", "description", "brotherhood1", null, "miscRecord1"
			}, {
				"", "description", "brotherhood1", ConstraintViolationException.class, "miscRecord1"
			}, {
				"title", "", "brotherhood1", ConstraintViolationException.class, "miscRecord1"
			}, {
				"title", "descriptionPruebita", "brotherhood1", null, "miscRecord1"
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.editMiscRecordTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3], super.getEntityId((String) testingData[i][4]));
	}
	private void editMiscRecordTemplate(final String string, final String string2, final String brotherhood, final Class<?> expected, final int entityId) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(brotherhood);
			final MiscRecord m = this.miscRecordService.findOne(entityId);
			m.setTitle(string);
			m.setDescription(string2);
			this.miscRecordService.save(m);
			this.miscRecordService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}
	/*
	 * Positive: A brotherhood deletes a miscRecord
	 * Sentence coverage: 96,2%
	 * Data coverage: not applicable
	 */
	@Test
	public void deleteMiscRecordDriver() {
		final Object testingData[][] = {
			{
				"miscRecord1", "brotherhood1", null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.deleteMiscRecordTemplate(super.getEntityId((String) testingData[i][0]), (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	private void deleteMiscRecordTemplate(final int entityId, final String string, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(string);
			final MiscRecord m = this.miscRecordService.findOne(entityId);
			this.miscRecordService.delete(m);
			this.miscRecordService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}
	/*
	 * Positive: A brotherhood edits a link record
	 * Sentence coverage: 76%
	 * Data coverage: 81,1%
	 */
	@Test
	public void editLinkRecordDriver() {
		final Object testingData[][] = {
			{
				"linkRecord1", "brotherhood1", null, "titulitoDePruebita"
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.editLinkRecordTemplate(super.getEntityId((String) testingData[i][0]), (String) testingData[i][1], (Class<?>) testingData[i][2], (String) testingData[i][3]);
	}
	private void editLinkRecordTemplate(final int entityId, final String string, final Class<?> expected, final String string2) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(string);
			final LinkRecord l = this.linkRecordService.findOne(entityId);
			l.setTitle(string2);
			this.linkRecordService.save(l);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

}
