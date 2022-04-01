<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<!DOCTYPE html>
<html>
<c:set var="title" value="Taxi Service | Error" />
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
				<h3><fmt:message key="error_jsp.anchor.info"/></h3>
				<hr>
					<div class="error-template">
						<img alt="" style="width:300px;" class="img-rounded" src="image/error.png">
						<h1><fmt:message key="error_jsp.anchor.h1" /></h1>
						<h2><fmt:message key="error_jsp.anchor.h2" /></h2>
						<div class="error-details">						
							<c:choose>
								<c:when test="${errorMessasge != null}">
									<fmt:message key="${errorMessasge}"/>
								</c:when>
								<c:otherwise>
									<c:set var = "code" value="${pageContext.errorData.statusCode}"/>
									<c:if test="${code == 403}">
										<fmt:message key="error_jsp.anchor.403"/>
									</c:if>
									<c:if test="${code == 404}">
										<fmt:message key="error_jsp.anchor.404"/>
									</c:if>
									<c:if test="${code == 500}">
										<fmt:message key="error_jsp.anchor.500"/>
									</c:if>
									<c:if test="${code == 503}">
										<fmt:message key="error_jsp.anchor.503"/>
									</c:if>
								</c:otherwise>
							</c:choose>
						</div>
						<br>
						<a href="?command=home_page" class="btn btn-default" role="button"><fmt:message key="general.button.anchor.home"/></a>
					</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>