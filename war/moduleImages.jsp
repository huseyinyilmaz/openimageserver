<div>
	<c:choose>
		<c:when test="${albums.currentAlbumId==0}">
			Please choose an album to see images inside
		</c:when>
		<c:otherwise>
			<c:forEach var="image" items="${albums.imageLinks}">
				<img src="${image.link}"></img>
			</c:forEach>
			<br></br>
			burda da create image olacak.
		</c:otherwise>
	</c:choose>
</div>