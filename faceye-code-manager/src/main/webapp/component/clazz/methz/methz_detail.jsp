<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/clazz/methz/methz.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/clazz/methz/methz.js"/>"></script>

<script type="text/javascript" src="<c:url value="/js/lib/prismjs/js/prismjs.js"/>"></script>
<link href="<c:url value="/js/lib/prismjs/css/css.css"/>" rel="stylesheet">
<style>
pre{
  padding:5px 5px 5px 5px;
}
code{
 padding:0px 0px 0px 0px;
 margin:0px 0px 0px 0px;
}
</style>
<div class="block-flat">
	<div class="header">
		<h3>
			<fmt:message key="clazz.methz.detail"></fmt:message>
		</h3>
	</div>
	<div class="content">
		<div classs="table-responsive">
			<table class="table table-bordered">
				<tr>
					<td class="bg-title-col width-p-20"><fmt:message key="clazz.methz.class"></fmt:message></td>
					<td>${methz.clazz.name}</td>
				</tr>

				<tr>
					<td class="bg-title-col width-p-20"><fmt:message key="clazz.methz.name"></fmt:message></td>
					<td>${methz.name}</td>
				</tr>
				<tr>
					<td class="bg-title-col width-p-20"><fmt:message key="clazz.methz.body"></fmt:message></td>
					<td><pre>
	<code class="language-java">${methz.body}</code>
	</pre></td>
				</tr>
				<!--@generate-entity-jsp-property-detail@-->
			</table>
		</div>
	</div>
	<div class="content">
		<pre>
	<code class="language-java">${methz.body}
	</code>
	</pre>
		<pre>
	<code class="language-java">public classs UserTest
{
	User user=null;
}
	</code>
	</pre>
	</div>
	<div class="content">
		<a href="<c:url value="/clazz/methz/edit/${methz.id}"/>" class="btn btn-sm btn-primary"><fmt:message key="global.edit" /></a> <a
			href="<c:url value="/clazz/methz/remove/${methz.id}"/>" class="btn btn-sm btn-danger"><fmt:message key="global.remove" /></a> <a href="<c:url value="/clazz/methz/home"/>"
			class="btn btn-sm btn-info"><fmt:message key="global.back" /></a>
	</div>
</div>
