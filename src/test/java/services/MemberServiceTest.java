
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
import domain.Member;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MemberServiceTest extends AbstractTest {

	@Autowired
	private BrotherhoodService	brotherhoodService;


	//TEST FINDALL DE MEMBER #8.2 y 9.1 

	@Test
	public void testFindAllMemberService() {
		final Integer numMember = 2;
		final Integer broId = super.getEntityId("brotherhood1");
		final Collection<Member> lista = this.brotherhoodService.getMembersByBrotherhood(this.brotherhoodService.findOne(broId));
		Assert.notNull(lista);
		Assert.isTrue(lista.size() == numMember);
	}

}
