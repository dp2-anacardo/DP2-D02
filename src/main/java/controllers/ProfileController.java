/*
 * ProfileController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.AdministratorService;
import services.BrotherhoodService;
import services.ChapterService;
import services.MemberService;
import services.SponsorService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import domain.Actor;
import domain.Administrator;
import domain.Brotherhood;
import domain.Chapter;
import domain.Member;
import domain.Sponsor;

@Controller
@RequestMapping("profile")
public class ProfileController extends AbstractController {

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private MemberService			memberService;

	@Autowired
	private ChapterService			chapterService;

	@Autowired
	private SponsorService			sponsorService;


	// Action-1 ---------------------------------------------------------------

	@RequestMapping(value = "/action-1", method = RequestMethod.GET)
	public ModelAndView action1() {
		final ModelAndView result = new ModelAndView("profile/action-1");
		final Actor user = this.actorService.getActorLogged();
		final UserAccount userAccount = LoginService.getPrincipal();

		if (userAccount.getAuthorities().iterator().next().getAuthority().equals("MEMBER")) {
			Member member;
			member = this.memberService.findOne(user.getId());
			Assert.notNull(member);
			result.addObject("member", member);
		}

		if (userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN")) {
			Administrator administrador1;
			administrador1 = this.administratorService.findOne(user.getId());
			Assert.notNull(administrador1);
			result.addObject("administrator", administrador1);
		}

		if (userAccount.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD")) {
			Brotherhood bro;
			bro = this.brotherhoodService.findOne(user.getId());
			Assert.notNull(bro);
			result.addObject("brotherhood", bro);
		}
		if (userAccount.getAuthorities().iterator().next().getAuthority().equals("CHAPTER")) {
			Chapter chapter;
			chapter = this.chapterService.findOne(user.getId());
			Assert.notNull(chapter);
			result.addObject("chapter", chapter);
		}
		if (userAccount.getAuthorities().iterator().next().getAuthority().equals("SPONSOR")) {
			Sponsor sponsor;
			sponsor = this.sponsorService.findOne(user.getId());
			Assert.notNull(sponsor);
			result.addObject("sponsor", sponsor);
		}
		return result;
	}

	@RequestMapping(value = "/exportJSON", method = RequestMethod.GET)
	public ModelAndView exportJSON() {
		final ModelAndView result = new ModelAndView("profile/exportJSON");
		final Actor user = this.actorService.getActorLogged();
		final UserAccount userAccount = LoginService.getPrincipal();
		final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

		if (userAccount.getAuthorities().iterator().next().getAuthority().equals("ADMIN")) {
			Administrator administrador1;
			administrador1 = this.administratorService.findOne(user.getId());
			Assert.notNull(administrador1);
			final String json = gson.toJson(administrador1);
			result.addObject("json", json);
		}

		if (userAccount.getAuthorities().iterator().next().getAuthority().equals("BROTHERHOOD")) {
			Brotherhood bro;
			bro = this.brotherhoodService.findOne(user.getId());
			Assert.notNull(bro);
			final String json = gson.toJson(bro);
			result.addObject("json", json);
		}

		if (userAccount.getAuthorities().iterator().next().getAuthority().equals("MEMBER")) {
			Member member;
			member = this.memberService.findOne(user.getId());
			Assert.notNull(member);
			//member.setBoxes(null);
			final String json = gson.toJson(member);
			result.addObject("json", json);
		}

		if (userAccount.getAuthorities().iterator().next().getAuthority().equals("CHAPTER")) {
			Chapter chapter;
			chapter = this.chapterService.findOne(user.getId());
			Assert.notNull(chapter);
			//member.setBoxes(null);
			final String json = gson.toJson(chapter);
			result.addObject("json", json);
		}

		if (userAccount.getAuthorities().iterator().next().getAuthority().equals("SPONSOR")) {
			Sponsor sponsor;
			sponsor = this.sponsorService.findOne(user.getId());
			Assert.notNull(sponsor);
			//member.setBoxes(null);
			final String json = gson.toJson(sponsor);
			result.addObject("json", json);
		}
		return result;
	}

	@RequestMapping(value = "/deleteInformation", method = RequestMethod.GET)
	public ModelAndView messageDelete() {
		ModelAndView result;
		try {
			Assert.isTrue(this.actorService.getActorLogged() != null);
			this.actorService.deleteInformation();
			result = new ModelAndView("redirect:/j_spring_security_logout");
		} catch (final Exception e) {
			return this.forbiddenOperation();
		}

		return result;
	}
	private ModelAndView forbiddenOperation() {
		return new ModelAndView("redirect:/action-1.do");
	}

}
