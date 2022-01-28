<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	#messageList{
		padding-left:0;
	}
	
	#messageList li{
		list-style:none;
		border:1px solid black;
		padding:10px;
		height:50px;
	}
	
	#messageList li:hover{
		cursor:pointer;
	}
</style>
</head>
<body>
	<h1>MESSAGE</h1>
	<hr/>
	<div id="messageDiv">
	<!--
		 targetid	// 수신 대상
		 sender		// 발신자
		 message	// 전달 내용
	  -->
	  <input type="text" placeholder="targetID" id="targetid"/><br/>
	  <input type="text" placeholder="sender" id="sender"/><br/>
	  <input type="text" placeholder="message" id="message"/><br/>
	  <button id="add">SEND MESSAGE</button>
	</div>
	<ul id="messageList">
	</ul>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
	// message 목록 호출
	getMessageList();
	
	function getMessageList(){
		$.getJSON("messages/list",function(result){
			console.log(result);
			var str = "";
			$(result).each(function(){
				var opendate = "";
				if(this.opendate > 0){
					opendate = getDate(this.opendate);
				}else{
					opendate = "미확인";
				}
				
				str += "<li onclick='readMessage("+this.mno+",\""+this.targetid+"\",this);'>";
				str += this.mno+" | "+this.targetid+" | ";
				str += this.sender+" | "+this.message+" | ";
				str += opendate+" | "+getDate(this.senddate);
				str += "</li>";
			});
			$("#messageList").html(str);
		});
	}
	// 수정할 메세지 번호, 포인트 증가시킬 사용자 아이디, 클릭된 li 요소
	function readMessage(mno, targetid, el){
		console.log(mno);
		console.log(targetid);
		console.log(el);
		
		$.ajax({
			type : "patch",
			url : "messages/read/"+mno,
			headers : {
				"Content-Type" : "application/json"
			},
			data : JSON.stringify({uid : targetid}),
			success : function(data){
				// alert(data);
				console.log(data);
				// data == MessageVO
				var str = data.mno+" | "+data.targetid+" | "+data.sender+" | ";
				str += data.message+" | "+getDate(data.opendate)+" | "+getDate(data.senddate);
				console.log(str);
				$(el).html(str);
			},
			error : function(res){
				alert(res.responseText);
			}
		});
	}
	
	function getDate(date){
		var dateTime = new Date(date);
		var dateStr = dateTime.getFullYear()+"년 ";
		dateStr += dateTime.getMonth()+1+"월 ";
		dateStr += dateTime.getDate()+"일 ";
		dateStr += dateTime.getHours()+"시 ";
		dateStr += dateTime.getMinutes()+"분";
		return dateStr;
	}

	// message 추가
	$("#add").click(function(){
		$.ajax({
			type : "post",
			url : "messages/add",
			dataType : "text",		// 결과를 받을 타입
			data : {
				targetid : $("#targetid").val(),
				sender : $("#sender").val(),
				message : $("#message").val()
			},
			success : function(data){
				alert(data);
				// message 목록 갱신
				getMessageList();
				$("#messageDiv input").each(function(){
					$(this).val("");
				});
				$("#targetid").focus();
			},
			error : function(res, status, error){
				console.log("code : "+res.status+" / message : "+res.responseText);
				alert(res.responseText);
			}
		})
	});
</script>
</body>
</html>