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


<security:authorize access="hasRole('AUDITOR')">
<display:table pagesize="5" name="audits" id="row"
requestURI="audit/auditor/list.do?idAuditor=${row.auditor.id}" >

<display:column>
	<a href="audit/auditor/show.do?auditId=${row.id}"><spring:message code="show" /></a>
</display:column>
<display:column titleKey="audit.auditor.name">
<jstl:out value="${row.auditor.name}"></jstl:out>
</display:column>
<display:column titleKey="audit.position.ticker">
<jstl:out value="${row.position.ticker}"></jstl:out>
</display:column>
<display:column titleKey="audit.moment">
<jstl:out value="${row.moment}"></jstl:out>
</display:column>
<display:column titleKey="audit.text">
<jstl:out value="${row.text}"></jstl:out>
</display:column>
<display:column titleKey="audit.score">
<jstl:out value="${row.score}"></jstl:out>
</display:column>
<display:column titleKey="audit.draftMode">
<jstl:out value="${row.draftMode}"></jstl:out>
</display:column>

<display:column>
<jstl:if test="${(row.draftMode eq 1)}">
	<a href="audit/auditor/edit.do?auditId=${row.id}"><spring:message code="edit" /></a>
</jstl:if>
</display:column>

</display:table>

<input type="button" name="create" value="<spring:message code="audit.create" />"
			onclick="javascript: relativeRedir('audit/auditor/create.do');" /><br>

</security:authorize>






<security:authorize access="hasRole('COMPANY')">
<display:table pagesize="5" name="audits" id="row"
requestURI="audit/company/list.do?idPosition=${row.position.id}" >

<display:column titleKey="audit.auditor.name">
<jstl:out value="${row.auditor.name}"></jstl:out>
</display:column>
<display:column titleKey="audit.position.ticker">
<jstl:out value="${row.position.ticker}"></jstl:out>
</display:column>
<display:column titleKey="audit.moment">
<jstl:out value="${row.moment}"></jstl:out>
</display:column>
<display:column titleKey="audit.text">
<jstl:out value="${row.text}"></jstl:out>
</display:column>
<display:column titleKey="audit.score">
<jstl:out value="${row.score}"></jstl:out>
</display:column>
<display:column titleKey="audit.draftMode">
<jstl:out value="${row.draftMode}"></jstl:out>
</display:column>
</display:table>

<acme:cancel url="position/company/list.do" code="cancel"/>
</security:authorize>






