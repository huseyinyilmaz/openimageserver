<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ page errorPage="/ois/system/errorPage.jsp" %>
<%@ page import="ois.view.CSParamType"%>
<%@ page import="ois.view.CSActionType"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="albumBean" class="ois.view.AlbumBean" scope="request"></jsp:useBean>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Create Image Page</title>
	<link rel="stylesheet" type="text/css" href="/ois/css/images.css"> 
</head>
<body>
<div class="main">
<%@ include file="modules/moduleHeader.jsp"%>
<div class="body">
	<form action="/imageupload" method="post" enctype="multipart/form-data">
		<input type="hidden" name="<%=CSParamType.ACTION.toString()%>" value="<%=CSActionType.CREATE_IMAGE.toString()%>"></input>
		<input type="hidden" name="<%=CSParamType.ITEM%>" value="${albumBean.keyString}"></input>
	<table>
	<tr>
		<th>Image Info</th>
	</tr>
	<tr>
		<td>name:</td>
		<td><input type="text" name="<%=CSParamType.NAME.toString()%>" id="<%=CSParamType.NAME.toString()%>"></td>
	</tr>
	<tr>
		<td>description:</td>
	</tr>
	<tr>
	
		<td colspan="2"><input type="text" name="<%=CSParamType.DESCRIPTION.toString()%>" id="<%=CSParamType.DESCRIPTION.toString()%>"></td>
	</tr>

	<tr>
		<td colspan="2"><input type="file" name="<%=CSParamType.FILE.toString()%>" id="<%=CSParamType.FILE.toString()%>"></td>
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