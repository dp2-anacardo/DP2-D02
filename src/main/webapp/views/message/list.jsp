<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<display:table name="nestedBoxes" id="row" requestURI="message/list.do"
	pagesize="5" class="displaytag">

	<spring:message code="messageBox.name" var="subjectHeader" />
	<display:column property="name" title="${subjectHeader}" />

	<!-- Display -->
	<spring:message code="messageBox.display" var="displayHeader" />
	<display:column title="${displayHeader}">
		<a href="message/list.do?messageBoxID=${row.id}"> <spring:message
				code="messageBox.display" /></a>
	</display:column>

	<spring:message code="messageBox.edit" var="editHeader" />
	<display:column title="${editHeader}">
		<a href="messageBox/edit.do?messageBoxID=${row.id}"> <spring:message
				code="messageBox.edit" /></a>
	</display:column>
	
	<spring:message code="messageBox.unnest" var="unnestHeader" />
	<display:column title="${unnestHeader}">
		<a href="messageBox/unnest.do?srcBoxID=${row.id}&destBoxID=${messageBox}"> <spring:message
				code="messageBox.unnest" /></a>
	</display:column>
	

</display:table>

<display:table name="messages" id="row" requestURI="message/list.do"
	pagesize="5" class="displaytag">


	<spring:message code="message.subject" var="subjectHeader" />
	<display:column property="subject" title="${subjectHeader}" />

	<spring:message code="message.sender" var="senderHeader" />
	<display:column property="sender.name" title="${senderHeader}"
		sortable="true" />

	<spring:message code="message.priority" var="priorityHeader" />
	<jstl:choose>
		<jstl:when test="${pageContext.response.locale.language == 'es'}">
			<display:column title="${priorityHeader}" sortable="true">${row.priority.name["ES"]}</display:column>
		</jstl:when>
		<jstl:when test="${pageContext.response.locale.language == 'en'}">
			<display:column title="${priorityHeader}" sortable="true">${row.priority.name["EN"]}</display:column>
		</jstl:when>
	</jstl:choose>

	<!-- Display -->
	<display:column>
		<a
			href="message/display.do?messageID=${row.id}&messageBoxID=${messageBox}">
			<spring:message code="message.display" />
		</a>
	</display:column>

</display:table>

<security:authorize
	access="hasAnyRole('ADMIN', 'MEMBER', 'BROTHERHOOD', 'SPONSOR', 'CHAPTER')">
	<acme:cancel url="message/create.do" code="message.create"/>
</security:authorize>

<acme:cancel url="messageBox/list.do" code="messageBox.goBack"/>
