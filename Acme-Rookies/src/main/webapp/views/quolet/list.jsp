<%--
 * action-2.jsp
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

<security:authorize access="hasRole('COMPANY')">
	
<display:table pagesize="5" name="quolets" id="row"
requestURI="quolet/company/list.do?idAudit=${audit.id }" >

<display:column>
<jstl:if test="${(row.draftMode eq 1)}">
	<a href="quolet/company/edit.do?idQuolet=${row.id}"><spring:message code="quolet.edit" /></a>
</jstl:if>
</display:column>	
<display:column titleKey="quolet.ticker">
<jstl:out value="${row.ticker}"></jstl:out>
</display:column>
<display:column titleKey="quolet.moment">
<jstl:out value="${row.moment}"></jstl:out>
</display:column>
<display:column titleKey="quolet.body">
<jstl:out value="${row.body}"></jstl:out>
</display:column>
<display:column titleKey="quolet.picture">
<jstl:out value="${row.picture}"></jstl:out>
</display:column>
<display:column titleKey="quolet.draftMode">
<jstl:out value="${row.draftMode}"></jstl:out>
</display:column>
<display:column titleKey="quolet.nameCompany">
<jstl:out value="${row.company.nameCompany}"></jstl:out>
</display:column>
</display:table>

<input type="button" name="create" value="<spring:message code="quolet.create" />"
			onclick="javascript: relativeRedir('quolet/company/create.do?idAudit=${audit.id}');" />

</security:authorize>
