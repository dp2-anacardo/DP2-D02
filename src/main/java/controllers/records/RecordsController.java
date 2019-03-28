
package controllers.records;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import forms.LegalRecordForm;
import forms.PeriodRecordForm;

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


	//**********************************************************************************
	//**
	//**		ALL RECORDS LIST
	//**
	//**********************************************************************************
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

		//Comprueba si el usuario logeado es la brotherhood de los records
		if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
			final Brotherhood bh = this.brotherhoodService.findOne(this.actorService.getActorLogged().getId());
			if (bh != null)
				if (bh.equals(this.brotherhoodService.findOne(brotherhoodId)))
					result.addObject("isBrotherhood", true);
		}

		return result;
	}

	@RequestMapping(value = "/history", method = RequestMethod.GET)
	public ModelAndView history() {

		ModelAndView result;
		Brotherhood bh;
		try {
			bh = (Brotherhood) this.actorService.getActorLogged();
			Assert.isTrue(this.brotherhoodService.findAll().contains(this.brotherhoodService.findOne(bh.getId())));
			Assert.notNull(bh);
		} catch (final Exception e) {
			result = this.forbiddenOperation();
			return result;
		}

		final Collection<InceptionRecord> inceptions = this.inceptionRecordService.getInceptionRecordByBrotherhood(bh.getId());
		final Collection<LegalRecord> legals = this.legalRecordService.getLegalRecordByBrotherhood(bh.getId());
		final Collection<MiscRecord> miscs = this.miscRecordService.getMiscRecordByBrotherhood(bh.getId());
		final Collection<PeriodRecord> periods = this.periodRecordService.getPeriodRecordByBrotherhood(bh.getId());
		final Collection<LinkRecord> links = this.linkRecordService.getLinkRecordByBrotherhood(bh.getId());

		result = new ModelAndView("records/list");
		result.addObject("InceptionRecord", inceptions);
		result.addObject("LegalRecord", legals);
		result.addObject("LinkRecord", links);
		result.addObject("MiscRecord", miscs);
		result.addObject("PeriodRecord", periods);

		//Comprueba si el usuario logeado es la brotherhood de los records
		if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser"))
			if (bh != null)
				if (bh.equals(this.brotherhoodService.findOne(bh.getId())))
					result.addObject("isBrotherhood", true);

		return result;
	}
	//**********************************************************************************
	//**
	//**		ALL RECORDS CREATES GET AND POST
	//**
	//**********************************************************************************

	//INCEPTION RECORD CREATE
	@RequestMapping(value = "/inceptionRecord/create", method = RequestMethod.GET)
	public ModelAndView createInception() {

		ModelAndView result;
		final InceptionRecord iR = this.inceptionRecordService.create();
		result = this.createModelAndView(iR);

		return result;
	}

	@RequestMapping(value = "/inceptionRecord/create", method = RequestMethod.POST, params = "create")
	public ModelAndView createInception(@ModelAttribute("iRF") @Valid final InceptionRecordForm iRF, final BindingResult binding) {
		ModelAndView result;
		InceptionRecord iR;

		iR = this.inceptionRecordService.reconstructCreate(iRF, binding);

		if (binding.hasErrors())
			result = this.createModelAndView(iRF, null);
		else
			try {
				final Brotherhood bh = this.brotherhoodService.findOne(this.actorService.getActorLogged().getId());
				iR.setBrotherhood(bh);
				this.inceptionRecordService.save(iR);
				result = this.list(iR.getBrotherhood().getId());
			} catch (final Throwable oops) {
				result = this.createModelAndView(iRF, "record.edit.error");
			}
		return result;
	}

	//LEGAL RECORD CREATE
	@RequestMapping(value = "/legalRecord/create", method = RequestMethod.GET)
	public ModelAndView createLegal() {

		ModelAndView result;
		final LegalRecord lR = this.legalRecordService.create();
		result = this.createModelAndView(lR);

		return result;
	}

	@RequestMapping(value = "/legalRecord/create", method = RequestMethod.POST, params = "create")
	public ModelAndView createLegal(@ModelAttribute("lRF") @Valid final LegalRecordForm lRF, final BindingResult binding) {
		ModelAndView result;
		LegalRecord lR;

		if (!lRF.getLaw().equals("")) {
			final List<String> laws = new ArrayList<String>();
			laws.add(lRF.getLaw());
			lRF.setApplicableLaws(laws);
		}

		lR = this.legalRecordService.reconstructCreate(lRF, binding);

		if (binding.hasErrors()) {
			result = this.createModelAndView(lRF, null);
			if (lRF.getLaw().equals(""))
				result.addObject("customErrorMessage", "record.error.oneLaw");
		} else
			try {
				final Brotherhood bh = this.brotherhoodService.findOne(this.actorService.getActorLogged().getId());
				lR.setBrotherhood(bh);
				this.legalRecordService.save(lR);
				result = this.list(lR.getBrotherhood().getId());
			} catch (final Throwable oops) {
				result = this.createModelAndView(lRF, "record.edit.error");
			}
		return result;
	}
	//MISC RECORD CREATE
	@RequestMapping(value = "/miscRecord/create", method = RequestMethod.GET)
	public ModelAndView createMisc() {

		ModelAndView result;
		final MiscRecord mR = this.miscRecordService.create();
		result = this.createModelAndView(mR);

		return result;
	}

	@RequestMapping(value = "/miscRecord/create", method = RequestMethod.POST, params = "create")
	public ModelAndView createMisc(@ModelAttribute("mRF") final MiscRecord mRF, final BindingResult binding) {
		ModelAndView result;
		MiscRecord mR;

		mR = this.miscRecordService.reconstructCreate(mRF, binding);

		if (binding.hasErrors())
			result = this.createModelAndView(mRF, null);
		else
			try {
				final Brotherhood bh = this.brotherhoodService.findOne(this.actorService.getActorLogged().getId());
				mR.setBrotherhood(bh);
				this.miscRecordService.save(mR);
				result = this.list(mR.getBrotherhood().getId());
			} catch (final Throwable oops) {
				result = this.createModelAndView(mRF, "record.edit.error");
			}
		return result;
	}

	//PERIOD RECORD CREATE
	@RequestMapping(value = "/periodRecord/create", method = RequestMethod.GET)
	public ModelAndView createPeriod() {

		ModelAndView result;
		final PeriodRecord pR = this.periodRecordService.create();
		result = this.createModelAndView(pR);

		return result;
	}

	@RequestMapping(value = "/periodRecord/create", method = RequestMethod.POST, params = "create")
	public ModelAndView createPeriod(@ModelAttribute("pRF") @Valid final PeriodRecordForm pRF, final BindingResult binding) {
		ModelAndView result;
		PeriodRecord pR;

		if (!pRF.getLink().equals("")) {
			final List<Url> photos = new ArrayList<Url>();
			final Url photo = new Url();
			photo.setLink(pRF.getLink());
			photos.add(photo);
			pRF.setPhoto(photos);
		}
		if (pRF.getStartYear() > pRF.getEndYear()) {
			result = this.createModelAndView(pRF, null);
			result.addObject("error", "record.error.1");
		} else {

			pR = this.periodRecordService.reconstructCreate(pRF, binding);

			if (binding.hasErrors()) {
				result = this.createModelAndView(pRF, null);
				if (pRF.getLink().equals("")) {
					result.addObject("customErrorMessage", "record.error.onePhoto");
					result.addObject("error", "record.error.1");
				}
			} else
				try {
					final Brotherhood bh = this.brotherhoodService.findOne(this.actorService.getActorLogged().getId());
					pR.setBrotherhood(bh);
					this.periodRecordService.save(pR);
					result = this.list(pR.getBrotherhood().getId());
				} catch (final Throwable oops) {
					result = this.createModelAndView(pRF, "record.edit.error");
				}
		}
		return result;
	}
	//LINK RECORD CREATE
	@RequestMapping(value = "/linkRecord/create", method = RequestMethod.GET)
	public ModelAndView createLink() {

		ModelAndView result;
		final LinkRecord lR = this.linkRecordService.create();
		result = this.createModelAndView(lR);

		return result;
	}

	@RequestMapping(value = "/linkRecord/create", method = RequestMethod.POST, params = "create")
	public ModelAndView createLink(@ModelAttribute("lRF") final LinkRecord lRF, final BindingResult binding) {
		ModelAndView result;
		LinkRecord lR;
		//final LinkRecord lR2 = new LinkRecord();

		lR = this.linkRecordService.reconstructCreate(lRF, binding);

		if (binding.hasErrors())
			result = this.createModelAndView(lRF, null);
		else
			try {
				final Brotherhood bh = this.brotherhoodService.findOne(this.actorService.getActorLogged().getId());
				lR.setBrotherhood(bh);

				/*
				 * final String title = "Link record with brotherhood " + lR.getBrotherhood().getTitle();
				 * final String description = "Autogenerated link record with brotherhood " + lR.getBrotherhood().getTitle();
				 * 
				 * lR2.setTitle(title);
				 * lR2.setDescription(description);
				 * lR2.setBrotherhood(lR.getLinkedBH());
				 * lR2.setLinkedBH(lR.getBrotherhood());
				 */

				this.linkRecordService.save(lR);
				//this.linkRecordService.save(lR2);
				result = this.list(lR.getBrotherhood().getId());
			} catch (final Throwable oops) {
				result = this.createModelAndView(lRF, "record.edit.error");
			}
		return result;
	}
	//**********************************************************************************
	//**
	//**		ALL RECORDS SHOWS
	//**
	//**********************************************************************************

	//INCEPTION RECORD SHOW
	@RequestMapping(value = "/inceptionRecord/show", method = RequestMethod.GET)
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
			if (bh != null)
				if (bh.equals(iR.getBrotherhood()))
					result.addObject("isBrotherhood", true);
		}

		return result;
	}

	//LEGAL RECORD SHOW
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
			if (bh != null)
				if (bh.equals(lR.getBrotherhood()))
					result.addObject("isBrotherhood", true);
		}

		return result;
	}

	//LINK RECORD SHOW
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
			if (bh != null)
				if (bh.equals(lR.getBrotherhood()))
					result.addObject("isBrotherhood", true);
		}

		return result;
	}

	//PERIOD RECORD SHOW
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
			if (bh != null)
				if (bh.equals(pR.getBrotherhood()))
					result.addObject("isBrotherhood", true);
		}

		return result;
	}

	//MISC RECORD SHOW
	@RequestMapping(value = "/miscRecord/show", method = RequestMethod.GET)
	public ModelAndView showMisc(@RequestParam final int id) {
		ModelAndView result;
		final MiscRecord mR;
		try {
			Assert.notNull(id);
			mR = this.miscRecordService.findOne(id);
		} catch (final Exception e) {
			result = this.forbiddenOperation();
			return result;
		}

		result = new ModelAndView("records/miscRecord/show");
		result.addObject("record", mR);

		//Comprueba si el record pertenece al usuario logeado
		if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
			final Brotherhood bh = this.brotherhoodService.findOne(this.actorService.getActorLogged().getId());
			if (bh != null)
				if (bh.equals(mR.getBrotherhood()))
					result.addObject("isBrotherhood", true);
		}

		return result;
	}

	//**********************************************************************************
	//**
	//**		ALL RECORDS EDITS
	//**
	//**********************************************************************************

	//INCEPTION RECORD EDIT GET AND POST
	@RequestMapping(value = "/inceptionRecord/edit", method = RequestMethod.GET)
	public ModelAndView editInception(@RequestParam final int id) {
		ModelAndView result;

		try {
			final Actor user = this.actorService.getActorLogged();
			final Brotherhood bh = this.brotherhoodService.findOne(user.getId());
			Assert.notNull(bh);

			result = this.editModelAndView(this.inceptionRecordService.findOne(id));
		} catch (final Throwable oops) {
			result = this.forbiddenOperation();
		}

		return result;
	}

	@RequestMapping(value = "/inceptionRecord/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveInception(@ModelAttribute("iRF") @Valid final InceptionRecordForm iRF, final BindingResult binding) {
		ModelAndView result;
		InceptionRecord iR;

		iR = this.inceptionRecordService.reconstructEdit(iRF, binding);

		iRF.setPhoto(iR.getPhoto());

		if (binding.hasErrors())
			result = this.editModelAndView(iRF, null);
		else
			try {
				this.inceptionRecordService.save(iR);
				result = new ModelAndView("redirect:/records/inceptionRecord/show.do?id=" + iR.getId());
			} catch (final Throwable oops) {
				result = this.editModelAndView(iRF, "record.edit.error");
			}
		return result;
	}

	//LEGAL RECORD EDIT GET AND POST
	@RequestMapping(value = "/legalRecord/edit", method = RequestMethod.GET)
	public ModelAndView editLegal(@RequestParam final int id) {
		ModelAndView result;

		try {
			final Actor user = this.actorService.getActorLogged();
			final Brotherhood bh = this.brotherhoodService.findOne(user.getId());
			Assert.notNull(bh);

			result = this.editModelAndView(this.legalRecordService.findOne(id));
		} catch (final Throwable oops) {
			result = this.forbiddenOperation();
		}

		return result;
	}

	@RequestMapping(value = "/legalRecord/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveLegal(@ModelAttribute("lRF") @Valid final LegalRecordForm lRF, final BindingResult binding) {
		ModelAndView result;
		LegalRecord lR;

		lR = this.legalRecordService.reconstructEdit(lRF, binding);

		lRF.setApplicableLaws(lR.getApplicableLaws());

		if (binding.hasErrors())
			result = this.editModelAndView(lRF, null);
		else
			try {
				this.legalRecordService.save(lR);
				result = new ModelAndView("redirect:/records/legalRecord/show.do?id=" + lR.getId());
			} catch (final Throwable oops) {
				result = this.editModelAndView(lRF, "record.edit.error");
			}
		return result;
	}

	//MISC RECORD EDIT GET AND POST
	@RequestMapping(value = "/miscRecord/edit", method = RequestMethod.GET)
	public ModelAndView editMisc(@RequestParam final int id) {
		ModelAndView result;

		try {
			final Actor user = this.actorService.getActorLogged();
			final Brotherhood bh = this.brotherhoodService.findOne(user.getId());
			Assert.notNull(bh);

			result = this.editModelAndView(this.miscRecordService.findOne(id));
		} catch (final Throwable oops) {
			result = this.forbiddenOperation();
		}

		return result;
	}

	@RequestMapping(value = "/miscRecord/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveMisc(@ModelAttribute("mRF") final MiscRecord mRF, final BindingResult binding) {
		ModelAndView result;
		MiscRecord mR;

		mR = this.miscRecordService.reconstructEdit(mRF, binding);

		if (binding.hasErrors())
			result = this.editModelAndView(mRF, null);
		else
			try {
				this.miscRecordService.save(mR);
				result = new ModelAndView("redirect:/records/miscRecord/show.do?id=" + mR.getId());
			} catch (final Throwable oops) {
				result = this.editModelAndView(mRF, "record.edit.error");
			}
		return result;
	}

	//PERIOD RECORD EDIT GET AND POST
	@RequestMapping(value = "/periodRecord/edit", method = RequestMethod.GET)
	public ModelAndView editPeriod(@RequestParam final int id) {
		ModelAndView result;

		try {
			final Actor user = this.actorService.getActorLogged();
			final Brotherhood bh = this.brotherhoodService.findOne(user.getId());
			Assert.notNull(bh);

			result = this.editModelAndView(this.periodRecordService.findOne(id));
		} catch (final Throwable oops) {
			result = this.forbiddenOperation();
		}

		return result;
	}

	@RequestMapping(value = "/periodRecord/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView savePeriod(@ModelAttribute("pRF") @Valid final PeriodRecordForm pRF, final BindingResult binding) {
		ModelAndView result;
		PeriodRecord pR;

		pR = this.periodRecordService.reconstructEdit(pRF, binding);

		pRF.setPhoto(pR.getPhoto());

		if (pRF.getStartYear() > pRF.getEndYear()) {
			result = this.editModelAndView(pRF, null);
			result.addObject("error", "record.error.1");
		} else if (binding.hasErrors()) {
			result = this.editModelAndView(pRF, null);
			if (pR.getStartYear() > pR.getEndYear())
				result.addObject("error", "record.error.1");
		} else
			try {
				this.periodRecordService.save(pR);
				result = new ModelAndView("redirect:/records/periodRecord/show.do?id=" + pR.getId());
			} catch (final Throwable oops) {
				result = this.editModelAndView(pRF, "record.edit.error");
			}
		return result;
	}
	//LINK RECORD EDIT GET AND POST
	@RequestMapping(value = "/linkRecord/edit", method = RequestMethod.GET)
	public ModelAndView editLink(@RequestParam final int id) {
		ModelAndView result;

		try {
			final Actor user = this.actorService.getActorLogged();
			final Brotherhood bh = this.brotherhoodService.findOne(user.getId());
			Assert.notNull(bh);

			result = this.editModelAndView(this.linkRecordService.findOne(id));
		} catch (final Throwable oops) {
			result = this.forbiddenOperation();
		}

		return result;
	}

	@RequestMapping(value = "/linkRecord/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveLink(@ModelAttribute("lRF") final LinkRecord lRF, final BindingResult binding) {
		ModelAndView result;
		LinkRecord lR;

		lR = this.linkRecordService.reconstructEdit(lRF, binding);

		if (binding.hasErrors())
			result = this.editModelAndView(lRF, null);
		else
			try {
				this.linkRecordService.save(lR);
				result = new ModelAndView("redirect:/records/linkRecord/show.do?id=" + lR.getId());
			} catch (final Throwable oops) {
				result = this.editModelAndView(lRF, "record.edit.error");
			}
		return result;
	}
	//**********************************************************************************
	//**
	//**		ALL RECORDS DELETE
	//**
	//**********************************************************************************

	//INCEPTION RECORD DELETE
	@RequestMapping(value = "/inceptionRecord/delete", method = RequestMethod.GET)
	public ModelAndView deleteInception(@RequestParam final int id) {
		ModelAndView result = null;
		final InceptionRecord iR;
		try {
			Assert.notNull(id);
			iR = this.inceptionRecordService.findOne(id);

			final Brotherhood bh = this.brotherhoodService.findOne(this.actorService.getActorLogged().getId());
			if (bh.equals(iR.getBrotherhood())) {
				this.inceptionRecordService.delete(iR);
				result = this.list(bh.getId());
			}

		} catch (final Exception e) {
			result = this.forbiddenOperation();
			return result;
		}
		return result;
	}

	//LEGAL RECORD DELETE
	@RequestMapping(value = "/legalRecord/delete", method = RequestMethod.GET)
	public ModelAndView deleteLegal(@RequestParam final int id) {
		ModelAndView result = null;
		final LegalRecord lR;
		try {
			Assert.notNull(id);
			lR = this.legalRecordService.findOne(id);

			final Brotherhood bh = this.brotherhoodService.findOne(this.actorService.getActorLogged().getId());
			if (bh.equals(lR.getBrotherhood())) {
				this.legalRecordService.delete(lR);
				result = this.list(bh.getId());
			}

		} catch (final Exception e) {
			result = this.forbiddenOperation();
			return result;
		}
		return result;
	}

	//MISC RECORD DELETE
	@RequestMapping(value = "/miscRecord/delete", method = RequestMethod.GET)
	public ModelAndView deleteMisc(@RequestParam final int id) {
		ModelAndView result = null;
		final MiscRecord mR;
		try {
			Assert.notNull(id);
			mR = this.miscRecordService.findOne(id);

			final Brotherhood bh = this.brotherhoodService.findOne(this.actorService.getActorLogged().getId());
			if (bh.equals(mR.getBrotherhood())) {
				this.miscRecordService.delete(mR);
				result = this.list(bh.getId());
			}

		} catch (final Exception e) {
			result = this.forbiddenOperation();
			return result;
		}
		return result;
	}

	//PERIOD RECORD DELETE
	@RequestMapping(value = "/periodRecord/delete", method = RequestMethod.GET)
	public ModelAndView deletePeriod(@RequestParam final int id) {
		ModelAndView result = null;
		final PeriodRecord pR;
		try {
			Assert.notNull(id);
			pR = this.periodRecordService.findOne(id);

			final Brotherhood bh = this.brotherhoodService.findOne(this.actorService.getActorLogged().getId());
			if (bh.equals(pR.getBrotherhood())) {
				this.periodRecordService.delete(pR);
				result = this.list(bh.getId());
			}

		} catch (final Exception e) {
			result = this.forbiddenOperation();
			return result;
		}
		return result;
	}

	//LINK RECORD DELETE
	@RequestMapping(value = "/linkRecord/delete", method = RequestMethod.GET)
	public ModelAndView deleteLink(@RequestParam final int id) {
		ModelAndView result = null;
		final LinkRecord lR;
		try {
			Assert.notNull(id);
			lR = this.linkRecordService.findOne(id);

			final Brotherhood bh = this.brotherhoodService.findOne(this.actorService.getActorLogged().getId());
			if (bh.equals(lR.getBrotherhood())) {
				this.linkRecordService.delete(lR);
				result = this.list(bh.getId());
			}

		} catch (final Exception e) {
			result = this.forbiddenOperation();
			return result;
		}
		return result;
	}

	//**********************************************************************************
	//**
	//**		RECORDS ADD/DELETE PHOTOS AND LAWS
	//**
	//**********************************************************************************

	//INCEPTION RECORD ADD PHOTO
	@RequestMapping(value = "/inceptionRecord/edit", method = RequestMethod.POST, params = "addPhoto")
	public ModelAndView addPhoto(@ModelAttribute("iRF") @Valid final InceptionRecordForm iRF, final BindingResult binding) {
		ModelAndView result;
		InceptionRecord iR;

		final Actor user = this.actorService.getActorLogged();
		final Brotherhood bh = this.brotherhoodService.findOne(user.getId());
		Assert.notNull(bh);

		iR = this.inceptionRecordService.reconstructAddPhoto(iRF, binding);

		if (binding.hasErrors()) {
			iR = this.inceptionRecordService.findOne(iRF.getId());
			iRF.setPhoto(iR.getPhoto());
			result = this.editModelAndView(iRF, null);
		} else
			try {
				this.inceptionRecordService.save(iR);
				result = this.editModelAndView(iR);
				;
			} catch (final Throwable oops) {
				result = this.editModelAndView(iRF, "record.edit.error");
			}
		return result;
	}
	//INCEPTION RECORD DELETE PHOTO
	@RequestMapping(value = "/inceptionRecord/deletePhoto", method = RequestMethod.GET)
	public ModelAndView deleteInceptionPhoto(@RequestParam("id") final int id, @RequestParam("pos") final int pos) {
		ModelAndView result;
		try {
			final InceptionRecord iR = this.inceptionRecordService.findOne(id);

			final Actor user = this.actorService.getActorLogged();
			final Brotherhood bh = this.brotherhoodService.findOne(user.getId());
			Assert.notNull(bh);
			final List<Url> photos = (List<Url>) iR.getPhoto();

			if (!(iR.getBrotherhood().equals(bh)) || pos >= photos.size() || (photos.size() == 1))
				result = this.editModelAndView(iR);
			else {
				photos.remove(photos.get(pos));
				iR.setPhoto(photos);

				this.inceptionRecordService.save(iR);
				result = this.editModelAndView(iR);
			}
		} catch (final Throwable oops) {
			result = this.forbiddenOperation();
		}

		return result;
	}

	//PERIOD RECORD ADD PHOTO
	@RequestMapping(value = "/periodRecord/edit", method = RequestMethod.POST, params = "addPhoto")
	public ModelAndView addPhoto(@ModelAttribute("pRF") @Valid final PeriodRecordForm pRF, final BindingResult binding) {
		ModelAndView result;
		PeriodRecord pR;

		final Actor user = this.actorService.getActorLogged();
		final Brotherhood bh = this.brotherhoodService.findOne(user.getId());
		Assert.notNull(bh);

		pR = this.periodRecordService.reconstructAddPhoto(pRF, binding);

		if (binding.hasErrors()) {

			pR = this.periodRecordService.findOne(pRF.getId());
			pRF.setPhoto(pR.getPhoto());
			result = this.editModelAndView(pRF, null);
		} else
			try {
				this.periodRecordService.save(pR);
				result = this.editModelAndView(pR);
				;
			} catch (final Throwable oops) {
				result = this.editModelAndView(pRF, "record.edit.error");
			}
		return result;
	}
	//PERIOD RECORD DELETE PHOTO
	@RequestMapping(value = "/periodRecord/deletePhoto", method = RequestMethod.GET)
	public ModelAndView deletePeriodPhoto(@RequestParam("id") final int id, @RequestParam("pos") final int pos) {
		ModelAndView result;
		try {
			final PeriodRecord pR = this.periodRecordService.findOne(id);

			final Actor user = this.actorService.getActorLogged();
			final Brotherhood bh = this.brotherhoodService.findOne(user.getId());
			Assert.notNull(bh);
			final List<Url> photos = (List<Url>) pR.getPhoto();

			if (!(pR.getBrotherhood().equals(bh)) || pos >= photos.size() || (photos.size() == 1))
				result = this.editModelAndView(pR);
			else {
				photos.remove(photos.get(pos));
				pR.setPhoto(photos);

				this.periodRecordService.save(pR);
				result = this.editModelAndView(pR);
			}
		} catch (final Throwable oops) {
			result = this.forbiddenOperation();
		}

		return result;
	}

	//LEGAL RECORD ADD LAW
	@RequestMapping(value = "/legalRecord/edit", method = RequestMethod.POST, params = "addLaw")
	public ModelAndView addLaw(@ModelAttribute("lRF") @Valid final LegalRecordForm lRF, final BindingResult binding) {
		ModelAndView result;
		LegalRecord lR;

		final Actor user = this.actorService.getActorLogged();
		final Brotherhood bh = this.brotherhoodService.findOne(user.getId());
		Assert.notNull(bh);

		lR = this.legalRecordService.reconstructAddLaw(lRF, binding);

		if (binding.hasErrors()) {

			lR = this.legalRecordService.findOne(lRF.getId());
			lRF.setApplicableLaws(lR.getApplicableLaws());
			result = this.editModelAndView(lRF, null);
		} else
			try {
				this.legalRecordService.save(lR);
				result = this.editModelAndView(lR);
				;
			} catch (final Throwable oops) {
				result = this.editModelAndView(lRF, "record.edit.error");
			}
		return result;
	}
	//LEGAL RECORD DELETE LAW
	@RequestMapping(value = "/legalRecord/deleteLaw", method = RequestMethod.GET)
	public ModelAndView deleteLaw(@RequestParam("id") final int id, @RequestParam("law") final String law) {
		ModelAndView result;
		try {
			final LegalRecord lR = this.legalRecordService.findOne(id);

			final Actor user = this.actorService.getActorLogged();
			final Brotherhood bh = this.brotherhoodService.findOne(user.getId());
			Assert.notNull(bh);
			final List<String> laws = (List<String>) lR.getApplicableLaws();

			if (!(lR.getBrotherhood().equals(bh)) || (lR.getApplicableLaws().size() == 1))
				result = this.editModelAndView(lR);
			else {
				laws.remove(law);
				lR.setApplicableLaws(laws);

				this.legalRecordService.save(lR);
				result = this.editModelAndView(lR);
			}
		} catch (final Throwable oops) {
			result = this.forbiddenOperation();
		}

		return result;
	}
	//**********************************************************************************
	//**
	//**		MODELS AND VIEWS
	//**
	//**********************************************************************************

	//INCEPTION RECORD M&V
	protected ModelAndView editModelAndView(final InceptionRecord iR) {
		ModelAndView result;
		final InceptionRecordForm iRF = new InceptionRecordForm(iR);
		result = this.editModelAndView(iRF, null);
		return result;
	}

	protected ModelAndView editModelAndView(final InceptionRecordForm iRF, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("records/inceptionRecord/edit");
		result.addObject("iRF", iRF);
		result.addObject("size", iRF.getPhoto().size());
		result.addObject("cont", 0);
		result.addObject("messageCode", messageCode);

		return result;
	}

	protected ModelAndView createModelAndView(final InceptionRecord iR) {
		ModelAndView result;
		final InceptionRecordForm iRF = new InceptionRecordForm(iR);
		result = this.createModelAndView(iRF, null);
		return result;
	}

	protected ModelAndView createModelAndView(final InceptionRecordForm iRF, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("records/inceptionRecord/create");
		result.addObject("iRF", iRF);
		result.addObject("cont", 0);
		result.addObject("messageCode", messageCode);

		return result;
	}

	//PERIOD RECORD M&V
	protected ModelAndView editModelAndView(final PeriodRecord pR) {
		ModelAndView result;
		final PeriodRecordForm pRF = new PeriodRecordForm(pR);
		result = this.editModelAndView(pRF, null);
		return result;
	}

	protected ModelAndView editModelAndView(final PeriodRecordForm pRF, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("records/periodRecord/edit");
		result.addObject("pRF", pRF);
		result.addObject("cont", 0);
		result.addObject("size", pRF.getPhoto().size());
		result.addObject("messageCode", messageCode);

		return result;
	}

	protected ModelAndView createModelAndView(final PeriodRecord pR) {
		ModelAndView result;
		final PeriodRecordForm pRF = new PeriodRecordForm(pR);
		result = this.createModelAndView(pRF, null);
		return result;
	}

	protected ModelAndView createModelAndView(final PeriodRecordForm pRF, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("records/periodRecord/create");
		result.addObject("pRF", pRF);
		result.addObject("cont", 0);
		result.addObject("messageCode", messageCode);

		return result;
	}

	//LEGAL RECORD M&V
	protected ModelAndView editModelAndView(final LegalRecord lR) {
		ModelAndView result;
		final LegalRecordForm lRF = new LegalRecordForm(lR);
		result = this.editModelAndView(lRF, null);
		return result;
	}

	protected ModelAndView editModelAndView(final LegalRecordForm lRF, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("records/legalRecord/edit");
		result.addObject("lRF", lRF);
		result.addObject("size", lRF.getApplicableLaws().size());
		result.addObject("cont", 0);
		result.addObject("messageCode", messageCode);

		return result;
	}

	protected ModelAndView createModelAndView(final LegalRecord lR) {
		ModelAndView result;
		final LegalRecordForm lRF = new LegalRecordForm(lR);
		result = this.createModelAndView(lRF, null);
		return result;
	}

	protected ModelAndView createModelAndView(final LegalRecordForm lRF, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("records/legalRecord/create");
		result.addObject("lRF", lRF);
		result.addObject("cont", 0);
		result.addObject("messageCode", messageCode);

		return result;
	}

	//MISC RECORD M&V
	protected ModelAndView editModelAndView(final MiscRecord mR) {
		ModelAndView result;
		result = this.editModelAndView(mR, null);
		return result;
	}

	protected ModelAndView editModelAndView(final MiscRecord mRF, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("records/miscRecord/edit");
		result.addObject("mRF", mRF);
		result.addObject("cont", 0);
		result.addObject("messageCode", messageCode);

		return result;
	}

	protected ModelAndView createModelAndView(final MiscRecord mR) {
		ModelAndView result;
		result = this.createModelAndView(mR, null);
		return result;
	}

	protected ModelAndView createModelAndView(final MiscRecord mRF, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("records/miscRecord/create");
		result.addObject("mRF", mRF);
		result.addObject("cont", 0);
		result.addObject("messageCode", messageCode);

		return result;
	}

	//LINK RECORD M&V
	protected ModelAndView editModelAndView(final LinkRecord lR) {
		ModelAndView result;
		result = this.editModelAndView(lR, null);
		return result;
	}

	protected ModelAndView editModelAndView(final LinkRecord lRF, final String messageCode) {
		ModelAndView result;
		final Collection<Brotherhood> brotherhoods = this.brotherhoodService.findAll();
		brotherhoods.remove(this.brotherhoodService.findOne(this.actorService.getActorLogged().getId()));

		result = new ModelAndView("records/linkRecord/edit");
		result.addObject("lRF", lRF);
		result.addObject("cont", 0);
		result.addObject("brotherhoods", brotherhoods);
		result.addObject("messageCode", messageCode);

		return result;
	}

	protected ModelAndView createModelAndView(final LinkRecord lR) {
		ModelAndView result;
		result = this.createModelAndView(lR, null);
		return result;
	}

	protected ModelAndView createModelAndView(final LinkRecord lRF, final String messageCode) {
		ModelAndView result;
		final Collection<Brotherhood> brotherhoods = this.brotherhoodService.findAll();
		brotherhoods.remove(this.brotherhoodService.findOne(this.actorService.getActorLogged().getId()));

		result = new ModelAndView("records/linkRecord/create");
		result.addObject("lRF", lRF);
		result.addObject("cont", 0);
		result.addObject("brotherhoods", brotherhoods);
		result.addObject("messageCode", messageCode);

		return result;
	}

	//**********************************************************************************
	//**
	//**		MISC
	//**
	//**********************************************************************************

	private ModelAndView forbiddenOperation() {
		return new ModelAndView("redirect:/");
	}

}
