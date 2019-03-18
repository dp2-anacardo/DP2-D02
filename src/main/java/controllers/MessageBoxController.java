
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.MessageBoxService;
import domain.Actor;
import domain.MessageBox;

@Controller
@RequestMapping("/messageBox")
public class MessageBoxController extends AbstractController {

	@Autowired
	private MessageBoxService	messageBoxService;

	@Autowired
	private ActorService		actorService;


	@ExceptionHandler(TypeMismatchException.class)
	public ModelAndView handleMismatchException(final TypeMismatchException oops) {
		return new ModelAndView("redirect:/messageBox/list.do");
	}

	// List -------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<MessageBox> messageBoxes;
		final Collection<MessageBox> sons = new ArrayList<MessageBox>();

		messageBoxes = this.messageBoxService.findAllByActor(this.actorService.getActorLogged().getId());

		for (final MessageBox m : messageBoxes) {
			if (m.getSons() != null && m.getSons().size() > 0)
				sons.addAll(m.getSons());

			if (m.getFather() != null && m.getIsSystem() == false)
				sons.add(m);
		}

		messageBoxes.removeAll(sons);

		result = new ModelAndView("messageBox/list");
		result.addObject("messageBoxes", messageBoxes);
		result.addObject("requestURI", "messageBox/list.do");

		return result;
	}
	// Creation ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		MessageBox messageBox;

		messageBox = this.messageBoxService.create();
		result = this.createEditModelAndView(messageBox);

		return result;
	}

	// Edition -------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int messageBoxID) {
		ModelAndView result;
		MessageBox messageBox;

		try {
			final Actor principal = this.actorService.getActorLogged();
			messageBox = this.messageBoxService.findOne(messageBoxID);
			Assert.isTrue(principal.getBoxes().contains(messageBox));
			Assert.isTrue(messageBox.getIsSystem() == false);
		} catch (final Exception e) {
			result = this.forbiddenOperation();
			return result;
		}

		result = this.createEditModelAndView(messageBox);

		final Collection<MessageBox> actorBoxes = this.messageBoxService.findAllByActor(this.actorService.getActorLogged().getId());
		final Collection<MessageBox> messageBoxes = new ArrayList<MessageBox>();

		if (messageBox.getFather() == null) {
			messageBoxes.addAll(actorBoxes);
			messageBoxes.remove(messageBox);
			if (messageBox.getSons() != null && messageBox.getSons().size() > 0)
				messageBoxes.removeAll(messageBox.getSons());

			for (final MessageBox b : actorBoxes) {
				if (b.getIsSystem() == true)
					messageBoxes.remove(b);

				if (b.getFather() != null)
					messageBoxes.remove(b);
			}

		}

		result.addObject("actorBoxes", messageBoxes);
		result.addObject("messageBoxID", messageBoxID);

		return result;
	}
	// Save -------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final MessageBox messageBox, final BindingResult binding) {
		ModelAndView result;
		MessageBox msgBox;
		MessageBox security;

		try {
			msgBox = this.messageBoxService.reconstruct(messageBox, binding);

			if (binding.hasErrors())
				result = this.createEditModelAndView(msgBox);
			else if (this.messageBoxService.exists(msgBox))
				result = this.createEditModelAndView(msgBox, "messageBox.duplicated");
			else {
				try {
					final Actor principal = this.actorService.getActorLogged();
					if (messageBox.getId() != 0) {
						security = this.messageBoxService.findOne(messageBox.getId());
						Assert.isTrue(principal.getBoxes().contains(security));
						Assert.isTrue(security.getIsSystem() == false);
					}
				} catch (final Exception e) {
					result = this.forbiddenOperation();
					return result;
				}

				this.messageBoxService.save(msgBox);
				result = new ModelAndView("redirect:list.do");
			}
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(messageBox, "messageBox.commit.error");
		}

		return result;
	}

	// Delete ------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final MessageBox messageBox, final BindingResult binding) {
		ModelAndView result;
		MessageBox msgBox;
		MessageBox security;

		try {
			try {
				final Actor principal = this.actorService.getActorLogged();
				security = this.messageBoxService.findOne(messageBox.getId());
				Assert.isTrue(principal.getBoxes().contains(security));
				//Assert.isTrue(security.getMessages().size() == 0);

			} catch (final Exception e) {
				result = this.forbiddenOperation();
				return result;
			}
			msgBox = this.messageBoxService.reconstruct(messageBox, binding);
			this.messageBoxService.delete(msgBox);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {

			result = this.createEditModelAndView(messageBox, "messageBox.commit.error");
		}

		return result;
	}
	// Nest ------------------------------------------------------
	@RequestMapping(value = "/nest", method = RequestMethod.GET)
	public ModelAndView move(@RequestParam final int srcBoxID, @RequestParam final int destBoxID) {
		ModelAndView result;
		MessageBox son;
		MessageBox father;

		try {
			try {
				final Actor principal = this.actorService.getActorLogged();
				son = this.messageBoxService.findOne(srcBoxID);
				father = this.messageBoxService.findOne(destBoxID);

				Assert.isTrue(principal.getBoxes().contains(son));
				Assert.isTrue(principal.getBoxes().contains(father));
				Assert.isTrue(!son.getSons().contains(father) && !father.getSons().contains(son));

			} catch (final Exception e) {
				result = this.forbiddenOperation();
				return result;
			}
			this.messageBoxService.nestMessageBox(son, father);
			result = new ModelAndView("redirect:list.do?messageBoxID=" + destBoxID + "");
			result.addObject("messageBox", srcBoxID);
		} catch (final Throwable oops) {
			son = this.messageBoxService.findOne(srcBoxID);
			result = this.createEditModelAndView(son, "messageBox.commit.error");
		}

		return result;
	}

	// Unnest -------------------------------------------------------------
	@RequestMapping(value = "/unnest", method = RequestMethod.GET)
	public ModelAndView unnest(@RequestParam final int srcBoxID, @RequestParam final int destBoxID) {
		ModelAndView result;
		MessageBox son;
		MessageBox father;

		try {
			try {
				final Actor principal = this.actorService.getActorLogged();
				son = this.messageBoxService.findOne(srcBoxID);
				father = this.messageBoxService.findOne(destBoxID);

				Assert.isTrue(principal.getBoxes().contains(son));
				Assert.isTrue(principal.getBoxes().contains(father));
				Assert.isTrue(son.getFather() == father);
				Assert.isTrue(father.getSons().contains(son));

			} catch (final Exception e) {
				result = this.forbiddenOperation();
				return result;
			}
			this.messageBoxService.unnestMessageBox(son, father);
			result = new ModelAndView("redirect:list.do?messageBoxID=" + destBoxID + "");
			result.addObject("messageBox", srcBoxID);
		} catch (final Throwable oops) {
			son = this.messageBoxService.findOne(srcBoxID);
			result = this.createEditModelAndView(son, "messageBox.commit.error");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------
	protected ModelAndView createEditModelAndView(final MessageBox messageBox) {
		ModelAndView result;

		result = this.createEditModelAndView(messageBox, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MessageBox messageBox, final String message) {
		ModelAndView result;

		if (messageBox.getId() == 0)
			result = new ModelAndView("messageBox/create");
		else
			result = new ModelAndView("messageBox/edit");

		result.addObject("messageBox", messageBox);
		result.addObject("message", message);

		return result;
	}

	private ModelAndView forbiddenOperation() {
		return new ModelAndView("redirect:/messageBox/list.do");
	}

}
