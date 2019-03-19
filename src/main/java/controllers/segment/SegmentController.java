
package controllers.segment;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.BrotherhoodService;
import services.ParadeService;
import services.SegmentService;
import controllers.AbstractController;
import domain.Actor;
import domain.Brotherhood;
import domain.Parade;
import domain.Segment;

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
			result = new ModelAndView("parade/brotherhood/list");
			result.addObject("segments", segments);
			result.addObject("RequestURI", "segmet/brotherhood/list");
		}

		return result;

	}
}
