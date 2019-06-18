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


<security:authorize access="hasRole('AUDITOR')">

<form:form action="auditor/assing-position.do" modelAttribute="auditor">

	<!-- <select>
	<jstl:forEach var= "position" items="${positions}">
	<option><jstl:out value="${position.title }"/> </option>
	</jstl:forEach>
	</select>-->
 	<acme:multipleSelect items="${positions}" itemLabel="title" code="auditor.positions" path="positions"/>
	
	<acme:submit name="save" code="auditor.save"/>
	<acme:cancel url="welcome/index.do" code="auditor.cancel"/>
</form:form>
</security:authorize>
