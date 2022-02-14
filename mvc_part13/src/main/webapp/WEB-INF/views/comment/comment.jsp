<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
	.comments{
		list-style:none;
	}

	#modDiv{
		display:none;
	}
	
	#comments li span:hover{
		cursor:pointer;
		color:skyblue;
	}
</style>
<div>
	<!-- 로그인 된 사용자 -->
	<c:if test="${!empty userInfo}">
		<table border=1>
			<tr>
				<th colspan="2">ADD NEW COMMENT</th>
			</tr>
			<tr>
				<td>COMMENT AUTH</td>
				<td>${userInfo.uname}</td>
			</tr>
			<tr>
				<td>COMMENT TEXT</td>
				<td>
					<input type="text" id="commentText" placeholder="COMMENT TEXT"/>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="button" id="commentAddBtn" value="ADD BUTTON"/>
				</td>
			</tr>
		</table>
	</c:if>
</div>
<br/><hr/><br/>
<div id="modDiv">
	<h3>댓글 수정 &amp;삭제</h3>
	<!-- 댓글 번호 -->
	<div class="mod-title">
	</div>
	<!-- 댓글 내용 -->
	<div>
		<input type="text" id="modCommentText"/>
	</div>
	<div>
		<input type="button" id="commentModBtn" value="MODIFY"/>
		<input type="button" id="commentDelBtn" value="DELETE"/>
		<input type="button" id="closeBtn" value="CLOSE"/>
	</div>
</div>
<div>
	<ul id="comments" class="comments">
	</ul>
</div>
<script>
	
	var bno = '${board.bno}';
	var commentPage = 1;
	
	getListPage(commentPage);
	
	function getListPage(page){
		commentPage = page;
		$.getJSON('${path}/comments/'+bno+'/'+page, function(data){
			// data = Map<String,Object>
			// data.list = List<CommentVO>
			// data.pm = PageMaker
			console.log(data);
			var str = "";
			$(data.list).each(function(){
				// this = CommentVO
				str += "<li data-cno='"+this.cno+"' data-text='"+this.commentText+"' class='commentLi'>";
				str += "작성자 : "+this.commentAuth+" - 작성시간 : "+getDate(this.updatedate);
				str +="<br/>내용 : "+this.commentText;
				// 로그인한 사용자가 댓글 작성자인 경우에만 노출
				if(isCheckUser(this.uno)){
				str +=" - <button>MODIFY</button>";
				}
				str += "</li>";
				str += "<li>--------------------------------------------------</li>";
			});
			$("#comments").html(str);
			// data.pm
			var pageBlock = "<li>";
			for(var i=data.pm.startPage; i<=data.pm.endPage; i++){
				pageBlock +="<span onclick='getListPage("+i+");'>["+i+"]</span>";
			}
			pageBlock += "</li>";
			
			$("#comments").append(pageBlock);
		});
	}
	// 댓글 작성자 번호 매개변수로 넘겨받아 현재 로그인한 사용자 정보와 일치하는지 확인
	function isCheckUser(uno){
		let userUno = '${userInfo.uno}';
		if(userUno != "" && userUno == uno){
			return true;
		}
		return false;
	}
	// long type의 시간 정보를 넘겨받아 문자열 형태로 반환
	function getDate(timeValue){
		var dateObj = new Date(timeValue);
		var year = dateObj.getFullYear();
		var month = dateObj.getMonth()+1;
		var date = dateObj.getDate();
		var hour = dateObj.getHours();
		var minute = dateObj.getMinutes();
		var seconds = dateObj.getSeconds();
		return year+"."+month+"."+date+" "+hour+":"+minute+":"+seconds;
	}
	
	$("#comments").on("click",".commentLi button",function(){
		var commentLi = $(this).parent();
		var cno = commentLi.attr("data-cno");
		var text = commentLi.attr("data-text");
		console.log(commentLi);
		$(".mod-title").html(cno);
		$("#modCommentText").val(text);
		$("#modDiv").toggle("slow");
		$("#modCommentText").focus();
	});
	
	$("#closeBtn").click(function(){
		$(".mod-title").html("");
		$("#modCommentText").val("");
		$("#modDiv").toggle("slow");
	});
	
	// 댓글 작성
	$("#commentAddBtn").click(function(){
		// 댓글 등록 요청 전달
		// bno, commentText, uno
		var commentText = $("#commentText").val();
		var uno = "${userInfo.uno}";
		
		$.ajax({
			type : "post",
			// /common/comments/add
			url : '${path}/comments/add',
			headers : {
				"Content-Type" : "application/json",
				"X-HTTP-Method-Override" : "post"
			},
			data : JSON.stringify({
				bno : bno,
				commentText : commentText,
				uno : uno
			}),
			dataType : "text",
			success : function(result){
				alert(result);
				$("#commentText").val("");
				$("#commentText").focus();
				commentPage = 1;
				getListPage(commentPage);
			},
			error : function(res){
				alert(res.responseText);
			}
		});
	});
	
	// 댓글 수정
	$("#commentModBtn").click(function(){
		var cno = $(".mod-title").html();
		var text = $("#modCommentText").val();
		
		$.ajax({
			type : "patch",
			url : "${path}/comments/mod",
			headers : {
				"Content-Type" : "application/json",
				"X-HTTP-Method-Override" : "patch"
			},
			data : JSON.stringify({
				commentText : text,
				cno : cno
			}),
			dataType : "text",
			success : function(result){
				alert(result);
				$("#modDiv").hide("slow");
				getListPage(commentPage);
			},
			error : function(res){
				alert(res.responseText);
			}
		});
	});
	
	// 댓글 삭제 요청 처리
	$("#commentDelBtn").click(function(){
		var isConfirm = confirm("댓글 지울거임?");
		if(isConfirm){
			var cno = $(".mod-title").html();
			$.ajax({
				type : "delete",
				url : "${path}/comments/remove/"+cno,
				dataType : "text",
				success : function(result){
					alert(result);
					$("#modDiv").hide("slow");
					getListPage(commentPage);
				},
				error : function(res){
					alert(res.responseText);
				}
			});
		}
	});
</script>