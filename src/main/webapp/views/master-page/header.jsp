<%--
 * header.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="#"><img src="${configuration.banner}" alt="${configuration.systemName}" height="150" width="400"/></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/action-1.do"><spring:message code="master.page.administrator.action.1" /></a></li>
					<li><a href="administrator/action-2.do"><spring:message code="master.page.administrator.action.2" /></a></li>
					<li><a href="area/administrator/list.do"><spring:message code="master.page.administrator.listArea" /></a></li>		
					<li><a href="position/administrator/list.do"><spring:message code="master.page.administrator.listPosition" /></a></li>
					<li><a href="priority/administrator/list.do"><spring:message code="master.page.administrator.listPriority" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv" href="configuration/administrator/show.do"><spring:message code="master.page.administrator.configuration" /></a></li>
			<li><a class="fNiv" href="administrator/administrator/create.do"><spring:message code="master.page.administrator.Register"/></a></li>
		</security:authorize>
		
		<security:authorize access="hasRole('MEMBER')">
			<li><a class="fNiv"><spring:message	code="master.page.member" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="enrolment/member/list.do"><spring:message code="master.page.enrolment.list" /></a></li>					
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.finder" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="finder/member/edit.do"><spring:message code="master.page.finder.edit" /></a></li>
					<li><a href="finder/member/list.do"><spring:message code="master.page.finder.list" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('BROTHERHOOD')">
			<li><a class="fNiv"><spring:message	code="master.page.brotherhood" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="enrolment/brotherhood/list.do"><spring:message code="master.page.enrolment.list" /></a></li>
					<li><a href="procession/brotherhood/list.do"><spring:message code="master.page.brotherhood.processions" /></a></li>						
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a href="brotherhood/list.do"><spring:message code="master.page.brotherhood.listAll" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="profile/action-1.do"><spring:message code="master.page.profile.action.1" /></a></li>
					<li><a href="messageBox/list.do"><spring:message code="master.page.message" /></a></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

