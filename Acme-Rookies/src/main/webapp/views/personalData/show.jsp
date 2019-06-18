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
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<security:authorize access="hasRole('ROOKIE')">

<strong><spring:message code="personalData.fullName"/></strong>: <jstl:out value=" ${personalData.fullName}"></jstl:out><br>
<strong><spring:message code="personalData.statement"/></strong>: <jstl:out value=" ${personalData.statement}"></jstl:out><br>
<strong><spring:message code="personalData.phoneNumber"/></strong>: <jstl:out value=" ${personalData.phoneNumber}"></jstl:out><br>
<strong><spring:message code="personalData.githubProfile"/></strong>: <jstl:out value=" ${personalData.githubProfile}"></jstl:out><br>
<strong><spring:message code="personalData.linkedlnProfile"/></strong>: <jstl:out value=" ${personalData.linkedlnProfile}"></jstl:out><br>

<a href="personalData/rookie/delete.do?personalDataId=${personalData.id}"><spring:message code="delete" /></a>
<input type="button" name="create" value="<spring:message code="edit" />"
			onclick="javascript: relativeRedir('personalData/rookie/edit.do?personalDataId=${personalData.id}');" />
<acme:cancel url="curricula/rookie/list.do" code="cancel"/>

</security:authorize>





