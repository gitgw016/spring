<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>READ BOARD PAGE</h3>
	<form action="modifyPage" method="post">
		<div>
			<label>Title</label>
			<input type="text" name="title" required value="${boardVO.title}" />
		</div>
		<div>
			<label>작성자</label>
			<input type="text" name="writer" required readonly value="${boardVO.writer}" />
		</div>
		<div>
			<label>Content</label>
			<textarea name="content" required rows=5>${boardVO.content}</textarea>
		</div>
		<div>
			<input type="submit" value="MODIFY"/>
			<input type="button" value="LIST"/>
		</div>
		<input type="hidden" name="bno" value="${boardVO.bno}"/>
		<input type="hidden" name="page" value="${cri.page}"/>
		<input type="hidden" name="perPageNum" value="${cri.perPageNum}"/>
	</form>
</body>
</html>