<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="hasRole('BROTHERHOOD')">
<!DOCTYPE>
<html>
<head>
<link rel="stylesheet" href="styles/table.css" type="text/css">
</head>
<body>
<form:form action ="records/legalRecord/create.do" modelAttribute="lRF">

	<form:hidden path="id"/>

	<!-- Single areas -->
	<jstl:out value="${messageCode}"/>
	
	<acme:textboxbs code="record.title" path="title"/>
	<acme:textarea bold="true" code="record.description" path="description"/>
	<acme:textboxbs code="record.edit.legalName" path="legalName"/>
	<acme:textboxbs code="record.edit.vatNumber" path="vatNumber"/>
	<br/>
	<acme:textboxbs code="record.edit.oneLaw" path="law"/>
	<jstl:if test="${customErrorMessage!=null}">
	<div class="error">
	<spring:message code="${customErrorMessage}"/>
	</div>
	</jstl:if>
	<spring:message code="record.edit.explanation"/>
	<br/>
	<br/>
	
	<!-- Submit, delete and cancel -->
	
	<acme:submit name="create" code="record.edit.submit"/>&nbsp;
	
	<input type="button" name="cancel"
		value="<spring:message code="segment.cancel" />"
		onclick="javascript: window.history.back();" />
	
</form:form>
</body>
</html>
</security:authorize>