<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body>
	<c:set var="title" value="Taxi Service | Error" />
	<%@ include file="/WEB-INF/jspf/navbar.jspf"%>
	<div class="container">
		<div class="page-header">
			<h2>
				<fmt:message key="error_jsp.anchor.header" />
			</h2>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="error-template">
					<h1>
						<fmt:message key="error_jsp.anchor.h1" />
					</h1>
					<h2>
						<fmt:message key="error_jsp.anchor.h2" />
					</h2>
					<div class="error-details">
						<fmt:message key="${errorMessasge}" />
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>