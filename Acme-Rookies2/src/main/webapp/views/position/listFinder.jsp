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


<security:authorize access="isAnonymous()">

<form:form action="position/search.do" modelAttribute="finder">

	<acme:textbox code="position.keyWord" path="keyWord"/>
	
	<input type="submit" name="save" value="<spring:message code="position.search" />" />

	<input type="button" name="cancel" value="<spring:message code="position.cancel" />"
			onclick="javascript: relativeRedir('position/listAll.do');" />
</form:form>


<display:table pagesize="5" name="positions" id="row"
requestURI="position/listAll.do" >

<display:column>
	<a href="position/show.do?positionId=${row.id}"><spring:message code="position.moreDetails" /></a>
</display:column>

<display:column property="ticker" titleKey="position.ticker" />
<display:column property="title" titleKey="position.title" />
<display:column property="deadLine" titleKey="position.deadline" />
<display:column property="salary" titleKey="position.salary" />
	<display:column>
		<a href="position/company.do?positionId=${row.id}"><spring:message code="position.company" /></a>
	</display:column>
</display:table>

</security:authorize>
