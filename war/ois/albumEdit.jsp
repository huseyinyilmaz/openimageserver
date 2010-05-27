<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ page errorPage="/ois/system/errorPage.jsp" %>
<%@ page import="ois.view.CSParamType"%>
<%@ page import="ois.view.CSActionType"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="albumBean" class="ois.view.AlbumBean" scope="request"></jsp:useBean>
<jsp:useBean id="exception" class="java.lang.Exception" scope="request"></jsp:useBean>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="ois.ApplicationManager"%><html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Image Administration Page</title>
	<link rel="stylesheet" type="text/css" href="/ois/css/images.css"> 

</head>
<body>
<div class="main">
<%@ include file="modules/moduleHeader.jsp" %>
<div class="body">
	<%@ include file="modules/moduleException.jsp" %>
	<form action="<%=ApplicationManager.MAIN_PAGE%>" method="post">
	<input type="hidden" name="<%=CSParamType.ACTION.toString()%>" 
value="<c:choose><c:when test="${albumBean.keyString==null}"><%=CSActionType.CREATE_ALBUM.toString()%></c:when><c:otherwise><%=CSActionType.EDIT_ALBUM.toString()%></c:otherwise></c:choose>">
	<c:if test="${albumBean.keyString!='None'}"><input type="hidden" name="<%=CSParamType.ALBUM.toString()%>" value="${albumBean.keyString}"></input></c:if>
	<table>
		<tr><td class="pageTitle" colspan="2"><c:choose><c:when test="${albumBean.keyString==null}">Create a new album</c:when><c:otherwise>Edit album</c:otherwise></c:choose></td></tr>
		<tr>
			<td>name:</td>
			<td><input type="text" name="<%=CSParamType.NAME.toString()%>" id="createAlbumName" value="${albumBean.name}"></td>		
		</tr>
		<tr><td>description</td></tr>	
		<!--<tr><td colspan="2"><input type="text" name="<%=CSParamType.DESCRIPTION.toString()%>" id="createAlbumDescription" value="${albumBean.description}"></td></tr> -->
		<tr><td colspan="2"><textarea rows="4" cols="30" name="<%=CSParamType.DESCRIPTION.toString()%>" id="albumDescription">${albumBean.description}</textarea></td></tr>	
		<tr>
			<td><a href="<%=ApplicationManager.getHomeURL()%>">cancel</a></td>
			<td><c:choose>
					<c:when test="${albumBean.keyString==null}">
					<input type="submit" title="Create a new Album" value="Create" id="albumSubmitButton">
					</c:when>
					<c:otherwise>
					<input type="submit" title="Save album information" value="Save" id="albumSubmitButton">
					</c:otherwise>
					</c:choose>
			</td>
		</tr>
	</table>	 
	</form>
	</div>
</div>



</body>
</html>