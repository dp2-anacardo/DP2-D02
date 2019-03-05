<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="hasRole('MEMBER')">
<form:form action ="finder/member/edit.do" modelAttribute="finder">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="lastUpdate"/>
	<form:hidden path="processions"/>
	
	<!-- Single Attributes -->
	<jstl:out value="${messageCode}"/>

	<acme:textbox code="finder.update.keyword" path="keyWord"/>
	
	<form:label path="minimumDate">
		<spring:message code="finder.update.daterange" />:
		<spring:message code="finder.update.between"/>
	</form:label>
	<form:input path="minimumDate" placeholder="dd/MM/yyyy HH:mm"/>
	<form:errors cssClass="error" path="minimumDate" />
	
	<form:label path="maximumDate">
		<spring:message code="finder.update.and" />
	</form:label>
	<form:input path="maximumDate" placeholder="dd/MM/yyyy HH:mm"/>
	<form:errors cssClass="error" path="maximumDate" />
	<br />
	
	<acme:textbox code="finder.update.areaName" path="areaName"/>
	
	<!-- Submit and Cancel -->
	
	<acme:submit name="save" code="finder.update.update"/>&nbsp;
	
	<acme:cancel url="/" code="finder.update.cancel"/>
	
</form:form>
</security:authorize>