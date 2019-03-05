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
	<display:column title="${registerMoment}">
		<jstl:out value="${row.registerMoment}"></jstl:out>
	</display:column>
	
	<spring:message code="enrolment.dropOutMoment" var="dropOutMoment"/>
	<display:column title="${dropOutMoment}">
		<jstl:out value="${row.dropOutMoment}" ></jstl:out>
	</display:column>
	
	<spring:message code="enrolment.status" var="status"/>
	<display:column title="${status}">
			<jstl:if test="${row.status == 'ACCEPTED' }">
				<spring:message code="enrolment.accepted"/>
			</jstl:if>
			
			<jstl:if test="${row.status == 'PENDING' }">
				<spring:message code="enrolment.pending"/>
			</jstl:if>
			
			<jstl:if test="${row.status == 'REJECTED' }">
				<spring:message code="enrolment.rejected"/>
			</jstl:if>
	</display:column>
	
	<jstl:if test="${lang=='en' }">
	<spring:message code="enrolment.positionEn" var="positionEn"/>
	<display:column title="${positionEn}">
		<jstl:if test="${row.position.roleEn != 'default' }">
		<jstl:out value="${row.position.roleEn}"></jstl:out>
		</jstl:if>
	</display:column>
	</jstl:if>
	
	<jstl:if test="${lang=='es' }">
	<spring:message code="enrolment.positionEs" var="positionEs"/>
	<display:column title="${positionEs}">
		<jstl:if test="${row.position.roleEs != 'default' }">
		<jstl:out value="${row.position.roleEs}"></jstl:out>
		</jstl:if>
	</display:column>
	</jstl:if>
	
	<spring:message code="enrolment.member" var="member"/>
	<display:column title="${member}">
		<jstl:out value="${row.member.name}"></jstl:out>
	</display:column>
	
	<spring:message code="enrolment.accept" var="accept"/>
	<display:column title="${accept}">
	<jstl:if test="${row.status == 'PENDING'}">
		<input type="button" 
		onclick="javascript: relativeRedir('enrolment/brotherhood/accept.do?enrolmentId=${row.id}');"
		value="<spring:message code="enrolment.accept"/>"/>
	</jstl:if>
	</display:column>
	
	<spring:message code="enrolment.reject" var="reject"/>
	<display:column title="${reject}">
	<jstl:if test="${row.status == 'PENDING'}">
		<input type="button" 
		onclick="javascript: relativeRedir('enrolment/brotherhood/reject.do?enrolmentId=${row.id}');"
		value="<spring:message code="enrolment.reject"/>"/>
	</jstl:if>
	</display:column>
</display:table>
</security:authorize>

<security:authorize access="hasRole('MEMBER')">
<display:table name="enrolments" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	
	<spring:message code="enrolment.registerMoment" var="registerMoment"/>
	<display:column title="${registerMoment}">
		<jstl:out value="${row.registerMoment}"></jstl:out>
	</display:column>
	
	<spring:message code="enrolment.dropOutMoment" var="dropOutMoment"/>
	<display:column title="${dropOutMoment}">
		<jstl:out value="${row.dropOutMoment}"></jstl:out>
	</display:column>
	
	<spring:message code="enrolment.status" var="status"/>
	<display:column title="${status}">
		<jstl:if test="${row.status == 'ACCEPTED' }">
				<spring:message code="enrolment.accepted"/>
			</jstl:if>
			
			<jstl:if test="${row.status == 'PENDING' }">
				<spring:message code="enrolment.pending"/>
			</jstl:if>
			
			<jstl:if test="${row.status == 'REJECTED' }">
				<spring:message code="enrolment.rejected"/>
			</jstl:if>
	</display:column>
	
	<jstl:if test="${lang=='en' }">
	<spring:message code="enrolment.positionEn" var="positionEn"/>
	<display:column title="${positionEn}">
		<jstl:if test="${row.position.roleEn != 'default' }">
		<jstl:out value="${row.position.roleEn}"></jstl:out>
		</jstl:if>
	</display:column>
	</jstl:if>
	
	<jstl:if test="${lang=='es' }">
	<spring:message code="enrolment.positionEs" var="positionEs"/>
	<display:column title="${positionEs}">
		<jstl:if test="${row.position.roleEs != 'default' }">
		<jstl:out value="${row.position.roleEs}"></jstl:out>
		</jstl:if>
	</display:column>
	</jstl:if>
	
	<spring:message code="enrolment.brotherhood" var="brotherhood"/>
	<display:column title="${brotherhood}">
		<jstl:out value="${row.brotherhood.name}"></jstl:out>
	</display:column>
	
	<spring:message code="enrolment.dropOut" var="dropOut"/>
	<display:column title="${dropOut}">
	<jstl:if test="${row.dropOutMoment == null && row.status == 'ACCEPTED' }">
		<input type="button" 
		onclick="javascript: relativeRedir('enrolment/member/dropOut.do?enrolmentId=${row.id}');"
		value="<spring:message code="enrolment.dropOut"/>"/>
	</jstl:if>
	</display:column>
</display:table>
</security:authorize>