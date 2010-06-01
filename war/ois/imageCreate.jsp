<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ page errorPage="/ois/system/errorPage.jsp" %>
<%@ page import="ois.view.CSParamType"%>
<%@ page import="ois.view.CSActionType"%>
<%@page import="ois.ApplicationManager"%><html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="albumBean" class="ois.view.AlbumBean" scope="request"></jsp:useBean>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Create Image Page</title>
	<link type="text/css" href="/ois/css/ui-lightness/jquery-ui-1.8.1.custom.css" rel="stylesheet" />	
	<link rel="stylesheet" type="text/css" href="/ois/css/main.css">
	<script type="text/javascript" src="/ois/js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="/ois/js/jquery-ui-1.8.1.custom.min.js"></script>
	<script type="text/javascript" src="/ois/js/main.js"></script>
	<script type="text/javascript">
	$(function(){
		//initialize buttons
		$("#cancelButton").button({icons: {primary: 'ui-icon-cancel'},text: true}).click(function(){
			top.location="${albumBean.viewLink}";
			});
		$("#submitButton").button({icons: {primary: 'ui-icon-check'},text: true}).click(function(){
			$("#imageForm").submit();
			});
		});
	</script>
</head>
<body>
<div class="main">
<%@ include file="modules/moduleHeader.jsp"%>
<div class="body">
	<%@ include file="modules/moduleException.jsp"%>
	<form action="<%=ApplicationManager.IMAGE_UPLOAD_PAGE%>" method="post" enctype="multipart/form-data" id="imageForm">
		<input type="hidden" name="<%=CSParamType.ACTION.toString()%>" value="<%=CSActionType.CREATE_IMAGE.toString()%>"></input>
		<input type="hidden" name="<%=CSParamType.ALBUM.toString()%>" value="${albumBean.keyString}"></input>
	<table>
	<tr>
		<td class="title" colspan="2">Create a new image</td>
	</tr>
	<tr>
		<td class="title">name:</td>
		<td class="full"><input type="text" name="<%=CSParamType.NAME.toString()%>" class="text" maxlength="15" size="20" id="<%=CSParamType.NAME.toString()%>" <c:if test="${albumBean.currentImageBean!=null}"> value="${albumBean.currentImageBean.name}"</c:if> ></td>
	</tr>
	<tr>
		<td class="title" colspan="2">description:</td>
	</tr>
	<tr>
		<td colspan="2"><textarea rows="4" cols="100" class="text" name="<%=CSParamType.DESCRIPTION.toString()%>" id="<%=CSParamType.DESCRIPTION.toString()%>"><c:if test="${albumBean.currentImageBean!=null}">${albumBean.currentImageBean.description}</c:if></textarea></td>
	</tr>
	<tr>
		<td class="title">File to upload </td>
		<td class="full"><input type="file" name="<%=CSParamType.FILE.toString()%>" class="text" id="<%=CSParamType.FILE.toString()%>"></td>
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