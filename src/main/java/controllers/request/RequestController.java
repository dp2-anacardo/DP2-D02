
package controllers.request;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.BrotherhoodService;
import services.MemberService;
import services.MessageService;
import services.PriorityService;
import services.ParadeService;
import services.RequestService;
import controllers.AbstractController;
import domain.Actor;
import domain.Brotherhood;
import domain.Member;
import domain.Message;
import domain.Parade;
import domain.Request;

@Controller
@RequestMapping("request")
public class RequestController extends AbstractController {

	@Autowired
	RequestService		requestService;
	@Autowired
	ActorService		actorService;
	@Autowired
	MemberService		memberService;
	@Autowired
	ParadeService	paradeService;
	@Autowired
	BrotherhoodService	brotherhoodService;
	@Autowired
	MessageService		messageService;
	@Autowired
	PriorityService		priorityService;


	@RequestMapping(value = "/member/list")
	public ModelAndView list() {
		ModelAndView result;
		Collection<Request> requests;

		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
		final Member m = this.memberService.findOne(user.getId());
		requests = this.requestService.getRequestsByMember(m);

		result = new ModelAndView("request/member/list");
		result.addObject("requests", requests);
		result.addObject("requestURI", "request/member/list.do");

		return result;

	}

	@RequestMapping(value = "/member/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Collection<Parade> parades;
		final Request request = this.requestService.create();
		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
		final Member m = this.memberService.findOne(user.getId());
		parades = this.requestService.getParadesByMember(m);
		result = new ModelAndView("request/member/edit");
		result.addObject("parades", parades);
		result.addObject("request", request);

		return result;
	}

	@RequestMapping(value = "/member/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("request") final Request request, final BindingResult binding) {
		ModelAndView result;
		Collection<Parade> parades;
		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
		final Member m = this.memberService.findOne(user.getId());
		parades = this.requestService.getParadesByMember(m);
		try {
			request.setVersion(0);
			Assert.notNull(request.getParade());
			Collection<Request> requests;
			requests = this.requestService.getRequestsByMember(m);
			for (final Request r : requests)
				if (r.getParade().equals(request.getParade()))
					Assert.isTrue(false);
			this.requestService.save(request);
			result = new ModelAndView("redirect:/request/member/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("request/member/edit");
			result.addObject("message", "request.commit.error");
			result.addObject("parades", parades);
		}
		return result;
	}
	@RequestMapping(value = "/member,brotherhood/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int requestId) {
		ModelAndView result;
		Request r;
		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());

		r = this.requestService.findOne(requestId);
		if (r == null)
			result = new ModelAndView("redirect:/misc/403");
		else
			try {
				if (user instanceof Member)
					Assert.isTrue(r.getMember().equals(this.memberService.findOne(user.getId())));
				else if (user instanceof Brotherhood)
					Assert.isTrue(r.getParade().getBrotherhood().equals(this.brotherhoodService.findOne(user.getId())));
				result = new ModelAndView("request/member,brotherhood/show");
				result.addObject("r", r);
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/misc/403");
			}
		return result;

	}

	@RequestMapping(value = "/member/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int requestId) {
		ModelAndView result;
		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
		final Member m = this.memberService.findOne(user.getId());
		try {
			final Request r = this.requestService.findOne(requestId);
			Assert.notNull(r);
			Assert.isTrue(r.getMember().equals(m));
			Assert.isTrue(r.getStatus().equals("PENDING"));
			this.requestService.delete(r);
			result = new ModelAndView("redirect:/request/member/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/request/member/list.do");
		}
		return result;

	}

	//AQUI EMPIEZA LA PARTE DE BROTHERHOOD

	@RequestMapping(value = "/brotherhood/list", method = RequestMethod.GET)
	public ModelAndView listB(@RequestParam final int paradeId) {
		ModelAndView result;
		Collection<Request> requests;

		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
		final Brotherhood b = this.brotherhoodService.findOne(user.getId());
		final Parade p = this.paradeService.findOne(paradeId);
		if (p == null || !p.getBrotherhood().equals(b))
			result = new ModelAndView("redirect:/misc/403");
		else {
			requests = this.requestService.getRequestByParade(p);

			result = new ModelAndView("request/brotherhood/list");
			result.addObject("requests", requests);
			result.addObject("requestURI", "request/brotherhood/list.do");

		}
		return result;

	}

