
<%@page import="ois.controller.CSParamType"%>
<%@page import="ois.controller.CSActionType"%>
here
<table class="albums">
	<c:forEach var="album" items="${albums.albums}">
		<tr>
			<td class="album"><a href="/main?page=main&item=${album.key}" id="${album.key}" title="${album.description}">${album.name}</a></td>
			
			<td> 	
				<c:if test="${album.key>0}">
					<!--<a href="/main?<%=CSParamType.ACTION.toString()%>=<%=CSActionType.DELETE_ALBUM.toString()%>&<%=CSParamType.ITEM.toString()%>=${album.key}" title="delete ${album.name}">x</a>-->
					<form action="/main" method="post">
					<input type="hidden" name="<%=CSParamType.ACTION.toString()%>" value="<%=CSActionType.DELETE_ALBUM.toString()%>">
					<input type="hidden" name="<%=CSParamType.ITEM.toString()%>" value="${album.key}">
					<input type="submit" title="Delete this album" value="Delete" id="deleteAlbumSubmitButton">
					</form>
				</c:if>
			</td>
		</tr>
	</c:forEach>
	<tr>
	<td colspan="2">
	<form action="/main" method="post">
	<input type="hidden" name="<%=CSParamType.ACTION.toString()%>" value="<%=CSActionType.CREATE_ALBUM.toString()%>">
	Create a new album<br></br>
	name:<input type="text" name="<%=CSParamType.NAME.toString()%>" id="createAlbumName"><br></br>
	description:<br></br>
	<input type="text" name="<%=CSParamType.DESCRIPTION.toString()%>" id="createAlbumDescription"><br></br>
	<input type="submit" title="Create a new Album/" value="Create" id="createAlbumSubmitButton"> 
	</form>
	</td>
	</tr>
</table>