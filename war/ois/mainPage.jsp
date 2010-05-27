<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ page errorPage="/ois/system/errorPage.jsp" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="mainPageBean" class="ois.view.MainPageBean" scope="request"></jsp:useBean>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Image Administration Page</title>
	<link rel="stylesheet" type="text/css" href="/ois/css/images.css">
	<link type="text/css" href="css/ui-lightness/jquery-ui-1.8.1.custom.css" rel="stylesheet" />	
	<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.8.1.custom.min.js"></script>
	<script type="text/javascript">
	$(function(){
		$("button").button({
            icons: {
                primary: 'ui-icon-locked'
            },
            text: false
        })
		
	});
	</script>

</head>
<body>
<div class="main">
	<%@ include file="modules/moduleHeader.jsp" %>
	<%@ include file="modules/moduleBreadCrumbs.jsp" %>
	<div class="body">
		<table>
		<tr>
			<td><%@ include file="modules/moduleAlbumList.jsp" %></td>
			<td><%@ include file="modules/moduleAlbumImages.jsp" %></td>
		</tr>
		</table>
	</div> 
</div>
</body>
</html>