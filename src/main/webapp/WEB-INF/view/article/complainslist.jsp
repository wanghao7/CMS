<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<c:forEach items="${complianPage.list}" var="complain">
	<div class="row">
		<div class="col-md-3">${complain.user.username}</div>
		<div class="col-md-3">${complain.content}</div>
		<div class="col-md-3">
			<c:if test="${complain.compainOption.indexOf('1')!=-1 }">标题夸张</c:if>
			<c:if test="${complain.compainOption.indexOf('2')!=-1}">与事实不符</c:if> 
			<c:if test="${complain.compainOption.indexOf('3')!=-1}">疑似抄袭</c:if> 
		</div>
		<div><fmt:formatDate value="${complain.created}" pattern="yyyy年MM月dd日"/></div>
		<div><img alt="" src="/pic/${complain.picture}"  height="75px;" width="100px;"></div>
	</div>

</c:forEach> 