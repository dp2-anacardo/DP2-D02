<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<security:authorize access="hasRole('ADMIN')">

<spring:message code="administrator.name" />: ${administrator.name} <br/>
<jstl:if test="${administrator.middleName != ''}">
		<spring:message code="administrator.middleName" />: ${administrator.middleName} <br/>
	</jstl:if>
<spring:message code="administrator.surname" />: ${administrator.surname} <br/>
<spring:message code="administrator.email" />: ${administrator.email} <br/>
<spring:message code="administrator.phoneNumber" />: ${administrator.phoneNumber} <br/>


<input type="button" name="Edit PD" value="<spring:message code="edit.PD" />"
			onclick="javascript: relativeRedir('');" />
<input type="button" name="socialProfiles" value="<spring:message code="socialProfile" />"
			onclick="javascript: relativeRedir('/socialProfile/brotherhood,member,admin/list.do');" />
			
</security:authorize>
	
<security:authorize access="hasRole('BROTHERHOOD')">

<spring:message code="administrator.name" />: ${brotherhood.name} <br/>
<spring:message code="administrator.email" />: ${brotherhood.email} <br/>
<spring:message code="administrator.phoneNumber" />: ${brotherhood.phoneNumber} <br/>

<input type="button" name="socialProfiles" value="<spring:message code="socialProfile" />"
			onclick="javascript: relativeRedir('/socialProfile/brotherhood,member,admin/list.do');" />
<input type="button" name="Edit PD" value="<spring:message code="edit.PD" />"
			onclick="javascript: relativeRedir('');" />
</security:authorize>

<security:authorize access="hasRole('MEMBER')">

<spring:message code="administrator.name" />: ${member.name} <br/>
<jstl:if test="${member.middleName != ''}">
		<spring:message code="administrator.middleName" />: ${member.middleName} <br/>
	</jstl:if>
<spring:message code="administrator.surname" />: ${member.surname} <br/>
<spring:message code="administrator.email" />: ${member.email} <br/>
<spring:message code="administrator.phoneNumber" />: ${member.phoneNumber} <br/>


<input type="button" name="Edit PD" value="<spring:message code="edit.PD" />"
			onclick="javascript: relativeRedir('');" />
<input type="button" name="socialProfiles" value="<spring:message code="socialProfile" />"
			onclick="javascript: relativeRedir('/socialProfile/brotherhood,member,admin/list.do');" />
			
</security:authorize>

