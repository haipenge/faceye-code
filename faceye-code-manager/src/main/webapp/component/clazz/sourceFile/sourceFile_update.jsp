<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/clazz/sourceFile/sourceFile.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/clazz/sourceFile/sourceFile.js"/>"></script>
<div class="block-flat">
	<div class="header">
		<h3>
			<c:choose>
				<c:when test="${empty sourceFile.id}">
					<fmt:message key="clazz.sourceFile.add"></fmt:message>
				</c:when>
				<c:otherwise>
					<fmt:message key="clazz.sourceFile.edit"></fmt:message>
				</c:otherwise>
			</c:choose>
		</h3>
	</div>
	<div class="content">
	<form:form action="/clazz/sourceFile/save" method="post" role="form" cssClass="form-horizontal" commandName="sourceFile">
			<form:hidden path="id"/>
			<fieldset>
				<div class="form-group">
					<label class="col-md-2 control-label" for="name"> <spring:message code="clazz.sourceFile.isParse"/>
					</label>
					<div class="col-md-6">
					     <form:input path="isParse" cssClass="form-control"/>
					   <form:errors path="isParse" cssClass="error"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="name"> <spring:message code="clazz.sourceFile.path"/>
					</label>
					<div class="col-md-6">
					     <form:input path="path" cssClass="form-control"/>
					   <form:errors path="path" cssClass="error"/>
					</div>
				</div>
				<!--@generate-entity-jsp-property-update@-->
				<div class="form-group">
					<div class="col-md-offset-2 col-md-10">
						<button type="submit" class="btn btn-primary">
							<fmt:message key="global.submit.save"></fmt:message>
						</button>
					</div>
				</div>
			</fieldset>
		</form:form>
	</div>
</div>
