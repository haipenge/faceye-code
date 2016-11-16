<%@ include file="/component/core/taglib/taglib.jsp"%>
	<link rel="stylesheet" type="text/css"
		href="<c:url value="/css/component/clazz/methz/methz.css"/>" />
	<script type="text/javascript"
		src="<c:url value="/js/component/clazz/methz/methz.js"/>"></script>
	<div class="page-head">
		<h2>
			<fmt:message key="clazz.methz.manager"></fmt:message>
			<a class="btn btn-primary" href="<c:url value="/clazz/methz/input"/>">
				<fmt:message key="clazz.methz.add"></fmt:message>
			</a>
		</h2>
	</div>
	<div class="cl-mcont">
		<div class="block-flat">
		<c:import url="/component/core/template/msg/msg.jsp" />
			<div class="content">
				<form action="<c:url value="/clazz/methz/home"/>" method="post"
					role="form" class="form-horizontal">
					<fieldset>
						<div class="form-group">
							<div class="col-md-2">
							     <select name="EQ|clazz.$id" class="form-control">
							       <option value="0"><fmt:message key="global.select"/><fmt:message key="clazz.clazz"/></option>
							       <c:forEach items="${clazzs}" var ="clazz">
							         <option value="${clazz.id}"<c:if test="${clazz.id eq entity.clazz.id}"> selected</c:if>>${clazz.name}</option>
							       </c:forEach>
							     </select>
							</div>
							<div class="col-md-2">
								<input type="text" name="EQ|body" value="${searchParams.body}"
									placeholder="<fmt:message key="clazz.methz.body"></fmt:message>"
									class="form-control input-sm">
							</div>
							<div class="col-md-2">
								<input type="text" name="EQ|name" value="${searchParams.name}"
									placeholder="<fmt:message key="clazz.methz.name"></fmt:message>"
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
								  <th><fmt:message key='clazz.methz.class'></fmt:message></th>
								  <th><fmt:message key='clazz.methz.body'></fmt:message></th>
								  <th><fmt:message key='clazz.methz.name'></fmt:message></th>
								<!--@generate-entity-jsp-property-desc@-->
								<th><fmt:message key="global.view"/></th>
								<th><fmt:message key="global.edit"></fmt:message></th>
								<th><fmt:message key="global.remove"></fmt:message></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${page.content}" var="methz">
								<tr id="${methz.id}">
									<td><input type="checkbox" name="check-single" value="${methz.id}"></td>
									  <td>${methz.clazz.name}</td>
									  <td>${methz.body}</td>
									  <td>${methz.name}</td>
									
									<!--@generate-entity-jsp-property-value@-->
									<td><a href="<c:url value="/clazz/methz/detail/${methz.id}.html"/>"><fmt:message key="global.view"/></a></td>
									<td><a
										href="<c:url value="/clazz/methz/edit/${methz.id}"/>"> <fmt:message
												key="global.edit"></fmt:message>
									</a></td>
									<td><a
										href="<c:url value="/clazz/methz/remove/${methz.id}"/>"> <fmt:message
												key="global.remove"></fmt:message>
									</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<f:page page="${page}" url="/clazz/methz/home"  params="<%=request.getParameterMap()%>" />
			</div>
		</div>
	</div>
