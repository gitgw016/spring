<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="wrap">
		<div>
			<h1><a href="${path}">MAIN</a></h1>
		</div>
		<article>
			<h3>SIGN UP</h3>
			<form action="signUpPost" method="post">
				<table>
					<tr>
						<td>아이디</td>
						<td><input type="text" name="uid" placeholder="ID" alt="아이디" required/></td>
					</tr>
					<tr>
						<td>비밀번호</td>
						<td><input type="password" name="upw" placeholder="PASSWORD" alt="비밀번호" required/></td>
					</tr>
					<tr>
						<td>비밀번호 확인</td>
						<td><input type="password" name="repw" placeholder="PASSWORD!!" alt="비밀번호 확인" required/></td>
					</tr>
					<tr>
						<td>사용자 이름</td>
						<td><input type="text" name="uname" placeholder="NAME" alt="사용자 이름" required/></td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="submit" value="SIGN UP"/>
							<input type="button" onclick="location.href='${path}/user/signIn';" value="SIGN IN"/>
						</td>
					</tr>
				</table>
			</form>
		</article>
	</div>
<script>
	var msg = '${message}';
	if(msg != ''){
		alert(msg);
	}
</script>
</body>
</html>