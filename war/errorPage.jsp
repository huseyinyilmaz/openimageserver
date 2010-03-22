<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
   <title>Error Page</title>
</head>
<body>
<h2>Your application has generated an error</h2>
<h3>Please notify your help desk.</h3>
<b>Exception:</b><br> 
<%= exception.toString() %>
</body>
</html>