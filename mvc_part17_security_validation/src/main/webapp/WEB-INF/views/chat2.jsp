<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<sec:authorize access="isAuthenticated()">
	<sec:authentication var="member" property="principal.member"/>
</sec:authorize>
<!-- javascript TCP 통신 client lib -->
<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<style>
	.chatBox{
		height:200px;
		overflow-y:scroll;
		border:1px solid #ccc;
		border-radius:5px;
	}
	
	.chatWrap{
		margin-top:3px;
		margin-bottom:3px;
	}
	
	.profileImage{
		width:30px;
		height:30px;
		border-radius:15px;
		border:1px solid black;
	}
	
</style>
<div class="container">
	<h1>CHAT PAGE - ${member.u_name}</h1>
	<div class="container chatBox" id="data">
	</div>
	<br/>
	<div class="row">
		<div class="col-md-10">
			<input type="text" class="form-control" id="message"/>
		</div>
		<div class="col-md-2">
			<input type="button" id="sendBtn" class="form-control btn btn-primary" value="SEND"/>
		</div>
	</div>
	<script>
	
		var sock = new SockJS("chatImageHandler");
		
		// 서버와 연결이 성사 되었을때
		sock.onopen = function(){
			console.log("연결 성공 ^^");
			sock.send("${member.u_profile},${member.u_name},가 왔다.");
		}
		
		// 서버와 연결이 끊겼을 때
		sock.onclose = onClose;
		
		// server에서 메세지가 전달되었을때 호출할 메소드
		sock.onmessage = function(message){
			console.log(message);
			var msg = message.data;
			var datas = msg.split(",");
			var userProfile = datas[0];
			var userName = datas[1];
			var userMessage = datas[2];
			var str = "<div class='chatWrap'>";
			if(userProfile != null && userProfile != ''){
				str += "<img src='${path}/upload"+userProfile+"' class='profileImage'/>";
			}else{
				str += "<img src='${path}/resources/img/profile.jpg' class='profileImage'/>";
			}
			str += "&nbsp;";
			str += userName + " : " + userMessage;
			str += "</div>";
			$("#data").append(str);
			$("#data").focus();
			$("#data").scrollTop($("#data").prop("scrollHeight"));
			$("#message").focus();
		}
		
		function onClose(){
			$("#data").append("연결 끊김 ㅠㅠ");
		}
		
		function sendMessage(){
			var userProfile = "${member.u_profile}";
			var userName = "${member.u_name}";
			var message = $("#message").val();
			$("#message").val('');
			if(message == null || message == '' || message.length == 0){
				alert('내용 쓰라');
				$("#message").focus();
				return;
			}
			var sendMessage = userProfile+","+userName+","+message;
			console.log(sendMessage);
			
			sock.send(sendMessage);
		}
		
		$("#sendBtn").click(function(){
			sendMessage();
		});
		
		$("#message").keydown(function(key){
			console.log(key.keyCode);
			if(key.keyCode == 13){
				sendMessage();
			}
		});
	</script>
</div>
</body>
</html>