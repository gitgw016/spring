<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.uploadList{
		width:100%;
	}
	.uploadList li{
		text-align:center;
		float:left;
		padding:20px;
		list-style:none;
	}
</style>
</head>
<body>
	<h1><a href="${pageContext.request.contextPath}">HOME</a></h1>
	<h3>READ PAGE</h3>
	<table border=1>
		<tr>
			<td>제목</td>
			<td>${board.title}</td>
		</tr>
		<tr>
			<td>작성자</td>
			<td>${board.writer}</td>
		</tr>
		<tr>
			<td>내용</td>
			<td>${board.content}</td>
		</tr>
	</table>
	<hr/>
	<div>
		<c:set var="path" value="${pageContext.request.contextPath}"/>
		<ul class="uploadList">
			<c:forEach var="file" items="${board.files}">
				<li data-src='${file}'>
					<c:choose>
						<c:when test="${fn:contains(file,'s_')}">
							<img src="${path}/displayFile?fileName=${file}"/>
							<div>
								<a target="_blank" href="${path}/displayFile?fileName=${fn:replace(file,'s_','')}">
									${fn:substringAfter(fn:substringAfter(file,"s_"),"_")}
								</a>
							</div>
						</c:when>
						<c:otherwise>
							<img src="${path}/resources/img/file.png"/>
							<div>
								<a href="${path}/displayFile?fileName=${file}">
									${fn:substringAfter(file,"_")}
								</a>
							</div>
						</c:otherwise>
					</c:choose>
				</li>
			</c:forEach>
		</ul>
	</div>
	<div style="clear:both;"></div>
	<hr/>	
	<div>
		<c:if test="${!empty userInfo}">
			<c:if test="${userInfo.uno eq board.uno}">
				<input type="button" id="modifyBtn" value="MODIFY"/>
				<input type="button" id="deleteBtn" value="DELETE"/>	
			</c:if>
				<input type="button" id="replyBtn" value="REPLY"/>
		</c:if>
		<input type="button" id="listBtn" value="LIST"/>
	</div>
	<form id="readForm">
		<input type="hidden" name="csrf_token" value="${csrf_token}"/>
		<input type="hidden" name="bno" value="${board.bno}"/>
		<input type="hidden" name="page" value="${cri.page}"/>
		<input type="hidden" name="perPageNum" value="${cri.perPageNum}"/>
		<input type="hidden" name="searchType" value="${cri.searchType}"/>
		<input type="hidden" name="keyword" value="${cri.keyword}"/>
	</form>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<br/>
<hr/>
<br/>
<%@ include file="../comment/comment.jsp" %>
<%-- <jsp:include file="../comment/comment.jsp" /> 변수 공유 안됨 --%>
<script>
	var formObj = $("#readForm");
	//게시글 목록
	$("#listBtn").click(function(){
		formObj.attr("action","listReply").submit();
	});
	// 답변글 작성
	$("#replyBtn").click(function(){
		formObj.attr("action","replyRegister").submit();
	});
	// 게시글 수정
	$("#modifyBtn").click(function(){
		formObj.attr("action","modifyPage").submit();
	});
	// 게시글 삭제
	$("#deleteBtn").click(function(){
		var isDelete = confirm("진짜 지울겨?");
		if(isDelete){
			var arr = [];
			$(".uploadList li").each(function(){
				var fileName = $(this).attr("data-src");
				arr.push(fileName);
			});
			
			if(arr.length > 0){
				// post 방식의 ajax 요청 처리
				// $.post(url,parameters,callback함수)
				$.post("${pageContext.request.contextPath}/deleteAllFiles",{files : arr},function(result){
					alert(result);
				});
			}
			
			formObj.attr("action","remove");
			formObj.attr("method","post");
			formObj.submit();
		}else{
			alert("삭제 취소 ㅎ");
		}
	});
</script>
</body>
</html>