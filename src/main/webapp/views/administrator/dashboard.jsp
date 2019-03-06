<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
		<b><spring:message code="administrator.AvgOfMembersPerBrotherhood" /></b> ${AvgOfMembersPerBrotherhood} <br />
		<b><spring:message code="administrator.MinOfMembersPerBrotherhood" /></b> ${MinOfMembersPerBrotherhood} <br />
		<b><spring:message code="administrator.MaxOfMembersPerBrotherhood" /></b> ${MaxOfMembersPerBrotherhood} <br />
		<b><spring:message
				code="administrator.SteddevOfMembersPerBrotherhood" /></b> ${SteddevOfMembersPerBrotherhood} <br />
		<b><spring:message code="administrator.LargestBrotherhood" /></b> ${LargestBrotherhood} <br />
		<b><spring:message code="administrator.SmallestBrotherhoood" /></b> ${SmallestBrotherhoood} <br />
		<%-- <b><spring:message code="administrator.ProcessionIn30Days" /></b> ${ProcessionIn30Days} <br/> --%>

		<b><spring:message code="administrator.ProcessionIn30Days" /></b>
		<jstl:forEach var="x" items="${ProcessionIn30Days}">
			<jstl:out value="${x.tittle}"> </jstl:out>
		</jstl:forEach>
		<br />
		<b><spring:message code="administrator.RatioOfRequestsApproveds" /></b> ${RatioOfRequestsApproveds} <br />
		<b><spring:message code="administrator.RatioOfRequestsPendings" /></b> ${RatioOfRequestsPendings} <br />
		<b><spring:message code="administrator.RatioOfRequestsRejecteds" /></b> ${RatioOfRequestsRejecteds} <br />
		<b><spring:message code="administrator.RatioOfRequestToProcessionPerAPPROVED" /></b> ${RatioOfRequestToProcessionPerAPPROVED} <br />
		<b><spring:message code="administrator.RatioOfRequestToProcessionPerREJECTED" /></b> ${RatioOfRequestToProcessionPerREJECTED} <br />
		<b><spring:message code="administrator.RatioOfRequestToProcessionPerPENDING" /></b> ${RatioOfRequestToProcessionPerPENDING} <br />
	</security:authorize>
</body>
</html>