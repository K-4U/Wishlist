<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/WEB-INF/jsp/includes/base.jspf" %>

<%--@elvariable id="listAddCommand" type="nl.k4u.web.wishlist.fbo.ListFBO"--%>
<form:form modelAttribute="listAddCommand">
	<div class="col-md-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				<span class="panel-title">Voeg lijst toe</span>
				<a href="${base}lists/own" class="close" data-dismiss="alert"><span class="glyphicon glyphicon-remove"
				                                                                    aria-hidden="true"></span></a>
			</div>

			<div class="panel-body">
				<spring:bind path="delegate.listName">
					<div class="form-group ${status.error ? 'has-error' : ''}">
						<form:input path="${status.expression}" cssClass="form-control" placeholder="Lijst naam"/>
						<form:errors path="${status.expression}" cssClass="help-block"/>
					</div>
				</spring:bind>
			</div>

			<div class="panel-footer">
				<button class="btn btn-primary" type="submit">Opslaan</button>
			</div>
		</div>
	</div>

</form:form>
