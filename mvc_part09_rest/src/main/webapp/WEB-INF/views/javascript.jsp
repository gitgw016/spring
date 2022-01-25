<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>Javascript 비동기 통신</h3>
	<input type="text" id="name"/><br/>
	<input type="number" id="age"/><br/>
	<button type="button" id="btn">ㄱㄱ~</button><br/>
	<div id="box"></div>
	<div id="result">
		<table border="1">
			<tr>
				<th>이름</th>
				<th>나이</th>
			</tr>
		</table>
	</div>
	<script>
		/* AJAX(Asynchronous JavaScript AND XML ) */
		/* javascript에서는 비동기로 서버와 통신을 하기위해 XMLHttpRequest - (구 ActiveRequest) */
		/* JSON - Javascript Object Notation (자바 스크립트 오브젝트 형태의 데이터 표기법) : 경량화 DATA 교환 */
		var httpRequest;
		document.getElementById("btn").onclick = function(){
			// alert("btn 간닷!");
			var name = document.getElementById("name").value;
			var age = document.getElementById("age").value;
			
			httpRequest = new XMLHttpRequest();
			if(!httpRequest){
				alert("XMLHttp 인스턴스를 가지고 오지 못함");
				return;
			}
			console.log("XMLHttp 인스턴스 생겼다");
			/*
			httpRequest.open('get','getSample?name='+name+"&age="+age);
			httpRequest.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
			httpRequest.onreadystatechange = setContents;
			httpRequest.send();
			*/
			
			/*
			httpRequest.open('post','getSample');
			httpRequest.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
			httpRequest.onreadystatechange = setContents;
			httpRequest.send("name="+name+"&age="+age);
			*/
			var sample = {
				name : name,
				age : age
			};
			httpRequest.open('put','testPUT');
			httpRequest.setRequestHeader("Content-Type","application/json");
			httpRequest.onreadystatechange = setContents;
			console.log(sample);
			httpRequest.send(JSON.stringify(sample));
			
		};
		
		function setContents(){
			/*
				httpRequest.onreadystate
				0 - request가 초기화 되지 않음
				1 - 서버와 연결이 성사
				2 - 서버가 request를 전달 받음
				3 - 서버가 request 처리중
				4 - request에 대한 처리가 완료되었고 응답할 준비가 되었음
			*/
			// 응답이 날라 올것이다
			if(httpRequest.readyState === 4){
				// 상태 코드
				if(httpRequest.status === 200){
					// httpRequest.responseText - 서버의 응답을 텍스트 문자로 전달
					// httpRequest.responseXML - 서버의 응답을 XMLDocument 형태로 전달
					var result = httpRequest.responseText;
					console.log("result : "+result);
					// JSON.parse(string) : 매개변수로 전달 받은 문자열을 JSON 타입의 객체로 변환한다.
					var obj = JSON.parse(result);
					console.log(obj);
					console.log(obj.name);
					console.log(obj.age);
					console.log("-----------------------------------")
					result = JSON.stringify(obj); // 객체를 문자열으로 (서버 전송을 위해)
					console.log(result);
					document.getElementById("box").innerHTML = result;
					
				}else{
					// 요청 처리 중 오류 발생
					alert("서버 응답 오류");
				}
			}
		}
	</script>
</body>
</html>