<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('ADMIN')">
<display:table name="positions" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	
	<display:column> <a href="position/administrator/show.do?positionId=${row.id}">
	<spring:message code="position.show" /></a>
	</display:column>
	
	<jstl:if test="${lang=='en' }">
	<spring:message code="position.roleEn" var="roleEn"/>
	<display:column title="roleEn">
		<jstl:out value="${row.roleEn }"></jstl:out>
	</display:column>
	</jstl:if>
	
	<jstl:if test="${lang=='es' }">
	<spring:message code="position.roleEs" var="roleEs"/>
	<display:column title="roleEs">
		<jstl:out value="${row.roleEs }"></jstl:out>
	</display:column>
	</jstl:if>
	
	<display:column>
		<a href="position/administrator/edit.do?positionId=${row.id}">
			<spring:message code="position.edit"/>
		</a>
	</display:column>
</display:table>
<div>
	<a href="position/administrator/create.do">
	<spring:message code="position.create" />
	</a>
</div>
</security:authorize>