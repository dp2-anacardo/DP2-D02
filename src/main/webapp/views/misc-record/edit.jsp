<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="hasRole('BROTHERHOOD')">
<!DOCTYPE>
<html>
<head>
<link rel="stylesheet" href="styles/table.css" type="text/css">
</head>
<body>
<form:form action ="records/miscRecord/edit.do" modelAttribute="mRF">

	<form:hidden path="id"/>

	<!-- Single areas -->
	<jstl:out value="${messageCode}"/>
	
	<acme:textboxbs code="record.title" path="title"/>
	<acme:textarea bold="true" code="record.description" path="description"/>
	<br/>
	
	<!-- Submit, delete and cancel -->
	
	<acme:submit name="save" code="record.edit.save"/>&nbsp;
	
	<acme:cancel url="/records/miscRecord/delete.do?id=${mRF.id}" code="record.edit.delete"/>&nbsp;
	
	<acme:cancel url="/records/miscRecord/show.do?id=${mRF.id}" code="record.edit.cancel"/>
	
</form:form>
</body>
</html>
</security:authorize>