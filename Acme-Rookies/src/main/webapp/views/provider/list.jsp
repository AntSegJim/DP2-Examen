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

<security:authorize access="isAnonymous()">

<display:table pagesize="5" name="providers" id="row"
requestURI="provider/list.do" >

<display:column property="name" titleKey="list.name" />
<display:column property="surnames" titleKey="list.surnames" />
<display:column property="make" titleKey="list.make" />
<display:column property="phone" titleKey="list.phone" />
<display:column property="email" titleKey="list.email" />


<display:column>
	<a href="item/listProvider.do?providerId=${row.id}"><spring:message code="item.list" /></a>
</display:column>

</display:table>

</security:authorize>

