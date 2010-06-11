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
	<title>OIS-Create Image Page</title>
	<link type="text/css" href="/ois/css/ui-lightness/jquery-ui-1.8.1.custom.css" rel="stylesheet" />	
	<link rel="stylesheet" type="text/css" href="/ois/css/main.css">
	<script type="text/javascript" src="/ois/js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="/ois/js/jquery-ui-1.8.1.custom.min.js"></script>
	<script type="text/javascript" src="/ois/js/main.js"></script>
	<script type="text/javascript">
	var isNameValid = false;
	var isFileValid = false;

	var nameTitle = "Null name : ";
	var nameMessage = "Image name cannot be empty";

	var fileTitle = "Null file : ";
	var fileMessage = "Please choose an image to upload";
	
	function validateForm(){
		message = "";
		title = "";
		isFormValid = false;
		if(!isNameValid){
			//name is invalid
			isFormValid= false;
			message = nameMessage;
			title = nameTitle;
		}else{
			//name is valid
			if(!isFileValid){
				//file is invalid
				isFormValid= false;
				message = fileMessage;
				title = fileTitle;
			}else{
				isFormValid = true;
			};
		};
	
		if(isFormValid){
			//form is valid
			$("#submitButton").button( "option", "disabled", false );
			$("#highlightDiv").slideUp("fast");//hide();
		}else{
			//form is invalid
			$("#highlightTitle").html(title).next('span').html(message);
			$("#submitButton").button( "option", "disabled", true );
			$("#highlightDiv").slideDown("fast");//hide();
		}
		
	}
	$(function(){
<c:choose>
<c:when test="${exception.message!=null}">
		window.isExceptionOccured = true;
		window.isNameValid = true;
</c:when>
<c:otherwise>
		window.isExceptionOccured = false;
</c:otherwise>
</c:choose>
		//hide highlight div
		//$("#highlightDiv").hide();
		
		//initialize buttons
		$("#cancelButton").button({icons: {primary: 'ui-icon-cancel'},text: true}).click(function(){
			top.location="${albumBean.viewLink}";
			});
		$("#submitButton").button({icons: {primary: 'ui-icon-check'},text: true,disabled: true}).click(function(){
			$("#imageForm").submit();
			});

		$("#name").keyup(function(){
			name = $("#name").val();
			if(!name){
				isNameValid = false;
				nameTitle = "Null name : ";
				nameMessage = "Image name cannot be empty";
			}else if(!validateName(name)){
				isNameValid=false;
				nameTitle = "Invalid name : ";
				nameMessage = 'Image name "' + name +'" is invalid. Name should consist of letters, numbers and _ character';
			}else{
				isNameValid = true;
				nameTitle = "";
				nameMessage = "";
			}
			validateForm();
		});

		$("#file").change(function(){
			file = $("#file").val();
			if(file){
				isFileValid=true;
				fileTitle = "";
				fileMessage = "";
			}else{
				isFileValid=false;
				fileTitle = "Null file : ";
				fileMessage = "Please choose an image to upload"
			};
			validateForm();
		});
		
	});
	</script>
</head>
<body>
<div class="main">
<%@ include file="modules/moduleHeader.jsp"%>
<div class="body">
	<%@ include file="modules/moduleException.jsp"%>
	<!--highlight secion: this is used to give client side error messages -->
	<div class="ui-widget" id="highlightDiv"> 
		<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> 
			<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span> 
			<strong id="highlightTitle">Null file : </strong> <span id="highlightMessage">Please choose an image to upload</span></p> 
		</div> 
	</div> 	
	<form action="<%=ApplicationManager.IMAGE_UPLOAD_PAGE%>" method="post" enctype="multipart/form-data" id="imageForm">
		<input type="hidden" name="<%=CSParamType.ACTION.toString()%>" value="<%=CSActionType.CREATE_IMAGE.toString()%>"></input>
		<input type="hidden" name="<%=CSParamType.ALBUM.toString()%>" value="${albumBean.keyString}"></input>
	<table>
	<tr>
		<td class="title" colspan="2">Create a new image</td>
	</tr>
	<tr>
		<td class="title">name:</td>
		<td class="full"><input type="text" name="<%=CSParamType.NAME.toString()%>" class="text" maxlength="15" size="20" id="name" autocomplete="off" <c:if test="${albumBean.currentImageBean!=null}"> value="${albumBean.currentImageBean.name}"</c:if> ></td>
	</tr>
	<tr>
		<td class="title" colspan="2">description:</td>
	</tr>
	<tr>
		<td colspan="2"><textarea rows="4" cols="100" class="text" name="<%=CSParamType.DESCRIPTION.toString()%>" id="<%=CSParamType.DESCRIPTION.toString()%>"><c:if test="${albumBean.currentImageBean!=null}">${albumBean.currentImageBean.description}</c:if></textarea></td>
	</tr>
	<tr>
		<td class="title">File to upload </td>
		<td class="full"><input type="file" name="<%=CSParamType.FILE.toString()%>" class="text" id="file"></td>
	<tr>		
		<td class="text description" colspan="2">
		<ul>
			<li>JPEG, PNG, BMP, TIFF, ICO file formats are supported.</li>
			<li>Only upload images that are less than 1MB in space</li> 
		</ul>
		</td>
	</tr>
	</tr>
	</table>
	</form>
	<table>
	<tr>
		<td>
			<button id="cancelButton">Cancel</button>	 
			<button id="submitButton">Upload</button>
			 
		</td>
	</tr>	
	</table>
	</div>
</div>
</body>
</html>