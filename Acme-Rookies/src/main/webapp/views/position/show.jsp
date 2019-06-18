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

<b><spring:message code="position.ticker" /> : </b> <jstl:out value="${position.ticker}"></jstl:out><br/>
<b><spring:message code="position.title" /> : </b> <jstl:out value="${position.title}"></jstl:out> <br/>
<b><spring:message code="position.description" /> : </b> <jstl:out value="${position.description}"></jstl:out><br/>
<b><spring:message code="position.deadline" /> : </b> <jstl:out value="${position.deadLine}"></jstl:out><br/>
<b><spring:message code="position.requiredProfile" /> : </b> <jstl:out value="${position.requiredProfile}"></jstl:out><br/>
<b><spring:message code="position.skillsRequired" /> : </b> <jstl:out value="${position.skillsRequired}"></jstl:out><br/>
<b><spring:message code="position.technologiesRequired" /> : </b> <jstl:out value="${position.technologiesRequired}"></jstl:out><br/>
<b><spring:message code="position.salary" /> : </b> <jstl:out value="${position.salary}"></jstl:out><br/>
<b><spring:message code="position.draftMode" /> : </b> 
<jstl:if test="${position.draftMode eq 1 }">
Yes
</jstl:if>
<jstl:if test="${position.draftMode eq 0 }">
No
</jstl:if>

<br/>
<br/>
<input type="button" name="cancel" value="<spring:message code="position.cancel" />"
			onclick="javascript: relativeRedir('position/company/list.do');" />

</security:authorize>

<security:authorize access="isAnonymous()">

<b><spring:message code="position.ticker" /> : </b> <jstl:out value="${position.ticker}"></jstl:out><br/>
<b><spring:message code="position.title" /> : </b> <jstl:out value="${position.title}"></jstl:out> <br/>
<b><spring:message code="position.description" /> : </b> <jstl:out value="${position.description}"></jstl:out><br/>
<b><spring:message code="position.deadline" /> : </b> <jstl:out value="${position.deadLine}"></jstl:out><br/>
<b><spring:message code="position.requiredProfile" /> : </b> <jstl:out value="${position.requiredProfile}"></jstl:out><br/>
<b><spring:message code="position.skillsRequired" /> : </b> <jstl:out value="${position.skillsRequired}"></jstl:out><br/>
<b><spring:message code="position.technologiesRequired" /> : </b> <jstl:out value="${position.technologiesRequired}"></jstl:out><br/>
<b><spring:message code="position.salary" /> : </b> <jstl:out value="${position.salary}"></jstl:out>

<br/>


</security:authorize>
