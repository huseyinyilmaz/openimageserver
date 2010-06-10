<%@page import="com.google.appengine.api.users.UserService"%>
<%@page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@page import="ois.ApplicationManager"%>
<div class="title"><img src="/ois/img/ois48x48.png">	OPEN IMAGE SERVER	<span class="revisionTypeText"><%=ApplicationManager.VERSION%> (showcase version)</span></div>
<div align="right"><span class = "text description">(<%=UserServiceFactory.getUserService().getCurrentUser().getEmail()%>)</span>	<a href="<%=UserServiceFactory.getUserService().createLogoutURL("/")%>">logout</div>
