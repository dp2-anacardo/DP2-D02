
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
public class PublishProclaimTest extends AbstractTest {

	@Autowired
	private ProclaimService	proclaimService;


	@Test
	public void listProclaimDriver() {
		final Object testingData[][] = {
			{
				"chapter1", "probando", null
			},
			{
				"chapter1",
				"Espero que esta descripción sea de más de 250 caracteres para que salte el error de que debe estar entre 0 y 250 caracteres. Creo que aún no es suficiente así que sigo escribiendo hasta que crea conveniente. Seguiré si no coge la excepción. Parece ser que aún no son 250 caracteres ya que no salta la excepción, este sera el segundo intento, he comprobado en local poniendo todo AAAAAA y no ha dejado que se guarde así que debería de saltar, es solo cuestión de tiempo.",
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
