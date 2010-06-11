<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ page errorPage="/ois/system/errorPage.jsp" %>
<%@ page import="ois.view.CSParamType"%>
<%@ page import="ois.view.CSActionType"%>
<%@page import="ois.ApplicationManager"%><html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="imageBean" class="ois.view.ImageBean" scope="request"></jsp:useBean>
<jsp:useBean id="exception" class="java.lang.Exception" scope="request"></jsp:useBean>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>OIS-Create revision</title>
	<link type="text/css" href="/ois/css/ui-lightness/jquery-ui-1.8.1.custom.css" rel="stylesheet" />	
	<link rel="stylesheet" type="text/css" href="/ois/css/main.css">
	<script type="text/javascript" src="/ois/js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="/ois/js/jquery-ui-1.8.1.custom.min.js"></script>
	<script type="text/javascript" src="/ois/js/main.js"></script>
	<script type="text/javascript">
	var isWidthValid = true;
	var isHeightValid = true;
	var widthTitle = "";
	var heightTitle = "";
	var heightMessage = "";
	var widthMessage = "";


	function validateForm(){
		message = "";
		title = "";
		isFormValid = false;
		if(!isWidthValid){
			//name is invalid
			isFormValid= false;
			message = widthMessage;
			title = widthTitle;
		}else{
			//name is valid
			if(!isHeightValid){
				//file is invalid
				isFormValid= false;
				message = heightMessage;
				title = heightTitle;
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
		//hide highlight div
		$("#highlightDiv").hide();
		//initialize buttons
		$("#cancelButton").button({icons: {primary: 'ui-icon-cancel'},text: true}).click(function(){
			top.location="${imageBean.revisionsLink}";
			});
		$("#submitButton").button({icons: {primary: 'ui-icon-check'},text: true}).click(function(){
			$("#revisionForm").submit();
			});
		
		$("#width").keyup(function(){
			width = $("#width").val();
			if(!width){
				//width is null
				isWidthValid = false;
				widthTitle = "Null width value : ";
				widthMessage = "Please enter a width value";
			}else if(!isNumber(width)){
				//width is not null
				isWidthValid = false;
				widthTitle = "Non-numeric width value : ";
				widthMessage = "Please enter a number for width value";
			}else{
				isWidthValid = true;
				widthTitle = "";
				widthMessage = "";
			};
			validateForm();		
		});

		$("#height").keyup(function(){
			height = $("#height").val();
			if(!height){
				//width is null
				isHeightValid = false;
				heightTitle = "Null height value : ";
				heightMessage = "Please enter a height value";
			}else if(!isNumber(height)){
				//width is not null
				isHeightValid = false;
				heightTitle = "Non-numeric height value : ";
				heightMessage = "Please enter a number for height value";
			}else{
				isHeightValid = true;
				heightTitle = "";
				heightMessage = "";
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
	<%@ include file="modules/moduleException.jsp" %>
	<!--highlight secion: this is used to give client side error messages -->
	<div class="ui-widget" id="highlightDiv"> 
		<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> 
			<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span> 
			<strong id="highlightTitle"></strong> <span id="highlightMessage"></span></p> 
		</div> 
	</div> 	
	<form action="<%=ApplicationManager.MAIN_PAGE%>" method="post" id="revisionForm">
		<input type="hidden" name="<%=CSParamType.ACTION.toString()%>" value="<%=CSActionType.CREATE_REVISION.toString()%>"></input>
		<input type="hidden" name="<%=CSParamType.IMAGE.toString()%>" value="${imageBean.keyString}"></input>
	<table>
	<tr>
		<td class="title" colspan="2">Create a new image revision</td>
	</tr>
	<tr>
		<td class="title">width:</td>
		<td class="full"><input type="text" class="text" size="20" name="<%=CSParamType.WIDTH.toString()%>" id="width" value="${imageBean.currentDataBean.width}" autocomplete="off"></td>
	</tr>
	<tr>
		<td class="title">height:</td>
		<td class="full"><input type="text" class="text" size="20" name="<%=CSParamType.HEIGHT.toString()%>" id="height" value="${imageBean.currentDataBean.height}"  autocomplete="off"></td>
	</tr>
	<tr>
		<td class="title">Enhanced:</td>
		<td class="full"><input type="checkbox" name="<%=CSParamType.ENHANCED.toString()%>" id="<%=CSParamType.ENHANCED.toString()%>" <c:if test="${imageBean.currentDataBean.enhanced}">checked</c:if> ></td>
	</tr>
	</table>
	</form>
	
		<table>
	<tr>
		<td>
			<button id="cancelButton">Cancel</button>	 
			<button id="submitButton">Create</button>
			 
		</td>
	</tr>	
	</table>
	
	</div>
</div>
</body>
</html>