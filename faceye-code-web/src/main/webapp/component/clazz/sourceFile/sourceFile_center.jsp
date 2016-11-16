<%@ include file="/component/core/taglib/taglib.jsp"%>
	<link rel="stylesheet" type="text/css"
		href="<c:url value="/css/component/clazz/sourceFile/sourceFile.css"/>" />
	<script type="text/javascript"
		src="<c:url value="/js/component/clazz/sourceFile/sourceFile.js"/>"></script>
	<div class="page-head">
		<h2>
			<fmt:message key="clazz.sourceFile.manager"></fmt:message>
			<a class="btn btn-primary" href="<c:url value="/clazz/sourceFile/input"/>">
				<fmt:message key="clazz.sourceFile.add"></fmt:message>
			</a>
		</h2>
	</div>
	<div class="cl-mcont">
		<div class="block-flat">
		<c:import url="/component/core/template/msg/msg.jsp" />
			<div class="content">
				<form action="<c:url value="/clazz/sourceFile/home"/>" method="post"
					role="form" class="form-horizontal">
					<fieldset>
						<div class="form-group">
							<div class="col-md-2">
								<input type="text" name="EQ|isParse" value="${searchParams.isParse}"
									placeholder="<fmt:message key="clazz.sourceFile.isParse"></fmt:message>"
									class="form-control input-sm">
							</div>
							<div class="col-md-2">
								<input type="text" name="EQ|path" value="${searchParams.path}"
									placeholder="<fmt:message key="clazz.sourceFile.path"></fmt:message>"
									class="form-control input-sm">
							</div>
							<!--@generate-entity-jsp-query-detail@-->
							<div class="col-md-1">
								<button type="submit" class="btn btn-sm btn-primary">
									<fmt:message key="global.search"></fmt:message>
								</button>
							</div>
						</div>
					</fieldset>
				</form>
			</div>
			<div class="content">
				
				<button class="btn btn-primary btn-sm multi-remove">
					<fmt:message key="global.remove"></fmt:message>
				</button>
				<div classs="table-responsive">
					<table class="table table-bordered">
						<thead>
							<tr>
								<th><input type="checkbox" name="check-all"></th>
								  <th><fmt:message key='clazz.sourceFile.isParse'></fmt:message></th>
								  <th><fmt:message key='clazz.sourceFile.path'></fmt:message></th>
								<!--@generate-entity-jsp-property-desc@-->
								<th><fmt:message key="global.view"/></th>
								<th><fmt:message key="global.edit"></fmt:message></th>
								<th><fmt:message key="global.remove"></fmt:message></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${page.content}" var="sourceFile">
								<tr id="${sourceFile.id}">
									<td><input type="checkbox" name="check-single" value="${sourceFile.id}"></td>
									  <td>${sourceFile.isParse}</td>
									  <td>${sourceFile.path}</td>
									
									<!--@generate-entity-jsp-property-value@-->
									<td><a href="<c:url value="/clazz/sourceFile/detail/${sourceFile.id}.html"/>"><fmt:message key="global.view"/></a></td>
									<td><a
										href="<c:url value="/clazz/sourceFile/edit/${sourceFile.id}"/>"> <fmt:message
												key="global.edit"></fmt:message>
									</a></td>
									<td><a
										href="<c:url value="/clazz/sourceFile/remove/${sourceFile.id}"/>"> <fmt:message
												key="global.remove"></fmt:message>
									</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<f:page page="${page}" url="/clazz/sourceFile/home"  params="<%=request.getParameterMap()%>" />
			</div>
		</div>
	</div>
