
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Position;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class PositionServiceTest extends AbstractTest {

	@Autowired
	private PositionService	positionService;


	@Test
	public void createTest() {
		super.authenticate("admin1");
		Position position;

		position = this.positionService.create();

		Assert.notNull(position);

	}

	@Test
	public void saveTest() {
		super.authenticate("admin1");

		final Position position = this.positionService.create();

		position.setRoleEn("pruebaTest");
		position.setRoleEs("pruebaTest");

		this.positionService.save(position);
	}

	@Test
	public void saveTest2() {
		super.authenticate("admin1");

		final Position position = this.positionService.create();

		position.setRoleEn("President");
		position.setRoleEs("Presidente");

		final Position result = this.positionService.save(position);
		Assert.notNull(result);
	}

}
