
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
import domain.FloatEntity;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FloatEntityServiceTest extends AbstractTest {

	@Autowired
	private FloatEntityService	floatEntityService;
	@Autowired
	private BrotherhoodService	brotherhoodService;


	//TEST FINDALL DE FLOATENTITY #8.2 y 9.1 

	@Test
	public void testFindAllParadeService() {
		final Integer numFloats = 2;
		final Integer broId = super.getEntityId("brotherhood1");
		final Collection<FloatEntity> lista = this.floatEntityService.getFloatsByBrotherhood(this.brotherhoodService.findOne(broId));
		Assert.notNull(lista);
		Assert.isTrue(lista.size() == numFloats);
	}

}
