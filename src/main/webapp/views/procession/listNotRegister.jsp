<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="procession" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">

	<spring:message code="procession.title" var="title" />
	<display:column property="title" title="${title}"/>
	
	<spring:message code="procession.description" var="description" />
	<display:column property="description" title="${description}"/>
	
	<spring:message code="procession.moment" var="moment" />
	<display:column property="moment" title="${moment}"/>
	

</display:table>