<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

	<table class="table table-hover">
	  <thead>
          <tr>
            <th>id</th>
            <th>标题</th>
            <th>栏目</th>
            <th>分类</th>
            <th>发布时间</th>
            <th>状态</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
        	<c:forEach items="${pg.list}" var="article">
        		<tr>
        			<td>${article.id}</td>
        			<td>${article.title}</td>
        			<td>${article.channel.name}</td>
        			<td>${article.category.name}</td>
        			<td><fmt:formatDate value="${article.created}" pattern="yyyy年MM月dd日"/></td>
        			<td>
        				<c:choose>
        					<c:when test="${article.status==0}"> 待审核</c:when>
        					<c:when test="${article.status==1}"> 审核通过</c:when>
        					<c:when test="${article.status==2}"> 审核被拒</c:when>
        					<c:otherwise>
        						未知
        					</c:otherwise>
        				</c:choose>
        			</td>
        			<td width="200px">
        				<input type="button" value="删除"  class="btn btn-danger" onclick="del(${article.id})">
        				<input type="button" value="修改"  class="btn btn-warning" onclick="update(${article.id})" >
        			</td>
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
//修改 
	function update(id){
	//内部追加到home页面里的div  使用load加载的方式 
		$("#workcontent").load("/user/updateArtice?id="+id);
	}
	function del(id){
		if(!confirm("您确认删除么？")){
			return;
		}
		
		$.post('/user/deletearticle',{id:id},
				function(data){
					if(data==true){
						alert("刪除成功")
						//跳转到点击我的文章时的访问路径
						$("#workcontent").load("/user/articles");
					}else{
						alert("刪除失败")
					}
					
		},"json")
	}
	
	
	/**
	* 翻页
	*/
	function gopage(page){
		$("#workcontent").load("/user/articles?pageNum="+page);
	}
	
</script>