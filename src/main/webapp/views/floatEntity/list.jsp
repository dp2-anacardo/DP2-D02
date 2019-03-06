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

<security:authorize access="hasRole('BROTHERHOOD')">
	<display:table name="floatEntity" id="row" requestURI="${requestURI}"
		pagesize="5" class="displaytag">

		<spring:message code="float.title" var="title" />
		<display:column property="title" title="${title}">
			<jstl:out value="${row.title }"></jstl:out>
		</display:column>

		<spring:message code="float.description" var="description" />
		<display:column property="description" title="${description}">
			<jstl:out value="${row.description }"></jstl:out>
		</display:column>

		<display:column>
			<a href="floatEntity/brotherhood/edit.do?floatEntityId=${row.id}">
				<spring:message code="float.edit" />
			</a>
		</display:column>

		<display:column>
			<a href="floatEntity/brotherhood/show.do?floatEntityId=${row.id}"> <spring:message
					code="float.show" />
			</a>
		</display:column>

	</display:table>

	<div>
		<a href="floatEntity/brotherhood/create.do"> <spring:message
				code="float.create" />
		</a>
	</div>
</security:authorize>