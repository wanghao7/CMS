<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
		<table class="table  table-hover">
		  <thead class="thead-light">
		    <tr>
		      <th scope="col">编号</th>
		      <th scope="col">地址</th>
		      <th scope="col">名称</th>
		      <th scope="col">创建时间</th>
		      <th scope="col">操作</th>
		    </tr>
		  </thead>
		  <tbody>
			  <c:forEach var="link" items="${pageInfo.list }">
				    <tr>
				      <th scope="row">${link.id }</th>
				      <td>${link.url }</td>
				      <td>${link.name }</td>
				      <td>${link.created }</td>
				      <td>
				      	<input class="btn btn-outline-danger" onclick="update(${link.id })" type="button" value="修改" >
				      	<input class="btn btn-outline-danger" type="button" value="删除" >
				      </td>
				    </tr>
				</c:forEach>
		    	<tr>
				      <th>
				      	<button class="btn btn-outline-dark" onclick="toPage(this.value)" name="page" value="${pageInfo.prePage==0?"1":pageInfo.prePage }" >上一页</button>
				      	<button class="btn btn-outline-dark" onclick="toPage(this.value)" name="page" value="${pageInfo.nextPage==0?"pageInfo.pages":pageInfo.nextPage }" >下一页</button>
				      </th>
			      </tr>
		  </tbody>
		</table>
</div>


<script>
	function toPage(value){
// 		alert(value);
		$("#workcontent").load('/admin/link?page='+value);
	}
</script>
