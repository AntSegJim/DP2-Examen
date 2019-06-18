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


</head>
<body>

<security:authorize access="hasRole('CUSTOMER')">
<form:form action="burse/customer/save.do" modelAttribute="quolet">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="ticker" />	
	<form:hidden path="fixUpTask" />	
 	<form:hidden path="moment" />
 	
	<form:label path="body">
		<spring:message code="quolet.body" />:
	</form:label>
	<form:input path="body" />
	<form:errors cssClass="error" path="body" />
	<br />
	
	<form:label path="draftMode">
		<spring:message code="quolet.draftMode" />:
	</form:label>
	<form:input path="draftMode" />
	<form:errors cssClass="error" path="DraftMode" />
	<spring:message code="quolet.draftMode.message" />
	<br />
	
	<form:label path="picture">
		<spring:message code="quolet.picture" />:
	</form:label>
	<form:input path="picture" />
	<form:errors cssClass="error" path="picture" />
	<br />
	
	<input type="submit" name="save" value="<spring:message code="fixUpTask.save" />" />
		
	<input type="button" name="cancel" 
	value="<spring:message code="fixUpTask.cancel" />" 
	onclick="javascript:relativeRedir('fix-up-task/customer/list.do');"/>	
		 	
</form:form>
</security:authorize>
</body>
</html>