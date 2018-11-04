<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/WEB-INF/jsp/includes/base.jspf" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<%--TODO: Add title--%>
	<title></title>
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
	<div class="col-md-5 col-lg-2 col-sm-10 col-xs-10 loginBox">
		<div class="panel panel-default drop-heavy-shadow">
			<div class="panel-heading">
				<div class="panel-title">Login</div>
			</div>
			<div class="panel-body">
				<div class="row" style="padding-bottom: 10px">
					<div class="col-md-12">
						<img src='${base}img/logo.jpg'
						     class="center-block img-responsive img-circle drop-shadow"
						     style="height:200px; width:200px"/>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6 col-sm-10 col-md-offset-3 col-sm-offset-1 col-lg-8 col-lg-offset-2 text-center">
						<c:if test="${error}">
							<div class="alert alert-danger">${error}</div>
						</c:if>
						<form method="post" action="login">
							<div class="form-group">
								<input type="text" name="username" placeholder="Gebruikersnaam" class="form-control"/>
								<input type="password" name="password" placeholder="Wachtwoord" class="form-control"/>
								<button type="submit" class="btn btn-primary">Login</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>
