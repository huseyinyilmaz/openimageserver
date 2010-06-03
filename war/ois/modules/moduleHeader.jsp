<%@page import="com.google.appengine.api.users.UserService"%>
<%@page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@page import="ois.ApplicationManager"%>
<div class="title"><img src="/ois/images/ois32x32.png">	OPEN IMAGE SERVER</div>
<div align="right"><a href="<%=UserServiceFactory.getUserService().createLogoutURL(ApplicationManager.MAIN_PAGE + "?page=main")%>">logout</div>
