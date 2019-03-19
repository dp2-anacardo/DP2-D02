<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<head>
<link rel="stylesheet" href="styles/errorEmpty.css" type="text/css">
</head>

<div class="error">
<spring:message code="parade.notMatches"/>
</div>
<br/>

<display:table name="parade" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<spring:message code="parade.title" var="columnTitle"/>
	
	<display:column title="${columnTitle}">
		<jstl:out value="${row.title}"></jstl:out>
	</display:column>
	
	<display:column>
		<a href="parade/show.do?paradeId=${row.id}">
			<spring:message code="parade.show"/>
		</a>
	</display:column>
	
</display:table>