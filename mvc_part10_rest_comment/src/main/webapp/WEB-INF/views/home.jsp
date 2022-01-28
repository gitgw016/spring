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
	
	#pagination {
		margin-bottom : 30px;
	}
	
	#pagination li{
		list-style:none;
		float:left;
		padding:3px;
		border:1px solid skyblue;
		margin:3px;
	}
	
	#pagination li a{
		text-decoration:none;
		color:black;
	}

</style>
</head>
<body>
<a href="test">jackson databind test</a>
<br/>
<hr/>
<div id="modDiv">
	<div id="modCno"></div>
	<div>
		<input type="text" id="modText" />
	</div>
	<div>
		<input type="text" id="modAuth" />
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
	<br/>
	<br/>
	<br/>
</div>
<script>
	// 초기값은 1번 게시물
	var bno = 1;
	// 초기값은 첫번째 페이지
	var page = 1;
	
//	getCommentList();
	listPage(page);
	// 페이징 처리된 리스트
	function listPage(page){
		$("body").prepend($("#modDiv"));
		console.log(page);
		var url = "comments/"+bno+"/"+page;
		// $.ajax(type : url,dataType : 'json',success : function(data){})
		$.getJSON(url,function(data){
			console.log(data);
			console.log(data.list);
			console.log(data.pm);
			printList(data.list);
			// printPage(data.pm);
		});
	}
	
	$("#pagination").on("click","li a",function(event){
		event.preventDefault();	// 기본 이벤트 무시
		var commentPage = $(this).attr("href");
		page = commentPage;
		listPage(page);
	});
	
	function printPage(pm){
		var str = "";
		// 이전
		if(pm.prev){
			str += "<li><a href='"+(pm.startPage-1)+"'> << </a></li>";
		}
		// 페이지 블럭
		for(var i = pm.startPage; i <= pm.endPage; i++){
			str += "<li><a href='"+i+"'> "+i+" </a></li>";
		}
		// 다음
		if(pm.next){
			str += "<li><a href='"+(pm.endPage+1)+"'> >> </a></li>";
		}
		$("#pagination").html(str);
	}

	// bno 게시물의 전체 댓글 목록
	function getCommentList(){
		$("body").prepend($("#modDiv"));
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
		//$("#comments").html(str);
		$("#comments").append(str);
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
			//getCommentList();
			page = 1;
			listPage(page);
		}).fail(function(res,exception){
			alert(res);
			alert(res.status);
			alert(exception);
		}).always(function(res){
			console.log(res);
		});
	});
	
	// 수정 버튼 클릭 시
//	$("#comments li button").on("click",function(){
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
				// getCommentList();
				listPage(page);
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
//				getCommentList();
				listPage(page);
			}
		});
	});
	
	// 윈도우(창)의 스크롤에 따라 전체 문서의 크기와 스크롤 위치를 가지고
	// 스크롤이 하단으로 이동 했을 시 리스트 추가
	// window의 스크롤 이벤트 감지
	$(window).scroll(function(){
		var dh = $(document).height();
		var wh = $(window).height();
		var st = $(window).scrollTop();
		
		console.log("dh : " + dh);
		console.log("wh : " + wh);
		console.log("st : " + st);
		
		if((wh + st) >= (dh - 10)){
			page++;
			listPage(page);
		}
	});
	
	
	
</script>
</body>
</html>
