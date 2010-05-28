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
	<link type="text/css" href="/ois/css/ui-lightness/jquery-ui-1.8.1.custom.css" rel="stylesheet" />	
	<script type="text/javascript" src="/ois/js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="/ois/js/jquery-ui-1.8.1.custom.min.js"></script>
	<script type="text/javascript">

		function initViewButtons(){
			$("button.viewButton").button({icons: {primary: 'ui-icon-folder-collapsed'}});
			$("button.currentViewButton").button({icons: {primary: 'ui-icon-folder-open'},disabled: true})
<c:forEach var="albumBean" items="${mainPageBean.albumBeanList}">
			$("#viewButton-${albumBean.keyString}").click(function(){
				top.location="${albumBean.viewLink}";
				});
</c:forEach>			
		}
		

		$(function(){
			// initilaize buttons
			$("button.deleteButton").button({icons: {primary: 'ui-icon-trash'},text: false});
			$("button.editButton").button({icons: {primary: 'ui-icon-pencil'},text: false});
			$("button.viewButton").button({icons: {primary: 'ui-icon-folder-collapsed'}});
			$("button.currentViewButton").button({icons: {primary: 'ui-icon-folder-open'},disabled: true})
			//set events
<c:forEach var="albumBean" items="${mainPageBean.albumBeanList}">
			$("#viewButton-${albumBean.keyString}").click(function(){
				top.location="${albumBean.viewLink}";
				}).parents("div:first").hover(function(){
					$("td:first",this).next("td").fadeIn("slow");
					},function(){
						$("td:first",this).next("td").fadeOut("slow");
					}).find("td:first").next("td").hide();

			$("#editButton-${albumBean.keyString}").click(function(){
				top.location="${albumBean.editLink}";
				});
			$("#deleteButton-${albumBean.keyString}").click(function(){
					$("#deleteForm-${albumBean.keyString}").submit();
					alert("delete");
			});

			</c:forEach>			
//initialize create button
$("#createAlbumButton").button({icons: {primary: 'ui-icon-plusthick'}}).click(function(){
	top.location="${mainPageBean.albumCreateLink}";
});

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