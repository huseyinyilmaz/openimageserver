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
<!--					${albumBean.name}-->
<button id="viewButton-${albumBean.keyString}" class="currentViewButton">${albumBean.name}</button>
</c:when>
<c:otherwise>
<button id="viewButton-${albumBean.keyString}" class="viewButton">${albumBean.name}</button>
<!--					<a href="${albumBean.viewLink}" id="${albumBean.keyString}" title="${albumBean.description}">${albumBean.name}</a>-->
</c:otherwise>
</c:choose>
</td>

<td> 	
<c:if test="${albumBean.keyString!='none'}">
<!-- <a href="${albumBean.editLink}" title="delete ${albumBean.name}">edit</a> -->
<button id="editButton-${albumBean.keyString}" class="editButton iconButton">Edit Album</button>
<br></br>
<form action="<%=ApplicationManager.MAIN_PAGE%>" method="post" id="deleteForm-${albumBean.keyString}">
<input type="hidden" name="<%=CSParamType.ACTION.toString()%>" value="<%=CSActionType.DELETE_ALBUM.toString()%>">
<input type="hidden" name="<%=CSParamType.ALBUM.toString()%>" value="${albumBean.keyString}">
<!--<input type="submit" title="Delete this album" value="Delete" id="deleteAlbumSubmitButton"> -->
</form>
<button id="deleteButton-${albumBean.keyString}" class="deleteButton iconButton">Delete album</button>
</c:if>
</td>
</tr>
</table>
</div>
</c:forEach>
<button id="createAlbumButton" class="createAlbum">Create a new album</button>
<!--<a href="${mainPageBean.albumCreateLink}" title="Create a new album">Create a new album</a> -->
</div>