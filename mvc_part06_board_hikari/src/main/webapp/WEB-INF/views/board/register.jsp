<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>REGISTER BOARD PAGE</h3>
	<form action="register" method="POST">
		<div>
			<label>Title</label>
			<input type="text" name="title" required placeholder="TITLE"/>
		</div>
		<div>
			<label>작성자</label>
			<input type="text" name="writer" required placeholder="Writer"/>
		</div>
		<div>
			<label>Content</label>
			<textarea name="content" required placeholder="CONTENT" rows=5></textarea>
		</div>
		<div>
			<input type="submit" value="작성완료"/>
		</div>
	</form>
</body>
</html>