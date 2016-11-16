<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/clazz/sourceFile/sourceFile.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/clazz/sourceFile/sourceFile.js"/>"></script>
<div class="block-flat">
	<div class="header">
		<h3>
			<fmt:message key="clazz.sourceFile.detail"></fmt:message>
		</h3>
	</div>
	<div class="content">
		<div classs="table-responsive">
			<table class="table table-bordered table-hover">
				<tr>
					<td><fmt:message key="clazz.sourceFile.isParse"></fmt:message></td>
					<td>${sourceFile.isParse}</td>
				</tr>
				<tr>
					<td><fmt:message key="clazz.sourceFile.path"></fmt:message></td>
					<td>${sourceFile.path}</td>
				</tr>
				<!--@generate-entity-jsp-property-detail@-->
			</table>
		</div>
	</div>
	<div class="content">
	  <a href="<c:url value="/clazz/sourceFile/edit/${sourceFile.id}"/>" class="btn btn-sm btn-primary"><fmt:message key="global.edit"/></a>
	  <a href="<c:url value="/clazz/sourceFile/remove/${sourceFile.id}"/>" class="btn btn-sm btn-danger"><fmt:message key="global.remove"/></a>
	  <a href="<c:url value="/clazz/sourceFile/home"/>" class="btn btn-sm btn-info"><fmt:message key="global.back"/></a>
	</div>
</div>
