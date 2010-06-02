<c:if test="${exception.message!=null}">
	<div class="ui-widget">
		<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;"> 
			<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span><strong>Server Error:</strong> ${exception.message}</p>
		</div>
	</div>
	<%--><div class="exception">
	${exception.message}
	</div> --%>
</c:if>