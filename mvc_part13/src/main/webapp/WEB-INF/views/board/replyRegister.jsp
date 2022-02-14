<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2><a href="${pageContext.request.contextPath}">HOME</a></h2>
	<h3>REPLY REGISTER BOARD</h3>
	<form action="replyRegister" method="post">
		<table>
			<tr>
				<td>제목</td>
				<td><input type="text" name="title" required/></td>
			</tr>
			<tr>
				<td>작성자</td>
				<td>${sessionScope.userInfo.uname}</td>
			</tr>
			<tr>
				<td>내용</td>
				<td><textarea name="content" rows="30" cols="50"></textarea></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="등록"/></td>
			</tr>
		</table>
		<input type="hidden" name="uno" value="${userInfo.uno}"/>
		<input type="hidden" name="origin" value="${original.origin}"/>
		<input type="hidden" name="depth" value="${original.depth}"/>
		<input type="hidden" name="seq" value="${original.seq}"/>
		<input type="hidden" name="page" value="${cri.page}"/>
		<input type="hidden" name="perPageNum" value="${cri.perPageNum}"/>
		<input type="hidden" name="searchType" value="${cri.searchType}"/>
		<input type="hidden" name="keyword" value="${cri.keyword}"/>
	</form>
</body>
</html>