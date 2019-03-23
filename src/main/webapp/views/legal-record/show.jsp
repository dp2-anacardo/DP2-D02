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
<!DOCTYPE>
<html>
<head>
<link rel="stylesheet" href="styles/table.css" type="text/css">
</head>
<body>
<acme:showtext fieldset="false" code="record.title" value="${record.title}"/>
<br/>
<br/>
<acme:showtext fieldset="false" code="record.description" value="${record.description}"/>
<br/>
<br/>
<acme:showtext fieldset="false" code="record.edit.legalName" value="${record.legalName}"/>
<br/>
<br/>
<acme:showtext fieldset="false" code="record.edit.vatNumber" value="${record.vatNumber}"/>
<br/>
<!-- Applicable Laws -->
<table>
	<tr>
    	<th><spring:message code="record.edit.applicableLaws" /></th>
  	</tr>
	<jstl:forEach items="${record.applicableLaws}" 
					var="law">
		<tr>
    		<td><jstl:out value="${law}"/></td>
  		</tr>
	</jstl:forEach>
</table>
<acme:cancel url="/records" code="record.goBack" />
<jstl:if test="${isBrotherhood}">
	<acme:cancel url="/records/legalRecord/edit.do?id=${record.id}" code="record.edit"/>
	<acme:cancel url="/records/legalRecord/delete.do?id=${record.id}" code="record.edit.delete"/>&nbsp;
</jstl:if>
</body>