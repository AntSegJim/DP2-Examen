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

<display:table pagesize="5" name="problems" id="row"
requestURI="problem/company/list.do" >

<display:column>
	<a href="problem/company/show.do?problemId=${row.id}&positionId=${position.id}"><spring:message code="problem.show" /></a>
</display:column>

<display:column property="title" titleKey="problem.title" />
<display:column property="statement" titleKey="problem.statement" />

<display:column>
<jstl:if test="${(row.draftMode eq 1) and (position.isCancelled eq 0)}">
	<a href="problem/company/edit.do?problemId=${row.id}&positionId=${position.id}"><spring:message code="problem.edit" /></a>
</jstl:if>
</display:column>

</display:table>
<jstl:if test="${(position.isCancelled eq 0)}">
<input type="button" name="create" value="<spring:message code="problem.create" />"
			onclick="javascript: relativeRedir('problem/company/create.do?positionId=${position.id}');" />
</jstl:if>

<input type="button" name="cancel" value="<spring:message code="problem.cancel" />"
			onclick="javascript: relativeRedir('position/company/list.do');" />
</security:authorize>


