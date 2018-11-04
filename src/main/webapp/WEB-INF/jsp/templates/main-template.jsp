<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/WEB-INF/jsp/includes/base.jspf" %>
<tiles:importAttribute name="activePage"/>
<!DOCTYPE html>
<html lang="en">
<head>
	<%--TODO: Add title--%>
	<title></title>
</head>
<body>
<tiles:insertAttribute name="pageBody">
	<tiles:putAttribute name="activePage" value="${activePage}"/>
</tiles:insertAttribute>
</body>
</html>
