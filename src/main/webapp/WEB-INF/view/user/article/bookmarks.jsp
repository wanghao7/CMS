<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

	<table class="table table-hover">
<!-- 	  <thead> -->
<!--           <tr> -->
<!--             <th>id</th> -->
<!--             <th>标题</th> -->
<!--             <th>栏目</th> -->
<!--             <th>分类</th> -->
<!--             <th>发布时间</th> -->
<!--             <th>状态</th> -->
<!--             <th>操作</th> -->
<!--           </tr> -->
<!--         </thead> -->
        <tbody>
        	<c:forEach items="${pg.list}" var="bookmarks">
        		<tr>
        			<td><a href="${bookmarks.url}" >${bookmarks.text}</a> </td>
        			<td>${bookmarks.created}</td>
        			<td><input type="button" value="删除"  class="btn btn-danger" onclick="del(${bookmarks.sid})"></td>
        		</tr>
        	</c:forEach>
        </tbody>
      </table>
      
      <nav aria-label="Page navigation example">
		  <ul class="pagination justify-content-center">
		    <li class="page-item ">
		      <a class="page-link" href="#"  onclick="gopage('1')" >Previous</a>
		    </li>
		   	<c:forEach begin="1" end="${pg.pages}" varStatus="i">
		   		<li class="page-item"><a class="page-link" href="#" onclick="gopage(${i.index})">${i.index}</a></li>
		   	</c:forEach>
		    
		   
		    <li class="page-item">
		      <a class="page-link" onclick="gopage(${pg.pages})" href="#">Next</a>
		    </li>
		  </ul>
		</nav>
	
<script>
	function del(sid){
		if(!confirm("您确认删除么？")){
			return;
		}
		
		$.post('/user/deleteBookmark',{sid:sid},
				function(data){
					if(data==true){
						alert("刪除成功")
						//跳转到点击我的文章时的访问路径
						$("#workcontent").load("/user/bookmarks");
					}else{
						alert("刪除失败")
					}
					
		},"json")
	}
	
	
	/**
	* 翻页
	*/
	function gopage(page){
		$("#workcontent").load("/user/bookmarks?pageNum="+page);
	}
	
</script>