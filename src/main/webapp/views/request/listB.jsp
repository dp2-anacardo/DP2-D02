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
  background-color: orange;
}
.PENDING{
  background-color: grey;
}
</style>

<security:authorize access="hasRole('BROTHERHOOD')">
<display:table name="requests" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<display:column class="${row.status }">
		<a href="request/member,brotherhood/show.do?requestId=${row.id}">
			<spring:message code="request.show"/>
		</a>
	</display:column>
	
	<spring:message code="request.member" var="columnTitle"/>
	<display:column title="${columnTitle}" class="${row.status }">
		<jstl:out value="${row.member.name}"></jstl:out>
	</display:column>
	
	<spring:message code="request.status" var="columnTitle"/>
	<display:column title="${columnTitle}" class="${row.status }" sortable="true">
		<jstl:out value="${row.status}"></jstl:out>
	</display:column>
	
	
	
</display:table>

</security:authorize>