<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<!DOCTYPE html>
<html>
<c:set var="title" value="Taxi Service | Home" />
<c:set var="home" value="active" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body>
	<%@ include file="/WEB-INF/jspf/navbar.jspf"%>
	<div class="page-header">
		<div class="container">
			<h2>
				<fmt:message key="home_jsp.anchor.header" />
			</h2>
		</div>
	</div>


	<div class="container">
		<div class="row">
			<div class="col-md-offset-1 col-md-10 inf-content">
				<h3>
					<fmt:message key="home_jsp.anchor.info" />
				</h3>
				<hr>
				<%@ include file="/WEB-INF/jspf/carousel.jspf"%>
				<div id="tips">
					<h3>
						<fmt:message key="home_jsp.anchor.tips" />
					</h3>
					<ul>
						<li><fmt:message key="home_jsp.anchor.tip_1" /></li>
						<li><fmt:message key="home_jsp.anchor.tip_2" /></li>
						<li><fmt:message key="home_jsp.anchor.tip_3" /></li>
						<li><fmt:message key="home_jsp.anchor.tip_4" /></li>
						<li><fmt:message key="home_jsp.anchor.tip_5" /></li>
					</ul>
				</div>
			</div>
		</div>

	</div>
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>