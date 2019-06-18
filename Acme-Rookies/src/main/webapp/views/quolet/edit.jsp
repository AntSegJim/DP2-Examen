<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<security:authorize access="hasRole('CUSTOMER')">
<form:form action="quolet/customer/edit.do" modelAttribute="quolet">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:textarea code="quolet.body" path="body"/>
	<acme:textbox code="quolet.picture" path="picture"/>
	<form:label path="draftMode"><spring:message code="quolet.draftMode" />:</form:label>
	<form:select path="draftMode">
		<form:option value="1" label="Draft mode" />	
		<form:option value="0" label="Save mode" />		
	</form:select>
	<form:errors path="draftMode"/>
<br/>
	
 	
	
</form:form>
</security:authorize>
