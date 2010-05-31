<%@page import="ois.view.CSParamType"%>
<%@page import="ois.view.CSActionType"%>

<%@page import="ois.ApplicationManager"%>
<div class="sideMenu">
<c:forEach var="albumBean" items="${mainPageBean.albumBeanList}">
<div>
<table>
<tr>
<td>
<c:choose>
<c:when test="${albumBean.keyString==mainPageBean.currentAlbumKeyString}">
<button id="viewAlbumButton-${albumBean.keyString}" class="viewCurrentAlbumButton">${albumBean.name}</button>
</c:when>
<c:otherwise>
<button id="viewAlbumButton-${albumBean.keyString}" class="viewAlbumButton">${albumBean.name}</button>
</c:otherwise>
</c:choose>
</td>

<td> 	
<c:if test="${albumBean.keyString!='none'}">
<button id="editAlbumButton-${albumBean.keyString}" class="editAlbumButton iconButton">Edit Album</button>
<br>
<form action="<%=ApplicationManager.MAIN_PAGE%>" method="post" id="deleteAlbumForm-${albumBean.keyString}">
<input type="hidden" name="<%=CSParamType.ACTION.toString()%>" value="<%=CSActionType.DELETE_ALBUM.toString()%>">
<input type="hidden" name="<%=CSParamType.ALBUM.toString()%>" value="${albumBean.keyString}">
</form>
<button id="deleteAlbumButton-${albumBean.keyString}" class="deleteAlbumButton iconButton">Delete album</button>
</c:if>
</td>
</tr>
</table>
</div>
</c:forEach>
<br>
<button id="createAlbumButton" class="createAlbum">Create a new album</button>
</div>