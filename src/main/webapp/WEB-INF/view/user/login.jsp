<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户登录</title>
<link href="/resource/bootstrap-4.3.1/dist/css/bootstrap.css" rel="stylesheet">
<script type="/text/javascript" src="/resource/bootstrap-4.3.1/dist/js/bootstrap.js"></script>
</head>

<body background="/resource/images/bg.jpg">
	<div >
	  <h1 class="display-1"><p class="text-info">CMS</p></h1>
	  <div class="container" >
		<form method="post" action="login" >
		  <div class="form-group">
		    <div class="col-sm-5">
		      <input type="text" name="username" class="form-control" id="firstname" placeholder="用户名" >
		    </div>
		  </div>
		  <div class="form-group">
		    <div class="col-sm-5">
		      <input type="text" name="password"  class="form-control" id="lastname" placeholder="密码"  >
		      <p><a  class="text-primary">${error}</a></p>
		    </div>
		  </div>
		  <div style="color:red"  class="form-group">
		      <input type="checkbox"     >记住登录
		  </div>
		  <div class="form-group">
		    <div class="col-sm-offset-2 col-sm-5">
		    <button type="submit" class="btn btn-primary">登录</button>
		      <a class="btn btn-primary" href="register" role="button">注册</a>
		    </div>
		  </div>
		</form>
		
	</div>
	<nav class="nav fixed-bottom justify-content-center "  style="height="50px"> 
		<p class="text-white"   >You can do anything you put your mind to.世上无难事，只怕有心人。</p> 
	</nav>
	</div>


</body>
</html>