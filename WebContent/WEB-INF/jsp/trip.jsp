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

<div class="page-header">
	<div class="container">
   		<h2><fmt:message key="trip_jsp.anchor.header"/></h2>
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

	<div class="col-sm-offset-3 col-md-6 inf-content">
		<h3><fmt:message key="trip_jsp.anchor.info"/></h3>
		<hr>
		<form class="form-horizontal" action="?command=new_trip" method="post">

		<!-- Category select-->
		<div class="form-group">
		  <label class="control-label col-sm-3" for="category"><fmt:message key="general.label.anchor.category"/></label>  
		  <div class="col-sm-8">
		  <select class="form-control" name="category" required>
		  	<option></option>
		    <c:forEach var="category" items="${categories}">
		    	<option value = '<c:out value="${category.id}"/>'><c:out value="${category.name}"/></option>
		    </c:forEach>
		  </select>
		  </div>
		</div>

		<!-- Passenger count select-->
		<div class="form-group">
		  <label class="control-label col-sm-3" for="capacity"><fmt:message key="general.label.anchor.capacity"/></label>  
		  <div class="col-sm-8">
			<input type="text" name="capacity" placeholder="<fmt:message key='general.label.anchor.capacity_placeholder'/>" class="form-control" required>
			<span class="help-block">
			<c:if test = "${not empty errorCapacity}">
		  		<fmt:message key="${errorCapacity}"/>
		  	</c:if> 
		  	</span>
		  </div>
		</div>

		<!-- Origin address select-->
		<div class="form-group">
		  <label class="control-label col-sm-3" for="origin"><fmt:message key="general.label.anchor.origin"/></label>  
		  <div class="col-sm-8">
		  <select class="form-control" name="origin" required>
		  	<option></option>
		    <c:forEach var="location" items="${locations}">
		    	<option value = '<c:out value="${location.id}"/>'><c:out value="${location}"/></option>
		    </c:forEach>
		  </select>
		  </div>
		</div>

		<!-- Destination address street select-->
		<div class="form-group">
		  <label class="control-label col-sm-3" for="destination"><fmt:message key="general.label.anchor.destination"/></label>  
		  <div class="col-sm-8">
		  <select class="form-control"  name="destination" required>
		  	<option></option>
		    <c:forEach var="location" items="${locations}">
		    	<option value = '<c:out value="${location.id}"/>'><c:out value="${location}"/></option>
		    </c:forEach>
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