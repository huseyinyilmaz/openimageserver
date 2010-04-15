<%@page import="ois.view.CSParamType"%>
<%@page import="ois.view.CSActionType"%>
<%@page import="ois.view.CSPageType"%>

<div>
	<c:choose>
		<c:when test="${albums.currentAlbumId==0}">
			Please choose an album to see images inside
		</c:when>
		<c:otherwise>
			<c:forEach var="image" items="${albums.imageLinks}">
				<img src="${image.link}"></img>
			</c:forEach>
			<br></br>
			<a href="/main?"></a>
			<a href="/main?<%=CSParamType.PAGE.toString()%>=<%=CSPageType.IMAGE_CREATE.toString()%>&<%=CSParamType.ITEM.toString()%>=${albums.currentAlbumId}" title="Create a new Image in album ${album.name}">Create an image</a>
			
					</c:otherwise>
	</c:choose>
</div>