	@RequestMapping(value = "/brotherhood/reject", method = RequestMethod.GET)
	public ModelAndView reject(@RequestParam final int requestId) {
		ModelAndView result;
		Request r;

		r = this.requestService.findOne(requestId);

		if (r == null)
			result = new ModelAndView("redirect:/misc/403");
		else
			result = this.createEditModelAndView1(r, null);
		return result;

	}
	@RequestMapping(value = "/brotherhood/reject", method = RequestMethod.POST, params = "save")
	public ModelAndView saveR(final Request r, final BindingResult binding) {
		ModelAndView result;
		if (StringUtils.isEmpty(r.getComment())) {
			binding.rejectValue("comment", "error.comment");
			result = this.createEditModelAndView1(r, "error.comment");
		} else
			try {
				final Request r2 = this.requestService.findOne(r.getId());
				r.setMember(r2.getMember());
				r.setParade(r2.getParade());
				r.setStatus(r2.getStatus());
				r.setVersion(r2.getVersion());
				this.requestService.rejectRequest(r);
				final Message message = this.messageService.create();
				final Collection<Actor> recipients = new ArrayList<>();
				recipients.add(r.getMember());
				recipients.add(r.getParade().getBrotherhood());
				message.setRecipients(recipients);
				message.setPriority(this.priorityService.getHighPriority());
				message.setSubject("A request changed its status \n Una petición ha cambiado su estatus");
				message.setBody("A request has been rejected \n Una petición ha sido rechazada");
				this.messageService.save(message);
				result = new ModelAndView("redirect:/request/brotherhood/list.do?paradeId=" + r2.getParade().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView1(r, "request.commit.error");
			}
		return result;
	}

	public ModelAndView createEditModelAndView1(final Request r, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("request/brotherhood/reject");
		result.addObject("r", r);
		result.addObject("message", messageCode);
		return result;
	}

	@RequestMapping(value = "/brotherhood/accept", method = RequestMethod.GET)
	public ModelAndView accept(@RequestParam final int requestId) {
		ModelAndView result;
		Request r;

		r = this.requestService.findOne(requestId);

		if (r == null)
			result = new ModelAndView("redirect:/misc/403");
		else
			result = this.createEditModelAndView2(r, null);
		return result;

	}

	@RequestMapping(value = "/brotherhood/accept", method = RequestMethod.POST, params = "save")
	public ModelAndView accept(final Request r, final BindingResult binding) {
		ModelAndView result;
		final Request r2 = this.requestService.findOne(r.getId());
		if (r2 == null)
			result = new ModelAndView("redirect:/misc/403");
		else {
			final Collection<Request> requests = this.requestService.getRequestAcceptedByParade(r2.getParade());
			boolean notFree = false;
			for (final Request r3 : requests)
				if (r3.getPositionColumn() == r.getPositionColumn() && r3.getPositionRow() == r.getPositionRow())
					notFree = true;
			if (notFree || r.getPositionRow() >= r2.getParade().getMaxRow() || r.getPositionColumn() >= r2.getParade().getMaxColumn()) {
				r.setParade(r2.getParade());
				result = this.createEditModelAndView2(r, "error.position");
			} else
				try {
					r.setMember(r2.getMember());
					r.setParade(r2.getParade());
					r.setStatus(r2.getStatus());
					r.setVersion(r2.getVersion());
					this.requestService.acceptRequest(r);
					final Message message = this.messageService.create();
					final Collection<Actor> recipients = new ArrayList<>();
					recipients.add(r.getMember());
					recipients.add(r.getParade().getBrotherhood());
					message.setRecipients(recipients);
					message.setPriority(this.priorityService.getHighPriority());
					message.setSubject("A request changed its status \n Una petición ha cambiado su estatus");
					message.setBody("A request has been accepted \n Una petición ha sido aceptada");
					this.messageService.save(message);
					result = new ModelAndView("redirect:/request/brotherhood/list.do?paradeId=" + r2.getParade().getId());
				} catch (final Throwable oops) {
					result = this.createEditModelAndView1(r, "request.commit.error");
				}
		}

		return result;
	}

	public ModelAndView createEditModelAndView2(final Request r, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("request/brotherhood/accept");
		result.addObject("r", r);
		result.addObject("message", messageCode);
		final List<Integer> positions = this.calculateFreePosition(r.getParade());
		if (positions.size() > 0) {
			result.addObject("row", positions.get(0));
			result.addObject("column", positions.get(1));
			result.addObject("messageFullB", false);
		} else {
			result.addObject("messageFull", "request.parade.full");
			result.addObject("messageFullB", true);
		}
		return result;
	}

	public List<Integer> calculateFreePosition(final Parade p) {
		final List<Integer> result = new ArrayList<Integer>();
		final Integer maxR = p.getMaxRow();
		final Integer maxC = p.getMaxColumn();
		final Collection<Request> requests = this.requestService.getRequestAcceptedByParade(p);
		loop: for (int i = 0; i < maxR; i++)
			for (int j = 0; j < maxC; j++)
				if (requests.size() > 0) {
					for (final Request r : requests)
						if (i != r.getPositionRow() && j != r.getPositionColumn()) {
							result.add(i);
							result.add(j);
							break loop;
						}

				} else {
					result.add(i);
					result.add(j);
					break loop;
				}

		return result;

	}

}
