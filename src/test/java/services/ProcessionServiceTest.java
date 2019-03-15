
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
import domain.Procession;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ProcessionServiceTest extends AbstractTest {

	@Autowired
	private ProcessionService	processionService;


	//TEST FINDALL DE PROCESSION #8.2 y 9.1 

	@Test
	public void testFindAllProcessionService() {
		final Integer numProcession = 15;
		final Integer broId = super.getEntityId("brotherhood1");
		final Collection<Procession> lista = this.processionService.getProcessionsFinalByBrotherhood(broId);
		Assert.notNull(lista);
		Assert.isTrue(lista.size() == numProcession);
	}

}
