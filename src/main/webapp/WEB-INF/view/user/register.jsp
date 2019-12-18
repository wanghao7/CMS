<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户注册</title>
<link href="/resource/bootstrap-4.3.1/dist/css/bootstrap.css" rel="stylesheet">
<script type="text/javascript" src="/resource/jquery/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="/resource/js/jqueryvalidate/jquery.validate.js"></script>
<script type="text/javascript" src="/resource/js/jqueryvalidate/localization/messages_zh.js"></script>
<script type="text/javascript" src="/resource/bootstrap-4.3.1/dist/js/bootstrap.js"></script>
<script type="text/javascript">
$(function(){
	$("form").validate();
})
</script>
</head>

<body background="/resource/images/bg.jpg">
<div class="container" >
	<form:form modelAttribute="user" id="form" method="post" action="register">
	  <div class="form-group">
<!-- 	    <label  class="col-sm-2 control-label">用户名</label> -->
	    <p><a  class="text-success">用户名</a></p>
	    <div class="col-sm-5">
	      <form:input path="username"  remote="/user/checkname" />
	      <form:errors path="username" cssStyle="color:red" ></form:errors>
	    </div>
	  </div>
	  <div class="form-group">
<!-- 	    <label class="col-sm-2 control-label">密码</label> -->
	    <p><a  class="text-success">密码</a></p>
	    
	    <div class="col-sm-5">
	      <form:input path="password"/>
	      <form:errors path="password" cssStyle="color:red"  ></form:errors>
	    </div>
	  </div>
	
	  <div class="form-group">
	    <div class="col-sm-offset-2 col-sm-5">
	       <p><a  class="text-primary">${error }</a></p>
	      <button type="submit" class="btn btn-primary">注册</button>
	    </div>
	  </div>
	</form:form>
</div>
</body>
</html>