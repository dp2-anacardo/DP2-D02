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

<h3><b><spring:message code="record.InceptionRecord"/>:</b></h3>
<display:table name="InceptionRecord" id="row" requestURI="records/list" pagesize="5" class="displaytag">

	<spring:message code="record.title" var="title" />
	<display:column property="title" title="${title}"/>
	
	<spring:message code="record.description" var="description" />
	<display:column property="description" title="${description}"/>
	
	<display:column>
		<acme:cancel url="/records/inceptionRecord/show.do?id=${row.id}" code="record.show"/>
	</display:column>
	
	<jstl:if test="${isBrotherhood}">
	<display:column>
		<acme:cancel url="/records/inceptionRecord/edit.do?id=${row.id}" code="record.edit"/>&nbsp;
	</display:column>
	</jstl:if>
	
</display:table>
<h3><b><spring:message code="record.LegalRecord"/>:</b></h3>
<br/>
<display:table name="LegalRecord" id="row" requestURI="records/list" pagesize="5" class="displaytag">

	<spring:message code="record.title" var="title" />
	<display:column property="title" title="${title}"/>
	
	<spring:message code="record.description" var="description" />
	<display:column property="description" title="${description}"/>
	
	<display:column>
		<acme:cancel url="/records/legalRecord/show.do?id=${row.id}" code="record.show"/>
	</display:column>
	
	<jstl:if test="${isBrotherhood}">
	<display:column>
		<acme:cancel url="/records/legalRecord/edit.do?id=${row.id}" code="record.edit"/>&nbsp;
	</display:column>
	<display:column>
		<acme:cancel url="/records/legalRecord/delete.do?id=${row.id}" code="record.edit.delete"/>&nbsp;
	</display:column>
	</jstl:if>
	
</display:table>
<jstl:if test="${isBrotherhood}">
	<acme:cancel url="/records/legalRecord/create.do" code="record.edit.create"/>&nbsp;
</jstl:if>
<br/>
<br/>
<h3><b><spring:message code="record.LinkRecord"/>:</b></h3>
<br/>
<display:table name="LinkRecord" id="row" requestURI="records/list" pagesize="5" class="displaytag">

	<spring:message code="record.title" var="title" />
	<display:column property="title" title="${title}"/>
	
	<spring:message code="record.description" var="description" />
	<display:column property="description" title="${description}"/>
	
	<display:column>
		<acme:cancel url="/records/linkRecord/show.do?id=${row.id}" code="record.show"/>
	</display:column>
	
	<jstl:if test="${isBrotherhood}">
	<display:column>
		<acme:cancel url="/records/linkRecord/edit.do?id=${row.id}" code="record.edit"/>&nbsp;
	</display:column>
	<display:column>
		<acme:cancel url="/records/linkRecord/delete.do?id=${row.id}" code="record.edit.delete"/>&nbsp;
	</display:column>
	</jstl:if>
	
</display:table>
<jstl:if test="${isBrotherhood}">
	<acme:cancel url="/records/linkRecord/create.do" code="record.edit.create"/>&nbsp;
</jstl:if>
<br/>
<br/>
<h3><b><spring:message code="record.MiscRecord"/>:</b></h3>
<br/>
<display:table name="MiscRecord" id="row" requestURI="records/list" pagesize="5" class="displaytag">

	<spring:message code="record.title" var="title" />
	<display:column property="title" title="${title}"/>
	
	<spring:message code="record.description" var="description" />
	<display:column property="description" title="${description}"/>
	
	<display:column>
		<acme:cancel url="/records/miscRecord/show.do?id=${row.id}" code="record.show"/>
	</display:column>
	
	<jstl:if test="${isBrotherhood}">
	<display:column>
		<acme:cancel url="/records/miscRecord/edit.do?id=${row.id}" code="record.edit"/>&nbsp;
	</display:column>
	<display:column>
		<acme:cancel url="/records/miscRecord/delete.do?id=${row.id}" code="record.edit.delete"/>&nbsp;
	</display:column>
	</jstl:if>
		
</display:table>
<jstl:if test="${isBrotherhood}">
	<acme:cancel url="/records/miscRecord/create.do" code="record.edit.create"/>&nbsp;
</jstl:if>
<br/>
<br/>
<h3><b><spring:message code="record.PeriodRecord"/>:</b></h3>
<br/>
<display:table name="PeriodRecord" id="row" requestURI="records/list" pagesize="5" class="displaytag">

	<spring:message code="record.title" var="title" />
	<display:column property="title" title="${title}"/>
	
	<spring:message code="record.description" var="description" />
	<display:column property="description" title="${description}"/>
	
	<display:column>
		<acme:cancel url="/records/periodRecord/show.do?id=${row.id}" code="record.show"/>
	</display:column>
	
	<jstl:if test="${isBrotherhood}">
	<display:column>
		<acme:cancel url="/records/periodRecord/edit.do?id=${row.id}" code="record.edit"/>&nbsp;
	</display:column>
	<display:column>
		<acme:cancel url="/records/periodRecord/delete.do?id=${row.id}" code="record.edit.delete"/>&nbsp;
	</display:column>
	</jstl:if>
	
</display:table>
<jstl:if test="${isBrotherhood}">
	<acme:cancel url="/records/periodRecord/create.do" code="record.edit.create"/>&nbsp;
</jstl:if>
<br/>
<br/>
<br/>
<acme:cancel url="/" code="record.goBack" />