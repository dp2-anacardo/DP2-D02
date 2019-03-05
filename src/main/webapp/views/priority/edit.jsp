<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('ADMIN')">
<form:form action="priority/administrator/edit.do" modelAttribute="priorityForm">
	<form:hidden path="id" />
	
	<form:label path="titleES">
		<spring:message code="priority.nameES"/>
	</form:label>
	<form:input path="titleES"/>
	<form:errors cssClass="error" path="titleES"/>
	<br />
	
	<form:label path="titleEN">
		<spring:message code="priority.nameEN"/>
	</form:label>
	<form:input path="titleEN"/>
	<form:errors cssClass="error" path="titleEN"/>
	<br />
	
	<input type="submit" name="save"
		value="<spring:message code="priority.save" />" />&nbsp; 
	<jstl:if test="${priority.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="priority.delete" />" />
	</jstl:if>
	<input type="button" name="cancel"
		value="<spring:message code="priority.cancel" />"
		onclick="javascript: relativeRedir('priority/administrator/list.do');" />
	<br />
	
	
</form:form>
</security:authorize>