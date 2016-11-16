<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/clazz/clazz/clazz.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/clazz/clazz/clazz.js"/>"></script>
<h4>${clazz.pkg.name }</h4>
<div class="content">
	<ul>
		<c:forEach var="claz" items="${clazzs.content}">
			<li><a href="<c:url value="/code/example/${claz.longName}-${claz.id}.html"/>">${claz.longName}</a></li>
		</c:forEach>
	</ul>
</div>

