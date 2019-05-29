<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%
/* 	int result = 0;
	result = Integer.parseInt(request.getParameter("cnum")); */
%>
<c:import url="../temp/bootstrap.jsp" />
<script type="text/javascript">
	$(function(){
		getList();
		$("#btn").click(function(){
			var writer = $('#writer').val();
			var contents = $('#contents').val();
			var num = '${boardDTO.num}';

			var xhttp;
			if(window.XMLHttpRequest){
				xhttp = new XMLHttpRequest();
			}else{
				xhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			//2. 요청정보 기록
			xhttp.open("POST","../comments/commentsWrite",true);
			xhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
			//3. 전송	
			xhttp.send("num="+num+"&writer="+writer+"&contents="+contents);
			//4. reponse 처리
			xhttp.onreadystatechange = function(){
				if(this.readyState == 4 && this.status == 200){
					var result = this.responseText.trim()
					if(result =='1'){
						alert("성공");
						location.reload();
					}else{
						alert("실패");
					}
				}
			}
		}); //댓글작성
		
		function getList() {
			var xhttp;
			if(window.XMLHttpRequest){
				xhttp = new XMLHttpRequest();
			}else{
				xhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			xhttp.open("GET","../comments/commentsList",true);
			xhttp.send();
			xhttp.onreadystatechange = function(){
				if(this.readyState == 4 && this.status == 200){
					var result = this.responseText.trim();
					$('#commentsList').html(this.responseText);
				}
			}
		} //댓글 리스트
		
		$('#commentsList').on("click",".del",function(){
			var cnum = $(this).attr("id");
			var check =confirm("정말 삭제하시겠습니까?");
			if(check){
				$.get("../comments/commentsDelete?cnum="+cnum,function(data){
				data = data.trim();
				if(data=='1'){
					alert("Delete Success");
					location.reload();
				}else{
					alert("Delete Fail");
				}
				
				});
			}
		});
			
		
		
	});
</script>
</head>
<body>
<c:import url="../temp/header.jsp" />
	<div class="container">
		<h1>${board} Select</h1>
		<table class="table table-hover">
			<thead>
				<tr>
					<th>NUM</th>
					<th>TITLE</th>
					<th>WRITER</th>
					<th>DATE</th>
					<th>HIT</th>
					<th>FILE</th>
				</tr>
			</thead>
				<tr>
					<td>${boardDTO.num}</td>
					<td>${boardDTO.title}</td>
					<td>${boardDTO.writer}</td>
					<td>${boardDTO.reg_date}</td>
					<td>${boardDTO.hit}</td>
					<c:forEach items="${upload}" var="upload">
					<td><a href="../upload/${upload.fname}">${upload.oname}</a></td>
					</c:forEach>
				</tr>
				<tr>
					<td colspan="6">${boardDTO.contents}
				</tr>
		</table>
		<c:if test="${board ne 'notice'}">
			<div class="container">
				<!-- 댓글 입력폼 -->
				<div class="row">
					<div class="form-group">
						<label for="writer">Writer:</label> <input type="text"
							class="form-control" id="writer" name="writer">
					</div>
					<div class="form-group">
						<label for="contents">Contents:</label>
						<textarea class="form-control" rows="5" id="contents"
							name="contents"></textarea>
					</div>
				<button class="btn btn-danger" id="btn">Write</button>
				</div>
				<!--댓글리스트  -->
				<div class="row">
				<table class="table table-bordered" id="commentsList">
				
				
				</table>
				</div>
			</div>
		</c:if>
		<a href ="./${board}Update?num=${boardDTO.num}" class="btn btn-primary">Update</a>
	</div>
</body>
</html>