<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<!DOCTYPE html>
<html>
<c:set var="title" value="Taxi Service | Profile"/>
<c:set var="profile" value="active" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body>
<%@ include file="/WEB-INF/jspf/navbar.jspf"%>

<div class="page-header">
	<div class="container">
   		<h2><fmt:message key="general.anchor.profile"/></h2>
   	</div>
</div>

<div class="container">
<div class="row">

<div class="col-sm-offset-2 col-md-8 inf-content">
<h3><fmt:message key="profile_jsp.anchor.info"/></h3>
<hr>

<div class="col-md-4">
<img alt="" style="width:200px;" class="img-circle img-thumbnail" src="image/taxi.png"> 
</div>
<div class="col-md-8">
<table class="table">
	<thead>
      <tr>
        <th colspan="2"><fmt:message key="profile_jsp.anchor.table_header"/></th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <td>
        <strong>
        <span class="glyphicon glyphicon-phone text-primary"></span>
        <fmt:message key="general.label.anchor.phone"/>
        </strong>
        </td>
        <td><c:out value="${personPhone}"/></td>
      </tr>
      <tr>
        <td>
        <strong>
        <span class="glyphicon glyphicon-user text-primary"></span>
        <fmt:message key="general.label.anchor.name"/>
        </strong>
        </td>
        <td><c:out value="${personName}"/></td>
      </tr>
      <tr>
        <td>
        <strong>
        <span class="glyphicon glyphicon-briefcase text-primary"></span>
        <fmt:message key="general.label.anchor.surname"/>
        </strong>
        </td>
        <td><c:out value="${personSurname}"/></td>
      </tr>
    </tbody>
  </table>
  <a href="?command=profile_update_page" class="btn btn-default" role="button"><fmt:message key="general.button.anchor.edit"/></a>
  </div>
</div>
</div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>