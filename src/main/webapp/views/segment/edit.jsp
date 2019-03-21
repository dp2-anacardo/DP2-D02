<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="hasRole('BROTHERHOOD')">
<form:form action="segment/brotherhood/edit.do" modelAttribute="segment">
	<form:hidden path="id" />
	<form:hidden path="parade"/>
	
	<acme:textbox code="segment.originLatitude" path="originLatitude"/>
	<acme:textbox code="segment.originLongitude" path="originLongitude"/>
	<acme:textbox code="segment.destinationLatitude" path="destinationLatitude"/>
	<acme:textbox code="segment.destinationLongitude" path="destinationLongitude"/>
	<acme:textbox code="segment.time.origin" path="timeOrigin"/>
	<acme:textbox code="segment.time.destination" path="timeDestination"/>
	
	<acme:submit name="save" code="segment.save"/>
	<input type="button" name="cancel"
		value="<spring:message code="segment.cancel" />"
		onclick="javascript: window.history.back();" />
	<br />
	
</form:form>
</security:authorize>