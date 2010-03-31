
<%@page import="ois.view.CSParamType"%>
<%@page import="ois.view.CSActionType"%>
<%@page import="ois.view.CSPageType"%>
<table class="albums">
	<c:forEach var="album" items="${albums.albums}">
		<tr>
			<td class="album"><a href="/main?page=main&item=${album.key}" id="${album.key}" title="${album.description}">${album.name}</a></td>
			
			<td> 	
				<c:if test="${album.key>0}">
					<a href="/main?<%=CSParamType.PAGE.toString()%>=<%=CSPageType.ALBUM_EDIT.toString()%>&<%=CSParamType.ITEM.toString()%>=${album.key}" title="delete ${album.name}">edit</a>
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
		<a href="/main?<%=CSParamType.PAGE.toString()%>=<%=CSPageType.ALBUM_EDIT.toString()%>" title="Create a new album">Create a new album</a>
	</td>
	</tr>
</table>