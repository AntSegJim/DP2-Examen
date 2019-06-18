<%--
 * action-1.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
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

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="hasRole('ROOKIE')">
<spring:message code="application.position"/>
<select id="selectId" onchange="myFunction()">
  <option value="-100">---</option>
  <jstl:forEach items="${positions}" var="p">
  	<option value="${p.id}">${p.title}</option>
  </jstl:forEach>
</select>

<form:form id="miFormulario" action="application/rookie/edit.do?positionId=" modelAttribute="application">
<form:hidden path="id"/>
<form:hidden path="version"/>
<jstl:if test="${not empty exception}">
		<p style="color:red"> <spring:message code="application.hacker.create.error2" /> </p>
</jstl:if>
<acme:selectWithoutNullOption items="${curriculas}" itemLabel="personalData.fullName" code="application.curricula" path="curricula"/>

<input type="submit" name="save" 
	value="<spring:message code="application.create" />" />
	
<input type="button" name="cancel" value="<spring:message code="application.cancel" />"
			onclick="javascript: relativeRedir('application/rookie/list.do');" />
</form:form>

<script>
function myFunction(){
    var select = document.getElementById("selectId");
    var valor= select.options[select.selectedIndex].value;
    document.getElementById("miFormulario").action = "application/rookie/edit.do?positionId="+valor ;
}
</script>

</security:authorize>

