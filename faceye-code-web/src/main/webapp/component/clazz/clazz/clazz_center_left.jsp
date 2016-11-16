<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/clazz/clazz/clazz.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/clazz/clazz/clazz.js"/>"></script>
<div class="page-header">
	<h2>
     Java Code Example:
	</h2>
</div>
<div classs="table-responsive">
	<table class="table table-bordered">
		<tbody>
			<c:forEach items="${page.content}" var="clazz">
				<tr id="${clazz.id}">
					<td><a href="<c:url value="/code/example/${clazz.longName}-${clazz.id}.html"/>">${clazz.longName}</a></td>
				</tr>
				<tr>
				   <td><fmt:message key='clazz.clazz.pkg'></fmt:message>:${clazz.pkg.name}&nbsp;&nbsp;
				   <fmt:message key='clazz.clazz.name'></fmt:message>:${clazz.name}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<f:page page="${page}" url="/code/example/api" params="<%=request.getParameterMap()%>" />
