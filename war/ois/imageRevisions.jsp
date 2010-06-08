<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ page errorPage="/ois/system/errorPage.jsp" %>
<%@ page import="ois.view.CSParamType"%>
<%@ page import="ois.view.CSActionType"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="albumBean" class="ois.view.AlbumBean" scope="request"></jsp:useBean>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Image Revisions Page</title>
	<link rel="stylesheet" type="text/css" href="/ois/css/main.css">
	<link type="text/css" href="/ois/css/ui-lightness/jquery-ui-1.8.1.custom.css" rel="stylesheet" />	
	<link rel="stylesheet" type="text/css" href="/ois/css/imageRevision.css">
	
	<script type="text/javascript" src="/ois/js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="/ois/js/jquery-ui-1.8.1.custom.min.js"></script>
	<script type="text/javascript" src="/ois/js/main.js"></script>
<script type="text/javascript">
function deleteRevision(imageName,revisionName,formId){
	window.deleteFormId = formId;
	$("#deleteDialog").html("Are you sure you want to delete revision '" + revisionName + "' of image '" + imageName +"' ?")
	$("#deleteDialog").dialog('open');
	}
$(function(){
	//initialize buttons
	$("button.currentImageRevisionButton").button({icons: {primary: 'ui-icon-image'},disabled: true});
	$("button.imageRevisionButton").button({icons: {primary: 'ui-icon-image'}});
	$("button.deleteRevisionButton").button({icons: {primary: 'ui-icon-trash'},text: false});
	//initialize revision create button and set its click event
	$("button.createRevisionButton").button({icons: {primary: 'ui-icon-plusthick'}}).click(function(){
		top.location="${albumBean.currentImageBean.revisionCreateLink}";
	});
	//set events
<c:forEach var="dataBean" items="${albumBean.currentImageBean.dataBeanList}">
	//view Revision button
	$("#imageRevisionButton-${dataBean.keyString}").click(function(){
		top.location="${dataBean.viewLink}";
	});
	//delete Revision button
	$("#deleteRevisionButton-${dataBean.keyString}").click(function(){
		//$("#deleteRevisionForm-${dataBean.keyString}").submit();
		deleteRevision("${albumBean.currentImageBean.name}",'${dataBean.name}',"#deleteRevisionForm-${dataBean.keyString}");
	});
</c:forEach>
	$("div.imageWrapperDiv").hover(function(){
		$(this).find("td:eq(1)").fadeIn("slow");	
	},function(){
		$(this).find("td:eq(1)").fadeOut("slow");
	}).find("td:eq(1)").hide();
	//initialize revision info
	//-----------------------------------
	//initialize originalRevisionDialog
	$("#originalRevisionDialog").dialog({
		modal: true,
		autoOpen: false,
		show: 'blind',//slide
		closeOnEscape: true,
		title: '${albumBean.currentImageBean.currentDataBean.name}',
		height: ${albumBean.currentImageBean.currentDataBean.height}+90,
		width: ${albumBean.currentImageBean.currentDataBean.width}+40,
		
	});
	
	//set click thumbnail event
	$("#imageThumbnailDiv").click(function(){
		$("#originalRevisionDialog").dialog('open');
		
	});
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
<%@ include file="modules/moduleHeader.jsp"%>
<%@ include file="modules/moduleBreadCrumbs.jsp" %>
<div class="body">
<table>
	<tr>
		<td class="topLeft"><%@ include file="modules/moduleImageRevList.jsp"%></td>
		<td class="topLeft full"><%@ include file="modules/moduleImageRevision.jsp"%></td>
	</tr>
</table>
</div>
</div>
<!-- Delete confirmation dialog -->
<div id="deleteDialog" title="Delete Confirmation Dialog"></div>
</body>

</html>