<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('ADMIN')">

	<p>
	<spring:message code="position.roleEn"/>:
	<jstl:out value="${position.roleEn}"></jstl:out> 
	</p>
	
	<p>
	<spring:message code="position.roleEs"/>:
	<jstl:out value="${position.roleEs}"></jstl:out>
	</p>

	<input type="button" name="back"
		value="<spring:message code="position.back" />"
		onclick="javascript: relativeRedir('position/administrator/list.do');" />
</security:authorize>