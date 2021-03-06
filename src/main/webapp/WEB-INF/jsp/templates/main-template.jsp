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
	<title>Beckers Wishlist</title>
	<link href="${base}webjars/bootstrap/3.3.7-1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
	<link rel="stylesheet"
	      href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.2/css/bootstrap-select.min.css">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css"
	      integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
	<link href="${base}css/style.css" rel="stylesheet">

	<!-- Bootstrap DataTables | https://datatables.net -->
	<link rel="stylesheet" type="text/css" href="${base}webjars/datatables/1.10.16/css/dataTables.bootstrap.css"/>
	<link rel="stylesheet" type="text/css"
	      href="${base}webjars/datatables.net-responsive-bs/2.2.3/css/responsive.bootstrap.css"/>

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
				<li <c:if test="${activePage == 'home'}">class="active"</c:if>><a href="${base}">Home</a></li>
				<li <c:if test="${activePage == 'lists-own'}">class=" active"</c:if>><a href="${base}lists/own">Eigen
					lijsten</a></li>
				<li <c:if test="${activePage == 'lists'}">class="active"</c:if>><a href="${base}lists">Andere
					lijsten</a></li>
				<li role="separator" class="divider"></li>
				<li class="hidden-sm hidden"><a href="${base}logout">Uitloggen</a></li>
			</ul>
			<ul class="nav navbar-nav pull-right hidden-xs">
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
							<a href="${base}logout">Uitloggen</a>
						</li>
					</ul>
				</li>
			</ul>
		</div><!--/.nav-collapse -->
	</div>
</nav>

<div class="container">
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
	<tiles:insertAttribute name="pageBody">
		<tiles:putAttribute name="activePage" value="${activePage}"/>
	</tiles:insertAttribute>

</div><!-- /.container -->

<script src="${base}webjars/jquery/3.3.1-1/jquery.min.js"></script>


<!-- Include all compiled plugins (below), or include individual files as needed -->
<script type="text/javascript" src="${base}webjars/bootstrap/3.3.7-1/js/transition.js"></script>
<script type="text/javascript" src="${base}webjars/bootstrap/3.3.7-1/js/collapse.js"></script>
<script type="text/javascript" src="${base}webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.2/js/bootstrap-select.min.js"></script>

<script type="text/javascript">
    $.fn.selectpicker.Constructor.iconBase = '';
    $.fn.selectpicker.Constructor.tickIcon = 'fas fa-check';
</script>

<script type="text/javascript" src="${base}webjars/datatables/1.10.16/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="${base}webjars/datatables/1.10.16/js/dataTables.bootstrap.js"></script>
<script type="text/javascript"
        src="${base}webjars/datatables.net-responsive/2.2.3/js/dataTables.responsive.js"></script>
<script type="text/javascript"
        src="${base}webjars/datatables.net-responsive-bs/2.2.3/js/responsive.bootstrap.js"></script>

<script type="text/javascript" src="${base}js/application.js"></script>
</body>
</html>
