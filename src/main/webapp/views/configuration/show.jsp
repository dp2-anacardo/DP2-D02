<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<p>
	<acme:showtext code="configuration.edit.maxResults" 
	value="${config.maxResults}"
	fieldset="true"/>
</p>
<p>
	<acme:showtext code="configuration.edit.maxTime" 
	value="${config.maxTime}"
	fieldset="true"/>
</p>
<p>
	<acme:showtext code="configuration.edit.systemName" 
	value="${config.systemName}"
	fieldset="true"/>
</p>
<p>
	<acme:showtext code="configuration.edit.banner" 
	value="${config.banner}"
	fieldset="true"/>
</p>
<p>
	<acme:showtext code="configuration.edit.welcomeEn" 
	value="${config.welcomeMessageEn}"
	fieldset="true"/>
</p>
<p>
	<acme:showtext code="configuration.edit.welcomeEs" 
	value="${config.welcomeMessageEs}"
	fieldset="true"/> 
</p>
<p>
	<acme:showtext code="configuration.edit.defaultCC" 
	value="${config.defaultCC}"
	fieldset="true"/>
</p>

<!-- HACER SPAM WORD POS WORD Y NEG WORDS -->


<input type="button" name="edit PD"
		value="<spring:message code="configuration.edit" />"
		onclick="javascript: relativeRedir('/configuration/administrator/edit.do');" />
	<br />