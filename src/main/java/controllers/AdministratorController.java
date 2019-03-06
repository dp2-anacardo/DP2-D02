/*
 * AdministratorController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import domain.Actor;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	administratorService;


	// Action-1 ---------------------------------------------------------------		

	@RequestMapping("/action-1")
	public ModelAndView action1() {
		ModelAndView result;

		result = new ModelAndView("administrator/action-1");

		return result;
	}

	// Action-2 ---------------------------------------------------------------

	@RequestMapping("/action-2")
	public ModelAndView action2() {
		ModelAndView result;

		result = new ModelAndView("administrator/action-2");

		return result;
	}

	@RequestMapping(value = "/actorList", method = RequestMethod.GET)
	public ModelAndView actorList() {
		ModelAndView result;

		result = new ModelAndView("administrator/actorList");

		final Collection<Actor> actors = this.actorService.findAll();
		result.addObject("actors", actors);
		result.addObject("requestURI", "administrator/actorList");

		return result;
	}

	@RequestMapping(value = "/actorList/calculateScore", method = RequestMethod.GET)
	public ModelAndView calculateScore() {
		ModelAndView result;

		this.administratorService.computeAllScores();

		//		final Collection<Actor> actors = this.actorService.findAll();
		//		result.addObject("actors", actors);
		//		result.addObject("requestURI", "administrator/actorList");

		result = new ModelAndView("redirect:administrator/actorList.do");

		return result;
	}
}
