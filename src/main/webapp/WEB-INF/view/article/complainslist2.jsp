<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
 <table class="table table-hover">
		  <thead>
		    <tr >
		      <th scope="col">Id</th>
		      <th scope="col">文章标题</th>
		      <th scope="col">投诉内容</th>
		      <th scope="col">投诉人</th>
		      <th scope="col">投诉人邮箱</th>
		      <th scope="col">投诉人电话</th>
		      <th scope="col">投诉时间</th>
		      <th scope="col">投诉图片</th>
		     
		    </tr>
		  </thead>
		  <tbody>
		    <c:forEach items="${complianPage2.list}" var="link">
        		<tr style="font-size: 12px;">
        			<td>${link.id}</td>
        			<td>${link.title}</td>
        			<td>${link.content}</td>
        			<td>${link.username}</td>
        			<td>${link.email}</td>
        			<td>${link.mobile}</td>
        			<td><fmt:formatDate value="${link.created}" pattern="yyyy年MM月dd日"/></td>
        			<td><img alt="" src="/pic/${link.picture}"  height="75px;" width="100px;"></td>
        		</tr>
        	</c:forEach>
		  </tbody>
		</table>
		<nav aria-label="Page navigation example">
		  <ul class="pagination justify-content-center">
		    <li class="page-item">
		      <a class="page-link" href="javascript:void()" tabindex="-1" aria-disabled="true" onclick="gopage(${complianPage2.prePage==0?1:complianPage2.prePage})"  >Previous</a>
		    </li>
		   	<c:forEach begin="1" end="${complianPage2.pages}" varStatus="i">
		   		<li class="page-item"><a class="page-link" href="javascript:void()" onclick="gopage(${i.index})">${i.index}</a></li>
		   	</c:forEach>
		    <li class="page-item">
		      <a class="page-link" href="javascript:void()" onclick="gopage(${complianPage2.nextPage==0?complianPage2.pages:complianPage2.nextPage})">Next</a>
		    </li>
		  </ul>
		</nav>
		
		 <script type="text/javascript">
		 function gopage(page){
				$("#workcontent").load("/article/complains2?page="+page);
			}
		 </script>