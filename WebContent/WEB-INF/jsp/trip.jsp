<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<!DOCTYPE html>
<html>
<c:set var="title" value="Taxi Service | New Trip"/>
<c:set var="localePath" value="?command=new_trip_page&locale="/>
<c:set var="trip" value="active" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body>
<%@ include file="/WEB-INF/jspf/navbar.jspf"%>
<div class="container">
<div class="page-header">
   <h2><fmt:message key="trip_jsp.anchor.header"/></h2>
</div>
<div class="row">
<div class="col-sm-offset-3 col-md-6 inf-content">
<h3><fmt:message key="trip_jsp.anchor.info"/></h3>
<hr>
<form class="form-horizontal" action="?command=new_trip" method="post">

<!-- Category select-->
<div class="form-group">
  <label class="control-label col-sm-3" for="category"><fmt:message key="trip_jsp.label.anchor.category"/></label>  
  <div class="col-sm-8">
  <select class="form-control" name="category" required>
  	<option></option>
    <c:forEach var="category" items="${categories}">
    	<option><c:out value="${category.name}"/></option>
    </c:forEach>
  </select>
  </div>
</div>

<!-- Passenger count select-->
<div class="form-group">
  <label class="control-label col-sm-3" for="capacity"><fmt:message key="trip_jsp.label.anchor.capacity"/></label>  
  <div class="col-sm-8">
  <select class="form-control" name="capacity" required>
  	<option></option>
    <option>1</option>
    <option>2</option>
    <option>3</option>
    <option>4</option>
    <option>5</option>
    <option>6</option>
  </select>
  </div>
</div>

<!-- Origin address select-->
<div class="form-group">
  <label class="control-label col-sm-3" for="origin_street"><fmt:message key="trip_jsp.label.anchor.origin"/></label>  
  <div class="col-sm-8">
  <select class="form-control" id="origin_street" name="origin_street" required>
  	<option></option>
    <c:forEach var="streetName" items="${streetNames}">
    	<option><c:out value="${streetName}"/></option>
    </c:forEach>
  </select>
  </div>
</div>

<!-- Origin address street number select-->
<div class="form-group">
  <label class="control-label col-sm-3" for="origin_street_number"></label>  
  <div class="col-sm-8">
  <select class="form-control" id="origin_street_number" name="origin_street_number" required disabled>
  	<option></option>
  </select>
  </div>
</div>

<!-- Destination address street select-->
<div class="form-group">
  <label class="control-label col-sm-3" for="dest_street"><fmt:message key="trip_jsp.label.anchor.destination"/></label>  
  <div class="col-sm-8">
  <select class="form-control" id="dest_street" name="dest_street" required>
  	<option></option>
    <c:forEach var="streetName" items="${streetNames}">
    	<option><c:out value="${streetName}"/></option>
    </c:forEach>
  </select>
  </div>
</div>

<!-- Destination address street number select-->
<div class="form-group">
  <label class="control-label col-sm-3" for="dest_street_number"></label>  
  <div class="col-sm-8">
  <select class="form-control" id="dest_street_number" name="dest_street_number" required disabled>
  	<option></option>
  </select>
  </div>
</div>

<!-- Submit Button -->
<div class="form-group">
  <div class="col-sm-offset-3 col-sm-8">
    <button type="submit" class="btn btn-primary"><fmt:message key="general.button.anchor.submit"/></button>
  </div>
</div>

</form>
</div>
</div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>