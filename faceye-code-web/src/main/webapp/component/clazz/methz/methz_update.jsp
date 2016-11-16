<%@ include file="/component/core/taglib/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/component/clazz/methz/methz.css"/>" />
<script type="text/javascript" src="<c:url value="/js/component/clazz/methz/methz.js"/>"></script>
<div class="block-flat">
	<div class="header">
		<h3>
			<c:choose>
				<c:when test="${empty methz.id}">
					<fmt:message key="clazz.methz.add"></fmt:message>
				</c:when>
				<c:otherwise>
					<fmt:message key="clazz.methz.edit"></fmt:message>
				</c:otherwise>
			</c:choose>
		</h3>
	</div>
	<div class="content">
	<form:form action="/clazz/methz/save" method="post" role="form" cssClass="form-horizontal" commandName="methz">
			<form:hidden path="id"/>
			<fieldset>
				<div class="form-group">
					<label class="col-md-2 control-label" for="name"> <spring:message code="clazz.methz.class"/>
					</label>
					<div class="col-md-6">
					      <form:select path="clazz.id" cssClass="form-control">
					      <form:option value="0" label="--Please Select" />
							<form:options items="${clazzs}" itemValue="id" itemLabel="name" />
						  </form:select>
						  <!--
					      <select name="clazz.id" class="form-control">
					        <option value="0"><fmt:message key="global.select"></fmt:message><fmt:message key="clazz.clazz"></fmt:message></option>
					        <c:forEach items ="${clazzs}" var="clazz">
					          <option value="${clazz.id}">${clazz.name}</option>
					        </c:forEach>
					      </select>
					      -->
					   <form:errors path="class" cssClass="error"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="name"> <spring:message code="clazz.methz.body"/>
					</label>
					<div class="col-md-6">
					     <form:input path="body" cssClass="form-control"/>
					   <form:errors path="body" cssClass="error"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="name"> <spring:message code="clazz.methz.name"/>
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
