<%@ include file="/component/core/taglib/taglib.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<li>
    <a href="#"><i class="fa fa-file"></i><span>类管理</span></a>
		<ul class="sub-menu">
				<li class="<%=JspUtil.isActive(request, "/clazz/methz")%>">
					<a href="/clazz/methz/home"><fmt:message key="clazz.methz.manager"/></a>
				</li>
				<li class="<%=JspUtil.isActive(request, "/clazz/clazz")%>">
					<a href="/clazz/clazz/home"><fmt:message key="clazz.clazz.manager"/></a>
				</li>
				<li class="<%=JspUtil.isActive(request, "/clazz/pkg")%>">
					<a href="/clazz/pkg/home"><fmt:message key="clazz.pkg.manager"/></a>
				</li>
				<li class="<%=JspUtil.isActive(request, "/clazz/sourceFile")%>">
					<a href="/clazz/sourceFile/home"><fmt:message key="clazz.sourceFile.manager"/></a>
				</li>
		</ul>
</li>

<li><a href="#"><i class="fa fa-smile-o"></i><span>Generater Component</span></a>
	<ul class="sub-menu">
	<!--
		<li class="<%=JspUtil.isActive(request, "/spider/link")%>"><a href="/spider/link/home"><fmt:message key="spider.link.manager"></fmt:message></a></li>
		-->
		<!--@generate-entity-manager-list-group-item@-->
	</ul>
</li>
