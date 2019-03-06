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
<form:form action="request/brotherhood/accept.do" modelAttribute="r">
	<form:hidden path="id" />
	<jstl:if test="${messageFullB==true }">
	<div class="error"><spring:message code="${messageFull}"/>
	</div>
	</jstl:if>
	
	<form:label path="positionRow">
	<spring:message code="request.positionRow"/>
	</form:label>
	<form:input path="positionRow" value="${row}"/>
	<form:errors cssClass="error" path="positionRow"/>
	<br />
	
	<form:label path="positionColumn">
	<spring:message code="request.positionColumn"/>
	</form:label>
	<form:input path="positionColumn" value="${column}"/>
	<form:errors cssClass="error" path="positionColumn"/>
	<br />
	
	
	<acme:submit name="save" code="request.save"/>
	<br />
	
	
</form:form>
</security:authorize>