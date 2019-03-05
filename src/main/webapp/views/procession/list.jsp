<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('BROTHERHOOD')">
<display:table name="procession" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<spring:message code="procession.title" var="columnTitle"/>
	<display:column title="${columnTitle}">
		<jstl:out value="${row.title }"></jstl:out>
	</display:column>
	
	<display:column>
		<a href="procession/brotherhood/edit.do?processionId=${row.id}">
			<spring:message code="procession.edit"/>
		</a>
	</display:column>
	
	<display:column>
		<a href="procession/show.do?processionId=${row.id}">
			<spring:message code="procession.show"/>
		</a>
	</display:column>
	<display:column>
		<a href="request/brotherhood/list.do?processionId=${row.id}">
			<spring:message code="procession.request"/>
		</a>
	</display:column>
	
	
</display:table>
<div>
	<a href="procession/brotherhood/create.do"> <spring:message
				code="procession.create" />
	</a>
</div>
</security:authorize>