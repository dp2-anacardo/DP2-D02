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
	
	<display:column> <a href="proclaim/list.do?chapterId=${row.id}">
	<spring:message code="chapter.proclaims" /></a> </display:column>
	
	<display:column> <a href="area/list.do?chapterId=${row.id}">
	<spring:message code="chapter.area" /></a> </display:column>
	
</display:table>
<acme:cancel url="/" code="record.goBack" />