<%@page import="ois.view.CSParamType"%>
<%@page import="ois.view.CSActionType"%>
<%@page import="ois.ApplicationManager"%>
<div class="sideMenu">
<c:forEach var="dataBean" items="${albumBean.currentImageBean.dataBeanList}">
<div class="imageWrapperDiv">
<table>
<tr><td>
<c:choose>
<c:when test="${dataBean.keyString==albumBean.currentImageBean.currentDataBeanKeyString}">
<button class="currentImageRevisionButton">${dataBean.name}</button>
</c:when>
<c:otherwise>
<button class="imageRevisionButton" id="imageRevisionButton-${dataBean.keyString}">${dataBean.name}</button>
</c:otherwise>
</c:choose>
</td>
<td class="topLeft">
<c:if test="${dataBean.keyString!='none' && !dataBean.original && !dataBean.thumbnail}">
<form action="<%=ApplicationManager.MAIN_PAGE%>" method="post" id="deleteRevisionForm-${dataBean.keyString}">
<input type="hidden" name="<%=CSParamType.ACTION.toString()%>" value="<%=CSActionType.DELETE_REVISION.toString()%>">
<input type="hidden" name="<%=CSParamType.REVISION.toString()%>" value="${dataBean.keyString}">
<input type="hidden" name="<%=CSParamType.IMAGE.toString()%>" value="${albumBean.currentImageBean.keyString}">
</form>
<button id="deleteRevisionButton-${dataBean.keyString}" class="deleteRevisionButton iconButton">Delete revision</button>
</c:if>
</td></tr>
</table>
</div>
</c:forEach>
<br>
<button class="createRevisionButton">Create new revision</button>
<%--<a href="${albumBean.currentImageBean.revisionCreateLink}" title="Create a new revision">Create a new revision</a> --%>
</div>