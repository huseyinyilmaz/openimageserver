<%@page import="ois.ApplicationManager"%>
<%@page import="ois.view.CSParamType"%>
<%@page import="ois.view.CSActionType"%>
<%@page import="ois.view.CSPageType"%>

<div>
	<c:choose>
		<c:when test="${albumBean.currentImageBean.currentDataBeanKeyString=='none'}">
			Please choose a revision to see revision properties.
		</c:when>
		<c:otherwise>
			<table>
			<tr>
				<td>
				<div id="imageThumbnailDiv">
					<%--<img src="${albumBean.currentImageBean.currentDataBean.link}"></img> --%>
					<img src="${albumBean.currentImageBean.thumbnail.link}"></img>
					<br></br>
					<a class="small" href="#">(click on image to see full sized revision)</a>
				</div>	
				<br></br>
				</td>
			</tr>
			<tr>
			<td>
				<table class="infoTable" border="2">
				<tr><th colspan="2">Image Info</th></tr>
				<tr><td>Image Name</td><td>${albumBean.currentImageBean.name}</td></tr>
				<tr><td>Image CreationDate</td><td>${albumBean.currentImageBean.creationDateString}</td></tr>
				<tr><td>Image Description</td><td>${albumBean.currentImageBean.description}</td></tr>
				<tr><th colspan="2">Revision Info</th></tr>
				<tr><td>Image Type</td><td>${albumBean.currentImageBean.currentDataBean.typeString}</td></tr>
				<tr><td>Image CreationDate</td><td>${albumBean.currentImageBean.currentDataBean.creationDateString}</td></tr>
				<tr><td>Image Width</td><td>${albumBean.currentImageBean.currentDataBean.width}</td></tr>
				<tr><td>Image Height</td><td>${albumBean.currentImageBean.currentDataBean.height}</td></tr>
				<tr><td>Image enhanced</td><td>${albumBean.currentImageBean.currentDataBean.enhanced}</td></tr>
				</table>
				Image url:
				<textarea rows="3" cols="100">${albumBean.currentImageBean.currentDataBean.fullLink}</textarea>
			</td>
			</tr>
			</table>
			<%--
			<br></br>
			<form action="<%=ApplicationManager.MAIN_PAGE%>" method="post">
				<input type="hidden" name="<%=CSParamType.ACTION.toString()%>" value="<%=CSActionType.DELETE_REVISION.toString()%>">
				<input type="hidden" name="<%=CSParamType.REVISION.toString()%>" value="${albumBean.currentImageBean.currentDataBeanKeyString}">
				<input type="hidden" name="<%=CSParamType.IMAGE.toString()%>" value="${albumBean.currentImageBean.keyString}">
				<input type="submit" title="Delete this Revision" value="Delete" id="deleteRevisionSubmitButton">
			</form> --%>
			<%--Original revision dialog. It is shown when user clicks thumbnail --%>
			<div id="originalRevisionDialog"><img src="${albumBean.currentImageBean.currentDataBean.link}"></img></div>
		</c:otherwise>
  </c:choose>
</div>