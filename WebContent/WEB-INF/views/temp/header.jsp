<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="${pageContext.request.contextPath }/index.do">WebSiteName</a>
    </div>
    <ul class="nav navbar-nav">
      <li class="active"><a href="${pageContext.request.contextPath }/notice/noticeList">Notice</a></li>
      <li class="active"><a href="${pageContext.request.contextPath }/qna/qnaList">QnA </a></li>
      <li><a href="#">Page 2</a></li>
    </ul>
    <ul class="nav navbar-nav navbar-right">
    <c:choose>
    	<c:when test="${not empty memberdto}">
    	    <li><a href="${pageContext.request.contextPath }/member/memberCheck"><span class="glyphicon glyphicon-user"></span> My Page</a></li>
      		<li><a href="${pageContext.request.contextPath }/member/memberLogout"><span class="glyphicon glyphicon-log-in"></span>  LogOut</a></li>
    	</c:when>
    	<c:when test="${empty memberdto}">
     		<li><a href="${pageContext.request.contextPath }/member/memberCheck"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
     		<li><a href="${pageContext.request.contextPath }/member/memberLogin"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
    	</c:when>
    	<c:otherwise>
    	</c:otherwise>
    </c:choose>
    </ul>
  </div>
</nav>

<div class="container">
  <div class="jumbotron">
    <h1>Bootstrap Tutorial</h1> 
    <p>Bootstrap is the most popular HTML, CSS, and JS framework for developing
    responsive, mobile-first projects on the web.</p> 
  </div>
</div>