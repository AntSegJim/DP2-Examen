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


<security:authorize access="hasRole('ROOKIE')">
<display:table pagesize="5" name="educationsData" id="row"
requestURI="educationData/rookie/list.do?idCurricula=${curricula.id}" >

<display:column>
	<a href="educationData/rookie/edit.do?educationDataId=${row.id}&curriculaId=${curricula.id}" ><spring:message code="edit" /></a>
</display:column>

<display:column titleKey="educationData.degree">
<jstl:out value="${row.degree}"></jstl:out>
</display:column>
<display:column titleKey="educationData.institution">
<jstl:out value="${row.institution}"></jstl:out>
</display:column>
<display:column titleKey="educationData.mark">
<jstl:out value="${row.mark}"></jstl:out>
</display:column>
<display:column titleKey="educationData.startDate">
<jstl:out value="${row.startDate}"></jstl:out>
</display:column>
<display:column titleKey="educationData.endDate">
<jstl:out value="${row.endDate}"></jstl:out>
</display:column>

</display:table>
<a href="educationData/rookie/create.do?curriculaId=${curricula.id}" ><spring:message code="create" /></a>
<acme:cancel url="curricula/rookie/list.do" code="cancel"/>

</security:authorize>





