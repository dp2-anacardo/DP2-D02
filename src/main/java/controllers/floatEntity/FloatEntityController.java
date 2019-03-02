
package controllers.floatEntity;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.FloatEntityService;
import controllers.AbstractController;
import domain.FloatEntity;

@Controller
@RequestMapping("/floatEntity")
public class FloatEntityController extends AbstractController {

	@Autowired
	private FloatEntityService	floatEntityService;


	@RequestMapping(value = "/listNotRegister", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int brotherhoodId) {

		ModelAndView result;
		Collection<FloatEntity> f = new ArrayList<FloatEntity>();
		f = this.floatEntityService.findAll();
		for (final FloatEntity f1 : f)
			if (!(f1.getBrotherhood().getId() == brotherhoodId))
				f.remove(f1);
		result = new ModelAndView("floatEntity/listNotRegister");
		result.addObject("floatEntity", f);
		result.addObject("requestURI", "floatEntity/listNotRegister.do");

		return result;
	}
}
