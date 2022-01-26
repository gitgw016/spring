<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>COMMENT TEST</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<style>
	#comments li{
		height:100px;
		list-style:none;
		padding:10px;
		border:1px solid #ccc;
		margin:5px 0;
	}
	
	#modDiv{
		display:none;
		border:1px solid black;
		padding:10px;
	}	

</style>
</head>
<body>
<div id="modDiv">
	<div id="modCno"></div>
	<div>
		<input type="text" id="modAuth" />
	</div>
		<div>
		<input type="text" id="modText" />
	</div>
	<div>
		<button id="modBtn">MODIFY</button>
		<button id="delBtn">DELETE</button>
		<button id="closeBtn">CLOSE</button>
	</div>
</div>
	<h2>AJAX - REST TEST PAGE</h2>
<div>
	<div>
		comment auth : <input type="text" id="cAuth" placeholder="작성자 이름 작성" />
	</div>
	<div>
		comment content : <input type="text" id="cText" placeholder="댓글 내용 작성" />
	</div>
	<button id="addBtn">ADD COMMENT</button>
</div>
<div>
	<!-- 댓글 리스트 -->
	<ul id="comments"></ul>
	<!-- 댓글 페이징 처리 -->
	<ul id="pagination"></ul>
</div>
<script>
	// 초기값은 1번 게시물
	var bno = 1;
	// 초기값은 첫번째 페이지
	var page = 1;
	
	getCommentList();
	
	// bno 게시물의 전체 댓글 목록
	function getCommentList(){
		$("body").prepend($("#modDiv"));
		$("#modDiv").css("display","none");
		// type : GET , dataType : "json"
		//$.getJSON(url,callback함수);
		var url = "comments/all/"+bno;
		$.getJSON(url,function(data){
			console.log(data);
			printList(data);
		});
	}
	
	/* 댓글 목록 출력 */
	function printList(list){
		var str = "";
		// 배열을 순회하면서 저정된 객체를 반환
		// 실행 함수에서는 반환된 객체를 this keyword로 접근
		$(list).each(function(){
			// commentVO == this
			let cno = this.cno;
			let cText = this.commentText;
			let cAuth = this.commentAuth;
			console.log(cno+":"+cText+":"+cAuth);
			str +="<li>";
			if(this.commentDelete == 'N'){
				str+= cno+"-"+cAuth+"<br/><hr/>"+cText;
				str+="<br/><br/>";
				str+="<button data-cno='"+cno+"' data-text='"+cText+"' data-auth='"+cAuth+"'>MODIFY</button>";
			}else{
				str += "<h3>삭제된 댓글 입니다.</h3>";
			}
			str +="</li>";
		});
		$("#comments").html(str);
	}
	
	/* 댓글 삽입 요청 */
	$("#addBtn").click(function(){
		var auth = $("#cAuth").val();
		var text = $("#cText").val();
		
		$.ajax({
			type : 'POST',
			url : 'comments',
			headers : {
				"Content-Type" : "application/json"
			},
			dataType : "text",
			data : JSON.stringify({
				bno : bno,
				commentAuth : auth,
				commentText : text
			})
		}).done(function(data){
			alert(data);
			getCommentList();
		}).fail(function(res,exception){
			alert(res);
			alert(res.status);
			alert(exception);
		}).always(function(res){
			console.log(res);
		});
	});
	
	// 수정 버튼 클릭 시
//	$("#comments li button").on("click",function(){ -> 안됨
	$("#comments").on("click","li button",function(){
		$("#modDiv").css("display","none");
		var cno = $(this).attr("data-cno");
		var text = $(this).attr("data-text");
		var auth = $(this).attr("data-auth");
		$("#modCno").text(cno);
		$("#modText").val(text);
		$("#modAuth").val(auth);
		
//		alert("click");
		$(this).parent().after($("#modDiv"));
		$("#modDiv").slideDown("slow");
	});
	
	// 수정창 close
	$("#closeBtn").click(function(){
		$("#modDiv").slideUp("slow");		
	});
	
	// 댓글 수정 요청
	$("#modBtn").click(function(){
		var text = $("#modText").val();
		var auth = $("#modAuth").val();
		var cno = $("#modCno").html();
		
		$.ajax({
			type : "PATCH",
			url : "comments/"+cno,
			headers : {
				"Content-Type" : "application/json"
			},
			data : JSON.stringify({
				commentText : text,
				commentAuth : auth
			}),
			dataType : "text",
			success : function(data){
				alert(data);
				$("#modDiv").slideUp("slow");
				$("#modText").val("");
				$("#modAuth").val("");
				$("#modCon").text("");
				getCommentList();
			}
		});
	});
	
	// 댓글 삭제 요청
	$("#delBtn").click(function(){
		var cno = $("#modCno").text();
		$.ajax({
			type : "DELETE",
			url : "comments/"+cno,
			dataType : "text",
			success : function(data){
				alert(data);
				getCommentList();
			}
		});
	});
	
	
</script>
</body>
</html>








