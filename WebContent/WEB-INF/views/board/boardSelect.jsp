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
		var curPage = 1;
		///////////
		$("updateBtn").click(function(){
			var contents = $("#updateContents").val();
			var cnum = $("#cnum").val();
			$.post("../comments/commentsUpdate",
					{
						contents: contents,
						cnum: cnum
					}, function(data){
						data = data.trim();
						if(data=="1"){
							alert("Success");
							$("#c"+cnum).html(contents);
						}else{
							alert("Fail");
						}
					});
		});
		///////////////
		$("#commentsList").on("click",".update",function(){
			var id =$(this).attr("title");
			var con = $("#c"+id).html();
			$("#updateContents").html(con);
			$("#cnum").val(id);
			
		});
		
		
		///////////////
		$("#more").click(function(){
			curPage++;
			getList(curPage);
		})
		
		
		$("#btn").click(function(){
			var writer = $('#writer').val();
			var contents = $('#contents').val();
			var num = '${dto.num}';
			//jq post
			$.post("../comments/commentsWrite",
			{
				writer : writer,
				contents : contents,
				num : num
			},
			function (data, status) {
				data = data.trim();
				if(data == "1"){
					alert("success");
					getList(1);
				}else{
					alert("fail");
				}
			});
			
		}); //댓글작성
		
		function getList(count) {
			$.get("../comments/commentsList?num=${dto.num}&curPage="+count,function(data){
				data = data.trim();
				if(count == 1){
					$("#commentsList").html(data);
				}else{
				$("#commentsList").append(data);
				}			
			});
		} //댓글 리스트
		

	$('#commentsList').on("click",".del",function() {
		var cnum = $(this).attr("id");
			var check = confirm("정말 삭제하시겠습니까?");
			if (check) {
				$.get("../comments/commentsDelete?cnum=" + cnum,
						function(data) {
							data = data.trim();
							if (data == '1') {
								alert("Delete Success");
								getList(1);
							} else {
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
					<td>${dto.num}</td>
					<td>${dto.title}</td>
					<td>${dto.writer}</td>
					<td>${dto.reg_date}</td>
					<td>${dto.hit}</td>
					<c:forEach items="${upload}" var="upload">
					<td><a href="../upload/${upload.fname}">${upload.oname}</a></td>
					</c:forEach>
				</tr>
				<tr>
					<td colspan="6">${dto.contents}
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
				<button id="more">More</button>
				</div>
			</div>
		</c:if>
		<div class="container">
			<!-- Modal -->
			<div class="modal fade" id="myModal" role="dialog">
				<div class="modal-dialog">

					<!-- Modal content-->
					<div class="modal-content">
						<div class="modal-header">
							<h4 class="modal-title">${member.id}</h4>
						</div>
						<div class="modal-body">
							<div class="form-group">
								<label for="contents">Contents:</label>
								<textarea class="form-control" rows="5" id="updateContents"
									name="contents"></textarea>
									<input type="hidden" id="cnum">
							</div>
						</div>
						<div class="modal-footer">
							<button class="btn btn-danger" id="updateBtn" data-dismiss="modal">Update</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Close</button>
						</div>
					</div>

				</div>
			</div><!-- 모달 끝  -->
		</div>
		<a href ="./${board}Update?num=${dto.num}" class="btn btn-primary">Update</a>
	</div>
</body>
</html>