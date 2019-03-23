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
<form:form action ="records/legalRecord/edit.do" modelAttribute="lRF">

	<form:hidden path="id"/>
	

	<!-- Single areas -->
	<jstl:out value="${messageCode}"/>
	
	<acme:textboxbs code="record.edit.title" path="title"/>
	<acme:textboxbs code="record.edit.description" path="description"/>
	<acme:textboxbs code="record.edit.legalName" path="legalName"/>
	<acme:textboxbs code="record.edit.vatNumber" path="vatNumber"/>
	
	<!-- Edit laws -->

	<table>
		<tr>
    		<th><spring:message code="record.edit.applicableLaws" /></th>
    		<th></th>
  		</tr>
		<jstl:forEach items="${lRF.applicableLaws}" 
						var="law">
		<tr>
    		<td><jstl:out value="${law}"/></td>
    		<jstl:if test="${lRF.applicableLaws.size!=1}">
    		<td><acme:cancel url="/records/legalRecord/deleteLaw.do?id=${lRF.id}&law=${law}" 
    			code="record.edit.delete"/></td>
    		</jstl:if>
  		</tr>
		</jstl:forEach>
	</table>
	
	<form:input path="law"/>
	<form:errors path="law" cssClass="error" />
	<acme:submit name="addLaw" code="record.edit.addLaw"/>&nbsp;
	<br/>
	<br/>
	
	<!-- Submit, delete and cancel -->
	
	<acme:submit name="save" code="record.edit.submit"/>&nbsp;
	
	<acme:cancel url="/records/legalRecord/delete.do?id=${lRF.id}" code="record.edit.delete"/>&nbsp;
	
	<acme:cancel url="/records/legalRecord/show.do?id=${lRF.id}" code="record.edit.cancel"/>
	
</form:form>
</body>
</html>
</security:authorize>