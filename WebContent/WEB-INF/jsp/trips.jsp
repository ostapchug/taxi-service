<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<!DOCTYPE html>
<html>
<c:set var="title" value="Taxi Service | Trips"/>
<c:set var="localePath" value="?command=trips_page&locale="/>
<c:set var="trips" value="active" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body>
<%@ include file="/WEB-INF/jspf/navbar.jspf"%>
<div class="container">
<div class="page-header">
   <h2><fmt:message key="general.anchor.trips"/></h2>
</div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>