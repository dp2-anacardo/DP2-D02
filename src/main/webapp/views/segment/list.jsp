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


<security:authorize access="hasRole('BROTHERHOOD')">
<display:table name="path" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<spring:message code="segment.originLatitude" var="originLa" />
	<display:column property="originLatitude" title="${originLa}"/>
	
	<spring:message code="segment.originLongitude" var="originLo" />
	<display:column property="originLongitude" title="${originLo}"/>
	
	<spring:message code="segment.destinationLatitude" var="destLa" />
	<display:column property="destinationLatitude" title="${destLa}"/>
	
	<spring:message code="segment.destinationLongitude" var="destLo" />
	<display:column property="destinationLongitude" title="${destLo}"/>
	
	<spring:message code="segment.time.origin" var="timeO" />
	<display:column property="timeOrigin" title="${timeO}"/>
	
	<spring:message code="segment.time.destination" var="timeD" />
	<display:column property="timeDestination" title="${timeD}"/>
	
</display:table>
<div>
	<a href="segment/brotherhood/create.do">
	<spring:message code="segment.create" />
	</a>
	
</div>
</security:authorize>