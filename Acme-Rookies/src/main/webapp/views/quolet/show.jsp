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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<security:authorize access="hasRole('CUSTOMER')">

	<spring:message code="quolet.ticker" />: ${quolet.ticker} <br />
	
	<jstl:if test="${language eq 'es' }">
	<spring:message code="quolet.moment" />:
		<fmt:formatDate value="${quolet.moment}" pattern="dd-MM-yy HH:mm" />
		<br />
	</jstl:if>
	
	<jstl:if test="${language eq 'en' }">
	<spring:message code="quolet.moment" />:
		<fmt:formatDate value="${quolet.moment}" pattern="yy-MM-dd HH:mm" />
		<br />
	</jstl:if>
	<spring:message code="quolet.body" />: ${quolet.body} <br />
	<spring:message code="quolet.draftMode" />: ${quolet.draftMode} <br />
	<spring:message code="quolet.fixUpTask" />: ${quolet.fixUpTask.id} <br />
	<spring:message code="quolet.picture" />: ${quolet.picture} <br />

	<br>
	<div style="text-align: center;">
		<a href="burse/customer/list.do?fixUpTaskId=${quolet.fixUpTask.id}"> <spring:message
				code="quolet.volver" />
		</a>
	</div>

</security:authorize>


<security:authorize access="hasRole('HANDYWORKER')">

	<spring:message code="quolet.ticker" />: ${quolet.ticker} <br />
	<jstl:if test="${language eq 'es' }">
	<spring:message code="quolet.moment" />:
		<fmt:formatDate value="${quolet.moment}" pattern="dd-MM-yy HH:mm" />
		<br />
	</jstl:if>
	
	<jstl:if test="${language eq 'en' }">
	<spring:message code="quolet.moment" />:
		<fmt:formatDate value="${quolet.moment}" pattern="yy-MM-dd HH:mm" />
		<br />
	</jstl:if>
	<spring:message code="quolet.body" />: ${quolet.body} <br />
	<spring:message code="quolet.draftMode" />: ${quolet.draftMode} <br />
	<spring:message code="quolet.fixUpTask" />: ${quolet.fixUpTask.id} <br />
	<spring:message code="quolet.picture" />: ${quolet.picture} <br />

	<br>
	<div style="text-align: center;">
		<a href="burse/handy-worker/list.do?fixUpTaskId=${quolet.fixUpTask.id}"> <spring:message
				code="quolet.volver" />
		</a>
	</div>

</security:authorize>
