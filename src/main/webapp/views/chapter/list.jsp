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

<display:table name="chapters" id="row" requestURI="chapter/list" pagesize="5" class="displaytag">

	<spring:message code="record.title" var="title" />
	<display:column property="title" title="${title}"/>
	
	<spring:message code="chapter.surname" var="surname" />
	<display:column property="surname" title="${surname}"/>
	
	<spring:message code="chapter.area.name" var="area.name" />
	<display:column property="area" title="${area.name}"/>
	
	<spring:message code="chapter.area.pictures" var="area.pictures" />
	<display:column property="area.pictures" title="${area.pictures}"/>
	
</display:table>
<acme:cancel url="/" code="record.goBack" />