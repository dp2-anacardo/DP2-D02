<%--
 * action-2.jsp
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


<form:form action="messageBox/edit.do" modelAttribute="messageBox">

	<%--  Hidden properties --%>
	<form:hidden path="id" />

	<%-- Name--%>
	<acme:textbox code="messageBox.name" path="name" />
	<br>
	
	<jstl:if test="${!empty actorBoxes}">
		<select id="actorBoxes">
			<jstl:forEach items="${actorBoxes}" var="msgBox">
				<option value="${msgBox.id}"><jstl:out value="${msgBox.name}"/></option>
			</jstl:forEach>
		</select>

		<a id="nestButton"
			href="messageBox/nest.do?srcBoxID=${messageBoxID}&destBoxID="> <spring:message
				code="messageBox.nest" />
		</a>

		<br>
		<br>
	</jstl:if>

	<script type="text/javascript">
		$(document).ready(function() {
			changeLink();
			$('#actorBoxes').change(function() {
				changeLink();
			});
		});

		function changeLink() {
			var box = document.getElementById("actorBoxes").selectedOptions[0].value;
			var strBox = box.toString();
			var moveUrl = "messageBox/nest.do?srcBoxID=${messageBoxID}&destBoxID=";
			document.getElementById("nestButton").setAttribute("href", moveUrl + strBox);
		}
	</script>

	<%-- Buttons --%>
	<security:authorize access="isAuthenticated()">
		<acme:submit name="save" code="messageBox.save" />

		<acme:cancel url="messageBox/list.do" code="messageBox.cancel" />

		<jstl:if test="${messageBox.id != 0 && messageBox.isSystem == false}">
			<acme:submit name="delete" code="messageBox.delete" />
		</jstl:if>

	</security:authorize>

</form:form>