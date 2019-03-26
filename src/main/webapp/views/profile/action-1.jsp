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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>



<security:authorize access="hasRole('ADMIN')">

<acme:showtext code="administrator.name" value="${administrator.name}" fieldset="true"/>
<br>
<jstl:if test="${administrator.middleName != ''}">
		<acme:showtext code="administrator.middleName" value="${administrator.middleName}" fieldset="true"/>
		<br>
</jstl:if>
<acme:showtext code="administrator.surname" value="${administrator.surname}" fieldset="true"/>
<br>
<acme:showtext code="administrator.email" value="${administrator.email}" fieldset="true"/>
<br>
<acme:showtext code="administrator.phoneNumber" value="${administrator.phoneNumber}" fieldset="true"/>
<br>
<acme:showtext code="administrator.address" value="${administrator.address}" fieldset="true"/>
<br>




<input type="button" name="Edit PD" value="<spring:message code="edit.PD" />"
			onclick="javascript: relativeRedir('/administrator/administrator/edit.do');" />
<input type="button" name="socialProfiles" value="<spring:message code="socialProfile" />"
			onclick="javascript: relativeRedir('/socialProfile/brotherhood,member,admin/list.do');" />
			
<input type="button" name="socialProfiles" value="<spring:message code="profile.export" />"
			onclick="javascript: relativeRedir('/profile/exportJSON.do');" />

<input type="button" name="deleteInformation" value="<spring:message code="actor.deleteInformationButton" />"
			onclick="deleteValidation();"/>
			
</security:authorize>
	
<security:authorize access="hasRole('BROTHERHOOD')">

<acme:showtext code="administrator.name" value="${brotherhood.name}" fieldset="true"/>
<br>
<acme:showtext code="administrator.title" value="${brotherhood.title}" fieldset="true"/>
<br>
<acme:showtext code="administrator.email" value="${brotherhood.email}" fieldset="true"/>
<br>
<acme:showtext code="administrator.phoneNumber" value="${brotherhood.phoneNumber}" fieldset="true"/>
<br>
<acme:showtext code="administrator.address" value="${brotherhood.address}" fieldset="true"/>
<br>
<acme:showtext code="administrator.pictures" value="${brotherhood.pictures}" fieldset="true"/>
<br>

<input type="button" name="Edit PD" value="<spring:message code="edit.PD" />"
			onclick="javascript: relativeRedir('brotherhood/brotherhood/edit.do');" />
<input type="button" name="socialProfiles" value="<spring:message code="socialProfile" />"
			onclick="javascript: relativeRedir('/socialProfile/brotherhood,member,admin/list.do');" />
		
<input type="button" name="socialProfiles" value="<spring:message code="profile.export" />"
			onclick="javascript: relativeRedir('/profile/exportJSON.do');" />
			
<input type="button" name="deleteInformation" value="<spring:message code="actor.deleteInformationButton" />"
			onclick="javascript: relativeRedir('/profile/deleteInformation');" />
</security:authorize>



<security:authorize access="hasRole('MEMBER')">

<acme:showtext code="administrator.name" value="${member.name}" fieldset="true"/>
<br>
<jstl:if test="${member.middleName != ''}">
		<acme:showtext code="administrator.middleName" value="${member.middleName}" fieldset="true"/>
		<br>
</jstl:if>
<acme:showtext code="administrator.surname" value="${member.surname}" fieldset="true"/>
<br>
<acme:showtext code="administrator.email" value="${member.email}" fieldset="true"/>
<br>
<acme:showtext code="administrator.phoneNumber" value="${member.phoneNumber}" fieldset="true"/>
<br>
<acme:showtext code="administrator.address" value="${member.address}" fieldset="true"/>
<br>
<input type="button" name="Edit PD" value="<spring:message code="edit.PD" />"
			onclick="javascript: relativeRedir('/member/member/edit.do');" />
<input type="button" name="socialProfiles" value="<spring:message code="socialProfile" />"
			onclick="javascript: relativeRedir('/socialProfile/brotherhood,member,admin/list.do');" />
			
<input type="button" name="socialProfiles" value="<spring:message code="profile.export" />"
			onclick="javascript: relativeRedir('/profile/exportJSON.do');" />
	
<input type="button" name="deleteInformation" value="<spring:message code="actor.deleteInformationButton" />"
			onclick="javascript: relativeRedir('/profile/deleteInformation');" />			
</security:authorize>

<acme:cancel url="/" code="messageBox.goBack"/>

<script type="text/javascript">
	function deleteValidation(){
		doubleConfirmation();
		redirect();
	}
	
	</script>

<script type="text/javascript">
function redirect(){
	relativeRedir('/profile/deleteInformation.do');
}
</script>

<script type="text/javascript">
function doubleConfirmation(){
	return confirm('<spring:message code="delete.confirm" />');
}
</script>