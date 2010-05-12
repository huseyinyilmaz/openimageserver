<%@page import="ois.view.CSParamType"%>
<%@page import="ois.view.CSActionType"%>

<%@page import="ois.ApplicationManager"%><table class="listTable">
	<c:forEach var="dataBean" items="${imageBean.dataBeanList}">
		<tr>
			<td class="listCell">
			<c:choose>
				<c:when test="${dataBean.keyString==imageBean.currentDataBeanKeyString}">
					${dataBean.name}
				</c:when>
				<c:otherwise>
					<a href="${dataBean.viewLink}" id="${dataBean.keyString}">${dataBean.name}</a>
				</c:otherwise>
			</c:choose>
			</td>
			<td> 	
				<c:if test="${dataBean.keyString!='none'}">
					<form action="<%=ApplicationManager.MAIN_PAGE%>" method="post">
					<input type="hidden" name="<%=CSParamType.ACTION.toString()%>" value="<%=CSActionType.DELETE_ALBUM.toString()%>">
					<input type="hidden" name="<%=CSParamType.ITEM.toString()%>" value="${dataBean.keyString}">
					<input type="submit" title="Delete this data" value="Delete" id="deleteDataSubmitButton">
					</form>
				</c:if>
			</td>
		</tr>
	</c:forEach>
	<tr>
	<td colspan="2">
		<a href="${mainPageBean.albumCreateLink}" title="Create a new album">Create a new album</a>
	</td>
	</tr>
</table>