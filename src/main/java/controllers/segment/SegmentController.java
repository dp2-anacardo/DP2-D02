
package controllers.segment;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Actor;
import domain.Brotherhood;
import domain.Parade;
import domain.Segment;
import security.LoginService;
import services.ActorService;
import services.BrotherhoodService;
import services.ParadeService;
import services.SegmentService;

@Controller
@RequestMapping("segment/brotherhood")
public class SegmentController extends AbstractController {

	@Autowired
	private SegmentService		segmentService;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private ParadeService		paradeService;
	@Autowired
	private BrotherhoodService	brotherhoodService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int paradeId) {
		final ModelAndView result;
		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
		final Brotherhood b = this.brotherhoodService.findOne(user.getId());
		final Collection<Segment> segments;
		final Parade p = this.paradeService.findOne(paradeId);

		if (p == null || !p.getBrotherhood().equals(b))
			result = new ModelAndView("redirect:/misc/403");
		else {
			segments = this.segmentService.getPathByParade(paradeId);
			result = new ModelAndView("segment/brotherhood/list");
			result.addObject("path", segments);
			result.addObject("requestURI", "segment/brotherhood/list");
			result.addObject("paradeId", paradeId);
		}

		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int paradeId) {
		final ModelAndView result;
		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
		final Brotherhood b = this.brotherhoodService.findOne(user.getId());
		final Parade p = this.paradeService.findOne(paradeId);

		if (p == null || !p.getBrotherhood().equals(b))
			result = new ModelAndView("redirect:/misc/403");
		else {
			final Segment s = this.segmentService.create(p);
			result = this.createEditModelAndView(s);
		}
		return result;

	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int segmentId) {
		final ModelAndView result;
		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
		final Brotherhood b = this.brotherhoodService.findOne(user.getId());
		final Segment s = this.segmentService.findOne(segmentId);
		if (s == null || !s.getParade().getBrotherhood().equals(b))
			result = new ModelAndView("redirect:/misc/403");
		else
			result = this.createEditModelAndView(s);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Segment segment, final BindingResult binding) {
		ModelAndView result;
		try {
			segment = this.segmentService.reconstruct(segment, binding);
			this.segmentService.save(segment);
			result = new ModelAndView("redirect:/segment/brotherhood/list.do?paradeId=" + segment.getParade().getId());
		} catch (final ValidationException e) {
			result = this.createEditModelAndView(segment);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(segment, "segment.commit.error");
		}

		return result;

	}
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int segmentId) {
		ModelAndView result;
		final Actor user = this.actorService.findByUsername(LoginService.getPrincipal().getUsername());
		final Brotherhood b = this.brotherhoodService.findOne(user.getId());
		final Segment s = this.segmentService.findOne(segmentId);
		if (s == null || !s.getParade().getBrotherhood().equals(b))
			result = new ModelAndView("redirect:/misc/403");
		else
			try {
				this.segmentService.delete(s);
				result = new ModelAndView("redirect:/segment/brotherhood/list.do?paradeId=" + s.getParade().getId());
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/misc/403");
			}
		return result;
	}

	private ModelAndView createEditModelAndView(final Segment s) {
		ModelAndView result;
		result = this.createEditModelAndView(s, null);
		return result;

	}

	private ModelAndView createEditModelAndView(final Segment s, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("segment/brotherhood/edit");
		result.addObject("message", messageCode);
		result.addObject("segment", s);
		return result;
	}

}
