<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>沦落人---${article.title}</title>
<script type="text/javascript" src="/resource/js/jquery-3.2.1/jquery.js"></script>
<link href="/resource/bootstrap-4.3.1/dist/css/bootstrap.css" rel="stylesheet">
<script type="text/javascript" src="/resource/bootstrap-4.3.1/dist/js/bootstrap.js"></script>

</head>
<body>
	<div class="container" >
		<div class="row justify-content-center">
			<h3>${article.title }</h3>
		</div>
		<div class="row justify-content-center">
			<h6>
			作者：${article.user.username} &nbsp;&nbsp;&nbsp;
			栏目：${article.channel.name}  &nbsp;&nbsp;&nbsp;
			分类：${article.category.name}&nbsp;&nbsp;&nbsp;
			发表时间：<fmt:formatDate value="${article.created}" pattern="yyyy-MM-dd"/> 
			</h6>
		</div>
		<div  style="margin-top:30px">
			${article.content}
		</div>
		<div>
			<nav aria-label="...">
			  <ul class="pagination">
			    <li class="page-item ">
			      <a class="page-link" href="#" tabindex="-1" >上一篇</a>
			    </li>
			    <li class="page-item">
			      <a class="page-link" href="#">下一篇</a>
			    </li>
			  </ul>
			</nav>
		</div>
		
<!-- 		评论发布 -->
		<div class="form-group">
		    <label for="exampleFormControlTextarea1">评论区</label>
		    <textarea class="form-control" id="commentText" rows="3"></textarea>
		    <input type="button" class="btn btn-primary" onclick="addComment()" value="发表评论">
	    </div>
		<div id="comment">
			
		</div>
		
		
	</div>
	<script type="text/javascript">
		
		function gopage(page){
			show(page);
		}
		function showComment(page){
			$("#comment").load('/article/comments?id=${article.id}&page='+page);
		}
		$(function(){
			//显示第一页评论(默认)
			showComment(1);
		})
		function addComment(){
			$.post("/article/postComment",
					{articleId:'${article.id}',content:$("#commentText").val()},
					function(msg){
				if(msg.code==1){
					alert("发布成功");
					$("#commentText").val(" ");
					location.reload();
				}else{
					alert(msg.error);
				}
			},"json")
		}
	
	</script>
</body>
</html>