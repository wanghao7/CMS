<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

	状态:<select  name="status" onchange="gb(this.value)" >
			<option value="0"  ${status==0?'selected':"" }>待审核</option>
			<option value="1"  ${status==1?'selected':"" }>通过</option>
			<option value="2"  ${status==2?'selected':"" }>拒绝</option>
		</select>
	<table class="table table-hover">
	  <tr>
	    <th>id</th>
	    <th>标题</th>
	    <th>栏目</th>
	    <th>分类</th>
	    <th>发布时间</th>
	    <th>状态</th>
	    <th>投诉数</th>
	    <th>是否热门</th>
	    <th>操作</th>
	  </tr>
	  <c:forEach items="${articlePage.list }" var="article" >
	  <tr>
	    <td>${article.id }</td>
	    <td>${article.title }</td>
	    <td>${article.channel.name }</td>
	    <td>${article.category.name }</td>
	    <td><fmt:formatDate value="${article.created }" pattern="yyyy-MM-dd" /> </td>
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
		<td>${article.complainCnt}</td>
		<td>${article.hot==0?"非热门":"热门" }</td>
		<td width="200px">
			<input type="button" value="删除"  class="btn btn-danger" onclick="del(${article.id})">
			<input type="button" value="审核"  class="btn btn-warning" onclick="check(${article.id})" >
			<input type="button" value="投诉管理"  class="btn btn-warning" onclick="complainList(${article.id})" >
		</td>
	  </tr>
	  </c:forEach>
	</table>
<!-- </form> -->
<!-- 	分页 -->
		<nav aria-label="Page navigation example">
		  <ul class="pagination justify-content-center">
		    <li class="page-item ">
		      <a class="page-link" href="#"  onclick="gopage('1')" >Previous</a>
		    </li>
		   	<c:forEach begin="1" end="${articlePage.pages}" varStatus="i">
		   		<li class="page-item"><a class="page-link" href="javascript:void()" onclick="gopage(${i.index})">${i.index}</a></li>
		   	</c:forEach>
		    
		   
		    <li class="page-item">
		      <a class="page-link" href="#" onclick="gopage('${articlePage.pages}')">Next</a>
		    </li>
		  </ul>
		</nav>
<!-- 投诉 -->
<div class="modal fade"   id="complainModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
  <div class="modal-dialog" role="document" style="margin-left:100px;">
    <div class="modal-content" style="width:1200px;" >
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle">文章审核</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body" id="complainListDiv">
         
         		
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary" onclick="setStatus(1)">审核通过</button>
        <button type="button" class="btn btn-primary" onclick="setStatus(2)">审核拒绝</button>
       
      </div>
    </div>
  </div>
</div>


<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
	        <div class="row" id="divTitle"></div>
	       	<div class="row" id="divOptions" ></div>
	       	<div class="row" id="divContent"></div>	
      </div>
      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
	        <button type="button" class="btn btn-primary" onclick="setStatus(1)" >审核通过</button>
	        <button type="button" class="btn btn-primary" onclick="setStatus(2)" >审核拒绝</button>
	        <button type="button" class="btn btn-primary" onclick="setHot(1)" >设置热门</button>
	        <button type="button" class="btn btn-primary" onclick="setHot(0)" >取消热门</button>
      </div>
    </div>
  </div>
</div>


<script>

	$("#exampleModal").on('hidden.bs.modal', function (e) {
		refreshPage();
	})

	function gb(status){
		$("#workcontent").load("/admin/article?page=" + '${articlePage.pageNum}' + "&status="+status);
	}
	//全局变量global_article_id
	var global_article_id;
	function check(id){
		//返回对象
		$.post("/article/getDetail",{id:id},function(obj){
			if(obj.code==1){
				$("#exampleModalLabel").html(obj.data.title);
				$("#divOptions").html("栏目:"+obj.data.channel.name+
						"   分类:"+obj.data.category.name+"   作者:"+obj.data.user.username);
				
				$("#exampleModal").modal('show');
				$("#divContent").html(obj.data.content);
				//全局文章变量没写
				global_article_id=obj.data.id;
				return;
			}
			alert(obj.error);
		},"json")
	}
	// status 0 待审核  1通过 2 拒绝
	function setStatus(status){
		var id=global_article_id;
		$.post("/admin/setArticleStatus",{id:id,status:status},function(obj){
			if(obj.code==1){
				alert("操作成功");
				$("#exampleModal").modal('hide');
				//refreshPage();
				return;
			}
			alert(obj.error);
		},"json")
	}
	/**
	* 查看文章的投诉
	*/
	function complainList(id){
		$("#complainModal").modal('show')
		$("#complainListDiv").load("/article/complains?articleId="+id);
		
	}
	/**
	 0 非热门
	 1 热门
	*/
	function setHot(status){
		// 文章id
		var id = global_article_id;
		$.post("/admin/setArticeHot",{id:id,status:status},function(msg){
			if(msg.code==1){
				alert('操作成功')
				//隐藏当前的模态框
				$('#exampleModal').modal('hide')
				//刷新当前的页面
				//refreshPage();
				return;
			}
			alert(msg.error);
		},
		"json")
	}
	/**
	* 翻页
	*/
	function gopage(page){
		$("#workcontent").load("/admin/article?page="+page + "&status="+'${status}');
	}
	
	function refreshPage(){
		$("#workcontent").load("/admin/article?page=" + '${articlePage.pageNum}' + "&status="+'${status}');
	}

</script>