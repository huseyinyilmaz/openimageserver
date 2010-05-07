<%@page import="ois.view.CSParamType"%>
<%@page import="ois.view.CSActionType"%>
<%@page import="ois.view.CSPageType"%>

<div>
	<c:choose>
		<c:when test="${albums.currentAlbumKey=='None'}">
			Please choose an album to see images inside
		</c:when>
		<c:otherwise>
			<c:forEach var="image" items="${albums.imageLinks}">
				<img src="${image.link}"></img>
			</c:forEach>
			<br></br>
			<c:if test="${albums.currentAlbumKey!='None' && albums.currentAlbumKey!='All'}">
				<a href="/main?<%=CSParamType.PAGE.toString()%>=<%=CSPageType.IMAGE_CREATE.toString()%>&<%=CSParamType.ITEM.toString()%>=${albums.currentAlbumKey}" title="Create a new Image in album ${album.name}">Create an image</a>
			</c:if>
					</c:otherwise>
	</c:choose>
</div>