
package controllers.administrator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Area;
import domain.Brotherhood;
import domain.Member;
import domain.Parade;
import domain.Position;
import services.AdministratorService;
import services.AreaService;
import services.ParadeService;
import services.PositionService;

@Controller
@RequestMapping("administrator")
public class DashboardAdministratorController extends AbstractController {

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ParadeService			paradeService;

	@Autowired
	private AreaService				areaService;

	@Autowired
	private PositionService			positionService;


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
		final Collection<Parade> ParadeIn30Days = this.administratorService.getParadeIn30Days();

		/* Q5 */

		final Collection<Double> RatioOfRequestToParadePerAPPROVED = new ArrayList<Double>();
		final Collection<Parade> procesiones = this.paradeService.findAll();
		for (final Parade p : procesiones) {
			final Double d = this.administratorService.getRatioOfRequestToParadePerStatus(p, "APPROVED");
			if (d == null)
				RatioOfRequestToParadePerAPPROVED.add(0.0);
			else
				RatioOfRequestToParadePerAPPROVED.add(d);
		}

		final Collection<Double> RatioOfRequestToParadePerREJECTED = new ArrayList<Double>();
		for (final Parade p : procesiones) {
			final Double e = this.administratorService.getRatioOfRequestToParadePerStatus(p, "REJECTED");
			if (e == null)
				RatioOfRequestToParadePerREJECTED.add(0.0);
			else
				RatioOfRequestToParadePerREJECTED.add(e);
		}

		final Collection<Double> RatioOfRequestToParadePerPENDING = new ArrayList<Double>();
		for (final Parade p : procesiones) {
			final Double f = this.administratorService.getRatioOfRequestToParadePerStatus(p, "PENDING");
			if (f == null)
				RatioOfRequestToParadePerPENDING.add(0.0);
			else
				RatioOfRequestToParadePerPENDING.add(f);
		}

		/* Q6 */
		final Double RatioOfRequestsApproveds = this.administratorService.getRatioOfRequestPerStatus().get(0);
		final Double RatioOfRequestsPendings = this.administratorService.getRatioOfRequestPerStatus().get(1);
		final Double RatioOfRequestsRejecteds = this.administratorService.getRatioOfRequestPerStatus().get(2);

		/* Q8 */
		final Collection<Member> MembersAtLeast10PercentOfNumberOfRequestAccepted = this.administratorService.getMembersAtLeast10PercentOfNumberOfRequestAccepted();

		/* Q10 */

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

		//HISTOGRAM
		final String language = LocaleContextHolder.getLocale().getLanguage();
		final List<String> positions2 = new ArrayList<>();
		final List<Position> positions = (List<Position>) this.positionService.findAll();
		String s = "";
		positions.remove(this.positionService.getDefaultPosition());
		final List<Integer> HistogramOfPositions = new ArrayList<>();
		for (final Position p : positions) {
			HistogramOfPositions.add(this.administratorService.getHistogramOfPositions(p.getRoleEn(), p.getRoleEs()));
			if (language.equals("es"))
				if (p != positions.get(positions.size() - 1))
					s = s + '"' + p.getRoleEs() + '"' + ",";
				else
					s = s + '"' + p.getRoleEs() + '"';

			else
				positions2.add(p.getRoleEn());
		}

		/* Q13 */
		final Double AvgRecordsPerHistory = this.administratorService.getAvgRecordsPerHistory();
		final Double MaxRecordsPerHistory = this.administratorService.getMaxRecordsPerHistory();
		final Double MinRecordsPerHistory = this.administratorService.getMinRecordsPerHistory();
		final Double StddevRecordsPerHistory = this.administratorService.getStddevRecordsPerHistory();

		/* TODO Q14 */

		/* Q15 */
		final List<Brotherhood> BrotherhoodHistoryLargerThanAvg = this.administratorService.getBrotherhoodHistoryLargerThanAvg();

		/* Q16 */
		final Double RatioAreaNotCoordinatesByChapter = this.administratorService.getRatioAreaNotCoordinatesByChapter();

		/* Q17 */
		final Double AvgParadesCoordinatesByChapters = this.administratorService.getAvgParadesCoordinatesByChapters();
		final Double MinParadesCoordinatesByChapter = this.administratorService.getMinParadesCoordinatesByChapters();
		final Double MaxParadesCoordinatesByChapters = this.administratorService.getMaxParadesCoordinatesByChapters();
		final Double StddevParadesCoordinatesByChapters = this.administratorService.getStddevParadesCoordinatesByChapters();

		result = new ModelAndView("administrator/dashboard");

		result.addObject("AvgOfMembersPerBrotherhood", AvgOfMembersPerBrotherhood);
		result.addObject("MinOfMembersPerBrotherhood", MinOfMembersPerBrotherhood);
		result.addObject("MaxOfMembersPerBrotherhood", MaxOfMembersPerBrotherhood);
		result.addObject("SteddevOfMembersPerBrotherhood", SteddevOfMembersPerBrotherhood);

		result.addObject("LargestBrotherhood", LargestBrotherhood);

		result.addObject("SmallestBrotherhoood", SmallestBrotherhoood);

		result.addObject("ParadeIn30Days", ParadeIn30Days);

		result.addObject("RatioOfRequestsApproveds", RatioOfRequestsApproveds);
		result.addObject("RatioOfRequestsPendings", RatioOfRequestsPendings);
		result.addObject("RatioOfRequestsRejecteds", RatioOfRequestsRejecteds);

		result.addObject("RatioOfRequestToParadePerAPPROVED", RatioOfRequestToParadePerAPPROVED);
		result.addObject("RatioOfRequestToParadePerREJECTED", RatioOfRequestToParadePerREJECTED);
		result.addObject("RatioOfRequestToParadePerPENDING", RatioOfRequestToParadePerPENDING);

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
		result.addObject("HistogramOfPositions", HistogramOfPositions);
		result.addObject("positions2", positions2);

		result.addObject("AvgRecordsPerHistory", AvgRecordsPerHistory);
		result.addObject("MaxRecordsPerHistory", MaxRecordsPerHistory);
		result.addObject("MinRecordsPerHistory", MinRecordsPerHistory);
		result.addObject("StddevRecordsPerHistory", StddevRecordsPerHistory);

		result.addObject("BrotherhoodHistoryLargerThanAvg", BrotherhoodHistoryLargerThanAvg);

		result.addObject("RatioAreaNotCoordinatesByChapter", RatioAreaNotCoordinatesByChapter);

		result.addObject("AvgParadesCoordinatesByChapters", AvgParadesCoordinatesByChapters);
		result.addObject("MinParadesCoordinatesByChapter", MinParadesCoordinatesByChapter);
		result.addObject("MaxParadesCoordinatesByChapters", MaxParadesCoordinatesByChapters);
		result.addObject("StddevParadesCoordinatesByChapters", StddevParadesCoordinatesByChapters);

		return result;
	}
}
