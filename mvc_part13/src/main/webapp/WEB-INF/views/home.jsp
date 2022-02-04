<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div>
		<h3>MAIN PAGE</h3>
		<c:choose>
			<c:when test="${!empty sessionScope.userInfo}">
				<span>${userInfo.uname}</span>
				<a href="user/signOut">signOut</a>
			</c:when>
			<c:otherwise>
				<a href="user/signIn">SignIN</a><br/>
				<a href="user/signUp">SignUP</a>
			</c:otherwise>
		</c:choose>
	</div>
	<script>
		var msg = '${invalidate}';
		if(msg != ''){
			alert(msg);
			location.href='${pageContext.request.contextPath}/user/signOut';
		}
	</script>
</body>
</html>