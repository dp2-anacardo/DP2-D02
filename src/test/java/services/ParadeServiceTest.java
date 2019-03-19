
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
import domain.Parade;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ParadeServiceTest extends AbstractTest {

	@Autowired
	private ParadeService	paradeService;


	//TEST FINDALL DE PROCESSION #8.2 y 9.1 

	@Test
	public void testFindAllParadeService() {
		final Integer numParade = 15;
		final Integer broId = super.getEntityId("brotherhood1");
		final Collection<Parade> lista = this.paradeService.getParadesFinalByBrotherhood(broId);
		Assert.notNull(lista);
		Assert.isTrue(lista.size() == numParade);
	}

}
