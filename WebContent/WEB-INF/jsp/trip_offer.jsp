<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<!DOCTYPE html>
<html>
<c:set var="title" value="Taxi Service | Trip Offer" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body>
	<%@ include file="/WEB-INF/jspf/navbar.jspf"%>

	<div class="page-header">
		<div class="container">
			<h2>
				<fmt:message key="trip_offer_jsp.anchor.header" />
			</h2>
		</div>
	</div>

	<div class="container">

		<div class="row">

			<div class="col-md-offset-1 col-md-10 inf-content">
				<h3>
					<fmt:message key="trip_offer_jsp.anchor.info" />
				</h3>
				<hr>
				<div class="empty-template">
					<h3 class="text-muted">
						<fmt:message key="trip_offer_jsp.anchor.message" />
					</h3>
					<br>
					<form action="?command=trip_offer" method="post">
						<input type="hidden" name="offer">
						<button type="submit" class="btn btn-success offer"
							value="another_category">
							<fmt:message key="trip_offer_jsp.anchor.offer1" />
						</button>
						&nbsp;
						<button type="submit" class="btn btn-success offer"
							value="more_cars">
							<fmt:message key="trip_offer_jsp.anchor.offer2" />
						</button>
						&nbsp; <a href="?command=new_trip_page" class="btn btn-default"
							role="button"> <fmt:message key="trip_offer_jsp.anchor.back" />
						</a>
					</form>
				</div>
			</div>

		</div>

	</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>