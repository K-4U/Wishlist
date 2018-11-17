<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/WEB-INF/jsp/includes/base.jspf" %>
<tiles:importAttribute name="activePage"/>

<%--@elvariable id="owners" type="java.util.Map<nl.k4u.jpa.wishlist.pojo.BeckersUser, java.util.List<nl.k4u.jpa.wishlist.pojo.Wishlist>>"--%>
<c:forEach items="${owners}" var="owner">
	<div class="col-md-12">
		<div class="panel panel-default">
			<div class="panel-heading">
			<span class="panel-title"><div class="img-rounded profile-img"
			                               style="background-image: url('${base}img/avatars/${owner.key.avatarName}')"></div>
					${owner.key.name}</span>
			</div>
			<div class="list-group">
				<c:forEach items="${owner.value}" var="list">
					<a class="list-group-item list-group-hover" href="${base}list/${list.id}">
						<i class="fas fa-<c:out value="${list.icon == null ? 'list' : list.icon}" />"></i>&nbsp;<c:out
							value="${list.listName}"/>
					</a>
				</c:forEach>
			</div>
		</div>
	</div>
</c:forEach>
