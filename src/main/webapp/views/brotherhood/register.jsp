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
<form:form id="myform" action="brotherhood/create.do" modelAttribute="brotherhoodForm"
onsubmit="return validarForm(this)">

	<form:hidden path="id" />
 
	<acme:textbox code="administrator.username" path="username"/>
	<br />
	
	<form:label path="password" >
		<spring:message code="administrator.password" />*:
	</form:label>
	<form:password path="password" onchange='check_pass();'/>
	<form:errors cssClass="error" path="password" />
	<br />
	
	<form:label path="confirmPass">
		<spring:message code="administrator.confirmPass" />*:
	</form:label>
	<form:password path="confirmPass" onchange='check_pass();'/>
	<form:errors cssClass="error" path="password" />
	<br />
	
	
	<acme:textbox code="administrator.name" path="name"/>
	<br />
	
	<acme:textbox code="administrator.title" path="title"/>
	<br />
	
	<acme:textarea code="brotherhood.pictures" path="pictures"/>
	<br />
	
	<acme:textbox code="administrator.photo" path="photo"/>
	<br />
	
	<acme:textbox code="administrator.email" path="email"/>
	<br />
	
	<acme:textbox code="administrator.phoneNumber" path="phoneNumber"/>
	<br />
	
	<acme:textbox code="administrator.address" path="address"/>
	<br />
	
	<form:label path="area">
		<spring:message code="administrator.area"/>
	</form:label>
	<form:select path="area">	
		<form:options items="${areas}" itemValue="id" itemLabel="name"
			/>
	</form:select>
	<form:errors cssClass="error" path="area" />
	<br />
	
	<script type="text/javascript">
	function validarForm(form){
		phoneValidation();
		checkForm(form);
	}
	
	</script>
	
	<script type="text/javascript">
	function phoneValidation(){
		var phoneNumber = document.getElementById("phoneNumber").value;
		var regexPN = /^(\d\d\d\d+)$/;
		var regex1 = /^((\+[1-9][0-9]{0,2}) \(([1-9][0-9]{0,2})\) (\d\d\d\d+))$/;
		var regex2 = /^(\+[1-9][0-9]{0,2}) (\d\d\d\d+)$/;
		
		if (regexPN.test(phoneNumber)) {
			return true;
		} else if(regex1.test(phoneNumber)) {
			return true;
		}else if(regex2.test(phoneNumber)){
			return true;
		}else{
			return confirm('<spring:message code = "customer.confirm"/>');
		}
	}
	</script>
	
	<script>

	function checkForm(form){
		
	    if(!form.terms.checked) {
	      alert("Please indicate that you accept the Terms and Conditions");
	      form.terms.focus();
	      return false;
	    }
	    return true;
	  }

	</script>
	
	 <div class=terms>
	 <input type="checkbox" required name="terms">
	 <label for="terms"><spring:message code="terms" /></label>
	 </div>
	
	<input type="submit" name="save"
		value="<spring:message code="administrator.save"/>"
		 />&nbsp; 
	
		<input type="button" name="cancel"
		value="<spring:message code="administrator.back" />"
		onclick="javascript: relativeRedir('/');" />
	<br />
 	
</form:form>
</body>
</html>