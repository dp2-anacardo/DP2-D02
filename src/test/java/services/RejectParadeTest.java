
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Parade;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class RejectParadeTest extends AbstractTest {

	@Autowired
	private ParadeService	paradeService;


	@Test
	public void rejectParadeTest() {
		super.authenticate("chapter2");

		final int paradeId = super.getEntityId("parade7");
		final Parade parade = this.paradeService.findOne(paradeId);
		this.paradeService.rejectParade(parade);

		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectRejectedParadeTest() {
		super.authenticate("chapter2");

		final int paradeId = super.getEntityId("parade9");
		final Parade parade = this.paradeService.findOne(paradeId);
		this.paradeService.rejectParade(parade);

		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectAcceptedParadeTest() {
		super.authenticate("chapter2");

		final int paradeId = super.getEntityId("parade8");
		final Parade parade = this.paradeService.findOne(paradeId);
		this.paradeService.rejectParade(parade);

		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void rejectAnotherChapterParadeTest() {
		super.authenticate("chapter2");

		final int paradeId = super.getEntityId("parade4");
		final Parade parade = this.paradeService.findOne(paradeId);
		this.paradeService.rejectParade(parade);

		super.unauthenticate();
	}

}
