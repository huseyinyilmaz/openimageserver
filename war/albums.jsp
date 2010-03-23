
<%@page import="ois.controller.CSParamType"%>
<%@page import="ois.controller.CSActionType"%>
here
<table class="albums">
	<c:forEach var="album" items="${albums.albums}">
		<tr><td> <a href="/main?page=main&item=${album.key}">${album.name}</a></td></tr>
	</c:forEach>
	<tr>
	<td>
	<form action="/main" method="post">
	<input type="hidden" name="<%=CSParamType.ACTION.toString()%>" value="<%=CSActionType.CREATE_ALBUM.toString()%>">
	Create a new album<br></br>
	name:<input type="text" name="<%=CSParamType.NAME.toString()%>"><br></br>
	description:<br></br>
	<input type="text" name="<%=CSParamType.DESCRIPTION.toString()%>"><br></br>
	<input type="submit" title="Create a new Album/" value="Create"> 
	</form>
	</td>
	</tr>
</table>