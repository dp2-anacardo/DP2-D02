
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AdministratorDashboardTest extends AbstractTest {

	@Autowired
	private AdministratorService administatorService;


	@Test
	public void DashboardDriver1() {
		final Object testingData[][] = {
			{
				"admin1", 2.5, null
			}, {
				"member1", 2.5, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.DashboardTemplate1((String) testingData[i][0], (Double) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void DashboardDriver2() {
		final Object testingData[][] = {
			{
				"admin1", 5.0, null
			}, {
				"member1", 5.0, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.DashboardTemplate2((String) testingData[i][0], (Double) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void DashboardDriver3() {
		final Object testingData[][] = {
			{
				"admin1", 1.0, null
			}, {
				"member1", 1.0, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.DashboardTemplate3((String) testingData[i][0], (Double) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void DashboardDriver4() {
		final Object testingData[][] = {
			{
				"admin1", 0.0, null
			}, {
				"member1", 0.0, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.DashboardTemplate4((String) testingData[i][0], (Double) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void DashboardDriver5() {
		final Object testingData[][] = {
			{
				"admin1", "brotherhood1", null
			}, {
				"member1", "brotherhood1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.DashboardTemplate5((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void DashboardDriver6() {
		final Object testingData[][] = {
			{
				"admin1", 1, null
			}, {
				"member1", 1, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.DashboardTemplate6((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void DashboardDriver7() {
		final Object testingData[][] = {
			{
				"admin1", 33.33333, null
			}, {
				"member1", 33.33333, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.DashboardTemplate7((String) testingData[i][0], (Double) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void DashboardDriver8() {
		final Object testingData[][] = {
			{
				"admin1", 2.0, null
			}, {
				"member1", 2.0, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.DashboardTemplate8((String) testingData[i][0], (Double) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void DashboardDriver9() {
		final Object testingData[][] = {
			{
				"admin1", 0.0, null
			}, {
				"member1", 0.0, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.DashboardTemplate9((String) testingData[i][0], (Double) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void DashboardDriver10() {
		final Object testingData[][] = {
			{
				"admin1", 4.0, null
			}, {
				"member1", 4.0, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.DashboardTemplate10((String) testingData[i][0], (Double) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void DashboardDriver11() {
		final Object testingData[][] = {
			{
				"admin1", 2.0, null
			}, {
				"member1", 2.0, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.DashboardTemplate11((String) testingData[i][0], (Double) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void DashboardDriver12() {
		final Object testingData[][] = {
			{
				"admin1", 1, null
			}, {
				"member1", 1, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.DashboardTemplate12((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void DashboardDriver13() {
		final Object testingData[][] = {
			{
				"admin1", 6.66667, null
			}, {
				"member1", 6.66667, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.DashboardTemplate13((String) testingData[i][0], (Double) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void DashboardDriver14() {
		final Object testingData[][] = {
			{
				"admin1", 13.33333, null
			}, {
				"member1", 13.33333, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.DashboardTemplate14((String) testingData[i][0], (Double) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void DashboardDriver15() {
		final Object testingData[][] = {
			{
				"admin1", 73.33333, null
			}, {
				"member1", 73.33333, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.DashboardTemplate15((String) testingData[i][0], (Double) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void DashboardDriver16() {
		final Object testingData[][] = {
			{
				"admin1", 13.33333, null
			}, {
				"member1", 13.33333, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.DashboardTemplate16((String) testingData[i][0], (Double) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void DashboardDriver17() {
		final Object testingData[][] = {
			{
				"admin1", 75.0, null
			}, {
				"member1", 75.0, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.DashboardTemplate17((String) testingData[i][0], (Double) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void DashboardDriver18() {
		final Object testingData[][] = {
			{
				"admin1", 1.5, null
			}, {
				"member1", 1.5, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.DashboardTemplate18((String) testingData[i][0], (Double) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void DashboardDriver19() {
		final Object testingData[][] = {
			{
				"admin1", 1.0, null
			}, {
				"member1", 1.0, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.DashboardTemplate19((String) testingData[i][0], (Double) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void DashboardDriver20() {
		final Object testingData[][] = {
			{
				"admin1", 2.0, null
			}, {
				"member1", 2.0, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.DashboardTemplate20((String) testingData[i][0], (Double) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void DashboardDriver21() {
		final Object testingData[][] = {
			{
				"admin1", 0.5, null
			}, {
				"member1", 0.5, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.DashboardTemplate21((String) testingData[i][0], (Double) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void DashboardDriver22() {
		final Object testingData[][] = {
			{
				"admin1", 2, null
			}, {
				"member1", 2, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.DashboardTemplate22((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	private void DashboardTemplate1(final String string, final Double d, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(string);
			final Double res = this.administatorService.getAvgRecordsPerHistory();
			Assert.isTrue(d.equals(res));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

	private void DashboardTemplate2(final String string, final Double d, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(string);
			final Double res = this.administatorService.getMaxRecordsPerHistory();
			Assert.isTrue(d.equals(res));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

	private void DashboardTemplate3(final String string, final Double d, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(string);
			final Double res = this.administatorService.getMinRecordsPerHistory();
			Assert.isTrue(d.equals(res));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

	private void DashboardTemplate4(final String string, final Double d, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(string);
			final Double res = this.administatorService.getStddevRecordsPerHistory();
			Assert.isTrue(d.equals(res));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

	private void DashboardTemplate5(final String string, final String d, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(string);
			final String res = this.administatorService.getBrotherhoodWithLargestHistory();
			Assert.isTrue(d.equals(res));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

	private void DashboardTemplate6(final String string, final Integer d, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(string);
			final Integer res = this.administatorService.getBrotherhoodHistoryLargerThanAvg().size();
			Assert.isTrue(d == res);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

	private void DashboardTemplate7(final String string, final Double d, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(string);
			final Double res = this.administatorService.getRatioAreaNotCoordinatesByChapter();
			Assert.isTrue(d.equals(res));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

	private void DashboardTemplate8(final String string, final Double d, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(string);
			final Double res = this.administatorService.getAvgParadesCoordinatesByChapters();
			Assert.isTrue(d.equals(res));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

	private void DashboardTemplate9(final String string, final Double d, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(string);
			final Double res = this.administatorService.getMinParadesCoordinatesByChapters();
			Assert.isTrue(d.equals(res));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

	private void DashboardTemplate10(final String string, final Double d, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(string);
			final Double res = this.administatorService.getMaxParadesCoordinatesByChapters();
			Assert.isTrue(d.equals(res));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

	private void DashboardTemplate11(final String string, final Double d, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(string);
			final Double res = this.administatorService.getStddevParadesCoordinatesByChapters();
			Assert.isTrue(d.equals(res));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

	private void DashboardTemplate12(final String string, final Integer d, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(string);
			final Integer res = this.administatorService.getChaptersCoordinate10MoreParadesThanAvg().size();
			Assert.isTrue(d == res);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

	private void DashboardTemplate13(final String string, final Double d, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(string);
			final Double res = this.administatorService.getRatioParadeDraftVsFinal();
			Assert.isTrue(d.equals(res));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

	private void DashboardTemplate14(final String string, final Double d, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(string);
			final Double res = this.administatorService.getRatioParadeFinalModeAccepted();
			Assert.isTrue(d.equals(res));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

	private void DashboardTemplate15(final String string, final Double d, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(string);
			final Double res = this.administatorService.getRatioParadeFinalModeSubmitted();
			Assert.isTrue(d.equals(res));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

	private void DashboardTemplate16(final String string, final Double d, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(string);
			final Double res = this.administatorService.getRatioParadeFinalModeRejected();
			Assert.isTrue(d.equals(res));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

	private void DashboardTemplate17(final String string, final Double d, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(string);
			final Double res = this.administatorService.getRatioActiveSponsorships();
			Assert.isTrue(d.equals(res));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

	private void DashboardTemplate18(final String string, final Double d, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(string);
			final Double res = this.administatorService.getAvgSponsorshipsPerSponsor();
			Assert.isTrue(d.equals(res));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

	private void DashboardTemplate19(final String string, final Double d, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(string);
			final Double res = this.administatorService.getMinSponsorshipsActivesPerSponsor();
			Assert.isTrue(d.equals(res));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

	private void DashboardTemplate20(final String string, final Double d, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(string);
			final Double res = this.administatorService.getMaxSponsorshipsActivesPerSponsor();
			Assert.isTrue(d.equals(res));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

	private void DashboardTemplate21(final String string, final Double d, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(string);
			final Double res = this.administatorService.getStddevSponsorshipsActivesPerSponsor();
			Assert.isTrue(d.equals(res));

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

	private void DashboardTemplate22(final String string, final Integer d, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(string);
			final Integer res = this.administatorService.getTop5SponsorsInTermsOfSponsorshipsActives().size();
			Assert.isTrue(d == res);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}
}
