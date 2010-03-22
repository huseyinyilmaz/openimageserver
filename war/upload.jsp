<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%! String pageName = "upload.jsp"; %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>upload image</title>
	<link rel="stylesheet" type="text/css" href="/css/images.css">
</head>
<body>
	<div class="main">

<%@ include file="header.jsp" %>

	<div class="body">
<p>
Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
</p>
<p>
Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
</p>
<p>
Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
</p>
	
	</div>
	Last server visit = <%= new Date() %>
</div>

  <form action="ImageUpload" method="post" enctype="multipart/form-data" 
           name="productForm" id="productForm"><br><br>
    <table width="400px" align="center" border=0 style="background-color:ffeeff;">
      <tr>
        <td align="center" colspan=2 style="font-weight:bold;font-size:20pt;">
           Image Details</td>
      </tr>

      <tr>
        <td align="center" colspan=2>&nbsp;</td>
      </tr>

      <tr>
        <td>where to map the image Image: </td>
        <td>
          <input type="text" name="fileName1" id="fileName1">
        <td>
      </tr>

      <tr>
        <td>Image Link: </td>
        <td>
          <input type="file" name="file" id="file">
        <td>
      </tr>
      

      <tr>
        <td></td>
        <td><input type="submit" name="Submit" value="Submit"></td>
      </tr>
      <tr>
        <td colspan="2">&nbsp;</td>
      </tr>

    </table>
  </form>


</body>
</html>