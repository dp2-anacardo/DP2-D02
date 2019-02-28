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
<form:form action="position/administrator/edit.do" modelAttribute="position">
	<form:hidden path="id" />
	
	<form:label path="roleEn">
		<spring:message code="position.roleEn"/>
	</form:label>
	<form:input path="roleEn"/>
	<form:errors cssClass="error" path="roleEn"/>
	<br />
	
	<form:label path="roleEs">
		<spring:message code="position.roleEs"/>
	</form:label>
	<form:input path="roleEs"/>
	<form:errors cssClass="error" path="roleEs"/>
	<br />
	
	<input type="submit" name="save"
		value="<spring:message code="position.save" />" />&nbsp; 
	<jstl:if test="${position.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="position.delete" />" />
	</jstl:if>
	<input type="button" name="cancel"
		value="<spring:message code="position.cancel" />"
		onclick="javascript: relativeRedir('position/administrator/list.do');" />
	<br />
	
	
</form:form>
</security:authorize>