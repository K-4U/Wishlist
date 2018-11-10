<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/WEB-INF/jsp/includes/base.jspf" %>
<tiles:importAttribute name="activePage"/>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<%--TODO: Add title--%>
	<title></title>
	<link href="${base}webjars/bootstrap/3.3.7-1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
	<link href="${base}css/style.css" rel="stylesheet">

	<script src="${base}webjars/jquery/3.3.1-1/jquery.min.js"></script>
	<script src="${base}webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>
</head>
<body>
<nav class="navbar navbar-inverse">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
			        aria-expanded="false" aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand page-scroll" href="#page-top">Beckers Wishlist</a>
		</div>
		<div id="navbar" class="collapse navbar-collapse">
			<ul class="nav navbar-nav">
				<li <c:if test="${activePage == 'home'}">class="active"</c:if>><a href=${base}">Home</a></li>
				<li <c:if test="${activePage == 'lists-own'}">class=" active"</c:if>><a href="${base}lists/own">Eigen
					lijsten</a></li>
				<li <c:if test="${activePage == 'lists'}">class="active"</c:if>><a href="${base}lists">Andere
					lijsten</a></li>
			</ul>
			<ul class="nav navbar-nav pull-right">
				<li class="dropdown">
					<a class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
					   aria-expanded="false">
						<!-- The Profile picture inserted via div class below, with shaping provided by Bootstrap -->
						<div class="img-rounded profile-img"
						     style="background-image: url('${base}img/avatars/${user.avatarName}')"></div>
						<c:out value="${user.name}"/> <span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<li>
							<a href="#">Settings</a>
						</li>
						<li role="separator" class="divider"></li>
						<li>
							<a href="#">Log out</a>
						</li>
					</ul>
				</li>
			</ul>
		</div><!--/.nav-collapse -->
	</div>
</nav>

<div class="container">

	<tiles:insertAttribute name="pageBody">
		<tiles:putAttribute name="activePage" value="${activePage}"/>
	</tiles:insertAttribute>

</div><!-- /.container -->

</body>
</html>