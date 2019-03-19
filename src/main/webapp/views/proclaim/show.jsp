<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


	<acme:showtext fieldset="true" code="proclaim.moment" value="${proclaim.moment}"/>
	
	<acme:showtext fieldset="true" code="proclaim.description" 
	value="${proclaim.description}"/>
	
	<acme:showtext fieldset="true" code="proclaim.chapter" 
	value="${proclaim.chapter.title}"/>

	<acme:cancel url="/" code="proclaim.back" />