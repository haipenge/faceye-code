<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/clazz/clazz/clazz.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/clazz/clazz/clazz.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/lib/prismjs/js/prismjs.js"/>"></script>
<link href="<c:url value="/js/lib/prismjs/css/css.css"/>" rel="stylesheet">
<div class="block-flat" style="margin-left:200px;">
	<div class="header">
		<h3>
			<fmt:message key="clazz.clazz.detail"></fmt:message>
		</h3>
	</div>
	<div class="content">
		<div classs="table-responsive">
			<table class="table table-bordered table-hover">
				<tr>
					<td><fmt:message key="clazz.clazz.pkg"></fmt:message></td>
					<td>${clazz.pkg.name}</td>
				</tr>
				<tr>
					<td><fmt:message key="clazz.clazz.longName"></fmt:message></td>
					<td>${clazz.longName}</td>
				</tr>
				<tr>
					<td><fmt:message key="clazz.clazz.name"></fmt:message></td>
					<td>${clazz.name}</td>
				</tr>
				<!--@generate-entity-jsp-property-detail@-->
			</table>
		</div>
	</div>
	<h4>${clazz.longName}:&nbsp;&nbsp;<fmt:message key="clazz.clazz.api.example" />
		:
	</h4>
	<div class="content">
		<c:forEach var="result" items="${searchResults.content}">
			<pre>
        <code class="language-java">${result.contents[0]}</code>
       </pre>
		</c:forEach>
	</div>
	<c:if test="${not empty methzs}">
		<h4>${clazz.longName}:<fmt:message key="clazz.methz.body" />
		</h4>
		<c:forEach var="methz" items="${methzs}">
			<div class="content">
				<h4>Example Of ${clazz.name}</h4>
				<pre>
				<code class="language-java">${methz.body }</code>
			</pre>
			</div>
		</c:forEach>
	</c:if>

	<div class="content">
		<a href="<c:url value="/clazz/clazz/edit/${clazz.id}"/>" class="btn btn-sm btn-primary"><fmt:message key="global.edit" /></a> <a
			href="<c:url value="/clazz/clazz/remove/${clazz.id}"/>" class="btn btn-sm btn-danger"><fmt:message key="global.remove" /></a> <a href="<c:url value="/clazz/clazz/home"/>"
			class="btn btn-sm btn-info"><fmt:message key="global.back" /></a>
	</div>
</div>
