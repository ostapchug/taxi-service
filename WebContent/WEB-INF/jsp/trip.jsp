<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<!DOCTYPE html>
<html>
<c:set var="title" value="Taxi Service | Trip"/>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body>
<%@ include file="/WEB-INF/jspf/navbar.jspf"%>

<div class="page-header">
	<div class="container">
		<h2><fmt:message key="trip_jsp.anchor.header"/></h2>
	</div>
</div>

<div class="container">

<div class="row">

<div class="col-sm-offset-3 col-md-6 inf-content">
<h3><fmt:message key="trip_jsp.anchor.info"/></h3>
<hr>
<table class="table">
	<thead>
      <tr>
        <th colspan="2"><fmt:message key="trip_jsp.anchor.table_header"/></th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <td>
        <strong>
        <span class="glyphicon glyphicon-map-marker text-primary"></span>
        <fmt:message key="general.label.anchor.origin"/>
        </strong>
        </td>
        <td><c:out value="${trip.origin}"/></td>
      </tr>
      <tr>
        <td>
        <strong>
        <span class="glyphicon glyphicon-map-marker text-primary"></span>
        <fmt:message key="general.label.anchor.destination"/>
        </strong>
        </td>
        <td><c:out value="${trip.destination}"/></td>
      </tr>
      <tr>
        <td>
        <strong>
        <span class="glyphicon glyphicon-road text-primary"></span>
        <fmt:message key="general.label.anchor.distance"/>
        </strong>
        </td>
        <td><fmt:formatNumber value="${trip.distance}" type = "number"/></td>
      </tr>
      <tr>
        <td>
        <strong>
        <span class="glyphicon glyphicon-calendar text-primary"></span>
        <fmt:message key="general.label.anchor.date"/>
        </strong>
        </td>
        <td><fmt:formatDate pattern="dd.MM.yyyy, HH:mm:ss" value="${trip.date}"/></td>
      </tr>
      <tr>
        <td>
        <strong>
        <span class="glyphicon glyphicon-usd text-primary"></span>
        <fmt:message key="general.label.anchor.bill"/>
        </strong>
        </td>
        <td><fmt:formatNumber value="${trip.bill}" type = "number"/></td>
      </tr>
      <tr>
        <td>
        <strong>
        <span class="glyphicon glyphicon-eye-open text-primary"></span>
        <fmt:message key="general.label.anchor.status"/>
        </strong>
        </td>
        <td>
	        <c:if test="${trip.status == 'new'}">
	        	<span class="label label-info"><fmt:message key="trips_jsp.anchor.status.new"/></span>
	        </c:if>
	        <c:if test="${trip.status == 'accepted'}">
	        	<span class="label label-primary"><fmt:message key="trips_jsp.anchor.status.accepted"/></span>
	        </c:if>
	        <c:if test="${trip.status == 'completed'}">
	        	<span class="label label-success"><fmt:message key="trips_jsp.anchor.status.completed"/></span>
	        </c:if>
	        <c:if test="${trip.status == 'cancelled'}">
	        	<span class="label label-danger"><fmt:message key="trips_jsp.anchor.status.cancelled"/></span>
	        </c:if>
        </td>
      </tr>
      <tr>
        <td>
        <strong>
        <span class="glyphicon glyphicon-dashboard text-primary"></span>
        <fmt:message key="general.label.anchor.cars"/>
        </strong>
        </td>
        <td>
	        <c:forEach var="car" items="${trip.cars}">
	        	<span class="label label-default"><c:out value="${car.regNumber}"/></span>
	        </c:forEach>
		</td>
	  </tr>	
    </tbody>
  </table>
  <div class = "text-center">
  	<a href="?command=trips_page" class="btn btn-default" role="button"><fmt:message key="general.button.anchor.back"/></a>
  </div>
  </div>
</div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>