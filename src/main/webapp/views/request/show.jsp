<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<acme:showtext fieldset="true" code="request.positionRow" value="${r.positionRow}"/>
<acme:showtext fieldset="true" code="request.positionColumn" value="${r.positionColumn}"/>
<acme:showtext fieldset="true" code="request.status" value="${r.status}"/>
<acme:showtext fieldset="true" code="request.comment" value="${r.comment}"/>
<acme:showtext fieldset="true" code="request.parade" value="${r.parade.title}"/>


<security:authorize access="hasRole('BROTHERHOOD')">
<acme:showtext fieldset="true" code="request.member" value="${r.member.name}"/>
<jstl:if test="${r.status=='PENDING' }">
<acme:cancel url="request/brotherhood/accept.do?requestId=${r.id}" code="request.accept"/>
<acme:cancel url="request/brotherhood/reject.do?requestId=${r.id}" code="request.reject"/>
</jstl:if>


</security:authorize>
