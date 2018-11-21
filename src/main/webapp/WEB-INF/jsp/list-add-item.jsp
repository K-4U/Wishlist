<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/WEB-INF/jsp/includes/base.jspf" %>

<%--@elvariable id="itemAddCommand" type="nl.k4u.web.wishlist.fbo.ItemFBO"--%>
<form:form modelAttribute="itemAddCommand">
	<div class="col-md-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				<span class="panel-title">Voeg item toe</span>
				<a href="${base}list/${itemAddCommand.wishlist.id}" class="close" data-dismiss="alert"><span
						class="glyphicon glyphicon-remove"
						aria-hidden="true"></span></a>
			</div>

			<div class="panel-body">
				<spring:bind path="delegate.description">
					<div class="form-group ${status.error ? 'has-error' : ''}">
						<label for="delegate.description">Beschrijving</label>
						<form:input path="${status.expression}" cssClass="form-control"/>
						<form:errors path="${status.expression}" cssClass="help-block"/>
					</div>
				</spring:bind>

				<spring:bind path="delegate.price">
					<div class="form-group ${status.error ? 'has-error' : ''}">
						<label for="delegate.price">Prijs</label>
						<div class="input-group">
							<div class="input-group-addon"><i class="fas fa-euro-sign"></i></div>
							<form:input path="${status.expression}" cssClass="form-control"/>
						</div>
						<form:errors path="${status.expression}" cssClass="help-block"/>
					</div>
				</spring:bind>

				<spring:bind path="delegate.url">
					<div class="form-group ${status.error ? 'has-error' : ''}">
						<label for="delegate.url">Website/Winkel</label>
						<form:input path="${status.expression}" cssClass="form-control"/>
						<form:errors path="${status.expression}" cssClass="help-block"/>
					</div>
				</spring:bind>

				<spring:bind path="delegate.remarks">
					<div class="form-group ${status.error ? 'has-error' : ''}">
						<label for="delegate.url">Opmerkingen</label>
						<form:textarea path="${status.expression}" cssClass="form-control"/>
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
