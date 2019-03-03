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
<form:form action="procession/brotherhood/edit.do" modelAttribute="procession">
	<form:hidden path="id" />
	
	<acme:textbox code="procession.title" path="title"/>
	<acme:textarea code="procession.description" path="description"/>
	<acme:textbox code="procession.maxRow" path="maxRow"/>
	<acme:textbox code="procession.maxColumn" path="maxColumn"/>
	<acme:select items="${floats}" itemLabel="title" code="procession.floats" path="floats"/>
	
	
	<jstl:if test="${procession.isFinal == false }">
		<acme:submit name="saveFinal" code="procession.saveFinal"/>
		<acme:submit name="saveDraft" code="procession.saveDraft"/>
	</jstl:if>
	<jstl:if test="${procession.isFinal == true }">
		<acme:submit name="saveFinal" code="procession.save"/>
	</jstl:if>
	<jstl:if test="${procession.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="procession.delete" />" />
	</jstl:if>
	<acme:cancel url="procession/brotherhood/list.do" code="procession.cancel"/>
	<br />
	
	
	
</form:form>
</security:authorize>