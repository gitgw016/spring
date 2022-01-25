<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
	<div style="background-color:#ccc; height:300px;">dummy</div>
	<div>
		<input type="text" id="name"/><br/>
		<input type="number" id="age"/><br/>
		<input type="button" id="GET" value="GET"/><br/>
		<input type="button" id="POST" value="POST"/><br/>
		<input type="button" id="PUT" value="PUT"/><br/>
		<input type="button" id="xmlTest" value="xml Test"/><br/>
		<input type="button" id="xmlJSON" value="xml JSON"/><br/>
	</div>
	<div id="result" style="border:1px solid black; padding:10px;">
	</div>
	<script>
		$(function(){
			console.log('준비 됐다 ㄱㄱ');
			
			$("#GET").click(function(){
				let name = $("#name").val();
				let age = $("#age").val();
				console.log("name : "+name+" , age : "+age);
				
				$.ajax({
					async : false,			// 비동기 여부
					type : "GET" ,			// method - 전송방식
					// url : '${pageContext.request.contextPath}/getSample' ,
					url : 'getSample' ,		// 요청 URL
					data : {				// 전달 parameter
						name : name,
						age : age
					},
					dataType : "json",		// responseText type
					success : function(data){
						// 요청 처리 결과 - 성공 시
						console.log(data);
					},
					error : function(res){
						// 요청 처리 결과 - 실패 시
						console.log(res);
					}
				});
				console.log("ajax 호출 다했다");
			});
			$("#POST").click(function(){
				var name = $("#name").val();
				var age = $("#age").val();
				
				$.ajax({
					type : "post",
					url : "getSample",
					data : {
						name : name,
						age : age
					},
					dataType : "json",
					success : function(data){
						// data - List<SampleVO>
						console.log(data);
						var html = "<table border=1>";
						html += "<tr>";
						html += "<th>이름</th>";
						html += "<th>나이</th>";
						html += "</tr>";
						for(var i=0; i<data.length; i++){
							html += "<tr>";
							html += "<td>"+data[i].name+"</td>";
							html += "<td>"+data[i].age+"</td>";
							html += "</tr>";
						}
						html += "</table>";
						$("#result").html(html);
					}
				});
			});
			
			$("#PUT").click(function(){
				$.ajax({
					type : "PUT",
					url : "testPUT",
					dataType : "json",
					headers : {
						'Content-Type' : 'application/json',
						"X-HTTP-Method-Override" : "PUT"
					},
					data : JSON.stringify({
						name : $("#name").val(),
						age : $("#age").val()
					}),
					success : function(data){
						console.log(data);
						var html = "<tr>";
						html += "<td>"+data.name+"</td>";
						html += "<td>"+data.age+"</td>";
						html += "</tr>";
						$("#result table").append(html);
					}
				});
			});
			
			$("#xmlTest").click(function(){
				var name = $("#name").val();
				var age = $("#age").val();
				
				$.ajax({
					type : "post",
					url : "xmlTest",
					dataType : "xml",
					data : {
						name : name,
						age : age
					},
					success : function(result){
						console.log(result);
						var name = $(result).find("name").text();
						var age = $(result).find("age").text();
						
						var html = "<tr>";
						html += "<td>"+name+"</td>";
						html += "<td>"+age+"</td>";
						html += "</tr>";
						$("#result table").append(html);
					}
				});
			});
			
			$("#xmlJSON").click(function(){
				var input_name = $("#name").val();
				var input_age = $("#age").val();
				$.ajax({
					type : "post",
					url : "xmlTest",
					dataType : "xml",
					headers : {
						'Content-Type' : "application/json"
					},
					data : JSON.stringify({name : input_name , age : input_age}),
					success : function(data){
						alert(data);
						console.log(data);
					}
				});
			});
		});
	</script>
</body>
</html>