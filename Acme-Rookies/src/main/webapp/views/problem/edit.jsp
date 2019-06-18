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

<form:form action="problem/company/edit.do?positionId=${position.id }" modelAttribute="problem">

<form:hidden path="id"/>
<form:hidden path="version"/>

<acme:textbox code="problem.title" path="title"/>
<acme:textbox code="problem.statement" path="statement"/>
<acme:textbox code="problem.hint" path="hint"/>
<acme:textbox code="problem.attachment" path="attachment"/>

<jstl:if test="${problem.id ne 0 }">
<form:label path="draftMode"><spring:message code="problem.draftMode" />:</form:label>

<form:select path="draftMode">
		<form:option value="1" label="Yes" />	
		<form:option value="0" label="No" />	
	</form:select>
	<form:errors path="draftMode"/>

</jstl:if>

<br/>
<input type="submit" name="save" 
	value="<spring:message code="problem.save" />" />

<input type="button" name="cancel" value="<spring:message code="problem.cancel" />"
			onclick="javascript: relativeRedir('problem/company/list.do?positionId=${position.id}');" />
<jstl:if test="${problem.id ne 0 }">	
<input type="submit" name="delete" 
	value="<spring:message code="problem.delete" />" />
</jstl:if>		
</form:form>

</security:authorize>


