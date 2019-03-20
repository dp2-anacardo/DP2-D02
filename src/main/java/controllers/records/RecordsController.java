
package controllers.records;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.InceptionRecordService;
import services.LegalRecordService;
import services.LinkRecordService;
import services.MiscRecordService;
import services.PeriodRecordService;
import controllers.AbstractController;
import domain.InceptionRecord;
import domain.LegalRecord;
import domain.LinkRecord;
import domain.MiscRecord;
import domain.PeriodRecord;

@Controller
@RequestMapping("/records")
public class RecordsController extends AbstractController {

	@Autowired
	private InceptionRecordService	inceptionRecordService;
	@Autowired
	private LegalRecordService		legalRecordService;
	@Autowired
	private MiscRecordService		miscRecordService;
	@Autowired
	private PeriodRecordService		periodRecordService;
	@Autowired
	private LinkRecordService		linkRecordService;
	@Autowired
	private BrotherhoodService		brotherhoodService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int brotherhoodId) {

		ModelAndView result;
		try {
			Assert.isTrue(this.brotherhoodService.findAll().contains(this.brotherhoodService.findOne(brotherhoodId)));
			Assert.notNull(brotherhoodId);
		} catch (final Exception e) {
			result = this.forbiddenOperation();
			return result;
		}
		final Collection<InceptionRecord> inceptions = this.inceptionRecordService.getInceptionRecordByBrotherhood(brotherhoodId);
		final Collection<LegalRecord> legals = this.legalRecordService.getLegalRecordByBrotherhood(brotherhoodId);
		final Collection<MiscRecord> miscs = this.miscRecordService.getMiscRecordByBrotherhood(brotherhoodId);
		final Collection<PeriodRecord> periods = this.periodRecordService.getPeriodRecordByBrotherhood(brotherhoodId);
		final Collection<LinkRecord> links = this.linkRecordService.getLinkRecordByBrotherhood(brotherhoodId);

		result = new ModelAndView("records/list");
		result.addObject("InceptionRecord", inceptions);
		result.addObject("LegalRecord", legals);
		result.addObject("LinkRecord", links);
		result.addObject("MiscRecord", miscs);
		result.addObject("PeriodRecord", periods);

		return result;
	}
	private ModelAndView forbiddenOperation() {
		return new ModelAndView("redirect:/");
	}

}
