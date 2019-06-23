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

	<spring:message code="quolet.ticker" />: <jstl:out value="${quolet.ticker}"></jstl:out> <br />
	
	<jstl:if test="${lang eq 'es' }">
	<spring:message code="quolet.moment" />:
		<fmt:formatDate value="${quolet.moment}" pattern="dd-MM-yy HH:mm" />
		<br />
	</jstl:if>
	
	<jstl:if test="${lang eq 'en' }">
	<spring:message code="quolet.moment" />:
		<fmt:formatDate value="${quolet.moment}" pattern="yy/MM/dd HH:mm" />
		<br />
	</jstl:if>
	<spring:message code="quolet.body" />: <jstl:out value="${quolet.body}"></jstl:out> <br />
	<spring:message code="quolet.draftMode" />:
		<jstl:choose>
			<jstl:when test="${quolet.draftMode eq 0}">
				<spring:message code="quolet.inSaveMode" /> <br/>
			</jstl:when>
		
			<jstl:otherwise>
				<spring:message code="quolet.inDraftMode" /><br/>
			</jstl:otherwise>
	</jstl:choose>
	<spring:message code="quolet.picture" />: <br />
	<img width="400px" height="200px" src="<jstl:out value='${quolet.picture }'/> "><br/>
	 

	<br>
	<div style="text-align: center;">
		<input type="button" name="create" value="<spring:message code="quolet.volver" />"
			onclick="javascript: relativeRedir('quolet/company/list.do?idAudit=${idAudit}');" />
	</div>

</security:authorize>


<security:authorize access="hasRole('AUDITOR')">
	<jstl:choose>
		<jstl:when test="${quolet.numMonth < 1}">
			<jstl:set var="css" value="Indigo"></jstl:set>
		</jstl:when>

		<jstl:when test="${quolet.numMonth < 2 and quolet.numMonth >= 1}">
			<jstl:set var="css" value="DarkSlateGrey"></jstl:set>
		</jstl:when>

		<jstl:when test="${quolet.numMonth >= 2}">
			<jstl:set var="css" value="PapayaWhip"></jstl:set>
		</jstl:when>
	</jstl:choose>
	
	<div style="border: 10px solid ${css}; border-radius: 5px; display:inline-block; padding:1em;">
	
		<spring:message code="quolet.ticker" />: <jstl:out value="${quolet.ticker}"></jstl:out> <br />
		
		<jstl:if test="${lang eq 'es' }">
		<spring:message code="quolet.moment" />:
			<fmt:formatDate value="${quolet.moment}" pattern="dd-MM-yy HH:mm" />
			<br />
		</jstl:if>
		
		<jstl:if test="${lang eq 'en' }">
		<spring:message code="quolet.moment" />:
			<fmt:formatDate value="${quolet.moment}" pattern="yy/MM/dd HH:mm" />
			<br />
		</jstl:if>
		<spring:message code="quolet.body" />: <jstl:out value="${quolet.body}"></jstl:out> <br />
		<spring:message code="quolet.draftMode" />:
			<jstl:choose>
				<jstl:when test="${quolet.draftMode eq 0}">
					<spring:message code="quolet.inSaveMode" /> <br/>
				</jstl:when>
			
				<jstl:otherwise>
					<spring:message code="quolet.inDraftMode" /><br/>
				</jstl:otherwise>
		</jstl:choose>
		<spring:message code="quolet.picture" />: <jstl:out value="${quolet.picture}"></jstl:out> <br />
	
		<br>
	</div>
		<div style="text-align: center;">
			<input type="button" name="create" value="<spring:message code="quolet.volver" />"
				onclick="javascript: relativeRedir('quolet/auditor/list.do?idAudit=${idAudit}');" />
		</div>

	
</security:authorize>

