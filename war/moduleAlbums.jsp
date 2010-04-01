
<%@page import="ois.view.CSParamType"%>
<%@page import="ois.view.CSActionType"%>
<%@page import="ois.view.CSPageType"%>
<table class="albums">
	<c:forEach var="album" items="${albums.albums}">
		<tr>
			<td class="album">
			<c:choose>
				<c:when test="${album.id==albums.currentAlbumId}">
					${album.name}
				</c:when>
				<c:otherwise>
					<a href="/main?page=main&item=${album.id}" id="${album.id}" title="${album.description}">${album.name}</a>
				</c:otherwise>
			</c:choose>
			</td>
			<td> 	
				<c:if test="${album.id>0}">
					<a href="/main?<%=CSParamType.PAGE.toString()%>=<%=CSPageType.ALBUM_EDIT.toString()%>&<%=CSParamType.ITEM.toString()%>=${album.id}" title="delete ${album.name}">edit</a>
					<form action="/main" method="post">
					<input type="hidden" name="<%=CSParamType.ACTION.toString()%>" value="<%=CSActionType.DELETE_ALBUM.toString()%>">
					<input type="hidden" name="<%=CSParamType.ITEM.toString()%>" value="${album.id}">
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