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

<style type="text/css">
.LESS_1_MONTH{
  background-color: Indigo ;
}
.BETWEEN_1_AND_2_MONTH{
  background-color: DarkSlateGrey ;
}
.MORE_2_MONTH{
  background-color: PapayaWhip;
}
</style>

<security:authorize access="hasRole('COMPANY')">
	
<display:table pagesize="5" name="quolets" id="row"
requestURI="quolet/company/list.do?idAudit=${audit.id }" >

	<jstl:choose>
		<jstl:when test="${row.numMonth < 1}">
			<jstl:set var="css" value="LESS_1_MONTH"></jstl:set>
		</jstl:when>
	
		<jstl:when test="${row.numMonth < 2 and row.numMonth >= 1}">
				<jstl:set var="css" value="BETWEEN_1_AND_2_MONTH"></jstl:set>
		</jstl:when>
	
		<jstl:when test="${row.numMonth >= 2}">
				<jstl:set var="css" value="MORE_2_MONTH"></jstl:set>
		</jstl:when>
	</jstl:choose>

	<display:column class="${css}">
		<jstl:if test="${(row.draftMode eq 1)}">
			<a style="color:white;" href="quolet/company/edit.do?idQuolet=${row.id}"><spring:message code="quolet.edit" /></a>
		</jstl:if>
	</display:column>	
	
	<display:column class="${css}" titleKey="quolet.ticker">
		<jstl:out value="${row.ticker}"></jstl:out>
	</display:column>
	
	<jstl:choose>
		<jstl:when test="${lang eq 'en'}">
			<display:column class="${css}" property="moment" titleKey="quolet.moment" format="{0,date,yy/MM/dd hh:mm}"  />
		</jstl:when>
		
		<jstl:otherwise>
			<display:column class="${css}" property="moment" titleKey="quolet.moment" format="{0,date,dd-MM-yy hh:mm}"  />
		</jstl:otherwise>
	</jstl:choose>
	
	<display:column class="${css}" titleKey="quolet.body">
		<jstl:out value="${row.body}"></jstl:out>
	</display:column>
	
	<display:column class="${css}" titleKey="quolet.draftMode">
		<jstl:choose>
			<jstl:when test="${row.draftMode eq 0}">
				<spring:message code="quolet.inSaveMode" /> 
			</jstl:when>
		
			<jstl:otherwise>
				<spring:message code="quolet.inDraftMode" />
			</jstl:otherwise>
	</jstl:choose>
	</display:column>
		<display:column class="${css}">
			<a style="color:white;" href="quolet/company/show.do?idAudit=${audit.id}&idQuolet=${row.id}"><spring:message
					code="quolet.show" /></a>
		</display:column>
	</display:table>

<input type="button" name="create" value="<spring:message code="quolet.create" />"
			onclick="javascript: relativeRedir('quolet/company/create.do?idAudit=${audit.id}');" />

<input type="button" name="cancel" value="<spring:message code="quolet.cancel" />"
			onclick="javascript: relativeRedir('audit/company/list.do?positionId=${position.id}');" />
</security:authorize>

<security:authorize access="hasRole('AUDITOR')">
	
<display:table pagesize="5" name="quolets" id="row"
requestURI="quolet/auditor/list.do?idAudit=${audit.id }" >

<jstl:choose>
	<jstl:when test="${row.numMonth < 1}">
		<jstl:set var="css" value="LESS_1_MONTH"></jstl:set>
	</jstl:when>
	
	<jstl:when test="${row.numMonth < 2 and row.numMonth >= 1}">
			<jstl:set var="css" value="BETWEEN_1_AND_2_MONTH"></jstl:set>
	</jstl:when>
	
	<jstl:when test="${row.numMonth >= 2}">
			<jstl:set var="css" value="MORE_2_MONTH"></jstl:set>
	</jstl:when>
</jstl:choose>


	<display:column titleKey="quolet.ticker" class="${css}">
		<jstl:out value="${row.ticker}"></jstl:out>
	</display:column>
	
	<jstl:choose>
		<jstl:when test="${lang eq 'en'}">
			<display:column property="moment" class="${css}" titleKey="quolet.moment" format="{0,date,yy/MM/dd hh:mm}"  />
		</jstl:when>
		
		<jstl:otherwise>
			<display:column property="moment" class="${css}" titleKey="quolet.moment" format="{0,date,dd-MM-yy hh:mm}"  />
		</jstl:otherwise>
	</jstl:choose>
	
	<display:column titleKey="quolet.body" class="${css}">
		<jstl:out value="${row.body}"></jstl:out>
	</display:column>
	
	<display:column titleKey="quolet.picture" class="${css}">
		<jstl:out value="${row.picture}"></jstl:out>
	</display:column>
	
	<display:column titleKey="quolet.draftMode" class="${css}">
		<jstl:out value="${row.draftMode}"></jstl:out>
	</display:column>
	
	<display:column titleKey="quolet.nameCompany" class="${css}">
		<jstl:out value="${row.company.nameCompany}"></jstl:out>
	</display:column>

		<display:column class="${css}" >
			<a style="color:white;" 
				href="quolet/auditor/show.do?idAudit=${audit.id}&idQuolet=${row.id}"><spring:message
					code="quolet.show" /></a>
		</display:column>

	</display:table>

<input type="button" name="cancel" value="<spring:message code="quolet.cancel" />"
			onclick="javascript: relativeRedir('audit/auditor/list.do');" />

</security:authorize>

