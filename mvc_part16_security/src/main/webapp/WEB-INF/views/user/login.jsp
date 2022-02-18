<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>LOGIN PAGE</h1>
	<form method="post" action="${pageContext.request.contextPath}/login">
		<input type="text" name="username"/><br/>
		<input type="password" name="password"/><br/>
		<div>
			<label>
				<input type="checkbox" name="userCookie"/> Remember Me
			</label>
		</div>
		<input type="submit" value="로그인"/>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	</form>
</body>
</html>