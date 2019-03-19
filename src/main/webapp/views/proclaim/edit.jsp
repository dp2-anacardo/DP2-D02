<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('CHAPTER')">
<form:form action="proclaim/chapter/edit.do" modelAttribute="proclaim">
	<form:hidden path="id" />
	<form:hidden path="version"/>
	<form:hidden path="moment"/>
	<form:hidden path="chapter"/>
	
	<form:label path="description">
		<spring:message code="proclaim.description"/>
	</form:label>
	<form:textarea path="description"/>
	<form:errors cssClass="error" path="description"/>
	<br />
	
	<input type="submit" name="save"
		value="<spring:message code="proclaim.save" />"
		onclick="return confirm('<spring:message code="proclaim.confirm" />')" />&nbsp; 
	<input type="button" name="cancel"
		value="<spring:message code="proclaim.cancel" />"
		onclick="javascript: relativeRedir('proclaim/chapter/list.do');" />
	<br />
	
	
</form:form>
</security:authorize>