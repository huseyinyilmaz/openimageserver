<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ page errorPage="/ois/system/errorPage.jsp" %>
<%@ page import="ois.view.CSParamType"%>
<%@ page import="ois.view.CSActionType"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="imageBean" class="ois.view.ImageBean" scope="request"></jsp:useBean>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Image Revisions Page</title>
<link rel="stylesheet" type="text/css" href="/ois/css/images.css">
</head>
<body>
<div class="main"><%@ include file="modules/moduleHeader.jsp"%>
<div class="body">
<table>
	<tr>
		<td><%@ include file="modules/moduleImageRevList.jsp"%></td>
		<td><%@ include file="modules/moduleImageRevision.jsp"%></td>
	</tr>
</table>
</div>
</div>
</body>
</html>