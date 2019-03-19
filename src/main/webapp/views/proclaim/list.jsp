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


<display:table name="proclaims" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	
	<display:column> <a href="proclaim/show.do?proclaimId=${row.id}">
	<spring:message code="proclaim.show" /></a>
	</display:column>
	
	<spring:message code="proclaim.moment" var="moment"/>
	<display:column title="${moment}">
		<jstl:out value="${row.moment }"></jstl:out>
	</display:column>
	
	<spring:message code="proclaim.description" var="description"/>
	<display:column title="${description}">
		<jstl:out value="${row.description}"></jstl:out>
	</display:column>
	
	<spring:message code="proclaim.chapter" var="chapter"/>
	<display:column title="${chapter}">
		<jstl:out value="${row.chapter.title}"></jstl:out>
	</display:column>
	
</display:table>
<security:authorize access="hasRole('CHAPTER')">
<div>
	<input type="button" name="create"
		value="<spring:message code="proclaim.create" />"
		onclick="javascript: relativeRedir('proclaim/chapter/create.do');" />
	<acme:cancel url="/" code="proclaim.back" />
</div>
</security:authorize>