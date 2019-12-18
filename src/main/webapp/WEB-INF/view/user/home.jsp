<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>个人登录成功</title>

<link href="/resource/bootstrap-4.3.1/dist/css/bootstrap.css" rel="stylesheet">
<script type="text/javascript" src="/resource/jquery/jquery.min.js" ></script>
<script type="text/javascript" src="/resource/js/jqueryvalidate/jquery.validate.js" ></script>
<script type="text/javascript" src="/resource/js/jqueryvalidate/localization/messages_zh.js" ></script>
<script type="text/javascript" src="/resource/bootstrap-4.3.1/dist/js/bootstrap.js" ></script>

<link rel="stylesheet" href="/resource/kindeditor/themes/default/default.css" />
 <link rel="stylesheet" href="/resource/kindeditor/plugins/code/prettify.css" />
 <script charset="utf-8" src="/resource/kindeditor/plugins/code/prettify.js"></script>
 <script charset="utf-8" src="/resource/kindeditor/kindeditor-all.js"></script>
 <script charset="utf-8" src="/resource/kindeditor/lang/zh-CN.js"></script>

<style type="text/css">
	.mymenuselected li a:hover{
		background: #E0EEEE;
	}
</style>

</head>
<body>

	<nav class="navbar navbar-expand-lg navbar-light bg-light" style="background:#E0EEEE">
  <div class="collapse navbar-collapse" id="navbarSupportedContent" style="background:#E0EEEE">
    
    <ul class="navbar-nav mr-auto">
    	<li class="nav-item">
           <a class="nav-link" href="#"><img src="/resource/images/logo.png"> </a>
      </li>
      
      <li class="nav-item active">
        <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="#">Link</a>
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          Dropdown
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="#">Action</a>
          <a class="dropdown-item" href="#">Another action</a>
          <div class="dropdown-divider"></div>
          <a class="dropdown-item" href="#">Something else here</a>
        </div>
      </li>
      <li class="nav-item">
        <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
      </li>
    </ul>
    <form class="form-inline my-2 my-lg-0">
      <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
      <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
    </form>
    <div>
    	<ul class="nav">
    		<li class="nav-item nav-link"> <img width="35px" height="35px" src="/resource/images/guest.jpg"> </li>
    	
    		<li class="nav-item nav-link">a</li>
    		<li class="nav-item nav-link">c</li>
    		<li class="nav-item nav-link">d</li>
    	</ul>
    </div>
  </div>
</nav><!--  头结束 -->
	
	<div class="container row">
		<div class="col-md-2" style="margin-top:20px ; border-right:solid 2px"> 
			<!-- 左侧的菜单 -->
			<ul class="nav flex-column mymenuselected">
				  <li class="nav-item menuselected ">
				    <a id="postLink" class="nav-link active" href="#" onclick="showWork($(this),'/user/articles')" >我的文章</a>
				  </li>
				  <li class="nav-item">
				    <a  class="nav-link" href="#" onclick="showWork($(this),'/user/postArticle')">发表文章</a>
				  </li>
				  <li class="nav-item">
				    <a class="nav-link" href="#" onclick="showWork($(this),'/user/comments')" >我的评论</a>
				  </li>
				  <li class="nav-item">
				    <a class="nav-link " href="#" tabindex="-1" aria-disabled="true">个人设置</a>
				  </li>
				</ul>	
		</div>
		
		<div class="col-md-10" id="workcontent"> 
			
		</div>	
	</div>
	
<!-- 尾开始 -->
<nav style="background:#68838B" class="nav fixed-bottom justify-content-center "  style="height="50px"> 
	<p class="text-white"   >You can do anything you put your mind to.世上无难事，只怕有心人。</p> 
</nav>

<script type="text/javascript">

	KindEditor.ready(function(K) {
		window.editor1 = K.create();
		prettyPrint();
	});

	function showWork(obj,url){
// 		将原来a标签的样式都清除
		$(".mymenuselected li a ").removeClass("disabled");
// 		给本标签赋值
		obj.addClass("disabled")
		$("#workcontent").load(url);
	}
</script>



</body>
</html>