
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Segment;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SegmentServiceTest extends AbstractTest {

	@Autowired
	private SegmentService	segmentService;
	@Autowired
	private ParadeService	paradeService;


	@Test
	public void createSegmentDriver() {
		final Object testingData[][] = {
			{
				1.0, 1.0, 1.0, 1.0, "12:05", "12:10", "parade1", "brotherhood1", null
			}, {
				3.0, 1.0, 1.0, 1.0, "12:05", "12:10", "parade1", "brotherhood1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.createSegmentTemplate((Double) testingData[i][0], (Double) testingData[i][1], (Double) testingData[i][2], (Double) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], super.getEntityId((String) testingData[i][6]),
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	private void createSegmentTemplate(final Double oLa, final Double oLo, final Double dLa, final Double dLo, final String oT, final String dT, final int paradeId, final String brotherhood, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(brotherhood);
			final Segment s = this.segmentService.create(this.paradeService.findOne(paradeId));
			s.setOriginLatitude(oLa);
			s.setOriginLongitude(oLo);
			s.setDestinationLatitude(dLa);
			s.setDestinationLongitude(dLo);
			s.setTimeOrigin(oT);
			s.setTimeDestination(dT);
			this.segmentService.save(s);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}
	@Test
	public void editSegmentDriver() {
		final Object testingData[][] = {
			{
				1.0, 1.0, 1.0, 1.0, "12:05", "12:10", "parade1", "brotherhood1", null, "segment1"
			}, {
				999.0, 1.0, 1.0, 1.0, "12:05", "12:10", "parade1", "member1", IllegalArgumentException.class, "segment1"
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.editSegmentTemplate((Double) testingData[i][0], (Double) testingData[i][1], (Double) testingData[i][2], (Double) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], super.getEntityId((String) testingData[i][6]),
				(String) testingData[i][7], (Class<?>) testingData[i][8], super.getEntityId((String) testingData[i][9]));
	}

	private void editSegmentTemplate(final Double oLa, final Double oLo, final Double dLa, final Double dLo, final String oT, final String dT, final int paradeId, final String brotherhood, final Class<?> expected, final int segmentId) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(brotherhood);
			final Segment s = this.segmentService.findOne(segmentId);
			s.setOriginLatitude(oLa);
			s.setOriginLongitude(oLo);
			s.setDestinationLatitude(dLa);
			s.setDestinationLongitude(dLo);
			s.setTimeOrigin(oT);
			s.setTimeDestination(dT);
			this.segmentService.save(s);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}
	@Test
	public void deleteSegmentDriver() {
		final Object testingData[][] = {
			{
				"segment1", "brotherhood1", null
			}, {
				"segment1", "brotherhood2", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.deleteSegmentTemplate(super.getEntityId((String) testingData[i][0]), (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	private void deleteSegmentTemplate(final int segmentId, final String brotherhood, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(brotherhood);
			final Segment s = this.segmentService.findOne(segmentId);
			this.segmentService.delete(s);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}
	@Test
	public void listSegmentDriver() {
		final Object testingData[][] = {
			{
				"brotherhood1", "parade1", null
			}, {
				"member1", "parade1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.listSegmentTemplate((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}

	private void listSegmentTemplate(final String string, final int paradeId, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(string);
			this.segmentService.getPathByParade(paradeId);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

}
