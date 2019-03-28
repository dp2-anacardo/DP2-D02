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
<spring:message code="administrator.firstMessage" />
<form:form id="myform" action="sponsor/create.do" modelAttribute="sponsorForm">

	<form:hidden path="id" />
 
	<acme:textbox code="administrator.username" path="username"/>
	<br />
	
	<form:label path="password" >
		<spring:message code="administrator.password" />
	</form:label>
	<form:password path="password" onchange='check_pass();'/>
	<form:errors cssClass="error" path="password" />
	<br />
	
	<form:label path="confirmPass">
		<spring:message code="administrator.confirmPass" />
	</form:label>
	<form:password path="confirmPass" onchange='check_pass();'/>
	<form:errors cssClass="error" path="password" />
	<br />
	
	
	<acme:textbox code="administrator.name" path="name"/>
	<br />
	
	<acme:textbox code="administrator.middleName" path="middleName"/>
	<br />
	
	<acme:textbox code="administrator.surname" path="surname"/>
	<br />
	
	<acme:textbox code="administrator.photo" path="photo"/>
	<br />
	
	<acme:textbox code="administrator.email" path="email"/>
	<br />
	
	<acme:textbox code="administrator.phoneNumber" path="phoneNumber"/>
	<br />
	
	<acme:textbox code="administrator.address" path="address"/>
	<br />
	
	<script type="text/javascript">
	function phoneValidation(){
		var phoneNumber = document.getElementById("phoneNumber").value;
		var regexPN = /^(\d\d\d\d+)$/;
		var regex1 = /^((\+[1-9][0-9]{0,2}) \(([1-9][0-9]{0,2})\) (\d\d\d\d+))$/;
		var regex2 = /^(\+[1-9][0-9]{0,2}) (\d\d\d\d+)$/;
		
		if (regexPN.test(phoneNumber)) {
			return document.getElementById("myform").submit();
		} else if(regex1.test(phoneNumber)) {
			return document.getElementById("myform").submit();
		}else if(regex2.test(phoneNumber)){
			return document.getElementById("myform").submit();
		}else{
			var confirm = window.confirm('<spring:message code = "customer.confirm"/>');
			if(!confirm){
				return window.history.back();
			}else{
				return document.getElementById("myform").submit();
			}
		}
	}
	</script>
	
	 <div class=terms>
	 <input type="checkbox" required name="terms">
	 <label for="terms"><spring:message code="terms" /></label>
	 </div>
	
	<input type="button" name="save"
		value="<spring:message code="administrator.save"/>"
		onclick="phoneValidation();" />&nbsp; 
	
		<input type="button" name="cancel"
		value="<spring:message code="administrator.back" />"
		onclick="javascript: relativeRedir('/');" />
	<br />
 	
</form:form>
</body>
</html>