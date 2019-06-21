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
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<security:authorize access="hasRole('COMPANY')">

<b><spring:message code="problem.title" /> : </b> <jstl:out value="${problem.title}"></jstl:out><br/>
<b><spring:message code="problem.statement" /> : </b> <jstl:out value="${problem.statement}"></jstl:out> <br/>
<b><spring:message code="problem.hint" /> : </b> <jstl:out value="${problem.hint}"></jstl:out> <br/>

<b><spring:message code="problem.attachment" /></b>:
<br/>
<jstl:if test="${fn:length(problem.attachment) ne 0}">
<jstl:forEach var="item" items="${problem.attachment}">
<jstl:out value="${item}"></jstl:out>
<br/>
</jstl:forEach>
</jstl:if>

<b><spring:message code="problem.draftMode" /> : </b> 
<jstl:if test="${problem.draftMode eq 1 }">
Yes
</jstl:if>
<jstl:if test="${problem.draftMode eq 0 }">
No
</jstl:if>

<br/>
<br/>
<input type="button" name="cancel" value="<spring:message code="problem.cancel" />"
			onclick="javascript: relativeRedir('problem/company/list.do?positionId=${position.id}');" />

</security:authorize>
