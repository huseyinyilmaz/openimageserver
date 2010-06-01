<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ page errorPage="/ois/system/errorPage.jsp" %>
<%@ page import="ois.view.CSParamType"%>
<%@ page import="ois.view.CSActionType"%>
<%@page import="ois.ApplicationManager"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="albumBean" class="ois.view.AlbumBean" scope="request"></jsp:useBean>
<jsp:useBean id="exception" class="java.lang.Exception" scope="request"></jsp:useBean>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>OIS-<c:choose><c:when test="${albumBean.keyString==null}">Create Album</c:when><c:otherwise>Edit Album</c:otherwise></c:choose></title>
	<link type="text/css" href="/ois/css/ui-lightness/jquery-ui-1.8.1.custom.css" rel="stylesheet" />	
	<link rel="stylesheet" type="text/css" href="/ois/css/main.css">
	<script type="text/javascript" src="/ois/js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="/ois/js/jquery-ui-1.8.1.custom.min.js"></script>
	<script type="text/javascript" src="/ois/js/main.js"></script>
	<script type="text/javascript">
	$(function(){
		//hide highlight div
		$("#highlightDiv").hide();
		//initialize buttons
		$("#cancelButton").button({icons: {primary: 'ui-icon-cancel'},text: true}).click(function(){
			top.location="<%=ApplicationManager.getHomeURL()%>";
			});
		$("#submitButton").button({icons: {primary: 'ui-icon-check'},text: true<c:if test="${albumBean.keyString==null}">,disabled: true</c:if>}).click(function(){
				$("#albumForm").submit();
		});
		$("#name").keyup(function(){
			name = $("#name").val();
			if(!name){
				$("#submitButton").button( "option", "disabled", true );
				$("#highlightTitle").html('Null name : ' ).next('span').html('Album name cannot be empty' );
				$("#highlightDiv").slideDown("fast");//show();
			}else if(!validateName(name)){
				$("#submitButton").button( "option", "disabled", true );
				$("#highlightTitle").html('Invalid name : ' ).next('span').html('Album name "' + name +'" is invalid. Name should consist of letters, numbers and _ character' );
				$("#highlightDiv").slideDown("fast");//show();
			}else{
				$("#submitButton").button( "option", "disabled", false );
				$("#highlightDiv").slideUp("fast");//hide();
			}
			});
		});
		</script>
</head>
<body>
<div class="main">
<%@ include file="modules/moduleHeader.jsp" %>
<div class="body">
	<%@ include file="modules/moduleException.jsp" %>
	<!--highlight secion: this is used to give client side error messages -->
	<div class="ui-widget" id="highlightDiv"> 
		<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> 
			<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span> 
			<strong id="highlightTitle"></strong> <span id="highlightMessage"></span></p> 
		</div> 
	</div> 	
	<form action="<%=ApplicationManager.MAIN_PAGE%>" method="post" id="albumForm">
	<input type="hidden" name="<%=CSParamType.ACTION.toString()%>" 
value="<c:choose><c:when test="${albumBean.keyString==null}"><%=CSActionType.CREATE_ALBUM.toString()%></c:when><c:otherwise><%=CSActionType.EDIT_ALBUM.toString()%></c:otherwise></c:choose>">
	<c:if test="${albumBean.keyString!='None'}"><input type="hidden" name="<%=CSParamType.ALBUM.toString()%>" value="${albumBean.keyString}"></input></c:if>
	<table class="formTable">
		<tr><td class="title" colspan="2"><c:choose><c:when test="${albumBean.keyString==null}">Create a new album</c:when><c:otherwise>Edit album</c:otherwise></c:choose></td></tr>
		<tr>
			<td class="title">Name:</td>
			<td><input type="text" class="text" name="<%=CSParamType.NAME.toString()%>" id="name" value="${albumBean.originalName}" size="20" maxlength="15" autocomplete="off"></td>		
		</tr>
		<tr><td class="title" colspan="2">Description</td></tr>	
		<tr><td colspan="2" class="text"><textarea rows="4" cols="100" name="<%=CSParamType.DESCRIPTION.toString()%>" id="albumDescription" class="text">${albumBean.description}</textarea></td></tr>	
		</table>
		</form>
		<table>
		<tr>
			<td colspan="2"><%--<a href="<%=ApplicationManager.getHomeURL()%>">cancel</a>--%>
				<br>
				<button id="cancelButton">Cancel</button>
				<c:choose>
					<c:when test="${albumBean.keyString==null}">
					<%--<input type="submit" title="Create a new Album" value="Create" id="albumSubmitButton">--%>
					<button id="submitButton">Create</button></c:when>
					<c:otherwise>
					<%--<input type="submit" title="Save album information" value="Save" id="albumSubmitButton">--%>
					<button id="submitButton">Save</button>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</table>	 
	</div>
</div>



</body>
</html>