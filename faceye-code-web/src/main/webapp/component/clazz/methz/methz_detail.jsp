<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/clazz/methz/methz.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/clazz/methz/methz.js"/>"></script>
<div class="block-flat">
	<div class="header">
		<h3>
			<fmt:message key="clazz.methz.detail"></fmt:message>
		</h3>
	</div>
	<div class="content">
		<div classs="table-responsive">
			<table class="table table-bordered table-hover">
				<tr>
					<td><fmt:message key="clazz.methz.class"></fmt:message></td>
				    <td>${methz.clazz.name}</td>
				</tr>
				<tr>
					<td><fmt:message key="clazz.methz.body"></fmt:message></td>
					<td>${methz.body}</td>
				</tr>
				<tr>
					<td><fmt:message key="clazz.methz.name"></fmt:message></td>
					<td>${methz.name}</td>
				</tr>
				<!--@generate-entity-jsp-property-detail@-->
			</table>
		</div>
	</div>
	<div class="content">
	  <a href="<c:url value="/clazz/methz/edit/${methz.id}"/>" class="btn btn-sm btn-primary"><fmt:message key="global.edit"/></a>
	  <a href="<c:url value="/clazz/methz/remove/${methz.id}"/>" class="btn btn-sm btn-danger"><fmt:message key="global.remove"/></a>
	  <a href="<c:url value="/clazz/methz/home"/>" class="btn btn-sm btn-info"><fmt:message key="global.back"/></a>
	</div>
</div>
