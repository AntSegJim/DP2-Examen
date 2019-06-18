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

<security:authorize access="hasRole('CUSTOMER')">
	
<jstl:choose>
    <jstl:when test="${not empty error}">
    
        <p style="color:red"> <spring:message code="quolet.error" /> </p>
        
    </jstl:when>    
    <jstl:otherwise>
    
        <p><spring:message code="quolet.list" /></p>
		<display:table pagesize="5" name="quolets" id="row" requestURI="${requestURI}" >
		
		<display:column class="color" property="ticker" titleKey="quolet.ticker" />
		<jstl:if test="${language eq 'es' }">
			<display:column class="color" property="moment" titleKey="quolet.moment" format="{0,date,dd-MM-yy HH:mm}" />
		</jstl:if>
		<jstl:if test="${language eq 'en' }">
			<display:column class="color" property="moment" titleKey="quolet.moment" format="{0,date,yy/MM/dd HH:mm}" />
		</jstl:if>
		<display:column class="color" property="body" titleKey="quolet.body" />
		<display:column class="color" property="picture" titleKey="quolet.picture" />	
		
		<display:column class="color" titleKey="fixUpTask.show">
			<a href="burse/customer/show.do?quoletId=${row.id}">
				<spring:message code="fixUpTask.show" /></a>
		</display:column>
		
		<display:column class="color" titleKey="fixUpTask.edit">
			<a href="burse/customer/edit.do?quoletId=${row.id}">
				<spring:message code="fixUpTask.edit" /></a>
		</display:column>
		
		<display:column class="color" titleKey="fixUpTask.delete">
			<a href="burse/customer/delete.do?quoletId=${row.id}">
				<spring:message code="fixUpTask.delete" /></a>
		</display:column>
		</display:table>

		<input type="button" name="create" value="<spring:message code="quolet.create" />"
		onclick="javascript: relativeRedir('burse/customer/create.do?fixUpTaskId=${fixUpTaskId}');" />
        <br />
    </jstl:otherwise>
</jstl:choose>

<input type="button" name="Volver" 
	value="<spring:message code="quolet.volver" />" 
	onclick="javascript:relativeRedir('fix-up-task/customer/list.do');"/>
</security:authorize>

<security:authorize access="hasRole('HANDYWORKER')">
	
<jstl:choose>
    <jstl:when test="${not empty error}">
    
        <p style="color:red"> <spring:message code="quolet.error" /> </p>
        
    </jstl:when>    
    <jstl:otherwise>
        <p><spring:message code="quolet.list" /></p>
		<display:table pagesize="5" name="quolets" id="row" requestURI="${requestURI}" >
			<display:column class="color" property="ticker" titleKey="quolet.ticker" />
			<jstl:if test="${language eq 'es' }">
				<display:column class="color" property="moment" titleKey="quolet.moment" format="{0,date,dd-MM-yy HH:mm}" />
			</jstl:if>
			<jstl:if test="${language eq 'en' }">
				<display:column class="color" property="moment" titleKey="quolet.moment" format="{0,date,yy/MM/dd HH:mm}" />
			</jstl:if>
			<display:column class="color" property="body" titleKey="quolet.body" />
			<display:column class="color" property="picture" titleKey="quolet.picture" />	
			
			<display:column class="color" titleKey="fixUpTask.show">
				<a href="burse/handy-worker/show.do?quoletId=${row.id}">
					<spring:message code="fixUpTask.show" /></a>
		 </display:column>
			
			<display:column class="color" titleKey="fixUpTask.edit">
				<a>-</a>
		</display:column>
		
		<display:column class="color" titleKey="fixUpTask.delete">
				<a>-</a>
		</display:column>
		
		</display:table>

    </jstl:otherwise>
</jstl:choose>

<input type="button" name="Volver" 
	value="<spring:message code="quolet.volver" />" 
	onclick="javascript:relativeRedir('fix-up-task/handy-worker/list.do');"/>
</security:authorize>

<input type="hidden" id="lang" name="lang" value="${language}">

<script type="text/javascript">
var x = document.getElementsByClassName("color");
var lang = document.getElementById("lang").value;
var i = 0;
var d = new Date();
while (i < x.length) {
	if(lang == 'en'){
		var year = x[i+1].innerHTML.substr(0, 2);
		var month = x[i+1].innerHTML.substr(3, 2);
		var day = x[i+1].innerHTML.substr(6, 2);
		var d2 = new Date('20'+year, month-1, day);
	}else{
		var day = x[i+1].innerHTML.substr(0, 2);
		var month = x[i+1].innerHTML.substr(3, 2);
		var year = x[i+1].innerHTML.substr(6, 2);
		var d2 = new Date('20'+year, month-1, day);
	}
	var diff = ((d.getTime()- d2.getTime())/(1000*60*60*24))/30;
	if(diff < 1){
		document.getElementsByClassName("color")[i].style.background='Orchid';
		document.getElementsByClassName("color")[i+1].style.background='Orchid';
		document.getElementsByClassName("color")[i+2].style.background='Orchid';
		document.getElementsByClassName("color")[i+3].style.background='Orchid';
		document.getElementsByClassName("color")[i+4].style.background='Orchid';
		document.getElementsByClassName("color")[i+5].style.background='Orchid';
		document.getElementsByClassName("color")[i+6].style.background='Orchid';
	}else if(diff > 1 && diff < 2){
		document.getElementsByClassName("color")[i].style.background='PaleVioletRed';
		document.getElementsByClassName("color")[i+1].style.background='PaleVioletRed';
		document.getElementsByClassName("color")[i+2].style.background='PaleVioletRed';
		document.getElementsByClassName("color")[i+3].style.background='PaleVioletRed';
		document.getElementsByClassName("color")[i+4].style.background='PaleVioletRed';
		document.getElementsByClassName("color")[i+5].style.background='PaleVioletRed';
		document.getElementsByClassName("color")[i+6].style.background='PaleVioletRed';
	}else{
		document.getElementsByClassName("color")[i].style.background='aliceblue';
		document.getElementsByClassName("color")[i+1].style.background='aliceblue';
		document.getElementsByClassName("color")[i+2].style.background='aliceblue';
		document.getElementsByClassName("color")[i+3].style.background='aliceblue';
		document.getElementsByClassName("color")[i+4].style.background='aliceblue';
		document.getElementsByClassName("color")[i+5].style.background='aliceblue';
		document.getElementsByClassName("color")[i+6].style.background='aliceblue';
	}
	i= i+7;
}
</script>
