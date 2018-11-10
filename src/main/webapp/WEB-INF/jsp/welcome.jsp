<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/WEB-INF/jsp/includes/base.jspf" %>


<div class="col-md-12">
	<h3>Kies wat je wilt doen:</h3>
	<div class="row">
		<div class="col-md-4 col-sm-4 col-xs-12 col-lg-4">
			<a href="${base}lists" class="btn btn-default col-md-12 col-xs-12 col-sm-12 col-lg-12"
			   style="height: 200px;">
				Andere lijsten inzien
			</a>
		</div>
		<div class="col-md-4 col-sm-4 col-xs-12 col-lg-4">
			<a href="${base}lists/own" class="btn btn-default col-md-12 col-xs-12 col-sm-12 col-lg-12"
			   style="height: 200px;">
				Eigen lijsten inzien
			</a>
		</div>
		<div class="col-md-4 col-sm-4 col-xs-12 col-lg-4">
			<a href="${base}lists/own/add" class="btn btn-default col-md-12 col-xs-12 col-sm-12 col-lg-12"
			   style="height: 200px;">
				Lijst aanmaken
			</a>
		</div>
	</div>
</div>
