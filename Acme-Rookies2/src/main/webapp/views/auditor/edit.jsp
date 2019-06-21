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




<form:form action="auditor/administrator/edit.do" modelAttribute="registrationForm" name="form">
<jstl:if test="${not empty exception}">
		<p style="color:red"> <spring:message code="auditor.error" /> </p>
</jstl:if>
	<form:hidden path="id"/>
	<form:hidden path="version" />
	<form:hidden path="userAccount.authorities" />
	<form:hidden path="patternPhone" />
	
	
	<fieldset>
	<legend><spring:message code="auditor.personalDatas" /></legend>
	
	<acme:textbox code="auditor.name" path="name"/>
	
	
	<acme:textbox code="auditor.vatNumber" path="vatNumber"/>
	
	
	
	<acme:textbox code="auditor.surname" path="surnames"/>
	
	
		
	<acme:textbox code="auditor.photo" path="photo"/>


	
	<acme:textbox code="auditor.email" path="email"/>
	

	<acme:textbox code="auditor.phone" path="phone"/>
	
	
	<acme:textbox code="auditor.adress" path="address"/>

	<br />
	<p><spring:message code="auditor.information" /></p>
	</fieldset>
	<br />
	
	
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
	 <legend><spring:message code="auditor.userAccount" /></legend>
	 
	 <acme:textbox code="auditor.username" path="userAccount.username"/>

	<acme:password code="auditor.password" path="userAccount.password"/>

	
	<acme:password code="auditor.confirmation.password" path="password"/>
	
	</fieldset>
	<br />
	

	
	 	<!--   <input type="checkbox" class="checkbox"  required name="checkbox" id="checkbox" onclick= "enableSending();"/>  <a  target="_blank" href="https://www.google.com/">
				<spring:message code="Terminos.Condiciones" /></a> -->
			
	  <input type="submit" name="save" onclick=" return validar(); " value="<spring:message code="auditor.save"/> "> 
	<!--<acme:submit name="save" code="auditor.save"/>-->
	<acme:cancel url="welcome/index.do" code="auditor.cancel"/>
</form:form>


<script>
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


