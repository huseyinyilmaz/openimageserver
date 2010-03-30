<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ page errorPage="/errorPage.jsp" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="albums" class="ois.view.AlbumsBean" scope="request"></jsp:useBean>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Image Administration Page</title>
	<link rel="stylesheet" type="text/css" href="/css/images.css"> 
</head>
<body>
<div class="main">
	<%@ include file="header.jsp" %>
	<div class="body">
		<table>
		<tr>
			<td><%@ include file="albums.jsp" %></td>
			<td><%@ include file="images.jsp" %></td>
		</tr>
		</table>
	</div> 
</div>



</body>
</html>