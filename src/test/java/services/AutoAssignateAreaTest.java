
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Chapter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AutoAssignateAreaTest extends AbstractTest {

	@Autowired
	private ChapterService	chapterService;


	@Test
	public void autoAssignateArea() {
		super.authenticate("chapter1");

		final int chapterId = super.getEntityId("chapter1");
		final Chapter chapter = this.chapterService.findOne(chapterId);
		final int areaId = super.getEntityId("areaBrotherhood1");

		this.chapterService.autoAssignateArea(chapter, areaId);
		this.chapterService.save(chapter);

		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void autoAssignateAreaWithChapter() {
		super.authenticate("chapter1");

		final int chapterId = super.getEntityId("chapter1");
		final Chapter chapter = this.chapterService.findOne(chapterId);
		final int areaId = super.getEntityId("area2");

		this.chapterService.autoAssignateArea(chapter, areaId);
		this.chapterService.save(chapter);

		super.unauthenticate();
	}

}
