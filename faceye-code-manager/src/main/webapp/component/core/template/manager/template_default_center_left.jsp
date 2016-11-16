<%@ include file="/component/core/taglib/taglib.jsp"%>
<li><a href="#"><i class="fa fa-smile-o"></i><span><fmt:message key="spider.spider"/></span></a>
	<ul class="sub-menu">
	<!--
		<li class="<%=JspUtil.isActive(request, "/spider/link")%>"><a href="/spider/link/home"><fmt:message key="spider.link.manager"></fmt:message></a></li>
		-->
		<!--@generate-entity-manager-list-group-item@-->
	</ul>
</li>
