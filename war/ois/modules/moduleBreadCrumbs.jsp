<%@page import="ois.ApplicationManager"%>
<%@page import="ois.view.CSParamType"%>
<%@page import="ois.view.CSPageType"%><div class="breadCrumbs">
<a href="<%=ApplicationManager.MAIN_PAGE +"?"+ CSParamType.PAGE.toString() + "=" + CSPageType.MAIN%>">Home</a>
<c:if test="${albumBean!=null}">
 > <a href="${albumBean.viewLink}">${albumBean.name}</a>
<c:if test="${albumBean.currentImageKeyString!=null && albumBean.currentImageKeyString!='none'}">
 > <a href="${albumBean.currentImageBean.revisionsLink}">${albumBean.currentImageBean.name}</a>
<c:if test="${albumBean.currentImageBean.currentDataBeanKeyString!=null && albumBean.currentImageBean.currentDataBeanKeyString!='none'}">
 > <a href="${albumBean.currentImageBean.currentDataBean.viewLink}">${albumBean.currentImageBean.currentDataBean.name}</a>
</c:if>
</c:if>
</c:if>
<c:if test="${mainPageBean.currentAlbumKeyString!=null && mainPageBean.currentAlbumKeyString!='none'}">
 > <a href="${mainPageBean.currentAlbumBean.viewLink}">${mainPageBean.currentAlbumBean.name}</a>
</c:if>

</div>