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
	<display:table name="sponsorship" id="row"
		requestURI="sponsorship/show.do" class="displaytag">
		<spring:message code="sponsorship.parade" var="paradeHeader" />
		<display:column property="parade.title" title="${paradeHeader}"
			sortable="false" />
	</display:table>

	<display:table name="sponsorship" id="row"
		requestURI="sponsorship/display.do" class="displaytag">
		<spring:message code="sponsorship.status" var="statusHeader" />
		<display:column title="${statusHeader}">
			<jstl:if test="${row.status eq false}">
				<spring:message code="sponsorship.status.off" />
			</jstl:if>
			<jstl:if test="${row.status eq true}">
				<spring:message code="sponsorship.status.on" />
			</jstl:if>
		</display:column>
	</display:table>

	<display:table name="sponsorship" id="row"
		requestURI="sponsorship/display.do" class="displaytag">
		<spring:message code="sponsorship.targetURL" var="targetURL" />
		<display:column title="${targetURL}">
			<a href="<%=request.getContextPath()%>/parade/show.do?paradeId=${row.parade.id}">Target URL</a>
		</display:column>
	</display:table>

	<display:table name="sponsorship" id="row"
		requestURI="sponsorship/display.do" class="displaytag">
		<spring:message code="sponsorship.banner" var="bannerHeader" />
		<display:column title="${bannerHeader}" sortable="false">
			<img src="${row.banner}" />
		</display:column>
	</display:table>

	<display:table name="sponsorship" id="row"
		requestURI="sponsorship/show.do" class="displaytag">

		<spring:message code="sponsorship.creditCard.holderName"
			var="creditCardHolderNameHeader" />
		<display:column property="creditCard.holderName"
			title="${creditCardHolderNameHeader}" sortable="false" />

		<spring:message code="sponsorship.creditCard.brandName"
			var="creditCardBrandNameHeader" />
		<display:column property="creditCard.brandName"
			title="${creditCardBrandNameHeader}" sortable="false" />

		<spring:message code="sponsorship.creditCard.number"
			var="creditCardNumberHeader" />
		<display:column property="creditCard.number"
			title="${creditCardNumberHeader}" sortable="false" />

		<spring:message code="sponsorship.creditCard.cvvCode"
			var="creditCardcvvCodeHeader" />
		<display:column property="creditCard.cvvCode"
			title="${creditCardcvvCodeHeader}" sortable="false" />

		<spring:message code="sponsorship.creditCard.expiration"
			var="creditCardExpirationHeader" />
		<display:column property="creditCard.expiration"
			title="${creditCardExpirationHeader}" sortable="false" format="{0,date,MM/YY}" />
	</display:table>

	<acme:cancel url="sponsorship/update.do?sponsorshipId=${row.id}"
		code="sponsorship.update" />
	<acme:cancel url="sponsorship/list.do" code="sponsorship.back" />


</security:authorize>