<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

</head>
<body>
	<form:form action="sponsorship/create.do" modelAttribute="sponsorship">

		<%--  Hidden properties --%>
		<form:hidden path="id" />

		<%-- Parade--%>
		<acme:select items="${paradeList}" itemLabel="title"
			code="sponsorship.parade" path="parade" />
		<br>

		<%-- Banner --%>
		<acme:textbox code="sponsorship.banner" path="banner" />
		<br>
		
		<%-- Status --%>
		<spring:message code="sponsorship.status" />
		<form:select path="status" multiple="false">
			<jstl:choose>
				<jstl:when test="${pageContext.response.locale.language == 'es'}">
					 <form:option value="1"><spring:message code="sponsorship.status.on"/></form:option>
					 <form:option value="0"><spring:message code="sponsorship.status.off"/></form:option>
				</jstl:when>
				<jstl:when test="${pageContext.response.locale.language == 'en'}">
					 <form:option value="1"><spring:message code="sponsorship.status.on"/></form:option>
					 <form:option value="0"><spring:message code="sponsorship.status.off"/></form:option>
				</jstl:when>
			</jstl:choose>

		</form:select>
		<form:errors class="error" path="status" />
		<br>

		<%-- CreditCard --%>
		<div id="card">
			<%-- creditCard --%>
			<form:label path="creditCard">
				<spring:message code="sponsorship.creditCard" />
			</form:label>
			<br>


			<form:label path="creditCard.holderName">
				<spring:message code="sponsorship.creditCard.holderName" />
			</form:label>
			<form:input path="creditCard.holderName" />
			<form:errors class="error" path="creditCard.holderName" />
			<br>

			<form:label path="creditCard.brandName">
				<spring:message code="sponsorship.creditCard.brandName" />:
			</form:label>
			<form:select id="brandName" path="creditCard.brandName">
				<form:option value="VISA" label="VISA" />
				<form:option value="MASTERCARD" label="MASTERCARD" />
				<form:option value="DINNERS" label="DINNERS" />
				<form:option value="DISCOVER" label="DISCOVER" />
				<form:option value="AMEX" label="AMEX" />
			</form:select>
			<form:errors class="error" path="creditCard.brandName" />
			<br>

			<form:label path="creditCard.number">
				<spring:message code="sponsorship.creditCard.number" />
			</form:label>
			<form:input path="creditCard.number" type="number" />
			<form:errors class="error" path="creditCard.number" />
			<br>

			<form:label path="creditCard.expiration">
				<spring:message code="sponsorship.creditCard.expiration" />
			</form:label>
			<form:input path="creditCard.expiration" placeholder="MM/YY"
				format="{0,date,MM/YY}" />
			<form:errors class="error" path="creditCard.expiration" />
			<br>

			<form:label path="creditCard.cvvCode">
				<spring:message code="sponsorship.creditCard.cvvCode" />
			</form:label>
			<form:input path="creditCard.cvvCode" type="number" />
			<form:errors class="error" path="creditCard.cvvCode" />
			<br>

		</div>

		<%-- Buttons --%>
		<security:authorize access="hasRole('SPONSOR')">
			<acme:submit name="save" code="sponsorship.save" />

			<acme:cancel url="sponsorship/list.do" code="sponsorship.back" />

		</security:authorize>

	</form:form>
</body>
</html>