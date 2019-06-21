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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('AUDITOR')">

<b><spring:message code="audit.text" /> : </b> <jstl:out value="${audit.text}"></jstl:out><br/>
<b><spring:message code="audit.moment" /> : </b> <jstl:out value="${audit.moment}"></jstl:out> <br/>
<b><spring:message code="audit.score" /> : </b> <jstl:out value="${audit.score}"></jstl:out> <br/>
<b><spring:message code="audit.auditor" /> : </b> <jstl:out value="${audit.auditor.name}(${audit.auditor.phone})"></jstl:out> <br/>
<b><spring:message code="audit.position" /> : </b> <jstl:out value="${audit.position.ticker}"></jstl:out> <br/>
<b><spring:message code="audit.draftMode" /> : </b> 
<jstl:if test="${audit.draftMode eq 1 }">
Yes
</jstl:if>
<jstl:if test="${audit.draftMode eq 0 }">
No
</jstl:if>
<br/>
<acme:cancel url="audit/auditor/list.do" code="cancel"/>
</security:authorize>
