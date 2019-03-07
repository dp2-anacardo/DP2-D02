<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

</head>
<body>
<security:authorize access="hasRole('BROTHERHOOD')">
<spring:message code="administrator.firstMessage" />
<form:form action="area/brotherhood/edit.do" modelAttribute="bro"
onsubmit="return msgAlerta()">

	<form:hidden path="id" />
 
 	<form:label path="area">
	<spring:message code="administrator.area"/>
	</form:label>
	<form:select path="area">	
		<form:options items="${areas}" itemValue="id" itemLabel="name"
			/>
	</form:select>
	
	<script type="text/javascript">
	function msgAlerta(){
		return confirm('<spring:message code = "administrator.confirm.area"/>');
	}
	</script>
	
	<input type="submit" name="save"
		value="<spring:message code="administrator.save"
		/>"
		 />&nbsp; 
	
		<input type="button" name="cancel"
		value="<spring:message code="administrator.back" />"
		onclick="javascript: relativeRedir('/');" />
	<br />
 	
</form:form>
</security:authorize>
</body>
</html>