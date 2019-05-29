<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	<c:forEach items="${commentsList}" var="dto">
		<tr>
			<td>${dto.writer}</td>
			<td>${dto.contents}</td>
			<td>${dto.reg_date}</td>
			<c:if test="${'get' eq dto.writer}">
			<td><button data-toggle="modal" data-target="#myModal">Update</button><button id="${dto.cnum}" class="del">Delete</button></td>
			</c:if>
		</tr>
		
	</c:forEach>
	