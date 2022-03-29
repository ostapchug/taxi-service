<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<!DOCTYPE html>
<html>
<c:set var="title" value="Taxi Service | Error" />
<c:set var="localePath" value="?command=error_page&locale="/>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body>
	<%@ include file="/WEB-INF/jspf/navbar.jspf"%>
	<div class="page-header">
		<div class="container">
			<h2><fmt:message key="error_jsp.anchor.header" /></h2>
		</div>
	</div>
	
	<div class="container ">
		<div class="row">
			<div class="col-sm-offset-3 col-md-6 inf-content">
				<div class="error-template">
					<h1>
						<fmt:message key="error_jsp.anchor.h1" />
					</h1>
					<h2>
						<fmt:message key="error_jsp.anchor.h2" />
					</h2>
					<div class="error-details">						
						<c:choose>
							<c:when test="${errorMessasge != null}">
								<fmt:message key="${errorMessasge}"/>
							</c:when>
							<c:otherwise>
								<c:out value="${pageContext.errorData.statusCode}"></c:out>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>