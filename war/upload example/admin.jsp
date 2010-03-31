<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ page errorPage="/errorPage.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c2" uri="http://java.sun.com/jstl/core_rt" %>

 <%@page import="java.util.Date"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Image administration</title>
	<link rel="stylesheet" type="text/css" href="/css/images.css"> 
</head>
<body>

Some simple math: ${2+2}
		<br/>
		Some simple math with c:out: <c:out value="${2+2}"/>
		<br/>
		Some simple math with c2:out: <c2:out value="${2+2}"/>
		<br/>
		
<div class="main">
<%@ include file="header.jsp" %>

<div class="body">
<table>
<tr>
<td>
<%@ include file="albums.jsp" %>
</td>
<td>
<%@ include file="images.jsp" %>
</td>
</tr>

</table>
</div> 
	Last server visit = <%= new Date() %>
</div>
</body>
</html>