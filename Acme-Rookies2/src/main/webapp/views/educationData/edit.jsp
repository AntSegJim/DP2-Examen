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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>



</head>
<body>

<security:authorize access="hasRole('ROOKIE')">
<form:form action="educationData/rookie/edit.do?curriculaId=${curricula.id}" modelAttribute="educationData">

	<form:hidden path="id" />
	<form:hidden path="version" />

 
 	<acme:textbox code="educationData.degree" path="degree"/>
	<br />
	<acme:textbox code="educationData.institution" path="institution"/>
	<br />
	<acme:textbox code="educationData.mark" path="mark"/>
	<br />
	<acme:textbox code="educationData.startDate" path="startDate"/>
	<br />
	<acme:textbox code="educationData.endDate" path="endDate"/>
	<br />
	
	<acme:submit name="save" code="save"/>	
	<jstl:if test="${educationData.id ne 0 }">
		<acme:submit name="delete" code="delete"/>
	</jstl:if>
	<acme:cancel url="educationData/rookie/list.do?curriculaId=${curricula.id }" code="cancel"/>
	<br />
</form:form>
</security:authorize>
</body>
</html>