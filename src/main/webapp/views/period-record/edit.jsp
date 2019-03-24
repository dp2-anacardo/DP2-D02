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
<form:form action ="records/periodRecord/edit.do" modelAttribute="pRF">

	<form:hidden path="id"/>
	

	<!-- Single areas -->
	<jstl:out value="${messageCode}"/>
	
	<acme:textboxbs code="record.edit.title" path="title"/>
	<acme:textboxbs code="record.edit.description" path="description"/>
	<acme:textboxbs code="record.edit.startYear" path="startYear"/>
	<acme:textboxbs code="record.edit.endYear" path="endYear"/>
	
	<!-- Edit photos -->

	<table>
		<tr>
    		<th><spring:message code="record.photo" /></th>
    		<th><spring:message code="record.edit.miniature"/></th>
    		<th></th>
  		</tr>
		<jstl:forEach items="${pRF.photo}" 
						var="photos">
		<tr>
    		<td><jstl:out value="${photos.link}"/></td>
    		<td><img src="${photos.link}" alt="link" height=32 width=32/></td>
    		<td><acme:cancel url="/records/periodRecord/deletePhoto.do?id=${pRF.id}&pos=${cont}" 
    			code="record.edit.delete"/></td>
  		</tr>
  		<jstl:set var="cont" value="${cont+1}" />
		</jstl:forEach>
	</table>
	
	<form:input path="link"/>
	<form:errors path="link" cssClass="error" />
	<acme:submit name="addPhoto" code="record.edit.addPhoto"/>&nbsp;
	<br/>
	<br/>
	
	<!-- Submit, delete and cancel -->
	
	<acme:submit name="save" code="record.edit.submit"/>&nbsp;
	
	<acme:cancel url="/records/periodRecord/delete.do?id=${pRF.id}" code="record.edit.delete"/>&nbsp;
	
	<acme:cancel url="/records/periodRecord/show.do?id=${pRF.id}" code="record.edit.cancel"/>
	
</form:form>
</body>
</html>
</security:authorize>