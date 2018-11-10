<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/WEB-INF/jsp/includes/base.jspf" %>

<%--@elvariable id="list" type="nl.k4u.jpa.wishlist.pojo.Wishlist"--%>
<div class="col-md-12">
	<div class="panel panel-default">
		<div class="panel-heading">
			<span class="panel-title">
				<i class="fas fa-<c:out value="${list.icon == null ? 'list' : list.icon}" />"></i>&nbsp;<c:out
					value="${list.listName}"/>
			</span>
			<c:if test="${owner}">
				<a href="${base}list/${list.id}/add" class="close" data-dismiss="alert"><span
						class="fas fa-plus" aria-hidden="true"></span></a>
			</c:if>
		</div>
		<ul class="list-group">
			<c:forEach items="${list.items}" var="item">
				<li class="list-group-item"><c:out value="${item.description}"/></li>
			</c:forEach>
		</ul>
	</div>
</div>
