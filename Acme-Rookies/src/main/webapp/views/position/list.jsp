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

<security:authorize access="hasRole('COMPANY')">

<display:table pagesize="5" name="positions" id="row"
requestURI="position/company/list.do" >


<display:column>
	<jstl:if test="${(row.isCancelled eq 0) and (row.draftMode eq 0 )}">
		<a href="audit/company/list.do?positionId=${row.id}"><spring:message code="position.audit" /></a>
	</jstl:if>
</display:column>

<display:column>
	<a href="position/company/show.do?positionId=${row.id}"><spring:message code="position.moreDetails" /></a>
</display:column>

<display:column property="ticker" titleKey="position.ticker" />
<display:column property="title" titleKey="position.title" />
<display:column property="deadLine" titleKey="position.deadline" />
<display:column property="salary" titleKey="position.salary" />


<display:column>
<jstl:if test="${row.draftMode eq 1 }">
	<a href="position/company/edit.do?positionId=${row.id}"><spring:message code="position.edit" /></a>
</jstl:if>

<jstl:if test="${(row.isCancelled eq 0) and (row.draftMode eq 0 )}">
	<a href="position/company/cancel.do?positionId=${row.id}"><spring:message code="position.canceled" /></a>
</jstl:if>

<jstl:if test="${(row.isCancelled eq 1) and (row.draftMode eq 0 )}">
	Cancel
</jstl:if>
</display:column>
<display:column titleKey="position.problem">
	<a href="problem/company/list.do?positionId=${row.id}"><spring:message code="position.problem" /></a>
</display:column>
</display:table>

	<input type="button" name="create" value="<spring:message code="position.create" />"
			onclick="javascript: relativeRedir('position/company/create.do');" />

</security:authorize>


<security:authorize access="isAnonymous()">

<display:table pagesize="5" name="positions" id="row"
requestURI="position/list.do" >

<display:column>
	<a href="position/show.do?positionId=${row.id}"><spring:message code="position.moreDetails" /></a>
</display:column>

<display:column property="ticker" titleKey="position.ticker" />
<display:column property="title" titleKey="position.title" />
<display:column property="deadLine" titleKey="position.deadline" />
<display:column property="salary" titleKey="position.salary" />

</display:table>

<input type="button" name="cancel" value="<spring:message code="position.cancel" />"
			onclick="javascript: relativeRedir('company/list.do');" />

</security:authorize>
