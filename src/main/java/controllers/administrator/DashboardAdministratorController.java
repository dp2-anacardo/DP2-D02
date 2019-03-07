
package controllers.administrator;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.AreaService;
import services.ProcessionService;
import controllers.AbstractController;
import domain.Area;
import domain.Member;
import domain.Procession;

@Controller
@RequestMapping("administrator")
public class DashboardAdministratorController extends AbstractController {

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ProcessionService		processionService;

	@Autowired
	private AreaService				areaService;


	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		final ModelAndView result;
		/* Q1 */
		final Double AvgOfMembersPerBrotherhood = this.administratorService.getStatsMemberPerBrotherhood().get(0);
		final Double MinOfMembersPerBrotherhood = this.administratorService.getStatsMemberPerBrotherhood().get(1);
		final Double MaxOfMembersPerBrotherhood = this.administratorService.getStatsMemberPerBrotherhood().get(2);
		final Double SteddevOfMembersPerBrotherhood = this.administratorService.getStatsMemberPerBrotherhood().get(3);
		/* Q2 */
		final String LargestBrotherhood = this.administratorService.getLargestBrotherhood().getName();
		/* Q3 */
		final String SmallestBrotherhoood = this.administratorService.getSmallestBrotherhoood().getName();
		/* Q4 */
		final Collection<Procession> ProcessionIn30Days = this.administratorService.getProcessionIn30Days();

		/* Q5 */

		final Collection<Double> RatioOfRequestToProcessionPerAPPROVED = new ArrayList<Double>();
		final Collection<Procession> procesiones = this.processionService.findAll();
		for (final Procession p : procesiones) {
			final Double d = this.administratorService.getRatioOfRequestToProcessionPerStatus(p, "APPROVED");
			if (d == null)
				RatioOfRequestToProcessionPerAPPROVED.add(0.0);
			else
				RatioOfRequestToProcessionPerAPPROVED.add(d);
		}

		final Collection<Double> RatioOfRequestToProcessionPerREJECTED = new ArrayList<Double>();
		for (final Procession p : procesiones) {
			final Double e = this.administratorService.getRatioOfRequestToProcessionPerStatus(p, "REJECTED");
			if (e == null)
				RatioOfRequestToProcessionPerREJECTED.add(0.0);
			else
				RatioOfRequestToProcessionPerREJECTED.add(e);
		}

		final Collection<Double> RatioOfRequestToProcessionPerPENDING = new ArrayList<Double>();
		for (final Procession p : procesiones) {
			final Double f = this.administratorService.getRatioOfRequestToProcessionPerStatus(p, "PENDING");
			if (f == null)
				RatioOfRequestToProcessionPerPENDING.add(0.0);
			else
				RatioOfRequestToProcessionPerPENDING.add(f);
		}

		/* Q6 */
		final Double RatioOfRequestsApproveds = this.administratorService.getRatioOfRequestPerStatus().get(0);
		final Double RatioOfRequestsPendings = this.administratorService.getRatioOfRequestPerStatus().get(1);
		final Double RatioOfRequestsRejecteds = this.administratorService.getRatioOfRequestPerStatus().get(2);

		/* Q8 */
		final Collection<Member> MembersAtLeast10PercentOfNumberOfRequestAccepted = this.administratorService.getMembersAtLeast10PercentOfNumberOfRequestAccepted();

		/* Q10 */
		//TODO: CountOfBrotherhoodPerArea(areaId))
		final Collection<Area> areas = this.areaService.findAll();
		final Collection<Double> CountOfBrotherhoodPerArea = new ArrayList<Double>();
		for (final Area a : areas) {
			final Integer areaId = a.getId();
			final Double res = this.administratorService.getCountOfBrotherhoodPerArea(areaId);
			CountOfBrotherhoodPerArea.add(res);
		}

		final Double MinBrotherhoodPerArea = this.administratorService.getMinBrotherhoodPerArea();
		final Double MaxBrotherhoodPerArea = this.administratorService.getMaxBrotherhoodPerArea();
		final Double AvgBrotherhoodPerArea = this.administratorService.getAvgBrotherhoodPerArea();
		final Double StddevBrotherhoodPerArea = this.administratorService.getStddevBrotherhoodPerArea();

		/* Q11 */
		final Double MinResultFinder = this.administratorService.getStatsFinders().get(0);
		final Double MaxResultFinder = this.administratorService.getStatsFinders().get(1);
		final Double AvgResultFinder = this.administratorService.getStatsFinders().get(2);
		final Double StddevResultFinder = this.administratorService.getStatsFinders().get(3);

		/* Q12 */
		final Double RatioOfNotEmptyFinders = this.administratorService.getRatioOfNotEmptyFinders();
		final Double RatioOfEmptyFinders = this.administratorService.getRatioOfEmptyFinders();

		result = new ModelAndView("administrator/dashboard");

		result.addObject("AvgOfMembersPerBrotherhood", AvgOfMembersPerBrotherhood);
		result.addObject("MinOfMembersPerBrotherhood", MinOfMembersPerBrotherhood);
		result.addObject("MaxOfMembersPerBrotherhood", MaxOfMembersPerBrotherhood);
		result.addObject("SteddevOfMembersPerBrotherhood", SteddevOfMembersPerBrotherhood);

		result.addObject("LargestBrotherhood", LargestBrotherhood);

		result.addObject("SmallestBrotherhoood", SmallestBrotherhoood);

		result.addObject("ProcessionIn30Days", ProcessionIn30Days);

		result.addObject("RatioOfRequestsApproveds", RatioOfRequestsApproveds);
		result.addObject("RatioOfRequestsPendings", RatioOfRequestsPendings);
		result.addObject("RatioOfRequestsRejecteds", RatioOfRequestsRejecteds);

		result.addObject("RatioOfRequestToProcessionPerAPPROVED", RatioOfRequestToProcessionPerAPPROVED);
		result.addObject("RatioOfRequestToProcessionPerREJECTED", RatioOfRequestToProcessionPerREJECTED);
		result.addObject("RatioOfRequestToProcessionPerPENDING", RatioOfRequestToProcessionPerPENDING);

		result.addObject("MembersAtLeast10PercentOfNumberOfRequestAccepted", MembersAtLeast10PercentOfNumberOfRequestAccepted);

		result.addObject("CountOfBrotherhoodPerArea", CountOfBrotherhoodPerArea);
		result.addObject("MinBrotherhoodPerArea", MinBrotherhoodPerArea);
		result.addObject("MaxBrotherhoodPerArea", MaxBrotherhoodPerArea);
		result.addObject("AvgBrotherhoodPerArea", AvgBrotherhoodPerArea);
		result.addObject("StddevBrotherhoodPerArea", StddevBrotherhoodPerArea);

		result.addObject("MinResultFinder", MinResultFinder);
		result.addObject("MaxResultFinder", MaxResultFinder);
		result.addObject("AvgResultFinder", AvgResultFinder);
		result.addObject("StddevResultFinder", StddevResultFinder);

		result.addObject("procesiones", procesiones);
		result.addObject("areas", areas);

		result.addObject("RatioOfNotEmptyFinders", RatioOfNotEmptyFinders);
		result.addObject("RatioOfEmptyFinders", RatioOfEmptyFinders);

		return result;
	}
}
