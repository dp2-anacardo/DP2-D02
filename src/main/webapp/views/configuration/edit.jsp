<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" uri="/WEB-INF/tags" %>

<security:authorize access="hasRole('ADMIN')">
<form:form action ="configuration/administrator/edit.do" modelAttribute="configuration">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="spamWords" />
	<form:hidden path="positiveWords" />
	<form:hidden path="negativeWords" />

	<acme:textarea code="configuration.edit.maxResult" path="maxResult"/>
	<acme:textarea code="configuration.edit.maxTime" path="maxTime"/>
	<acme:textarea code="configuration.edit.systemName" path="systemName"/>
	<acme:textarea code="configuration.edit.banner" path="banner"/>
	<acme:textarea code="configuration.edit.welcomeEn" 
		path="welcomeMessageEn"/>
	<acme:textarea code="configuration.edit.welcomeEs" 
		path="welcomeMessageEs"/>
	<acme:textarea code="configuration.edit.defaultCC" path="defaultCC"/>
	
	<input type="submit" name="save"
		value="<spring:message code="configuration.edit.submit" />" />&nbsp; 
	
	<acme:cancel url="/configuration/administrator/edit.do" code="configuration.edit.cancel"/>
	
</form:form>
</security:authorize>