<%@page import="ois.view.CSParamType"%>
<%@page import="ois.view.CSActionType"%>
<%@page import="ois.view.CSPageType"%>

<div>
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
						<a href="/main?<%=CSParamType.PAGE.toString()%>=<%=CSPageType.IMAGE_EDIT.toString()%>&<%=CSParamType.ITEM.toString()%>=${imageBean.keyString}">edit</a> | 
						<form action="/main" method="post">
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
				<a href="/main?<%=CSParamType.PAGE.toString()%>=<%=CSPageType.IMAGE_CREATE.toString()%>&<%=CSParamType.ITEM.toString()%>=${mainPageBean.currentAlbumKeyString}" title="Create a new Image in album ${albumBean.name}">Create an image</a>
			</c:if>
					</c:otherwise>
	</c:choose>
</div>