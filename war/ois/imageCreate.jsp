<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ page errorPage="/ois/system/errorPage.jsp" %>
<%@ page import="ois.view.CSParamType"%>
<%@ page import="ois.view.CSActionType"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="albumBean" class="ois.view.AlbumBean" scope="request"></jsp:useBean>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<%@page import="ois.ApplicationManager"%><html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Create Image Page</title>
	<link rel="stylesheet" type="text/css" href="/ois/css/images.css"> 
</head>
<body>
<div class="main">
<%@ include file="modules/moduleHeader.jsp"%>
<div class="body">
	<%@ include file="modules/moduleException.jsp"%>
	<form action="<%=ApplicationManager.IMAGE_UPLOAD_PAGE%>" method="post" enctype="multipart/form-data">
		<input type="hidden" name="<%=CSParamType.ACTION.toString()%>" value="<%=CSActionType.CREATE_IMAGE.toString()%>"></input>
		<input type="hidden" name="<%=CSParamType.ALBUM.toString()%>" value="${albumBean.keyString}"></input>
	<table>
	<tr>
		<td class="pageTitle" colspan="2">Create a new image</td>
	</tr>
	<tr>
		<td>name:</td>
		<td><input type="text" name="<%=CSParamType.NAME.toString()%>" id="<%=CSParamType.NAME.toString()%>" <c:if test="${albumBean.currentImageBean!=null}"> value="${albumBean.currentImageBean.name}"</c:if> ></td>
	</tr>
	<tr>
		<td>description:</td>
	</tr>
	<tr>
		<td colspan="2"><textarea rows="4" cols="30" name="<%=CSParamType.DESCRIPTION.toString()%>" id="<%=CSParamType.DESCRIPTION.toString()%>"><c:if test="${albumBean.currentImageBean!=null}">${albumBean.currentImageBean.description}</c:if></textarea></td>
	</tr>
	<tr>
		<td colspan="2"><input type="file" name="<%=CSParamType.FILE.toString()%>" id="<%=CSParamType.FILE.toString()%>"></td>
	</tr>
	<tr>
		<td><a href="${albumBean.viewLink}">cancel</a></td>
		<td><input type="submit" title="Create new image" value="Create" id="imageSubmitButton"> </td>
	</tr>	
	</table>
	</form>
	</div>
</div>
</body>
</html>