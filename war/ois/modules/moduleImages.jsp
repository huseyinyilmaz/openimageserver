<%@page import="ois.view.CSParamType"%>
<%@page import="ois.view.CSActionType"%>
<%@page import="ois.view.CSPageType"%>

<div>
	<c:choose>
		<c:when test="${albums.currentAlbumKey=='None'}">
			Please choose an album to see images inside
		</c:when>
		<c:otherwise>
			<table>
			<tr>
			<c:forEach var="image" items="${albums.imageLinks}">
				<td>
				<div>
					<img src="${image.link}"></img>
					<div>
						<a href="/main?<%=CSParamType.PAGE.toString()%>=<%=CSPageType.IMAGE_EDIT.toString()%>&<%=CSParamType.ITEM.toString()%>=${image.key}">edit</a> | 
						<form action="/main" method="post">
							<input type="hidden" name="<%=CSParamType.ACTION.toString()%>" value="<%=CSActionType.DELETE_IMAGE.toString()%>">
							<input type="hidden" name="<%=CSParamType.ITEM.toString()%>" value="${image.key}">
							<input type="submit" title="Delete this image" value="Delete" id="deleteImageSubmitButton">
						</form>
					</div>  
				</div>	
				</td>
			</c:forEach>
			</tr>
			</table>
			<br></br>
			<c:if test="${albums.currentAlbumKey!='None' && albums.currentAlbumKey!='All'}">
				<a href="/main?<%=CSParamType.PAGE.toString()%>=<%=CSPageType.IMAGE_CREATE.toString()%>&<%=CSParamType.ITEM.toString()%>=${albums.currentAlbumKey}" title="Create a new Image in album ${album.name}">Create an image</a>
			</c:if>
					</c:otherwise>
	</c:choose>
</div>