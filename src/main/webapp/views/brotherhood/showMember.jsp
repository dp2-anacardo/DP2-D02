<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>



<security:authorize access="hasRole('ADMIN')">

	<acme:showtext code="administrator.name" value="${actor.name}"
		fieldset="true" />
	<br>
	<jstl:if test="${member}">
		<jstl:if test="${actor.middleName != ''}">
			<acme:showtext code="administrator.middleName"
				value="${actor.middleName}" fieldset="true" />
			<br>
		</jstl:if>
	</jstl:if>
	
	<jstl:if test="${member}">
	<acme:showtext code="administrator.surname" value="${actor.surname}"
		fieldset="true" />
	<br>
	</jstl:if>
	
	<acme:showtext code="administrator.email" value="${actor.email}"
		fieldset="true" />
	<br>
	<acme:showtext code="administrator.phoneNumber"
		value="${actor.phoneNumber}" fieldset="true" />
	<br>
	<acme:showtext code="administrator.address" value="${actor.address}"
		fieldset="true" />
	<br>

	<jstl:if test="${actor.score != null}">
		<acme:showtext code="member.score" value="${actor.score}"
			fieldset="true" />
		<br>
	</jstl:if>

	<jstl:if test="${actor.score == null}">
		<acme:showtext code="member.score" value="N/A" fieldset="true" />
		<br>
	</jstl:if>

	<jstl:if test="${actor.isSuspicious != null}">
		<acme:showtext code="member.isSpammer" value="${actor.isSuspicious}"
			fieldset="true" />
		<br>
	</jstl:if>

	<jstl:if test="${actor.isSuspicious == null}">
		<acme:showtext code="member.isSpammer" value="N/A" fieldset="true" />
		<br>
	</jstl:if>
	
	<acme:cancel url="/administrator/actorList.do" code="messageBox.goBack" />
	
</security:authorize>

<security:authorize access="hasRole('BROTHERHOOD')">

	<acme:showtext code="administrator.name" value="${m.name}"
		fieldset="true" />
	<br>
	<jstl:if test="${administrator.middleName != ''}">
		<acme:showtext code="administrator.middleName" value="${m.middleName}"
			fieldset="true" />
		<br>
	</jstl:if>
	<acme:showtext code="administrator.surname" value="${m.surname}"
		fieldset="true" />
	<br>
	<acme:showtext code="administrator.email" value="${m.email}"
		fieldset="true" />
	<br>
	<acme:showtext code="administrator.phoneNumber"
		value="${m.phoneNumber}" fieldset="true" />
	<br>
	<acme:showtext code="administrator.address" value="${m.address}"
		fieldset="true" />
	<br>

	<acme:cancel url="/" code="messageBox.goBack" />
</security:authorize>



