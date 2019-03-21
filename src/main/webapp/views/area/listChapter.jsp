<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('CHAPTER')">
	<display:table name="area" id="row" requestURI="${requestURI}"
		pagesize="5" class="displaytag">
		<spring:message code="area.name" var="columnTitle" />
		<display:column title="${columnTitle}">
			<jstl:out value="${row.name }"></jstl:out>
		</display:column>

<jstl:if test="${b}">
		<spring:message code="area.autoAssign" var="autoAssign" />
		<display:column title="${autoAssign}">
			<a href="area/chapter/autoAssign.do?areaId=${row.id}"> <spring:message
					code="area.autoAssign" />
			</a>
		</display:column>
</jstl:if>

<jstl:if test="${!b}">
<spring:message code="area.brotherhood" var="brotherhood" />
<display:column title ="${brotherhood}"> 
	<a href="brotherhood/listNotRegister.do?areaId=${row.id}">
	<spring:message code="area.brotherhood" /></a>
	</display:column>
</jstl:if>
	</display:table>
		<acme:cancel url="/" code="priority.back" />
</security:authorize>