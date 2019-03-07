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
<form:form action="floatEntity/brotherhood/edit.do" modelAttribute="floatEntity">
	<form:hidden path="id" />
	<form:hidden path="version"/>
	
	<acme:textbox code="float.title" path="title"/>
	<br />
	
	<acme:textbox code="float.description" path="description"/>
	<br />
	
	<acme:textarea code="float.pictures" path="pictures"/>
	<jstl:if test="${not empty attachmentError }">
		<p class="error">${attachmentError }</p>
	</jstl:if>
	
	<br />
	
	<acme:submit name="save" code="float.save"/>
	<jstl:if test="${floatEntity.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="float.delete" />" />
	</jstl:if>
	<acme:cancel url="floatEntity/brotherhood/list.do" code="float.cancel"/>
	<br />
	
	
</form:form>
</security:authorize>