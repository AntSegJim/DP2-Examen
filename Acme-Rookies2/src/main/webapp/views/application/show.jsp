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

<security:authorize access="hasRole('COMPANY')">
<spring:message code="application.curricula"/>:<jstl:out value=" ${application.curricula.personalData.fullName}"></jstl:out><br>
<spring:message code="application.moment"/>: <jstl:out value="${application.moment}"></jstl:out><br>
<spring:message code="application.explication"/>: <jstl:out value="${application.explication}"></jstl:out><br>
<spring:message code="application.urlCode"/>: <jstl:out value="${application.urlCode}"></jstl:out><br>
<spring:message code="application.submitMoment"/>: <jstl:out value="${application.submitMoment}"></jstl:out><br>
<spring:message code="application.status"/>: 
<jstl:if test="${application.status eq 0 }">
<spring:message code="application.status.pending"/>
</jstl:if>
<jstl:if test="${application.status eq 1 }">
<spring:message code="application.status.submitted"/>
</jstl:if>
<jstl:if test="${application.status eq 2 }">
<spring:message code="application.status.accepted"/>
</jstl:if>
<jstl:if test="${application.status eq 3 }">
<spring:message code="application.status.cancel"/>
</jstl:if>
<br>
<display:table pagesize="1" name="problem" id="row" >

	<display:column property="title" titleKey="application.problem.title" />
	<display:column property="statement" titleKey="application.problem.statement" />
	<display:column property="hint" titleKey="application.problem.hint" />
	<display:column property="attachment" titleKey="application.problem.attachment" />
	
</display:table>
<input type="button" name="create" value="<spring:message code="application.back" />"
			onclick="javascript: relativeRedir('application/company/list.do');" />

</security:authorize>

<security:authorize access="hasRole('ROOKIE')">

<spring:message code="application.curricula"/>:<jstl:out value=" ${application.curricula.personalData.fullName}"></jstl:out><br>
<spring:message code="application.moment"/>: <jstl:out value="${application.moment}"></jstl:out><br>
<spring:message code="application.explication"/>: <jstl:out value="${application.explication}"></jstl:out><br>
<spring:message code="application.urlCode"/>: <jstl:out value="${application.urlCode}"></jstl:out><br>
<spring:message code="application.submitMoment"/>: <jstl:out value="${application.submitMoment}"></jstl:out><br>
<spring:message code="application.status"/>: 
<jstl:if test="${application.status eq 0 }">
<spring:message code="application.status.pending"/>
</jstl:if>
<jstl:if test="${application.status eq 1 }">
<spring:message code="application.status.submitted"/>
</jstl:if>
<jstl:if test="${application.status eq 2 }">
<spring:message code="application.status.accepted"/>
</jstl:if>
<jstl:if test="${application.status eq 3 }">
<spring:message code="application.status.cancel"/>
</jstl:if>
<br>

<display:table pagesize="1" name="problem" id="row" >

	<display:column property="title" titleKey="application.problem.title" />
	<display:column property="statement" titleKey="application.problem.statement" />
	<display:column property="hint" titleKey="application.problem.hint" />
	<display:column property="attachment" titleKey="application.problem.attachment" />
	
</display:table>
<input type="button" name="create" value="<spring:message code="application.back" />"
			onclick="javascript: relativeRedir('application/rookie/list.do');" />

</security:authorize>