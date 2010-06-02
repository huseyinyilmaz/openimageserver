<%@page import="ois.ApplicationManager"%>
<%@page import="ois.view.CSParamType"%>
<%@page import="ois.view.CSActionType"%>
<%@page import="ois.view.CSPageType"%>
<div>
<c:choose>
<%--There is no current row --%>
<%----------------------------%>
<c:when test="${mainPageBean.currentAlbumKeyString=='none'}">
<span class="text">	Please choose an album to see images inside</span>
</c:when>

<%--There is a current row --%>
<%---------------------------%>
<c:otherwise>
<span class="title">	${mainPageBean.currentAlbumBean.originalName}</span>
<p class="text">${mainPageBean.currentAlbumBean.description}</p>
<br>
<button class="createImageButton">Upload a new Image</button>
<%--colNum Holds column value of images it used to
calculate image number in every row--%>
<c:set var="colNum" value="0" scope="request" />
<table class="albumImagesTable">
<tr class="imageRow">
<c:forEach var="imageBean" items="${mainPageBean.currentAlbumBean.imageBeanList}">
<c:set var="colNum" value="${colNum+1}" scope="request" />
<td>

<div class="imageDiv" id="imageDiv-${imageBean.keyString}">
<div class="innerImageDiv" id="innerImageDiv-${imageBean.keyString}">
${imageBean.name}<br>
<img src="${imageBean.thumbnail.link}" id="imageThumbnail-${imageBean.keyString}"></img>
</div>
<div><%--<a href="${imageBean.revisionsLink}">revisions</a> | <a href="${imageBean.editLink}">edit</a> | --%>
<button id="viewImageButton-${imageBean.keyString}" class="viewImageButton iconButton">Show revisions of ${imageBean.name}</button>
<button id="editImageButton-${imageBean.keyString}" class="editImageButton iconButton">edit ${imageBean.name}</button>
<button id="deleteImageButton-${imageBean.keyString}" class="deleteImageButton iconButton">delete ${imageBean.name}</button>
<form action="<%=ApplicationManager.MAIN_PAGE%>" method="post" id="deleteImageForm-${imageBean.keyString}">
<input type="hidden" name="<%=CSParamType.ACTION.toString()%>" value="<%=CSActionType.DELETE_IMAGE.toString()%>"> 
<input type="hidden" name="<%=CSParamType.IMAGE.toString()%>"value="${imageBean.keyString}">
<input type="hidden"name="<%=CSParamType.ALBUM.toString()%>" value="${mainPageBean.currentAlbumBean.keyString}">
</form>
<%--<input type="submit" title="Delete this image" value="Delete" id="deleteImageSubmitButton"></form> --%>
</div>
</div>
</td>
<%--If we have fifth image in current row pass to next row --%>
<c:if test="${colNum==4}">
<c:set var="colNum" value="0" scope="request" />
</tr>
<tr class="imageRow">
</c:if>
</c:forEach>
</tr>
</table>
<br>

<c:if test="${!empty mainPageBean.currentAlbumBean.imageBeanList}">
<button class="createImageButton">Upload a new Image</button>
</c:if>
</c:otherwise>
</c:choose>
</div>