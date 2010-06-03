<%@page import="ois.ApplicationManager"%>
<%@page import="ois.view.CSParamType"%>
<%@page import="ois.view.CSActionType"%>
<%@page import="ois.view.CSPageType"%>

<div class="imageRevision">
	<table>
		<tr>
			<td><span class="imageRevisionTitle">Image name:</span></td>
			<td>${albumBean.currentImageBean.name}</td>
		</tr>

		<tr>
			<td><span class="imageRevisionTitle">Image creation date:</span></td>
			<td>${albumBean.currentImageBean.creationDateString}</td>
		</tr>
		<tr>
			<td><span class="imageRevisionTitle">Description:</span></td>
			<td>${albumBean.currentImageBean.description}</td>
		</tr>
	</table>
	<br>
	<c:choose>
		<c:when test="${albumBean.currentImageBean.currentDataBeanKeyString=='none'}">
			<span class="text">Please choose a revision to see revision properties.</span>
		</c:when>
		<c:otherwise>
			<table>
			<tr>
				<td>
				<div id="imageThumbnailDiv">
					<img src="${albumBean.currentImageBean.thumbnail.link}">
					<br>
					<a class="small" href="#">(click on image to see full sized revision)</a>
				</div>	
				<br>
				</td>
			</tr>
			<tr>
				<td>
						<table>
							<tr>
								<td><span class="imageRevisionTitle">Image name:</span></td>
								<td>${albumBean.currentImageBean.currentDataBean.name}</td>
							</tr>
							<tr>
								<td><span class="imageRevisionTitle">Image type:</span></td>
								<td>${albumBean.currentImageBean.currentDataBean.typeString}</td>
							</tr>
							<tr>
								<td><span class="imageRevisionTitle">Revision creation date:</span></td>
								<td>${albumBean.currentImageBean.currentDataBean.creationDateString}</td>
							</tr>
							<tr>
								<td><span class="imageRevisionTitle">Width:</span></td>
								<td>${albumBean.currentImageBean.currentDataBean.width}</td>
							</tr>
							<tr>
								<td><span class="imageRevisionTitle">Height:</span></td>
								<td>${albumBean.currentImageBean.currentDataBean.height}</td>
							</tr>
							<tr>
								<td><span class="imageRevisionTitle">Height:</span></td>
								<td>${albumBean.currentImageBean.currentDataBean.enhanced}</td>
							</tr>
						</table>
						<br>					
				</td>
			</tr>			
			<tr><td><span class="imageRevisionTitle">Image url:</span></td></tr>
			<tr><td>${albumBean.currentImageBean.currentDataBean.fullLink}</td></tr>
			</table>
			<%--Original revision dialog. It is shown when user clicks thumbnail --%>
			<div id="originalRevisionDialog"><img src="${albumBean.currentImageBean.currentDataBean.link}"></div>
		</c:otherwise>
  </c:choose>
</div>