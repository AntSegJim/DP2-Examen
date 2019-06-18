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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>



</head>
<body>

<security:authorize access="hasRole('ROOKIE')">
<form:form action=" personalData/rookie/edit.do" modelAttribute="registrationForm">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="patternPhone"/>
	

 
 	<acme:textbox code="personalData.fullName" path="fullName"/>
	<br />
	<acme:textbox code="personalData.statement" path="statement"/>
	<br />
	<acme:textbox code="personalData.phoneNumber" path="phoneNumber"/>
	<br />
	<acme:textbox code="personalData.githubProfile" path="githubProfile"/>
	<br />
	<acme:textbox code="personalData.linkedlnProfile" path="linkedlnProfile"/>
	<br />
	
	<input type="submit" name="save" onclick=" return validar(); "
	value="<spring:message code="save" />" />
	<acme:cancel url="curricula/rookie/list.do" code="cancel"/>
	<br />
</form:form>
</security:authorize>
</body>
<script>
function validar(){
	return validar_phone();
}

 function validar_phone(){
  var numeroTelefono=document.getElementById('phoneNumber');
   var expresionRegular1=/^\+[0-9]{0,3}\ \([0-9]{0,3}\)\ [0-9]{4,}$|^\+[1-9][0-9]{0,2}\ [0-9]{4,}$|^[0-9]{4,}|^\+[0-9]\ $|^$|^\+$/gm;//<-- hay que cambiar el pattern
 

	 if(!expresionRegular1.test(numeroTelefono.value)){
	 var confirmarTelefono= confirm('Are you sure you want to register that phone number?');
	 
	 
	 if(confirmarTelefono==true){
	 
		 document.getElementById('patternPhone').value=true;
		 
	 }
 }
	 return confirmarTelefono ;

	}
</script>
</html>