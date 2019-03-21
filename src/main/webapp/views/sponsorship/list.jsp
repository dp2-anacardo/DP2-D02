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

<security:authorize access="hasRole('SPONSOR')">
	<display:table name="sponsorships" id="row" requestURI="${requestURI}"
		pagesize="5" class="displaytag">

		<spring:message code="sponsorship.parade" var="parade" />
		<display:column property="parade.title" title="${parade}" />

		<spring:message code="sponsorship.status" var="status" />
		<display:column title="${status}">
			<jstl:if test="${row.status eq false}">
				<spring:message code="sponsorship.status.off" />
			</jstl:if>
			<jstl:if test="${row.status eq true}">
				<spring:message code="sponsorship.status.on" />
			</jstl:if>
		</display:column>

		<spring:message code="sponsorship.targetURL" var="targetURL" />
		<display:column title="${targetURL}">
			<a href="${row.targetURL}">${row.targetURL}</a>
		</display:column>

		<spring:message code="sponsorship.switch" var="rerere" />
		<display:column title="${rerere}">
			<jstl:if test="${row.status eq false}">
				<acme:cancel url='sponsorship/activate.do?sponsorshipId=${row.id}'
					code="sponsorship.activate" />
			</jstl:if>
			<jstl:if test="${row.status eq true}">
				<acme:cancel url='sponsorship/desactivate.do?sponsorshipId=${row.id}'
					code="sponsorship.desactivate" />
			</jstl:if>
		</display:column>
	</display:table>

	<div>
		<acme:cancel url="sponsorship/create.do" code="sponsorship.create" />
		<acme:cancel url="/" code="sponsorship.back" />
	</div>
</security:authorize>