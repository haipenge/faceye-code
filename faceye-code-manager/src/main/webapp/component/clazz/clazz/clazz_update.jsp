<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/clazz/clazz/clazz.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/clazz/clazz/clazz.js"/>"></script>
<div class="block-flat">
	<div class="header">
		<h3>
			<c:choose>
				<c:when test="${empty clazz.id}">
					<fmt:message key="clazz.clazz.add"></fmt:message>
				</c:when>
				<c:otherwise>
					<fmt:message key="clazz.clazz.edit"></fmt:message>
				</c:otherwise>
			</c:choose>
		</h3>
	</div>
	<div class="content">
	<form:form action="/clazz/clazz/save" method="post" role="form" cssClass="form-horizontal" commandName="clazz">
			<form:hidden path="id"/>
			<fieldset>
				<div class="form-group">
					<label class="col-md-2 control-label" for="name"> <spring:message code="clazz.clazz.pkg"/>
					</label>
					<div class="col-md-6">
					      <form:select path="pkg.id" cssClass="form-control">
					      <form:option value="0" label="--Please Select" />
							<form:options items="${pkgs}" itemValue="id" itemLabel="name" />
						  </form:select>
						  <!--
					      <select name="pkg.id" class="form-control">
					        <option value="0"><fmt:message key="global.select"></fmt:message><fmt:message key="clazz.pkg"></fmt:message></option>
					        <c:forEach items ="${pkgs}" var="pkg">
					          <option value="${pkg.id}">${pkg.name}</option>
					        </c:forEach>
					      </select>
					      -->
					   <form:errors path="pkg" cssClass="error"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="name"> <spring:message code="clazz.clazz.longName"/>
					</label>
					<div class="col-md-6">
					     <form:input path="longName" cssClass="form-control"/>
					   <form:errors path="longName" cssClass="error"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="name"> <spring:message code="clazz.clazz.name"/>
					</label>
					<div class="col-md-6">
					     <form:input path="name" cssClass="form-control"/>
					   <form:errors path="name" cssClass="error"/>
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
