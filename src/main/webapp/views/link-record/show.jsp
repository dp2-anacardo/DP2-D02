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
<b><spring:message code="record.linkedBH"/>:</b>
<br/>
<table>
	<tr>
    	<th><spring:message code="bh.title"/></th>
    	<th><spring:message code="bh.date"/></th>
    	<th></th>
    	<th></th>
    	<th></th>
  	</tr>
	<tr>
   		<td><jstl:out value="${record.linkedBH.title}"/></td>
   		<td><jstl:out value="${record.linkedBH.establishmentDate}"/></td>
   		<td><a href="member/list.do?brotherhoodId=${record.linkedBH.id}"><spring:message code="brotherhood.member" /></a></td>
   		<td><a href="floatEntity/listNotRegister.do?brotherhoodId=${record.linkedBH.id}"><spring:message code="brotherhood.float" /></a></td>
   		<td><a href="records/list.do?brotherhoodId=${record.linkedBH.id}"><spring:message code="brotherhood.records" /></a></td>
  	</tr>
</table>
<br/>
<acme:cancel url="/records/list.do?brotherhoodId=${record.brotherhood.id}" code="record.goBack" />
<jstl:if test="${isBrotherhood}">
	<acme:cancel url="/records/linkRecord/edit.do?id=${record.id}" code="record.edit"/>
	<acme:submit name="delete" code="record.edit.delete"/>&nbsp;
</jstl:if>
</body>
</html>