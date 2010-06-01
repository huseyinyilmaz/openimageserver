<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ page errorPage="/ois/system/errorPage.jsp" %>
<%@ page import="ois.view.CSParamType"%>
<%@ page import="ois.view.CSActionType"%>
<%@page import="ois.ApplicationManager"%><html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="imageBean" class="ois.view.ImageBean" scope="request"></jsp:useBean>
<jsp:useBean id="exception" class="java.lang.Exception" scope="request"></jsp:useBean>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Create revision</title>
	<link type="text/css" href="/ois/css/ui-lightness/jquery-ui-1.8.1.custom.css" rel="stylesheet" />	
	<link rel="stylesheet" type="text/css" href="/ois/css/main.css">
	<script type="text/javascript" src="/ois/js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="/ois/js/jquery-ui-1.8.1.custom.min.js"></script>
	<script type="text/javascript" src="/ois/js/main.js"></script>
	<script type="text/javascript">
	$(function(){
		//initialize buttons
		$("#cancelButton").button({icons: {primary: 'ui-icon-cancel'},text: true}).click(function(){
			top.location="${imageBean.revisionsLink}";
			});
		$("#submitButton").button({icons: {primary: 'ui-icon-check'},text: true}).click(function(){
			$("#revisionForm").submit();
			});
		});
	</script>
</head>
<body>
<div class="main">
<%@ include file="modules/moduleHeader.jsp"%>
<div class="body">
	<%@ include file="modules/moduleException.jsp" %>
	<form action="<%=ApplicationManager.MAIN_PAGE%>" method="post" id="revisionForm">
		<input type="hidden" name="<%=CSParamType.ACTION.toString()%>" value="<%=CSActionType.CREATE_REVISION.toString()%>"></input>
		<input type="hidden" name="<%=CSParamType.IMAGE.toString()%>" value="${imageBean.keyString}"></input>
	<table>
	<tr>
		<td class="title" colspan="2">Create a new image revision</td>
	</tr>
	<tr>
		<td class="title">width:</td>
		<td class="full"><input type="text" class="text" size="20" name="<%=CSParamType.WIDTH.toString()%>" id="<%=CSParamType.WIDTH.toString()%>" value="${imageBean.currentDataBean.width}"></td>
	</tr>
	<tr>
		<td class="title">height:</td>
		<td class="full"><input type="text" class="text" size="20" name="<%=CSParamType.HEIGHT.toString()%>" id="<%=CSParamType.HEIGHT.toString()%>" value="${imageBean.currentDataBean.height}"></td>
	</tr>
	<tr>
		<td class="title">Enhanced:</td>
		<td class="full"><input type="checkbox" name="<%=CSParamType.ENHANCED.toString()%>" id="<%=CSParamType.ENHANCED.toString()%>" <c:if test="${imageBean.currentDataBean.enhanced}">checked</c:if> ></td>
	</tr>
	</table>
	</form>
	
		<table>
	<tr>
		<td>
			<button id="cancelButton">Cancel</button>	 
			<button id="submitButton">Submit</button>
			 
		</td>
	</tr>	
	</table>
	
	</div>
</div>
</body>
</html>