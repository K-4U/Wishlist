<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/WEB-INF/jsp/includes/base.jspf" %>

<%--@elvariable id="list" type="nl.k4u.jpa.wishlist.pojo.Wishlist"--%>
<div class="col-md-12">
	<div class="panel panel-default">
		<div class="panel-heading">
			<span class="panel-title">
				<i class="fas fa-<c:out value="${list.icon == null ? 'list' : list.icon}" />"></i>&nbsp;<c:out
					value="${list.listName}"/>
			</span>
			<c:if test="${owner}">
				<a href="${base}list/${list.id}/add" class="close" data-dismiss="alert"><span
						class="fas fa-plus" aria-hidden="true"></span></a>
			</c:if>
		</div>
		<div>
			<table class="datatable table" style="max-width: 100% !important;">
				<thead>
				<tr>
					<th>Beschrijving</th>
					<th>Prijs</th>
					<th>Winkel</th>
					<th>Acties</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${list.items}" var="item">
					<c:if test="${!item.deleted && ((!owner && item.purchasedBy == null) || owner || item.purchasedBy == user)}">
						<tr data-id="${item.id}">
							<td>
								<c:if test="${item.remarks != null && !(item.remarks == '')}">
								<a href="#" data-remarks="${item.remarks}"
								   data-title="${item.description}"
								   data-target="#details"
								   data-toggle="modal">
									</c:if>
									<c:out value="${item.description}"/>
									<c:if test="${item.remarks != null && !(item.remarks == '')}">
								</a>
								</c:if>
							</td>
							<td><fmt:formatNumber value="${item.price}" type="currency" currencySymbol="&euro;"/></td>
							<c:if test="${item.hasValidUrl()}">
								<td>
									<a href="${item.url}" target="_blank"><c:out value="${item.store}"/></a>
								</td>
							</c:if>
							<c:if test="${!item.hasValidUrl()}">
								<td>
									<c:out value="${item.store}"/>
								</td>
							</c:if>
							<c:if test="${owner}">
								<td>
									<a href="${base}list/${list.id}/edit/${item.id}">
										<i class="fas fa-edit" aria-hidden="true"></i>
									</a>
									<a href="#" data-href="${base}list/${list.id}/remove/${item.id}"
									   data-target="#confirm"
									   data-toggle="modal">
										<i class="fas fa-trash" aria-hidden="true"></i>
									</a>
								</td>
							</c:if>
							<c:if test="${!owner && item.purchasedBy == null}">
								<td>
									<a href="#" data-href="${base}list/${list.id}/buy/${item.id}"
									   data-target="#confirm-buy"
									   data-toggle="modal">
										<i class="fas fa-shopping-basket" aria-hidden="true"></i>
									</a>
								</td>
							</c:if>
							<c:if test="${!owner && item.purchasedBy == user}">
								<td>
									Al gekocht door jou.
								</td>
							</c:if>
						</tr>
					</c:if>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>

<div class="modal fade" id="confirm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">

			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">Weet je het zeker?</h4>
			</div>

			<div class="modal-body">
				<p>Weet je zeker dat je dit wilt verwijderen? Je kan deze actie niet ongedaan maken!</p>
				<p>Dit zorgt ervoor dat het item niet meer zichtbaar is om te kopen, maar zal geen bericht sturen naar
					iemand die dit misschien al heeft gekocht.</p>
			</div>

			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Nope!</button>
				<a class="btn btn-primary btn-ok">Yes please</a>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="confirm-buy" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">

			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title">Weet je het zeker?</h4>
			</div>

			<div class="modal-body">
				<p>Weet je zeker dat je dit gaat kopen? Je kan deze actie ongedaan maken, maar dat is natuurlijk niet
					netjes!</p>
				<p>Dit zal geen berichten sturen naar iemand, en de eigenaar van deze lijst zal hier niet achter
					komen.</p>
			</div>

			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Nope!</button>
				<a class="btn btn-primary btn-ok">Yes please</a>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="details" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">

			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title"></h4>
			</div>

			<div class="modal-body">

			</div>

			<div class="modal-footer">
				<a class="btn btn-primary btn-ok" data-dismiss="modal">Ok</a>
			</div>
		</div>
	</div>
</div>
