
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Proclaim;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ProclaimServiceTest extends AbstractTest {

	@Autowired
	private ProclaimService	proclaimService;


	@Test
	public void createProclaimTest() {
		super.authenticate("chapter1");

		final Proclaim proclaim = this.proclaimService.create();
		proclaim.setDescription("Probando");
		this.proclaimService.save(proclaim);

		super.unauthenticate();
	}

	@Test(expected = ConstraintViolationException.class)
	public void createProclaimLongDescriptionTest() {
		super.authenticate("chapter1");

		final Proclaim proclaim = this.proclaimService.create();
		proclaim
			.setDescription("Espero que esta descripci�n sea de m�s de 250 caracteres para que salte el error de que debe estar entre 0 y 250 caracteres. Creo que a�n no es suficiente as� que sigo escribiendo hasta que crea conveniente. Seguir� si no coge la excepci�n. Parece ser que a�n no son 250 caracteres ya que no salta la excepci�n, este sera el segundo intento, he comprobado en local poniendo todo AAAAAA y no ha dejado que se guarde as� que deber�a de saltar, es solo cuesti�n de tiempo.");
		this.proclaimService.save(proclaim);
		this.proclaimService.flush();

		super.unauthenticate();
	}

}
