<%@page import="ois.ApplicationManager"%>
<%@page import="ois.view.CSParamType"%>
<%@page import="ois.view.CSActionType"%>
<%@page import="ois.view.CSPageType"%>

<div>
	<c:choose>
		<c:when test="${imageBean.currentDataBeanKeyString=='none'}">
			Please choose a revision to see revision properties.
		</c:when>
		<c:otherwise>
			<table>
			<tr>
				<td>
				<div>
					<img src="${imageBean.currentDataBean.link}"></img>
				</div>	
				</td>
			</tr>
			<tr>
			<td>
				<table class="infoTable" border="2">
				<tr><th colspan="2">Image Info</th></tr>
				<tr><td>Image Name</td><td>${imageBean.name}</td></tr>
				<tr><td>Image CreationDate</td><td>${imageBean.creationDateString}</td></tr>
				<tr><td>Image Description</td><td>${imageBean.description}</td></tr>
				<tr><th colspan="2">Revision Info</th></tr>
				<tr><td>Image Type</td><td>${imageBean.currentDataBean.typeString}</td></tr>
				<tr><td>Image CreationDate</td><td>${imageBean.currentDataBean.creationDateString}</td></tr>
				<tr><td>Image Width</td><td>${imageBean.currentDataBean.width}</td></tr>
				<tr><td>Image Height</td><td>${imageBean.currentDataBean.height}</td></tr>
				<tr><td>Image enhanced</td><td>${imageBean.currentDataBean.enhanced}</td></tr>
				</table>
				Image url:
				<textarea rows="3" cols="100">${imageBean.currentDataBean.fullLink}</textarea>
			</td>
			</tr>
			</table>
			<br></br>
			<form action="<%=ApplicationManager.MAIN_PAGE%>" method="post">
				<input type="hidden" name="<%=CSParamType.ACTION.toString()%>" value="<%=CSActionType.DELETE_REVISION.toString()%>">
				<input type="hidden" name="<%=CSParamType.ITEM.toString()%>" value="${imageBean.currentDataBeanKeyString}">
				<input type="submit" title="Delete this Revision" value="Delete" id="deleteRevisionSubmitButton">
			</form>
			
		</c:otherwise>
  </c:choose>
</div>