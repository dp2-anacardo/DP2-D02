
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PriorityService;
import domain.Priority;
import forms.PriorityForm;

@Controller
@RequestMapping("priority")
public class PriorityController extends AbstractController {

	@Autowired
	private PriorityService	priorityService;


	@ExceptionHandler(TypeMismatchException.class)
	public ModelAndView handleMismatchException(final TypeMismatchException oops) {
		return new ModelAndView("redirect:/administrator/list.do");
	}

	@RequestMapping(value = "/administrator/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Priority> priorities;

		priorities = this.priorityService.findAll();

		result = new ModelAndView("priority/administrator/list");
		result.addObject("priorities", priorities);
		result.addObject("requestURI", "priority/administrator/list.do");

		return result;
	}

	@RequestMapping(value = "/administrator/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int priorityId) {
		ModelAndView result;
		Priority priority;

		priority = this.priorityService.findOne(priorityId);

		result = new ModelAndView("priority/administrator/show");
		result.addObject("priority", priority);
		result.addObject("requestURI", "priority/administrator/show.do");

		return result;
	}

	@RequestMapping(value = "/administrator/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final PriorityForm priorityForm;

		priorityForm = new PriorityForm();
		result = this.createEditModelAndView(priorityForm);

		return result;
	}

	@RequestMapping(value = "/administrator/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int priorityId) {
		ModelAndView result = null;
		Priority priority;
		final PriorityForm priorityForm;

		try {
			priority = this.priorityService.findOne(priorityId);
			Assert.notNull(priority);
			priorityForm = new PriorityForm();

			priorityForm.setId(priority.getId());
			priorityForm.setVersion(priority.getVersion());
			priorityForm.setTitleEN(priority.getName().get("EN"));
			priorityForm.setTitleES(priority.getName().get("ES"));

			result = this.createEditModelAndView(priorityForm);
		} catch (final Exception e) {

		}

		return result;
	}
	@RequestMapping(value = "/administrator/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("priorityForm") @Valid final PriorityForm priorityForm, final BindingResult binding) {
		ModelAndView result;
		Priority priority;

		priority = this.priorityService.reconstruct(priorityForm, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(priorityForm);
		else if (this.priorityService.exists(priority))
			result = this.createEditModelAndView(priorityForm, "priority.duplicated");
		else
			try {
				this.priorityService.save(priority);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(priorityForm, "priority.commit.error");
			}

		return result;
	}
	@RequestMapping(value = "/administrator/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final PriorityForm priorityForm, final BindingResult binding) {
		ModelAndView result;
		final Priority priority;

		try {
			priority = this.priorityService.findOne(priorityForm.getId());
			if (this.priorityService.getPriorityCount(priority) > 0)
				result = this.createEditModelAndView(priorityForm, "priority.delete.inuse");
			else {
				this.priorityService.delete(priority);
				result = new ModelAndView("redirect:list.do");
			}
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(priorityForm, "priority.commit.error");
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final PriorityForm priorityForm) {
		ModelAndView result;

		result = this.createEditModelAndView(priorityForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final PriorityForm priorityForm, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("priority/administrator/edit");
		result.addObject("priorityForm", priorityForm);
		result.addObject("message", messageCode);

		return result;
	}

}
