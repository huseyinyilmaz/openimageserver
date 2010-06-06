<%@page import="com.google.appengine.api.users.UserService"%>
<%@page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@page import="ois.ApplicationManager"%>
<div class="title"><img src="/ois/images/ois48x48.png">	OPEN IMAGE SERVER	<span class="revisionTypeText">v 0.3.111 beta (showcase version)</span></div>
<div align="right"><a href="<%=UserServiceFactory.getUserService().createLogoutURL(ApplicationManager.MAIN_PAGE + "?page=main")%>">logout</div>
