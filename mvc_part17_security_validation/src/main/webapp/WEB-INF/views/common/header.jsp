<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="path" value="${pageContext.request.contextPath}" scope="session"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<%-- <link href="${path}/resources/bootstrap/css/bootstrap.css" rel="stylesheet"/> --%>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<%-- <script src="${path}/resources/bootstrap/js/bootstrap.js"></script> --%>
<script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.3/dist/jquery.validate.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<%-- <script src="${path}/resources/js/jquery.validate.js"></script> --%>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" type="text/css" />  
<script src="http://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
<style>
	.profile_img{
		width:25px;
		height:25px;
		border-radius:12.5px;
	}
</style>
</head>
<body>
	<header class="container">
		<nav class="navbar navbar-default" role="navigation">
			<div class="navbar-hedaer">
				<button type="button" class="navbar-toggle" 
						data-toggle="collapse" 
						data-target=".navbar-ex1-collapse">
					<span class="sr-only">Toggle Navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="/">
					<span class="glyphicon glyphicon-leaf"></span>
				</a>
			</div>
			<div class="collapse navbar-collapse navbar-right navbar-ex1-collapse">
				<ul class="nav navbar-nav">
				<!-- 인가 완료된 사용자 -->
				<sec:authorize access="isAuthenticated()">
					<sec:authentication var="member" property="principal.member"/>
					<li>
					<a href="#">
						<c:choose>
							<c:when test="${!empty member.u_profile}">
								<img class="profile_img" src="${path}/upload${member.u_profile}"/>
							</c:when>
							<c:otherwise>
								<img class="profile_img" src="${path}/resources/img/profile.jpg"/>
							</c:otherwise>
						</c:choose>
						${member.u_name}
					</a>
					</li>
					<sec:authorize access="hasAnyRole('ROLE_MEMBERSHIP','ROLE_MASTER')">
					<li><a href="${path}/mngt/main">MANAGEMENT</a></li>
					</sec:authorize>
					<li><a href="${path}/chat">CHAT</a></li>
					<li><a href="${path}/chat2">CHAT2</a></li>
					<li><a href="${path}/user/logout">로그아웃</a></li>
				</sec:authorize>
				<!-- 익명 사용자 -->
				<sec:authorize access="isAnonymous()">
					<li><a href="${path}/user/login">로그인</a></li>
					<li><a href="${path}/user/join">회원가입(validation)</a></li>
				</sec:authorize>
					
				</ul>			
			</div>
		</nav>	
	</header>	