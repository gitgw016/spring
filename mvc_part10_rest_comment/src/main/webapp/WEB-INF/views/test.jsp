<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>jackson databind - test</h3>					<!-- 기본 -->
	<form action="test" method="post" enctype="application/x-www-form-urlencoded"> 
		<input type="text" name="test" value='{"cno" : 1, "bno" : 1, "commentText" : "mingu go home","commentAuth":"bmg"}'/>
		<input type="submit" value="확인"/>
	</form>
	<br/><hr/>
	<button id="btn">JSON TEST</button>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
$("#btn").click(function(){
	$.ajax({
		type : "post",
		url : "test",
		headers : {
			"Content-Type" : "application/json"
		},
		data : JSON.stringify({"cno" : 1, "bno" : 1, "commentText" : "mingu go home","commentAuth":"bmg"}),
		dataType : "text"
	}).done(function(result){
		alert(result);
	});
});
</script>
</body>
</html>