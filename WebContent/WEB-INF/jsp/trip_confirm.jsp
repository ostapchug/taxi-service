<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<!DOCTYPE html>
<html>
<c:set var="title" value="Taxi Service | Trip Confirm"/>
<c:set var="localePath" value="?command=trip_confirm_page&locale="/>

<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body>
<%@ include file="/WEB-INF/jspf/navbar.jspf"%>

<div class="page-header">
	<div class="container">
		<h2><fmt:message key="trip_confirm_jsp.anchor.header"/></h2>
	</div>
</div>

<div class="container">

<div class="row">

<div class="col-sm-offset-3 col-md-6 inf-content">
<h3><fmt:message key="trip_confirm_jsp.anchor.info"/></h3>
<hr>
<table class="table">
	<thead>
      <tr>
        <th colspan="2"><fmt:message key="trip_confirm_jsp.anchor.table_header"/></th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <td>
        <strong>
        <span class="glyphicon glyphicon-list text-primary"></span>
        <fmt:message key="general.label.anchor.category"/>
        </strong>
        </td>
        <td><c:out value="${category}"/></td>
      </tr>
      <tr>
        <td>
        <strong>
        <span class="glyphicon glyphicon-user text-primary"></span>
        <fmt:message key="general.label.anchor.capacity"/>
        </strong>
        </td>
        <td><c:out value="${tripConfirm.capacity}"/></td>
      </tr>
      <tr>
        <td>
        <strong>
        <span class="glyphicon glyphicon-arrow-right text-primary"></span>
        <fmt:message key="general.label.anchor.origin"/>
        </strong>
        </td>
        <td><c:out value="${origin}"/></td>
      </tr>
      <tr>
        <td>
        <strong>
        <span class="glyphicon glyphicon-arrow-left text-primary"></span>
        <fmt:message key="general.label.anchor.destination"/>
        </strong>
        </td>
        <td><c:out value="${destination}"/></td>
      </tr>
      <tr>
        <td>
        <strong>
        <span class="glyphicon glyphicon-road text-primary"></span>
        <fmt:message key="general.label.anchor.distance"/>
        </strong>
        </td>
        <td><c:out value="${tripConfirm.distance}"/></td>
      </tr>
      <tr>
        <td>
        <strong>
        <span class="glyphicon glyphicon-usd text-primary"></span>
        <fmt:message key="general.label.anchor.price"/>
        </strong>
        </td>
        <td><c:out value="${tripConfirm.price}"/></td>
      </tr>
      <tr>
        <td>
        <strong>
        <span class="glyphicon glyphicon-piggy-bank text-primary"></span>
        <fmt:message key="general.label.anchor.discount"/>
        </strong>
        </td>
        <td><c:out value="${tripConfirm.discount}"/></td>
      </tr>
      <tr>
        <td>
        <strong>
        <span class="glyphicon glyphicon-usd text-primary"></span>
        <fmt:message key="general.label.anchor.total"/>
        </strong>
        </td>
        <td><c:out value="${tripConfirm.total}"/></td>
      </tr>
      <tr>
        <td>
        <strong>
        <span class="glyphicon glyphicon-time text-primary"></span>
        <fmt:message key="general.label.anchor.wait_time"/>
        </strong>
        </td>
        <td><c:out value="${tripConfirm.waitTime}"/></td>
      </tr>
      <c:if test="${fn:length(tripConfirm.cars) > 1}">
	      <tr>
	        <td>
	        <strong>
	        <span class="glyphicon glyphicon-dashboard text-primary"></span>
	        <fmt:message key="general.label.anchor.car_count"/>
	        </strong>
	        </td>
	        <td><c:out value="${fn:length(tripConfirm.cars)}"/></td>
	      </tr>	
      </c:if>
    </tbody>
  </table>
  	<form action = "?command=trip_confirm" method="post">
	  	<input type="text" name="confirm" value = "confirmed" style = "display: none">
		<div class = "text-center">
			<button type="submit" class="btn btn-success"><fmt:message key="general.button.anchor.submit"/></button>&nbsp;
			<a href="?command=new_trip_page" class="btn btn-danger" role="button"><fmt:message key="general.button.anchor.cancel"/></a>
		</div>
	</form>
  </div>
</div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>