<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>



<security:authorize access="hasRole('ADMIN')">

<acme:showtext code="administrator.name" value="${m.name}" fieldset="true"/>
<br>
<jstl:if test="${administrator.middleName != ''}">
		<acme:showtext code="administrator.middleName" value="${m.middleName}" fieldset="true"/>
		<br>
</jstl:if>
<acme:showtext code="administrator.surname" value="${m.surname}" fieldset="true"/>
<br>
<acme:showtext code="administrator.email" value="${m.email}" fieldset="true"/>
<br>
<acme:showtext code="administrator.phoneNumber" value="${m.phoneNumber}" fieldset="true"/>
<br>
<acme:showtext code="administrator.address" value="${m.address}" fieldset="true"/>
<br>
<acme:showtext code="administrator.score" value="${m.score }" fieldset="true"/>
			
</security:authorize>
	
<security:authorize access="hasRole('BROTHERHOOD')">

<acme:showtext code="administrator.name" value="${m.name}" fieldset="true"/>
<br>
<jstl:if test="${administrator.middleName != ''}">
		<acme:showtext code="administrator.middleName" value="${m.middleName}" fieldset="true"/>
		<br>
</jstl:if>
<acme:showtext code="administrator.surname" value="${m.surname}" fieldset="true"/>
<br>
<acme:showtext code="administrator.email" value="${m.email}" fieldset="true"/>
<br>
<acme:showtext code="administrator.phoneNumber" value="${m.phoneNumber}" fieldset="true"/>
<br>
<acme:showtext code="administrator.address" value="${m.address}" fieldset="true"/>
<br>

</security:authorize>



