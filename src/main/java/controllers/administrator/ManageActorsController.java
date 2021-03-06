/*
 * AdministratorController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.administrator;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import domain.Member;

@Controller
@RequestMapping("/administrator")
public class ManageActorsController extends AbstractController {

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	administratorService;


	@RequestMapping(value = "/actorList", method = RequestMethod.GET)
	public ModelAndView actorList() {
		ModelAndView result;

		result = new ModelAndView("administrator/actorList");

		final Collection<Actor> actors = this.actorService.findAll();
		final Collection<Actor> actorList = new ArrayList<Actor>();

		actorList.addAll(actors);

		for (final Actor a : actors)
			if (a instanceof Administrator)
				actorList.remove(a);

		result.addObject("actors", actorList);
		result.addObject("requestURI", "administrator/actorList");

		return result;
	}

	@RequestMapping(value = "/actorList/calculateScore", method = RequestMethod.GET)
	public ModelAndView calculateScore() {
		ModelAndView result;

		this.administratorService.computeAllScores();

		result = new ModelAndView("redirect:/administrator/actorList.do");

		return result;
	}

	@RequestMapping(value = "/actorList/calculateSpam", method = RequestMethod.GET)
	public ModelAndView calculateSpam() {
		ModelAndView result;

		this.administratorService.computeAllSpam();

		result = new ModelAndView("redirect:/administrator/actorList.do");

		return result;
	}

	@RequestMapping(value = "/actorList/ban", method = RequestMethod.GET)
	public ModelAndView ban(@RequestParam final int actorId) {
		ModelAndView result;

		final Actor actor = this.actorService.findOne(actorId);
		actor.setIsBanned(true);
		this.actorService.save(actor);

		result = new ModelAndView("redirect:/administrator/actorList.do");

		return result;
	}
	@RequestMapping(value = "/actorList/unban", method = RequestMethod.GET)
	public ModelAndView unban(@RequestParam final int actorId) {
		ModelAndView result;

		final Actor actor = this.actorService.findOne(actorId);
		actor.setIsBanned(false);
		this.actorService.save(actor);

		result = new ModelAndView("redirect:/administrator/actorList.do");

		return result;
	}

	@RequestMapping(value = "/actorList/showMember", method = RequestMethod.GET)
	public ModelAndView showMember(@RequestParam final int actorId) {
		ModelAndView result;

		result = new ModelAndView("administrator/actorList/showMember");

		try {

			final Actor actor = this.actorService.findOne(actorId);
			Assert.notNull(actor);

			Boolean member = false;
			if (actor instanceof Member)
				member = true;

			result.addObject("actor", actor);
			result.addObject("member", member);

		} catch (final Throwable oops) {
			return new ModelAndView("redirect:/misc/403");
		}

		return result;
	}

	@RequestMapping(value = "/actorList/desactivate", method = RequestMethod.GET)
	public ModelAndView desactivateSponsorship() {
		ModelAndView result;

		this.administratorService.desactivateExpiredSponsorships();

		result = new ModelAndView("redirect:/administrator/actorList.do");

		return result;
	}
}
