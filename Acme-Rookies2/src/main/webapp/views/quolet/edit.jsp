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


<security:authorize access="hasRole('AUDITOR')">
<form:form action="quolet/auditor/edit.do?idAudit=${audit.id }" modelAttribute="quolet">

	<jstl:if test="${not empty exception}">
		<p style="color:red"> <spring:message code="position.error" /> </p>
	</jstl:if>

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
<br/>	
<input type="submit" name="save" 
	value="<spring:message code="quolet.save" />" />
	
<input type="button" name="cancel" value="<spring:message code="quolet.cancel" />"
			onclick="javascript: relativeRedir('quolet/auditor/list.do?idAudit=${audit.id}');" /> 	
			
<jstl:if test="${quolet.id ne 0 }">
<input type="submit" name="delete" 
	value="<spring:message code="quolet.delete" />" />
</jstl:if>
			
	
</form:form>
</security:authorize>
