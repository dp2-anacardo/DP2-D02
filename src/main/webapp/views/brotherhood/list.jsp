<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="brotherhood" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">

	<spring:message code="administrator.name" var="name" />
	<display:column property="name" title="${name}"/>
	
	<spring:message code="administrator.email" var="email" />
	<display:column property="email" title="${email}"/>
	
	<spring:message code="administrator.phoneNumber" var="phoneNumber" />
	<display:column property="phoneNumber" title="${phoneNumber}"/>
	
	<spring:message code="administrator.title" var="title" />
	<display:column property="title" title="${title}"/>
	
	<display:column> <a href="member/list.do?brotherhoodId=${row.id}">
	<spring:message code="brotherhood.member" /></a> </display:column>
	
	<display:column> <a href="procession/listNotRegister.do?brotherhoodId=${row.id}">
	<spring:message code="brotherhood.procession" /></a> </display:column>
	
	<display:column> <a href="floatEntity/listNotRegister.do?brotherhoodId=${row.id}">
	<spring:message code="brotherhood.float" /></a> </display:column>

</display:table>