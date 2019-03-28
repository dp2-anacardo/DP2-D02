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

<security:authorize access="hasRole('ADMIN')">
	<display:table name="area" id="row" requestURI="${requestURI}"
		pagesize="5" class="displaytag">
		<spring:message code="area.name" var="columnTitle" />
		<display:column title="${columnTitle}">
			<jstl:out value="${row.name }"></jstl:out>
		</display:column>

		<display:column>
			<a href="area/administrator/edit.do?areaId=${row.id}"> <spring:message
					code="area.edit" />
			</a>
		</display:column>
	</display:table>
	<div>
		<a href="area/administrator/create.do"> <spring:message
				code="area.create" />
		</a>
		<acme:cancel url="/" code="priority.back" />

	</div>
</security:authorize>

<display:table name="areas" id="row" requestURI="area/list" pagesize="5" class="displaytag">

	<spring:message code="area.name" var="name" />
	<display:column property="name" title="${name}"/>
	
</display:table>