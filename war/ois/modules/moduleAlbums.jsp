
<%@page import="ois.view.CSParamType"%>
<%@page import="ois.view.CSActionType"%>
<%@page import="ois.view.CSPageType"%>
<table class="albums">
	<c:forEach var="albumBean" items="${mainPageBean.albumBeanList}">
		<tr>
			<td class="album">
			<c:choose>
				<c:when test="${albumBean.keyString==mainPageBean.currentAlbumKeyString}">
					${albumBean.name}
				</c:when>
				<c:otherwise>
					<a href="/main?page=main&item=${albumBean.keyString}" id="${albumBean.keyString}" title="${albumBean.description}">${albumBean.name}</a>
				</c:otherwise>
			</c:choose>
			</td>
			<td> 	
				<c:if test="${albumBean.keyString!='none'}">
					<a href="/main?<%=CSParamType.PAGE.toString()%>=<%=CSPageType.ALBUM_EDIT.toString()%>&<%=CSParamType.ITEM.toString()%>=${albumBean.keyString}" title="delete ${albumBean.name}">edit</a>
					<form action="/main" method="post">
					<input type="hidden" name="<%=CSParamType.ACTION.toString()%>" value="<%=CSActionType.DELETE_ALBUM.toString()%>">
					<input type="hidden" name="<%=CSParamType.ITEM.toString()%>" value="${albumBean.keyString}">
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