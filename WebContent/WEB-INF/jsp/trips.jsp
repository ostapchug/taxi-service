<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<!DOCTYPE html>
<html>
<c:set var="title" value="Taxi Service | Trips"/>
<c:set var="trips" value="active" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<%@ include file="/WEB-INF/jspf/datepicker.jspf"%>
<body>
<%@ include file="/WEB-INF/jspf/navbar.jspf"%>

<div class="page-header">
	<div class="container">
   		<h2><fmt:message key="general.anchor.trips"/></h2>
   	</div>
</div>
	
<div class="container">

	<div class="row">
		<c:if test = "${not empty errorMessage}">
			<div class="alert alert-danger alert-dismissible">
				<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
				<strong>
					<fmt:message key="general.label.anchor.error"/>
				</strong>
				<fmt:message key="${errorMessage}"/>
			</div>    		
		</c:if>

		<div class="inf-content">
			<h3><fmt:message key="trips_jsp.anchor.info"/></h3>
			<hr>
	
			<c:choose>
				<c:when test="${not empty tripList or not empty dateRange or not empty phone}">
					<%@ include file="/WEB-INF/jspf/trips_menu.jspf"%>
					<%@ include file="/WEB-INF/jspf/trips_table.jspf"%>
				</c:when>
				<c:otherwise>
					<div class = "empty-template">
						<h3 class="text-muted"><fmt:message key="trips_jsp.anchor.table.empty" /></h3>
						<br>
						<a href="?command=new_trip_page" class="btn btn-success" role="button">
							<fmt:message key="trip_jsp.anchor.info"/>
						</a>
					</div>
				</c:otherwise>
			</c:choose>
	
			<c:if test = "${totalPages > 1}">
				<p:pager currentPage="${currentPage}" totalPages = "${totalPages}"/>
			</c:if>
		
		</div>
		
	</div>
	
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>