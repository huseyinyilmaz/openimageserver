<%@page import="ois.view.CSParamType"%>
<%@page import="ois.view.CSActionType"%>
<%@page import="ois.view.CSPageType"%>


<%@page import="ois.ApplicationManager"%><div>
	<c:choose>
		<c:when test="${mainPageBean.currentAlbumKeyString=='none'}">
			Please choose an album to see images inside
		</c:when>
		<c:otherwise>
			<table>
			<tr>
			<c:forEach var="imageBean" items="${mainPageBean.currentAlbumBean.imageBeanList}">
				<td>
				<div>
					<img src="${imageBean.thumbnail.link}"></img>
					<div>
						<a href="${imageBean.editLink}">edit</a> | 
						<form action="<%=ApplicationManager.MAIN_PAGE%>" method="post">
							<input type="hidden" name="<%=CSParamType.ACTION.toString()%>" value="<%=CSActionType.DELETE_IMAGE.toString()%>">
							<input type="hidden" name="<%=CSParamType.ITEM.toString()%>" value="${imageBean.keyString}">
							<input type="submit" title="Delete this image" value="Delete" id="deleteImageSubmitButton">
						</form>
					</div>  
				</div>	
				</td>
			</c:forEach>
			</tr>
			</table>
			<br></br>
			<c:if test="${mainPageBean.currentAlbumKeyString!='none'}">
				<a href="${mainPageBean.currentAlbumBean.createImageLink}" title="Create a new Image in album ${albumBean.name}">Create an image</a>
			</c:if>
					</c:otherwise>
	</c:choose>
</div>