<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<security:authorize access="hasRole('BROTHERHOOD')">
<display:table name="segments" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<display:column property="originLatitude"></display:column>
	
	<spring:message code="segment.origin" var="origin"/>
	<display:column title="${origin}">
		<jstl:out value="${row.originLatitude}"></jstl:out>
	</display:column>
	
</display:table>
<div>
	<a href="segment/brotherhood/create.do">
	<spring:message code="segment.create" />
	</a>
	
</div>
</security:authorize>