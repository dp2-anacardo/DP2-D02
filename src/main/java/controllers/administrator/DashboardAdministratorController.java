
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import domain.Procession;

@Controller
@RequestMapping("administrator")
public class DashboardAdministratorController {

	@Autowired
	private AdministratorService	administratorService;


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
		/* Q5 TODO:Revisar */
		//final Double RatioOfRequestToProcessionPerStatus = this.administratorService.getRatioOfRequestToProcessionPerStatus(procession, status);
		/* Q6 */
		final Double RatioOfRequestsApproveds = this.administratorService.getRatioOfRequestPerStatus().get(0);
		final Double RatioOfRequestsPendings = this.administratorService.getRatioOfRequestPerStatus().get(1);
		final Double RatioOfRequestsRejecteds = this.administratorService.getRatioOfRequestPerStatus().get(2);

		result = new ModelAndView("administrator/dashboard");
		result.addObject("AvgOfMembersPerBrotherhood", AvgOfMembersPerBrotherhood);
		result.addObject("MinOfMembersPerBrotherhood", MinOfMembersPerBrotherhood);
		result.addObject("MaxOfMembersPerBrotherhood", MaxOfMembersPerBrotherhood);
		result.addObject("SteddevOfMembersPerBrotherhood", SteddevOfMembersPerBrotherhood);
		result.addObject("LargestBrotherhood", LargestBrotherhood);
		result.addObject("SmallestBrotherhoood", SmallestBrotherhoood);
		result.addObject("ProcessionIn30Days", ProcessionIn30Days);
		//		result.addObject("RatioOfRequestToProcessionPerStatus", RatioOfRequestToProcessionPerStatus);
		result.addObject("RatioOfRequestsApproveds", RatioOfRequestsApproveds);
		result.addObject("RatioOfRequestsPendings", RatioOfRequestsPendings);
		result.addObject("RatioOfRequestsRejecteds", RatioOfRequestsRejecteds);

		return result;
	}
}
