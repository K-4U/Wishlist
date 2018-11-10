<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/WEB-INF/jsp/includes/base.jspf" %>

<div class="col-md-12">
	<div class="panel panel-default">
		<div class="panel-heading">
			<span class="panel-title">Eigen lijsten</span>
			<a href="${base}lists/own/add" class="close" data-dismiss="alert"><span class="fas fa-plus"
			                                                                        aria-hidden="true"></span></a>
		</div>
		<div class="list-group">
			<%--@elvariable id="lists" type="java.util.List<nl.k4u.jpa.wishlist.pojo.Wishlist>"--%>
			<c:forEach items="${lists}" var="list">
				<a class="list-group-item list-group-hover" href="${base}list/${list.id}">
					<i class="fas fa-<c:out value="${list.icon == null ? 'list' : list.icon}" />"></i>&nbsp;<c:out
						value="${list.listName}"/>
				</a>
			</c:forEach>
		</div>
	</div>
</div>
