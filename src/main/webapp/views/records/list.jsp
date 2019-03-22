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

<b><spring:message code="record.InceptionRecord"/></b>
<display:table name="InceptionRecord" id="row" requestURI="records/list" pagesize="5" class="displaytag">

	<spring:message code="record.title" var="title" />
	<display:column property="title" title="${title}"/>
	
	<spring:message code="record.description" var="description" />
	<display:column property="description" title="${description}"/>
	
	<display:column>
		<acme:cancel url="/records/inceptionRecord/show.do?id=${row.id}" code="record.show"/>
	</display:column>
	
</display:table>
<br>
<b><spring:message code="record.LegalRecord"/></b>
<display:table name="LegalRecord" id="row" requestURI="records/list" pagesize="5" class="displaytag">

	<spring:message code="record.title" var="title" />
	<display:column property="title" title="${title}"/>
	
	<spring:message code="record.description" var="description" />
	<display:column property="description" title="${description}"/>
	
	<display:column>
		<acme:cancel url="/records/legalRecord/show.do?id=${row.id}" code="record.show"/>
	</display:column>
	
</display:table>
<br>
<b><spring:message code="record.LinkRecord"/></b>
<display:table name="LinkRecord" id="row" requestURI="records/list" pagesize="5" class="displaytag">

	<spring:message code="record.title" var="title" />
	<display:column property="title" title="${title}"/>
	
	<spring:message code="record.description" var="description" />
	<display:column property="description" title="${description}"/>
	
	<display:column>
		<acme:cancel url="/records/linkRecord/show.do?id=${row.id}" code="record.show"/>
	</display:column>
	
</display:table>
<br>
<b><spring:message code="record.MiscRecord"/></b>
<display:table name="MiscRecord" id="row" requestURI="records/list" pagesize="5" class="displaytag">

	<spring:message code="record.title" var="title" />
	<display:column property="title" title="${title}"/>
	
	<spring:message code="record.description" var="description" />
	<display:column property="description" title="${description}"/>
	
</display:table>
<br>
<b><spring:message code="record.PeriodRecord"/></b>
<display:table name="PeriodRecord" id="row" requestURI="records/list" pagesize="5" class="displaytag">

	

	<spring:message code="record.title" var="title" />
	<display:column property="title" title="${title}"/>
	
	<spring:message code="record.description" var="description" />
	<display:column property="description" title="${description}"/>
	
	<display:column>
		<acme:cancel url="/records/periodRecord/show.do?id=${row.id}" code="record.show"/>
	</display:column>
	
</display:table>

<acme:cancel url="/" code="record.goBack" />