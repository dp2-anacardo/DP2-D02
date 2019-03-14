
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Brotherhood;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class BrotherhoodServiceTest extends AbstractTest {

	@Autowired
	private BrotherhoodService	brotherhoodService;


	//TEST FINDALL DE BROTHERHOOD #8.2 y 9.1 

	@Test
	public void testFindAllBrotherhoodService() {
		//Numero de hermandades en el populate = 1
		final Integer numBrotherhood = 1;
		final List<Brotherhood> lista = this.brotherhoodService.findAll();
		Assert.notNull(lista);
		Assert.isTrue(lista.size() == numBrotherhood);
	}

}
