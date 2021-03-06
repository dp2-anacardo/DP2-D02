
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Proclaim;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class PublishProclaimTest extends AbstractTest {

	@Autowired
	private ProclaimService proclaimService;


	//In this test we are testing the requirement 17(publish a proclaim).
	//In the negative case we are testing that the published description can not
	//have a description longer than 250 characters.
	//Sequence coverage: 100%
	//Data coverage: 100%

	@Test
	public void listProclaimDriver() {
		final Object testingData[][] = {
			{
				"chapter1", "probando", null
			}, {
				"chapter1",
				"Espero que esta descripci�n sea de m�s de 250 caracteres para que salte el error de que debe estar entre 0 y 250 caracteres. Creo que a�n no es suficiente as� que sigo escribiendo hasta que crea conveniente. Seguir� si no coge la excepci�n. Parece ser que a�n no son 250 caracteres ya que no salta la excepci�n, este sera el segundo intento, he comprobado en local poniendo todo AAAAAA y no ha dejado que se guarde as� que deber�a de saltar, es solo cuesti�n de tiempo.",
				ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.listProclaimTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	private void listProclaimTemplate(final String username, final String description, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(username);

			final Proclaim proclaim = this.proclaimService.create();
			proclaim.setDescription(description);
			this.proclaimService.save(proclaim);
			this.proclaimService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);

	}

}
