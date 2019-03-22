
package controllers.records;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.BrotherhoodService;
import services.InceptionRecordService;
import services.LegalRecordService;
import services.LinkRecordService;
import services.MiscRecordService;
import services.PeriodRecordService;
import controllers.AbstractController;
import datatype.Url;
import domain.Actor;
import domain.Brotherhood;
import domain.InceptionRecord;
import domain.LegalRecord;
import domain.LinkRecord;
import domain.MiscRecord;
import domain.PeriodRecord;
import forms.InceptionRecordForm;

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
	@Autowired
	private ActorService			actorService;


	//ALL RECORDS LIST
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int brotherhoodId) {

		ModelAndView result;
		try {
			final Boolean contains = this.brotherhoodService.findAll().contains(this.brotherhoodService.findOne(brotherhoodId));
			Assert.isTrue(contains);
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

	//ALL RECORDS SHOWS
	@RequestMapping(value = "inceptionRecord/show", method = RequestMethod.GET)
	public ModelAndView showInception(@RequestParam final int id) {
		ModelAndView result;
		final InceptionRecord iR;

		try {
			Assert.notNull(id);
			iR = this.inceptionRecordService.findOne(id);
		} catch (final Exception e) {
			result = this.forbiddenOperation();
			return result;
		}

		result = new ModelAndView("records/inceptionRecord/show");
		result.addObject("record", iR);

		//Comprueba si el record pertenece al usuario logeado
		if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
			final Brotherhood bh = this.brotherhoodService.findOne(this.actorService.getActorLogged().getId());
			if (bh.equals(iR.getBrotherhood()))
				result.addObject("isBrotherhood", true);
		}

		return result;
	}

	@RequestMapping(value = "/legalRecord/show", method = RequestMethod.GET)
	public ModelAndView showLegal(@RequestParam final int id) {
		ModelAndView result;
		final LegalRecord lR;
		try {
			Assert.notNull(id);
			lR = this.legalRecordService.findOne(id);
		} catch (final Exception e) {
			result = this.forbiddenOperation();
			return result;
		}

		result = new ModelAndView("records/legalRecord/show");
		result.addObject("record", lR);

		//Comprueba si el record pertenece al usuario logeado
		if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
			final Brotherhood bh = this.brotherhoodService.findOne(this.actorService.getActorLogged().getId());
			if (bh.equals(lR.getBrotherhood()))
				result.addObject("isBrotherhood", true);
		}

		return result;
	}

	@RequestMapping(value = "/linkRecord/show", method = RequestMethod.GET)
	public ModelAndView showLink(@RequestParam final int id) {
		ModelAndView result;
		final LinkRecord lR;
		try {
			Assert.notNull(id);
			lR = this.linkRecordService.findOne(id);
		} catch (final Exception e) {
			result = this.forbiddenOperation();
			return result;
		}

		result = new ModelAndView("records/linkRecord/show");
		result.addObject("record", lR);

		//Comprueba si el record pertenece al usuario logeado
		if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
			final Brotherhood bh = this.brotherhoodService.findOne(this.actorService.getActorLogged().getId());
			if (bh.equals(lR.getBrotherhood()))
				result.addObject("isBrotherhood", true);
		}

		return result;
	}

	@RequestMapping(value = "/periodRecord/show", method = RequestMethod.GET)
	public ModelAndView showPeriod(@RequestParam final int id) {
		ModelAndView result;
		final PeriodRecord pR;
		try {
			Assert.notNull(id);
			pR = this.periodRecordService.findOne(id);
		} catch (final Exception e) {
			result = this.forbiddenOperation();
			return result;
		}

		result = new ModelAndView("records/periodRecord/show");
		result.addObject("record", pR);

		//Comprueba si el record pertenece al usuario logeado
		if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
			final Brotherhood bh = this.brotherhoodService.findOne(this.actorService.getActorLogged().getId());
			if (bh.equals(pR.getBrotherhood()))
				result.addObject("isBrotherhood", true);
		}

		return result;
	}
	//ALL RECORDS EDITS
	@RequestMapping(value = "/inceptionRecord/edit", method = RequestMethod.GET)
	public ModelAndView editInception(@RequestParam final int id) {
		ModelAndView result;

		final Actor user = this.actorService.getActorLogged();
		final Brotherhood bh = this.brotherhoodService.findOne(user.getId());
		Assert.notNull(bh);

		result = this.createEditModelAndView(this.inceptionRecordService.findOne(id));

		return result;
	}

	@RequestMapping(value = "/inceptionRecord/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveInception(final InceptionRecordForm iRF, final BindingResult binding) {
		ModelAndView result;
		InceptionRecord iR;

		iR = this.inceptionRecordService.reconstructEdit(iRF, binding);

		iRF.setPhoto(iR.getPhoto());

		if (binding.hasErrors())
			result = this.createEditModelAndView(iRF, null);
		else
			try {
				this.inceptionRecordService.save(iR);
				result = new ModelAndView("redirect:/records/inceptionRecord/show.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(iRF, "record.edit.error");
			}
		return result;
	}

	//INCEPTION RECORD ADD PHOTO
	@RequestMapping(value = "/inceptionRecord/edit", method = RequestMethod.POST, params = "addPhoto")
	public ModelAndView addPhoto(final InceptionRecordForm iRF, final BindingResult binding) {
		ModelAndView result;
		InceptionRecord iR;

		final Actor user = this.actorService.getActorLogged();
		final Brotherhood bh = this.brotherhoodService.findOne(user.getId());
		Assert.notNull(bh);

		iR = this.inceptionRecordService.reconstructAddPhoto(iRF, binding);

		if (binding.hasErrors()) {

			iR = this.inceptionRecordService.findOne(iRF.getId());

			iRF.setPhoto(iR.getPhoto());

			result = this.createEditModelAndView(iRF, null);
		} else
			try {
				this.inceptionRecordService.save(iR);
				result = new ModelAndView("redirect:/record/inceptionRecord/edit.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(iRF, "record.edit.error");
			}
		return result;
	}
	//INCEPTION RECORD DELETE PHOTO
	@RequestMapping(value = "/inceptionRecord/deletePhoto", method = RequestMethod.GET)
	public ModelAndView deleteInceptionPhoto(@RequestParam("id") final int id, @RequestParam("photo") final char[] photo) {
		ModelAndView result;
		final InceptionRecord iR = this.inceptionRecordService.findOne(id);
		//final Url ph = new Url();
		//ph.setLink(photo);

		final Actor user = this.actorService.getActorLogged();
		final Brotherhood bh = this.brotherhoodService.findOne(user.getId());
		Assert.notNull(bh);

		if (!iR.getBrotherhood().equals(bh))
			result = this.createEditModelAndView(iR);

		final Collection<Url> photos = iR.getPhoto();
		photos.remove(photo);
		iR.setPhoto(photos);

		this.inceptionRecordService.save(iR);
		result = this.createEditModelAndView(iR);

		return result;
	}
	//ALL CREATE AND EDIT MODEL&VIEW
	protected ModelAndView createEditModelAndView(final InceptionRecord iR) {
		ModelAndView result;
		final InceptionRecordForm iRF = new InceptionRecordForm(iR);
		result = this.createEditModelAndView(iRF, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final InceptionRecordForm iRF, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("records/inceptionRecord/edit");
		result.addObject("iRF", iRF);
		result.addObject("messageCode", messageCode);

		return result;
	}

	private ModelAndView forbiddenOperation() {
		return new ModelAndView("redirect:/");
	}

}
