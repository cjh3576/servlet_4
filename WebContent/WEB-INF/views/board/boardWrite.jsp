<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Notice Write</title>
<c:import url="../temp/bootstrap.jsp" />
<script type="text/javascript" src="../se2/js/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/javascript">

	var oEditors = [];
	$(function() {
		nhn.husky.EZCreator.createInIFrame({
		    oAppRef: oEditors,
		    elPlaceHolder: "contents",
		    //SmartEditor2Skin.html 파일이 존재하는 경로
		    sSkinURI: "/servlet_4/se2/SmartEditor2Skin.html",  
		    htParams : {
		        // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
		        bUseToolbar : true,             
		        // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
		        bUseVerticalResizer : true,     
		        // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
		        bUseModeChanger : true,         
		        fOnBeforeUnload : function(){
		             
		        }
		    }, 
		    fOnAppLoad : function(){
		        //기존 저장된 내용의 text 내용을 에디터상에 뿌려주고자 할때 사용
		        oEditors.getById["contents"].exec("PASTE_HTML", [""]);
		    },
		    fCreator: "createSEditor2"
		}); //텍스트 폼 바꿈 스마트에디터
		
		//저장버튼 클릭시 form 전송
		$("#save").click(function(){
		    oEditors.getById["contents"].exec("UPDATE_CONTENTS_FIELD", []);
		    $("#frm").submit();
		});    
		
		 
		 
		var file = document.getElementById('file');
		var num = 0;
		var d1=0;
	$('#add').click(function() {
		d1++;
		if (num < 5) {
/* 		file.innerHTML = file.innerHTML + '<input type="file" class="form-control" id="f1" name = "f1">'; */
		$("#addfile").append('<div id="'+d1+'"><input type="file" class="form-control" id="" name="f'+d1+'"><span title="'+d1+'" class="del">X</span></div>')
		num++;
		} else{
			alert('5개 파일까지 가능합니다');
		}
	});
	
	$("#addfile").on("click",".del", function(){
		//$(this).prev().remove();
		//$(this).remove();
		var v = $(this).attr('title');
		$('#'+v).remove();
		num--;
	});
		
	});
</script>
<style type="text/css">
.del{
	color: red;
	cursor: pointer;
}
</style>
</head>
<body>
	<c:import url="../temp/header.jsp" />
	<div class="container">
		<form id = "frm" action="./${board}Write" method="post" enctype="multipart/form-data">
			<div class="form-group">
				<label for="title">Title:</label> <input type="text"
					class="form-control" id="title" name="title">
			</div>
			<div class="form-group">
				<label for="writer">Writer:</label> <input type="text"
					class="form-control" id="writer" name="writer">
			</div>
			<div class="form-group">
				<label for="contents">Contents:</label>
				<textarea class="form-control" rows="5" id="contents"
					name="contents"></textarea>
			</div>
			<div class="form-group" id="addfile">
				<label for="file">file:</label>
				
			</div>
			<div class="form-group">
				<input type="button" id="add" value="Add" class="btn btn-primary">
			</div>
			<input type="button" id = "save" class="btn btn-danger" value = "write">
		</form>


	</div>
</body>
</html>