<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('BROTHERHOOD')">
<display:table name="enrolments" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	
	<spring:message code="enrolment.registerMoment" var="registerMoment"/>
	<display:column title="registerMoment" format="{0,date,dd/MM/yyyy HH:mm">
		<jstl:out value="${row.registerMoment}"></jstl:out>
	</display:column>
	
	<spring:message code="enrolment.dropOutMoment" var="dropOutMoment"/>
	<display:column title="dropOutMoment" format="{0,date,dd/MM/yyyy HH:mm">
		<jstl:out value="${row.dropOutMoment}" ></jstl:out>
	</display:column>
	
	<spring:message code="enrolment.status" var="status"/>
	<display:column title="status">
		<jstl:out value="${row.status}"></jstl:out>
	</display:column>
	
	<jstl:if test="${lang=='en' }">
	<spring:message code="enrolment.positionEn" var="positionEn"/>
	<display:column title="positionEn">
		<jstl:out value="${row.position.roleEn}"></jstl:out>
	</display:column>
	</jstl:if>
	
	<jstl:if test="${lang=='es' }">
	<spring:message code="enrolment.positionEs" var="positionEs"/>
	<display:column title="positionEs">
		<jstl:out value="${row.position.roleEs}"></jstl:out>
	</display:column>
	</jstl:if>
	
	<spring:message code="enrolment.member" var="member"/>
	<display:column title="member">
		<jstl:out value="${row.member.name}"></jstl:out>
	</display:column>
	
	<jstl:if test="${row.status == 'PENDING' }">
	<spring:message code="enrolment.accept" var="accept"/>
	<display:column title="accept">
		<input type="submit" name="accept"
		value="<spring:message code="enrolment.accept"/>"/>
	</display:column>
	
	<spring:message code="enrolment.reject" var="reject"/>
	<display:column title="reject">
		<input type="submit" name="reject"
		value="<spring:message code="enrolment.reject"/>"/>
	</display:column>
	</jstl:if>
</display:table>
</security:authorize>

<security:authorize access="hasRole('MEMBER')">
<display:table name="enrolments" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	
	<spring:message code="enrolment.registerMoment" var="registerMoment"/>
	<display:column title="registerMoment" format="{0,date,dd/MM/yyyy HH:mm">
		<jstl:out value="${row.registerMoment}"></jstl:out>
	</display:column>
	
	<spring:message code="enrolment.dropOutMoment" var="dropOutMoment"/>
	<display:column title="dropOutMoment" format="{0,date,dd/MM/yyyy HH:mm">
		<jstl:out value="${row.dropOutMoment}"></jstl:out>
	</display:column>
	
	<spring:message code="enrolment.status" var="status"/>
	<display:column title="status">
		<jstl:out value="${row.status}"></jstl:out>
	</display:column>
	
	<jstl:if test="${lang=='en' }">
	<spring:message code="enrolment.positionEn" var="positionEn"/>
	<display:column title="positionEn">
		<jstl:out value="${row.position.roleEn}"></jstl:out>
	</display:column>
	</jstl:if>
	
	<jstl:if test="${lang=='es' }">
	<spring:message code="enrolment.positionEs" var="positionEs"/>
	<display:column title="positionEs">
		<jstl:out value="${row.position.roleEs}"></jstl:out>
	</display:column>
	</jstl:if>
	
	<spring:message code="enrolment.brotherhood" var="brotherhood"/>
	<display:column title="brotherhood">
		<jstl:out value="${row.brotherhood.name}"></jstl:out>
	</display:column>
	
	<jstl:if test="${dropOutMoment == null }">
	<spring:message code="enrolment.dropOut" var="dropOut"/>
	<display:column title="dropOut">
		<input type="submit" name="dropOut"
		value="<spring:message code="enrolment.dropOut"/>"/>
	</display:column>
	</jstl:if>
</display:table>
</security:authorize>