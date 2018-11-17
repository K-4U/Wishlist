<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/WEB-INF/jsp/includes/base.jspf" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<%--TODO: Add title--%>
	<title>Beckers Wishlist - Login</title>
	<link href="${base}webjars/bootstrap/3.3.7-1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
	<link href="${base}css/style.css" rel="stylesheet">
	<link href="${base}css/login.css" rel="stylesheet">


	<script src="${base}webjars/jquery/3.3.1-1/jquery.min.js"></script>
	<script src="${base}webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>
	<style>
		body {
			background: url("${base}img/wall.jpg") no-repeat bottom center fixed;
			-webkit-background-size: cover;
			-moz-background-size: cover;
			-o-background-size: cover;
			background-size: cover;
		}
	</style>
</head>
<body>
<div class="master">
	<div class="col-md-10 col-lg-2 col-sm-10 col-xs-10 loginBox">
		<div class="panel panel-default drop-heavy-shadow transparent">
			<%--<div class="panel-heading">
				<div class="panel-title">Login</div>
			</div>--%>
			<div class="panel-body">
				<%--@elvariable id="messages" type="java.util.List<nl.k4u.web.wishlist.beans.MessagesBean.Message>"--%>
				<c:if test="${messages.size() > 0}">
					<c:forEach items="${messages}" var="message">
						<div class="row">
							<div class="col-md-12 col-xs-12 col-lg-12 col-sm-12">
								<div class="alert alert-${message.type}">${message.message}</div>
							</div>
						</div>
					</c:forEach>
					<spring:eval expression="@messagesBean.clearMessages()"/>
				</c:if>
				<tiles:insertAttribute name="pageBody"/>
			</div>
		</div>
	</div>
</div>
</body>
</html>
