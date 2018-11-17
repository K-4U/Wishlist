<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/WEB-INF/jsp/includes/base.jspf" %>


<div class="row" style="padding-bottom: 10px">
	<div class="col-md-12">
		<img src='${base}img/logo.jpg'
		     class="center-block img-responsive img-circle drop-shadow"
		     style="height:200px; width:200px"/>
	</div>
</div>
<div class="row">
	<div class="col-md-6 col-sm-10 col-md-offset-3 col-sm-offset-1 col-lg-8 col-lg-offset-2 text-center">
		<form method="post" action="${base}public/reset-pass/submit">
			<input type="hidden" value="${token}" name="token"/>
			<div class="form-group">
				<input type="password" name="password" placeholder="Wachtwoord"
				       class="form-control transparent-input"
				       style="border-top-left-radius: 0; border-top-right-radius: 0"/>
				<input type="password" name="passwordRepeat" placeholder="Wachtwoord herhalen"
				       class="form-control transparent-input"
				       style="border-top-left-radius: 0; border-top-right-radius: 0"/>
			</div>
			<button type="submit" class="btn btn-primary">Reset</button>
		</form>
	</div>
</div>
