<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/clazz/pkg/pkg.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/clazz/pkg/pkg.js"/>"></script>
<div class="block-flat">
	<div class="header">
		<h3>
			<fmt:message key="clazz.pkg.detail"></fmt:message>
		</h3>
	</div>
	<div class="content">
		<div classs="table-responsive">
			<table class="table table-bordered">
				<tr>
					<td><fmt:message key="clazz.pkg.name"></fmt:message></td>
					<td>${pkg.name}</td>
				</tr>
				<!--@generate-entity-jsp-property-detail@-->
			</table>
		</div>
		<div classs="table-responsive">
			<table class="table table-bordered">
				<thead>
					<tr>
						<th><fmt:message key='clazz.clazz.pkg'></fmt:message></th>
						<th><fmt:message key='clazz.clazz.longName'></fmt:message></th>
						<th><fmt:message key='clazz.clazz.name'></fmt:message></th>
						<th>Times</th>
						<!--@generate-entity-jsp-property-desc@-->
						<th><fmt:message key="global.view" /></th>

					</tr>
				</thead>
				<tbody>
					<c:forEach items="${clazzs}" var="clazz">
						<tr id="${clazz.id}">
							<td>${clazz.pkg.name}</td>
							<td>${clazz.longName}</td>
							<td>${clazz.name}</td>
							<td>${clazz.times}</td>
							<!--@generate-entity-jsp-property-value@-->
							<td><a href="<c:url value="/clazz/clazz/detail/${clazz.id}.html"/>"><fmt:message key="global.view" /></a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<div class="content">
		<a href="<c:url value="/clazz/pkg/edit/${pkg.id}"/>" class="btn btn-sm btn-primary"><fmt:message key="global.edit" /></a> <a href="<c:url value="/clazz/pkg/remove/${pkg.id}"/>"
			class="btn btn-sm btn-danger"><fmt:message key="global.remove" /></a> <a href="<c:url value="/clazz/pkg/home"/>" class="btn btn-sm btn-info"><fmt:message key="global.back" /></a>
	</div>
</div>
