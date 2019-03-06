<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="actors" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<spring:message code="member.name" var="name" />
	<display:column property="name" title="${name}" />

	<spring:message code="member.email" var="email" />
	<display:column property="email" title="${email}" />

	<spring:message code="member.phoneNumber" var="phoneNumber" />
	<display:column property="phoneNumber" title="${phoneNumber}" />

	<spring:message code="member.score" var="score" />
	<display:column title="${score}">
		<jstl:if test="${row.score == null}">
			<jstl:out value="N/A"></jstl:out>
		</jstl:if>
		<jstl:if test="${row.score != null}">
			<jstl:out value="${row.score}"></jstl:out>
		</jstl:if>
	</display:column>
	
	<spring:message code="member.isSpammer" var="isSpammer" />
	<display:column title="${isSpammer}">
		<jstl:if test="${row.isSuspicious == null}">
			<jstl:out value="N/A"></jstl:out>
		</jstl:if>
		<jstl:if test="${row.isSuspicious != null}">
			<jstl:out value="${row.isSuspicious}"></jstl:out>
		</jstl:if>
	</display:column>
	
	<spring:message code="member.ban" var="ban" />
	<display:column title="${ban}">
		<jstl:if test="${(row.isSuspicious == true || (row.score <= -0.5 )) && row.isBanned == false}">
			<acme:cancel url='administrator/actorList/ban.do?actorId=${row.id}' code="administrator.ban" />
		</jstl:if>
		<jstl:if test="${(row.isSuspicious == true || (row.score <= -0.5 )) && row.isBanned == true}">
			<acme:cancel url='administrator/actorList/unban.do?actorId=${row.id}' code="administrator.unban" />
		</jstl:if>
	</display:column>
</display:table>

<acme:cancel url="administrator/actorList/calculateScore.do?" code="administrator.calculateScore" />
<acme:cancel url="administrator/actorList/calculateSpam.do" code="administrator.calculateSpam" />
<acme:cancel url="/" code="messageBox.goBack" />