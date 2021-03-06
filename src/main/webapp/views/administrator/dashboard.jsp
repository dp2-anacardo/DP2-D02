<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 5 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
</head>
<body>

	<security:authorize access="hasRole('ADMIN')">

		<script type="text/javascript"
			src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js"></script>

		<canvas id="myChart" width="40" height="10" aria-label="coza"
			role="img"></canvas>
		<b><spring:message code="administrator.histogram" /></b>
		<br />
		<script>
			var positions = '<jstl:out value="${positions2}"/>';
			var sillyString = positions.substr(1).slice(0, -1);
			positions2 = sillyString.split(",");
			var numberArray = '<jstl:out value="${HistogramOfPositions}"/>';
			var sillyString2 = numberArray.substr(1).slice(0, -1);
			numberArray2 = sillyString2.split(",").map(Number);

			var ctx = document.getElementById("myChart").getContext('2d');
			var myChart = new Chart(ctx, {
				type : 'bar',
				data : {
					labels : positions2,
					datasets : [
						{
							label : 'Number of positions',
							data : numberArray2,
							backgroundColor : 'rgba(0, 0, 255, 1)',
						}
					]
				}
			});
		</script>
		<b><spring:message code="administrator.AvgOfMembersPerBrotherhood" /></b> ${AvgOfMembersPerBrotherhood} <br />
		<b><spring:message code="administrator.MinOfMembersPerBrotherhood" /></b> ${MinOfMembersPerBrotherhood} <br />
		<b><spring:message code="administrator.MaxOfMembersPerBrotherhood" /></b> ${MaxOfMembersPerBrotherhood} <br />
		<b><spring:message
				code="administrator.SteddevOfMembersPerBrotherhood" /></b> ${SteddevOfMembersPerBrotherhood} <br />
		<b><spring:message code="administrator.LargestBrotherhood" /></b> ${LargestBrotherhood} <br />
		<b><spring:message code="administrator.SmallestBrotherhoood" /></b> ${SmallestBrotherhoood} <br />
		<%-- <b><spring:message code="administrator.ParadeIn30Days" /></b> ${ParadeIn30Days} <br/> --%>

		<b><spring:message code="administrator.ParadeIn30Days" /></b>
		<jstl:forEach var="x" items="${ParadeIn30Days}">
			${x.title}
		</jstl:forEach>
		<br />
		<b><spring:message code="administrator.RatioOfRequestsApproveds" /></b> ${RatioOfRequestsApproveds} <br />
		<b><spring:message code="administrator.RatioOfRequestsPendings" /></b> ${RatioOfRequestsPendings} <br />
		<b><spring:message code="administrator.RatioOfRequestsRejecteds" /></b> ${RatioOfRequestsRejecteds} <br />


		<b><spring:message
				code="administrator.RatioOfRequestToParadePerAPPROVED" /></b>
		<jstl:forEach var="x" items="${procesiones}" varStatus="status">
			<br>
			- ${x.title} : ${RatioOfRequestToParadePerAPPROVED[status.index]}
		</jstl:forEach>

		<br>
		<b><spring:message
				code="administrator.RatioOfRequestToParadePerPENDING" /></b>
		<jstl:forEach var="x" items="${procesiones}" varStatus="status">
			<br>
			- ${x.title} : ${RatioOfRequestToParadePerPENDING[status.index]}
		</jstl:forEach>

		<br>
		<b><spring:message
				code="administrator.RatioOfRequestToParadePerREJECTED" /></b>
		<jstl:forEach var="x" items="${procesiones}" varStatus="status">
			<br>
			- ${x.title} : ${RatioOfRequestToParadePerREJECTED[status.index]}
		</jstl:forEach>

		<br>
		<b><spring:message
				code="administrator.MembersAtLeast10PercentOfNumberOfRequestAccepted" /></b>
		<jstl:forEach var="MembersAtLeast10PercentOfNumberOfRequestAccepted"
			items="${MembersAtLeast10PercentOfNumberOfRequestAccepted}">
			- ${MembersAtLeast10PercentOfNumberOfRequestAccepted.name}
		</jstl:forEach>

		<br>
		<b><spring:message code="administrator.CountOfBrotherhoodPerArea" /></b>
		<jstl:forEach var="x" items="${areas}" varStatus="status">
			<br>
			- ${x.name} : ${CountOfBrotherhoodPerArea[status.index]}
		</jstl:forEach>

		<br>
		<b><spring:message code="administrator.MinBrotherhoodPerArea" /></b> ${MinBrotherhoodPerArea} <br />
		<b><spring:message code="administrator.MaxBrotherhoodPerArea" /></b> ${MaxBrotherhoodPerArea} <br />
		<b><spring:message code="administrator.AvgBrotherhoodPerArea" /></b> ${AvgBrotherhoodPerArea} <br />
		<b><spring:message code="administrator.StddevBrotherhoodPerArea" /></b> ${StddevBrotherhoodPerArea} <br />

		<b><spring:message code="administrator.MinResultFinder" /></b> ${MinResultFinder} <br />
		<b><spring:message code="administrator.MaxResultFinder" /></b> ${MaxResultFinder} <br />
		<b><spring:message code="administrator.AvgResultFinder" /></b> ${AvgResultFinder} <br />
		<b><spring:message code="administrator.StddevResultFinder" /></b> ${StddevResultFinder} <br />

		<b><spring:message code="administrator.RatioOfNotEmptyFinders" /></b> ${RatioOfNotEmptyFinders} <br />
		<b><spring:message code="administrator.RatioOfEmptyFinders" /></b> ${RatioOfEmptyFinders} <br />

		<b><spring:message code="administrator.AvgRecordsPerHistory" /></b> ${AvgRecordsPerHistory} <br />
		<b><spring:message code="administrator.MaxRecordsPerHistory" /></b> ${MaxRecordsPerHistory} <br />
		<b><spring:message code="administrator.MinRecordsPerHistory" /></b> ${MinRecordsPerHistory} <br />
		<b><spring:message code="administrator.StddevRecordsPerHistory" /></b> ${StddevRecordsPerHistory} <br />

		<b><spring:message
				code="administrator.BrotherhoodWithLargestHistory" /></b> ${BrotherhoodWithLargestHistory} <br />

		<b><spring:message
				code="administrator.BrotherhoodHistoryLargerThanAvg" /></b>
		<jstl:forEach var="BrotherhoodHistoryLargerThanAvg"
			items="${BrotherhoodHistoryLargerThanAvg}">
			<br>
			- ${BrotherhoodHistoryLargerThanAvg.name}
		</jstl:forEach>

		<br>
		<b><spring:message
				code="administrator.RatioAreaNotCoordinatesByChapter" /></b> 
		${RatioAreaNotCoordinatesByChapter} <br />

		<b><spring:message
				code="administrator.AvgParadesCoordinatesByChapters" /></b> ${AvgParadesCoordinatesByChapters} <br />
		<b><spring:message
				code="administrator.MinParadesCoordinatesByChapter" /></b> ${MinParadesCoordinatesByChapter} <br />
		<b><spring:message
				code="administrator.MaxParadesCoordinatesByChapters" /></b> ${MaxParadesCoordinatesByChapters} <br />
		<b><spring:message
				code="administrator.StddevParadesCoordinatesByChapters" /></b> ${StddevParadesCoordinatesByChapters} <br />

		<b><spring:message
				code="administrator.ChaptersCoordinate10MoreParadesThanAvg" /></b>
		<jstl:forEach var="ChaptersCoordinate10MoreParadesThanAvg"
			items="${ChaptersCoordinate10MoreParadesThanAvg}">
			<br>
			- ${ChaptersCoordinate10MoreParadesThanAvg.title}
		</jstl:forEach>

		<br>
		<b><spring:message code="administrator.RatioParadeDraftVsFinal" /></b> ${RatioParadeDraftVsFinal} <br />

		<b><spring:message
				code="administrator.RatioParadeFinalModeAccepted" /></b> ${RatioParadeFinalModeAccepted} <br />
		<b><spring:message
				code="administrator.RatioParadeFinalModeSubmitted" /></b> ${RatioParadeFinalModeSubmitted} <br />
		<b><spring:message
				code="administrator.RatioParadeFinalModeRejected" /></b> ${RatioParadeFinalModeRejected} <br />

		<b><spring:message code="administrator.RatioActiveSponsorships" /></b> ${RatioActiveSponsorships} <br />

		<b><spring:message code="administrator.AvgSponsorshipsPerSponsor" /></b> ${AvgSponsorshipsPerSponsor} <br />
		<b><spring:message
				code="administrator.MinSponsorshipsActivesPerSponsor" /></b> ${MinSponsorshipsActivesPerSponsor} <br />
		<b><spring:message
				code="administrator.MaxSponsorshipsActivesPerSponsor" /></b> ${MaxSponsorshipsActivesPerSponsor} <br />
		<b><spring:message
				code="administrator.StddevSponsorshipsActivesPerSponsors" /></b> ${StddevSponsorshipsActivesPerSponsor} <br />

		<b><spring:message
				code="administrator.Top5SponsorsInTermsOfSponsorshipsActives" /></b>
		<jstl:forEach var="Top5SponsorsInTermsOfSponsorshipsActives"
			items="${Top5SponsorsInTermsOfSponsorshipsActives}">
			<br>
			- ${Top5SponsorsInTermsOfSponsorshipsActives}
		</jstl:forEach>

	</security:authorize>
</body>
</html>