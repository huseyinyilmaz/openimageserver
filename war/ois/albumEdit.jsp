<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ page errorPage="/ois/system/errorPage.jsp" %>
<%@ page import="ois.view.CSParamType"%>
<%@ page import="ois.view.CSActionType"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="album" class="ois.controller.Album" scope="request"></jsp:useBean>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Image Administration Page</title>
	<link rel="stylesheet" type="text/css" href="/css/images.css"> 

</head>
<body>
<div class="main">
<%@ include file="modules/moduleHeader.jsp" %>

	<form action="/main" method="post">
	<input type="hidden" name="<%=CSParamType.ACTION.toString()%>" 
value="<c:choose><c:when test="${album.key==null}"><%=CSActionType.CREATE_ALBUM.toString()%></c:when><c:otherwise><%=CSActionType.EDIT_ALBUM.toString()%></c:otherwise></c:choose>">
	<c:if test="${album.key!='None'}"><input type="hidden" name="<%=CSParamType.ITEM.toString()%>" value="${album.key}"></input></c:if>
	Album info<br></br>
	name:<input type="text" name="<%=CSParamType.NAME.toString()%>" id="createAlbumName" value="${album.name}"><br></br>
	description:<br></br>
	<input type="text" name="<%=CSParamType.DESCRIPTION.toString()%>" id="createAlbumDescription" value="${album.description}"><br></br>
	<input type="submit" title="Create a new Album" value="Create" id="createAlbumSubmitButton"> 
	</form>
	
</div>



</body>
</html>