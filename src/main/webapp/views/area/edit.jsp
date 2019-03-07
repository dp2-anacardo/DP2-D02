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

<security:authorize access="hasRole('ADMIN')">
<form:form action="area/administrator/edit.do" modelAttribute="area">
	<form:hidden path="id" />
	
	<acme:textbox code="area.name" path="name"/>
	<br />
	
	<acme:textarea code="area.pictures" path="pictures"/>
	<jstl:if test="${not empty attachmentError }">
		<p class="error">${attachmentError }</p>
	</jstl:if>
	<br />
	
	<acme:submit name="save" code="area.save"/>
	<jstl:if test="${area.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="area.delete" />" />
	</jstl:if>
	<acme:cancel url="area/administrator/list.do" code="area.cancel"/>
	<br />
	
	
</form:form>
</security:authorize>