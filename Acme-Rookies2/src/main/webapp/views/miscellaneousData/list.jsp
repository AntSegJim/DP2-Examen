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
<display:table pagesize="5" name="miscellaneousesData" id="row"
requestURI="miscellaneousData/rookie/list.do?idCurricula=${curricula.id}" >

<display:column>
	<a href="miscellaneousData/rookie/edit.do?miscellaneousDataId=${row.id}&curriculaId=${curricula.id}" ><spring:message code="edit" /></a>
</display:column>

<display:column titleKey="miscellaneousData.text">
<jstl:out value="${row.text}"></jstl:out>
</display:column>
<display:column titleKey="miscellaneousData.attachment">
<jstl:out value="${row.attachment}"></jstl:out>
</display:column>

</display:table>
<a href="miscellaneousData/rookie/create.do?curriculaId=${curricula.id}" ><spring:message code="create" /></a>
<acme:cancel url="curricula/rookie/list.do" code="cancel"/>

</security:authorize>





