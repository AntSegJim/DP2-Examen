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

<security:authorize access="hasRole('COMPANY')">

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
	<spring:message code="quolet.picture" />: ${quolet.picture} <br />

	<br>
	<div style="text-align: center;">
		<input type="button" name="create" value="<spring:message code="quolet.volver" />"
			onclick="javascript: relativeRedir('quolet/company/list.do?idAudit=${idAudit}');" />
	</div>

</security:authorize>


<security:authorize access="hasRole('COMPANY')">

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
	<spring:message code="quolet.picture" />: ${quolet.picture} <br />

	<br>
	<div style="text-align: center;">
		<input type="button" name="create" value="<spring:message code="quolet.volver" />"
			onclick="javascript: relativeRedir('quolet/company/list.do?idAudit=${idAudit}');" />
	</div>

</security:authorize>
