<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<style type="text/css">
.ACCEPTED{
  background-color: green;
}
.REJECTED{
  background-color: red;
}
.SUBMITTED{
  background-color: grey;
}
</style>

<display:table name="parade" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">

		<jstl:if test="${row.status == 'ACCEPTED'}">
				<jstl:set var="css" value="ACCEPTED"></jstl:set>
			</jstl:if>
		<jstl:if test="${row.status == 'SUBMITTED'}">
		<jstl:set var="css" value="SUBMITTED"></jstl:set>
		</jstl:if>
		<jstl:if test="${row.status == 'REJECTED'}">
			<jstl:set var="css" value="REJECTED"></jstl:set>
		</jstl:if>

	<security:authorize access="hasRole('CHAPTER')">
	<spring:message code="parade.title" var="columnTitle"/>
	<display:column title="${columnTitle}" class="${css}">
		<jstl:out value="${row.title }"></jstl:out>
	</display:column>
	
	<spring:message code="parade.date" var="columnTitle"/>
	<display:column title="${columnTitle}" class="${css}">
		<jstl:out value="${row.moment }"></jstl:out>
	</display:column>
	
	<spring:message code="parade.status" var="status"/>
	<display:column title="${status}" class="${css}" sortable="true">
			<jstl:if test="${row.status == 'ACCEPTED' }">
				<spring:message code="parade.accepted"/>
			</jstl:if>
			
			<jstl:if test="${row.status == 'SUBMITTED' }">
				<spring:message code="parade.submitted"/>
			</jstl:if>
			
			<jstl:if test="${row.status == 'REJECTED' }">
				<spring:message code="parade.rejected"/>
			</jstl:if>
	</display:column>
	<spring:message code="parade.accept" var="accept"/>
	<display:column title="${accept}" class="${css}">
	<jstl:if test="${row.status == 'SUBMITTED'}">
		<input type="button" 
		onclick="javascript: relativeRedir('parade/chapter/accept.do?paradeId=${row.id}');"
		value="<spring:message code="parade.accept"/>"/>
	</jstl:if>
	</display:column>
	<spring:message code="enrolment.reject" var="reject"/>
	<display:column title="${reject}" class="${css}">
	<jstl:if test="${row.status == 'SUBMITTED'}">
		<input type="button" 
		onclick="javascript: relativeRedir('parade/chapter/reject.do?paradeId=${row.id}');"
		value="<spring:message code="parade.reject"/>"/>
	</jstl:if>
	</display:column>
	<display:column class="${css}">
		<a href="parade/show.do?paradeId=${row.id}">
			<spring:message code="parade.show"/>
		</a>
	</display:column>
	</security:authorize>

</display:table>
<acme:cancel url="/" code="messageBox.goBack" />