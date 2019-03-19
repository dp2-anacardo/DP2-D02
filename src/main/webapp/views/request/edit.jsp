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

<security:authorize access="hasRole('MEMBER')">
<form:form action="request/member/edit.do" modelAttribute="request">
	<form:hidden path="id" />
	
	<form:label path="parade">
		<spring:message code="request.parade"/>
	</form:label>
	<form:select path="parade">	
		<form:options items="${parades}" itemValue="id" itemLabel="title" multiple="false"
			/>
	</form:select>
	<form:errors cssClass="error" path="parade" />
	<br />
	<acme:submit name="save" code="request.save"/>
	<acme:cancel url="request/member/list.do" code="area.cancel"/>
	<br />
	
	
</form:form>
</security:authorize>