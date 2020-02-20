<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>hellow!Θ</title>
<script type="text/javascript" src="/resource/js/jquery-3.2.1/jquery.js"></script>
<link href="/resource/bootstrap-4.3.1/dist/css/bootstrap.css" rel="stylesheet">
<script type="text/javascript" src="/resource/bootstrap-4.3.1/dist/js/bootstrap.js"></script>
<style type="text/css">
.zt{
	font-family: PingFang SC,Hiragino Sans GB,Microsoft YaHei,WenQuanYi Micro Hei,Helvetica Neue,Arial,sans-serif;
  font-size: 20px;
}
.xzt{
	font-family: PingFang SC,Hiragino Sans GB,Microsoft YaHei,WenQuanYi Micro Hei,Helvetica Neue,Arial,sans-serif;
  font-size: 14px;
}
.xxzt{
	font-family: PingFang SC,Hiragino Sans GB,Microsoft YaHei,WenQuanYi Micro Hei,Helvetica Neue,Arial,sans-serif;
  font-size: 13px;
}

</style>
<!-- <script type="text/javascript"> -->
<%--  alert('${article.title}'); --%>
<!-- </script> -->
</head>
<body>
	<!-- 	导航条 -->
	<jsp:include page="common/header.jsp"></jsp:include>
	
	<div class="container ">
		<div class="row">
<!-- 		左侧栏目 -->
			<div class="col-md-2 zt">
				<div>沦落人</div>
				<div>
					<ul class="nav flex-column">
					<c:forEach var="channel" items="${channels }">
					  <li class="nav-item  ">
					    <a class="nav-link menu" id="bs"  href="/channel?channelId=${channel.id }">${channel.name }</a>
					  </li>
					</c:forEach>
					</ul>
				</div>
			</div>
			
<!-- 			中间内容列表 -->
			<div class="col-md-6">
<!-- 				轮播图 -->
					<div id="carouselExampleIndicators" class="carousel slide zt" data-ride="carousel">
					  <ol class="carousel-indicators">
					  	<c:forEach var="slide" items="${slides}" varStatus="index">
						    <li data-target="#carouselExampleIndicators" data-slide-to="${index.index }" class="${index.index==0?'active':'' }"></li>
					  	</c:forEach>
					  </ol>
					  
					  
					 <div class="carousel-inner xzt">
					    <c:forEach items="${slides}" var="slide" varStatus="index">
					    <div class="carousel-item ${index.index==0?'active':''}">
					      <img src="/pic/${slide.picture}" class="d-block w-100" width="390px" height="350" alt="${slide.title}">
					      <div class="carousel-caption d-none d-md-block">
					        <h5>${slide.title} </h5>
					      </div>
					    </div>
					    </c:forEach>
					  </div>
					  
					  
					  <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
					    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
					    <span class="sr-only">Previous</span>
					  </a>
					  <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
					    <span class="carousel-control-next-icon" aria-hidden="true"></span>
					    <span class="sr-only">Next</span>
					  </a>
					</div>
<!-- 					轮播图下方的文章列表 已审核 不删除的 -->
					<div style="margin-top:20px">
						<c:forEach items="${articlePage.list}" var="article">
							<div class="row" style="margin-top:5px">
								<div class="col-md-3">
<%-- 								/pic/${article.picture} --%>
									<img width="130px" height="120px" src="/pic/${article.picture}" 
									  onerror="this.src='/resource/images/guest.jpg'"
									  class="rounded" 
									 >
								</div>
								<div  class="col-md-9 ">
									<a href="/article/detail?id=${article.id}" target="_blank">${article.title}</a>
									
									<br><span  class="xzt">
									作者：${article.user.username}
									<br>
									栏目：<a> ${article.channel.name} </a>&nbsp;&nbsp;&nbsp;&nbsp; 分类：<a>${article.category.name}</a></span>
								</div>
							</div>
						</c:forEach>
					</div>
					
					<!-- 分页开始 -->
						<div class="row justify-content-center" style="margin-top:20px">
							<nav aria-label="Page navigation example" >
								  <ul class="pagination ">
								  
								    <li class="page-item">
								      <a class="page-link" href="/index?page=${articlePage.pageNum==1?1:articlePage.pageNum-1}&key=${key}" aria-label="Previous">
								        <span aria-hidden="true">&laquo;</span>
								      </a>
								    </li>
								    
								    <c:forEach begin="1" end="${articlePage.pages}" varStatus="index">
								    	
								    	<!-- 当前页码的处理 -->
								    	<c:if test="${articlePage.pageNum==index.index}">
								    		<li class="page-item"><a class="page-link" href="javascript:void()"><font color="red"> ${index.index} </font></a>  </li>
								  		</c:if>
								  		
								  		<!-- 非当前页码的处理 -->
										<c:if test="${articlePage.pageNum!=index.index}">
								    		<li class="page-item"><a class="page-link" href="/index?page=${index.index}&key=${key}"> ${index.index}</a></li>
								  		</c:if>
								  
								    </c:forEach>
								    
								    <li class="page-item">
								      <a class="page-link" href="/index?page=${articlePage.pageNum==articlePage.pages?articlePage.pageNum:articlePage.pageNum+1}&key=${key}" aria-label="Next">
								        <span aria-hidden="true">&raquo;</span>
								      </a>
								    </li>
								    
								  </ul>
								</nav>
						</div>
						<!-- 分页结束 -->
			</div>
			
<!-- 			右侧最新文章 -->
			<div class="col-md-4">
			
				<div class="card">
					  <div class="card-header">
					    最新文章
					  </div>
					  <div class="card-body">
					     <ol>
					     	<c:forEach items="${lastArticles}" var="article" varStatus="index">
					     		<li class="ex"> <a href="/article/detail?id=${article.id}" target="_blank" >${article.title}</a></li>
					     	</c:forEach>
					     	
					     </ol>
					  </div>
				</div>
				
				<div class="card" style="margin-top:50px">
					  <div class="card-header">
					    公告
					  </div>
					  <div class="card-body">
					     <ul>
					     	<li>1</li>
					     	<li>2</li>
					     	<li>3</li>
					     </ul>
					  </div>
				</div>	
					
			</div>
		</div>
	</div>
	
<!-- 	脚丫 -->
	<jsp:include page="common/footer.jsp"></jsp:include>

</body>
</html>