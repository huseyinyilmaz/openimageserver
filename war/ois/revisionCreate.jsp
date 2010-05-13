<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ page errorPage="/ois/system/errorPage.jsp" %>
<%@ page import="ois.view.CSParamType"%>
<%@ page import="ois.view.CSActionType"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="imageBean" class="ois.view.ImageBean" scope="request"></jsp:useBean>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<%@page import="ois.ApplicationManager"%><html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Create revision</title>
	<link rel="stylesheet" type="text/css" href="/ois/css/images.css"> 
</head>
<body>
<div class="main">
<%@ include file="modules/moduleHeader.jsp"%>
<div class="body">
	<form action="<%=ApplicationManager.MAIN_PAGE%>" method="post">
		<input type="hidden" name="<%=CSParamType.ACTION.toString()%>" value="<%=CSActionType.CREATE_REVISION.toString()%>"></input>
		<input type="hidden" name="<%=CSParamType.ITEM%>" value="${imageBean.keyString}"></input>
	<table>
	<tr>
		<th>Revision Info</th>
	</tr>
	<tr>
		<td>width:</td>
		<td><input type="text" name="<%=CSParamType.WIDTH.toString()%>" id="<%=CSParamType.WIDTH.toString()%>" value="${imageBean.currentDataBean.width}"></td>
	</tr>
	<tr>
		<td>height:</td>
		<td><input type="text" name="<%=CSParamType.HEIGHT.toString()%>" id="<%=CSParamType.HEIGHT.toString()%>" value="${imageBean.currentDataBean.height}"></td>
	</tr>
	<tr>
		<td>Enhanced:</td>
		<td><input type="checkbox" name="<%=CSParamType.ENHANCED.toString()%>" id="<%=CSParamType.ENHANCED.toString()%>"></td>
	</tr>
	
	
	
	<tr>
		<td></td>
		<td><input type="submit" title="Create new image" value="Create" id="imageSubmitButton"> </td>
		
	</tr>	
	</table>
	</form>
	</div>
</div>
</body>
</html>