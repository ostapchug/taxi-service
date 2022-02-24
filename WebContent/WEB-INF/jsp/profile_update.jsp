<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<!DOCTYPE html>
<html>
<c:set var="title" value="Taxi Service | Profile Update"/>
<c:set var="localePath" value="?command=profile_update_page&locale="/>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body>
<%@ include file="/WEB-INF/jspf/navbar.jspf"%>
<div class="container">
<div class="page-header">
   <h2><fmt:message key="profile_update_jsp.anchor.header"/></h2>
</div>
<div class="row">
<div class="col-sm-offset-3 col-md-6 inf-content">
<h3><fmt:message key="profile_update_jsp.anchor.info"/></h3>
<hr>
<form class="form-horizontal" action="app?command=profile_update" method="post">

<!-- Phone input-->
<div class="form-group">
  <label class="control-label col-sm-3" for="phone"><fmt:message key="general.label.anchor.phone"/></label>  
  <div class="col-sm-8">
  <input type="text" name="phone" placeholder="<fmt:message key="general.label.anchor.phone_placeholder"/>" value="<c:out value="${phone}"/>" class="form-control" required>
  <span class="help-block">
  <c:if test = "${not empty errorPhone}">
  	<fmt:message key="${errorPhone}"/>
  </c:if> 
  </span>  
  </div>
</div>

<!-- Password input-->
<div class="form-group">
  <label class="control-label col-sm-3" for="password"><fmt:message key="general.label.anchor.password"/></label>  
  <div class="col-sm-8">
  <input type="password" name="password" placeholder="<fmt:message key="general.label.anchor.password_placeholder"/>" class="form-control">  
  <span class="help-block">
  <c:if test = "${not empty errorPassword}">
  	<fmt:message key="${errorPassword}"/>
  </c:if>
  </span>  
  </div>
</div>

<!-- Confirm Password input-->
<div class="form-group">
  <label class="control-label col-sm-3" for="confirmPassword"><fmt:message key="general.label.anchor.password_confirm"/></label>  
  <div class="col-sm-8">
  <input type="password" name="passwordConfirm" placeholder="<fmt:message key="general.label.anchor.password_confirm_placeholder"/>" class="form-control"> 
  <span class="help-block"> 
  <c:if test = "${not empty errorPasswordConfirm}">
  	<fmt:message key="${errorPasswordConfirm}"/>
  </c:if>
  </span>   
  </div>
</div>

<!-- Name input-->
<div class="form-group">
  <label class="control-label col-sm-3" for="name"><fmt:message key="general.label.anchor.name"/></label>  
  <div class="col-sm-8">
  <input type="text" name="name" placeholder="<fmt:message key="general.label.anchor.name_placeholder"/>" value="<c:out value="${name}"/>" class="form-control">
  <span class="help-block"> 
  <c:if test = "${not empty errorName}">
  	<fmt:message key="${errorName}"/>
  </c:if>
  </span>  
  </div>
</div>

<!-- Surname input-->
<div class="form-group">
  <label class="control-label col-sm-3" for="surname"><fmt:message key="general.label.anchor.surname"/></label>  
  <div class="col-sm-8">
  <input type="text" name="surname" placeholder="<fmt:message key="general.label.anchor.surname_placeholder"/>" value="<c:out value="${surname}"/>" class="form-control">
  <span class="help-block">
  <c:if test = "${not empty errorSurname}">
  	<fmt:message key="${errorSurname}"/>
  </c:if>
  </span>  
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