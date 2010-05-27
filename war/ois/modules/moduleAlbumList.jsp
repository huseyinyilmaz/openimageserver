<%@page import="ois.view.CSParamType"%>
<%@page import="ois.view.CSActionType"%>

<%@page import="ois.ApplicationManager"%><table class="listTable">
	<c:forEach var="albumBean" items="${mainPageBean.albumBeanList}">
		<tr>
			<td class="listCell">
			<c:choose>
				<c:when test="${albumBean.keyString==mainPageBean.currentAlbumKeyString}">
					${albumBean.name}
				</c:when>
				<c:otherwise>
					<a href="${albumBean.viewLink}" id="${albumBean.keyString}" title="${albumBean.description}">${albumBean.name}</a>
				</c:otherwise>
			</c:choose>
			</td>
			<td> 	
				<c:if test="${albumBean.keyString!='none'}">
					<a href="${albumBean.editLink}" title="delete ${albumBean.name}">edit</a>
					<form action="<%=ApplicationManager.MAIN_PAGE%>" method="post">
					<input type="hidden" name="<%=CSParamType.ACTION.toString()%>" value="<%=CSActionType.DELETE_ALBUM.toString()%>">
					<input type="hidden" name="<%=CSParamType.ALBUM.toString()%>" value="${albumBean.keyString}">
					<input type="submit" title="Delete this album" value="Delete" id="deleteAlbumSubmitButton">
					</form>
				</c:if>
			</td>
		</tr>
	</c:forEach>
	<tr>
	<td colspan="2">
		<a href="${mainPageBean.albumCreateLink}" title="Create a new album">Create a new album</a>
	</td>
	</tr>
</table>