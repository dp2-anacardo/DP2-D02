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
.APPROVED{
  background-color: green;
}
.REJECTED{
  background-color: orange;
}
.PENDING{
  background-color: grey;
}
</style>

<security:authorize access="hasRole('MEMBER')">
<display:table name="requests" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<display:column class="${row.status }">
		<a href="request/member,brotherhood/show.do?requestId=${row.id}">
			<spring:message code="request.show"/>
		</a>
	</display:column>
	
	<spring:message code="request.parade" var="columnTitle"/>
	<display:column title="${columnTitle}" class="${row.status }">
		<jstl:out value="${row.parade.title}"></jstl:out>
	</display:column>
	
	<spring:message code="request.status" var="columnTitle"/>
	<display:column title="${columnTitle}" class="${row.status }">
		<jstl:out value="${row.status}"></jstl:out>
	</display:column>
	
	<display:column class="${row.status }">
	<jstl:if test="${row.status=='PENDING' }">
		<a href="request/member/delete.do?requestId=${row.id}">
			<spring:message code="request.delete"/>
		</a>
	</jstl:if>
	</display:column>
	
	
</display:table>
<div>
<acme:cancel url="request/member/create.do" code="request.create"/>
</div>

</security:authorize>