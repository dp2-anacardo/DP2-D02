
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.DataBinder;

import utilities.AbstractTest;
import domain.Chapter;
import forms.ChapterForm;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RegisterChapterTest extends AbstractTest {

	@Autowired
	private ChapterService	chapterService;

	@Autowired
	private ActorService	actorService;


	/*
	 * Testing functional requirement : requirement 7.1 (chapter register)
	 * Positive:An actor who is not authenticated is successfully registered as a chapter
	 * Negative:An actor who is not authenticated left an obligatory attribute in blank
	 * Sentence coverage: 100%
	 * Data coverage: 30%
	 */

	@Test
	public void registerChapterDriver() {
		final Object testingData[][] = {
			{
				"prueba2", "123456", "123456", "prueba2", "prueba2", "", "prueba@prueba.com", "", "", null
			}, {
				"prueba3", "123456", "123456", "prueba2", "prueba2", "", "prueba@prueba.com", "600102030", "", null
			}, {
				"", "123456", "123456", "prueba2", "prueba2", "", "prueba@prueba.com", "", "", ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateRegisterChapter((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (Class<?>) testingData[i][9]);
	}

	@Test
	public void editChapterDriver() {
		final Object testingData[][] = {
			{
				"chapter1", "newName", null
			}, {
				"chapter1", "", ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEditChapter((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	public void templateRegisterChapter(final String username, final String password, final String confirmPassword, final String name, final String title, final String photo, final String email, final String phoneNumber, final String address,
		final Class<?> expected) {

		Class<?> caught;
		caught = null;
		try {
			final ChapterForm sForm = new ChapterForm();

			final DataBinder binding = new DataBinder(new Chapter());

			sForm.setName(name);
			sForm.setUsername(username);
			sForm.setPassword(password);
			sForm.setConfirmPass(confirmPassword);
			sForm.setEmail(email);
			sForm.setAddress(address);
			sForm.setPhoneNumber(phoneNumber);
			sForm.setPhoto(photo);
			sForm.setTitle(title);

			final Chapter chapter = this.chapterService.reconstruct(sForm, binding.getBindingResult());

			this.chapterService.save(chapter);
			this.chapterService.flush();

		} catch (final Exception e) {
			caught = e.getClass();
		}
		super.checkExceptions(expected, caught);
	}

	public void templateEditChapter(final String username, final String name, final Class<?> expected) {

		Class<?> caught;
		caught = null;
		try {

			super.authenticate(username);

			final DataBinder binding = new DataBinder(new Chapter());

			final Chapter chapter = this.chapterService.findOne(this.actorService.getActorLogged().getId());

			chapter.setName(name);

			final Chapter result = this.chapterService.reconstruct(chapter, binding.getBindingResult());

			this.chapterService.save(result);
			this.chapterService.flush();

		} catch (final Exception e) {
			caught = e.getClass();
		}
		super.checkExceptions(expected, caught);
	}

}
