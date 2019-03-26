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

<acme:showtext fieldset="false" code="record.title" value="${record.title}"/>
<br/>
<br/>
<acme:showtext fieldset="false" code="record.description" value="${record.description}"/>
<br/>
<br/>
<acme:showtext fieldset="false" code="record.startYear" value="${record.startYear}"/>
<br/>
<br/>
<acme:showtext fieldset="false" code="record.endYear" value="${record.endYear}"/>
<br/>
<br/>
<b><spring:message code="record.photo"/>:</b>
<br/>
<jstl:forEach items="${record.photo}" var="photo">
	<img src="${photo.link}" alt="link" height=300 width=500/>
</jstl:forEach>
<br/>
<br/>
<acme:cancel url="/records/list.do?brotherhoodId=${record.brotherhood.id}" code="record.goBack" />
<jstl:if test="${isBrotherhood}">
	<acme:cancel url="/records/periodRecord/edit.do?id=${record.id}" code="record.edit"/>
	<acme:cancel url="/records/periodRecord/delete.do?id=${record.id}" code="record.edit.delete"/>
</jstl:if>