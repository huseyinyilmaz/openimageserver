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
		</tr>
	</c:forEach>
	<tr>
	<td colspan="2">
		<a href="${imageBean.revisionCreateLink}" title="Create a new revision">Create a new revision</a>
	</td>
	</tr>
</table>