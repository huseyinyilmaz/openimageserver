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
	<title>OIS-Main Page</title>
	<link rel="stylesheet" type="text/css" href="/ois/css/main.css">
	<link rel="stylesheet" type="text/css" href="/ois/css/albumImages.css">
	<link type="text/css" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.2/themes/ui-lightness/jquery-ui.css" rel="stylesheet" />	
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.2/jquery-ui.min.js"></script>
	
	<script type="text/javascript" src="/ois/js/main.js"></script>
	<script type="text/javascript">
	//holds delete album link that will image will be redirected
	function deleteAlbum(albumName,formId){
		window.deleteFormId = formId;
		$("#deleteDialog").html("Are you sure you want to delete album '" + albumName + "' ?")
		$("#deleteDialog").dialog('open');
		}
	function deleteImage(imageName,formId){
		window.deleteFormId = formId;
		$("#deleteDialog").html("Are you sure you want to delete image '" + imageName + "' ?")
		$("#deleteDialog").dialog('open');
		}

	$(function(){
			//Initialize albums menu
			//-----------------------------------------------
			// initilaize buttons
			$("button.deleteAlbumButton").button({icons: {primary: 'ui-icon-trash'},text: false});
			$("button.editAlbumButton").button({icons: {primary: 'ui-icon-pencil'},text: false});
			$("button.viewAlbumButton").button({icons: {primary: 'ui-icon-folder-collapsed'}});
			$("button.viewCurrentAlbumButton").button({icons: {primary: 'ui-icon-folder-open'},disabled: true});
			//set events
<c:forEach var="albumBean" items="${mainPageBean.albumBeanList}">
			$("#viewAlbumButton-${albumBean.keyString}").click(function(){
				top.location="${albumBean.viewLink}";
				}).parents("div:first").hover(function(){
					$("td:eq(1)",this).stop(true, true).fadeIn("slow");
					},function(){
						$("td:eq(1)",this).fadeOut("slow");
					}).find("td:eq(1)").stop(true, true).hide();
			var deleteAlbumFormId = null;
			$("#editAlbumButton-${albumBean.keyString}").click(function(){
				top.location="${albumBean.editLink}";
				});
			$("#deleteAlbumButton-${albumBean.keyString}").click(function(){
					//$("#deleteAlbumForm-${albumBean.keyString}").submit();
					deleteAlbum("${albumBean.name}","#deleteAlbumForm-${albumBean.keyString}");
			});
</c:forEach>			
			//initialize album create button
			$("button.createAlbum").button({icons: {primary: 'ui-icon-plusthick'}}).click(function(){
				top.location="${mainPageBean.albumCreateLink}";
			});
			//initialize images menu
			//--------------------------------------
			//initialize buttons
			$("button.deleteImageButton").button({icons: {primary: 'ui-icon-trash'},text: false});
			$("button.editImageButton").button({icons: {primary: 'ui-icon-pencil'},text: false});
			$("button.viewImageButton").button({icons: {primary: 'ui-icon-folder-collapsed'},text: false});
			//initialize image create button and set its click event
			$("button.createImageButton").button({icons: {primary: 'ui-icon-plusthick'}}).click(function(){
				top.location="${mainPageBean.currentAlbumBean.createImageLink}";
			});
			//set events			
<c:forEach var="imageBean" items="${mainPageBean.currentAlbumBean.imageBeanList}">
			$("#viewImageButton-${imageBean.keyString}").click(function(){
				top.location="${imageBean.revisionsLink}";
			});

			$("#editImageButton-${imageBean.keyString}").click(function(){
				top.location="${imageBean.editLink}";
			});

			$("#deleteImageButton-${imageBean.keyString}").click(function(){
				//$("#deleteImageForm-${imageBean.keyString}").submit();
				deleteImage("${imageBean.name}","#deleteImageForm-${imageBean.keyString}");
			});

			$("#imageDiv-${imageBean.keyString}").hover(function(){
				$(this).find("div:eq(1)").stop(true, true).slideDown();
			},function(){
				$(this).find("div:eq(1)").stop(true, true).slideUp();
			}).find("div:eq(1)").hide();

			$("#imageThumbnail-${imageBean.keyString}").click(function(){
				top.location="${imageBean.revisionsLink}";
			});
			
</c:forEach>
			//initialize delete confirmation dialog
			$("#deleteDialog").dialog({
				resizable: false,
				height:200,
				width: 600,
				modal: true,
				autoOpen: false,
				buttons: {
					'Delete': function() {
						$(window.deleteFormId).submit();
					},
					'Cancel': function() {
						$(this).dialog('close');
					}
				}
			});
	});

	
	</script>

</head>
<body>
<div class="main">
<%@ include file="modules/moduleHeader.jsp" %>
<%@ include file="modules/moduleBreadCrumbs.jsp" %>
<div class="body">

<table class="mainBodyTable">
<tr>
<td class="topLeft"><%@ include file="modules/moduleAlbumList.jsp" %></td>
<td class="full topLeft"><%@ include file="modules/moduleAlbumImages.jsp" %></td>
</tr>
</table>
</div> 
</div>
<!-- Delete confirmation dialog -->
<div id="deleteDialog" title="Delete Confirmation Dialog"></div>
</body>
</html>