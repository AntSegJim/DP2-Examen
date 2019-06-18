<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="provider/edit.do" modelAttribute="registrationForm">
<jstl:if test="${not empty exception}">
		<p style="color:red"> <spring:message code="provider.error" /> </p>
</jstl:if>
	<form:hidden path="id"/>
	<form:hidden path="version" />
	<form:hidden path="patternPhone"/>
	
	
	
	<fieldset>
	<legend><spring:message code="provider.personalDatas" /></legend>
	
	<acme:textbox code="provider.name" path="name"/>
		
	
	<acme:textbox code="provider.surnames" path="surnames"/>
	
	
	<acme:textbox code="provider.vatNumber" path="vatNumber"/>
	
		
	<acme:textbox code="provider.photo" path="photo"/>	
		
	<acme:textbox code="provider.email" path="email"/>	
	
	
	<acme:textbox code="provider.phone" path="phone"/>	

	
	<acme:textbox code="provider.address" path="address"/>	
	
	
	<br />
	<p><spring:message code="provider.information" /></p>
	</fieldset>
	<br />
	
	<fieldset>
	 <legend><spring:message code="provider.Data" /></legend>
	<acme:textbox code="provider.registro.make" path="make"/>	
	<br />
	</fieldset>
	
	<fieldset>
	 <legend><spring:message code="creditCard.Data" /></legend>
	<acme:textbox code="creditCard.registro.brandName" path="brandName"/>
	<acme:textbox code="creditCard.registro.holderName" path="holderName"/>		
	<acme:textbox code="creditCard.registro.number" path="number"/>
	<acme:textbox code="creditCard.registro.expirationMonth" path="expirationMonth"/>
	<acme:textbox code="creditCard.registro.expirationYear" path="ExpirationYear"/>
	<acme:textbox code="creditCard.registro.CW" path="CW"/>
	<br />
	</fieldset>
	
	<fieldset>
	 <legend><spring:message code="provider.userAccount" /></legend>
	<acme:textbox code="provider.username" path="userAccount.username"/>	
	<acme:password code="provider.password" path="userAccount.password"/>
	<acme:password code="provider.confirmation.password" path="password"/>
	</fieldset>
	<br />
	
	<acme:checkbox code="Terminos.Condiciones" path="check" />
	
	<input type="submit" name="save" onclick=" return validar(); "
	value="<spring:message code="provider.save" />" />
	
	<acme:cancel url="welcome/index.do" code="provider.cancel"/>
	
</form:form>

<script>
$( document ).ready(function() {
	document.getElementById("checkbox").value='false';
	document.getElementById("checkbox").checked=false;
});
$( '#checkbox' ).on( 'click', function() {
    if( $(this).is(':checked') ){
        document.getElementById("checkbox").value='true';
    } else {
    	document.getElementById("checkbox").value='false';
    }
});

function validar(){
	return validar_phone();
}

 function validar_phone(){
  var numeroTelefono=document.getElementById('phone');
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