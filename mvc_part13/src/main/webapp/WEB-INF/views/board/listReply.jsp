<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1><a href="${pageContext.request.contextPath}">HOME</a></h1>
	<div>
		<select>
			<option value="n">---------------------------------------</option>
			<option value="t">TITLE</option>
			<option value="c">CONTENT</option>
			<option value="tc">TITLE &amp; CONTENT</option>
		</select>
		<input type="text" id="keyword"/>
		<input type="button" value="검색" id="searchBtn"/>
		<input type="button" id="newBtn" value="글작성"/>
	</div>
	<br/>
	<table border=1>
		<tr>
			<th>BNO</th>
			<th>TITLE</th>
			<th>WRITER</th>
			<th>REGDATE</th>
			<th>VIEWCNT</th>
		</tr>
		<!-- 게시글 리스트 목록 -->
		<c:choose>
			<c:when test="${!empty list}">
				<c:forEach var="board" items="${list}">
					<c:choose>
						<c:when test="${board.showboard eq 'Y' }">
							<tr>
								<td>${board.bno}</td>
								<td>
									<a href="readPage${pm.search(pm.cri.page)}&bno=${board.bno}">
										<c:forEach var="i" begin="1" end="${board.depth}">
											&nbsp;&nbsp;
										</c:forEach>
										<c:if test="${board.depth != 0}">
										└>
										</c:if>
									<c:out value="${board.title}" escapeXml="true"/></a>
									[${board.commentCnts}]
								</td>
								<td>${board.writer}</td>
								<td>
									<f:formatDate value="${board.regdate}" pattern="yyyy.MM.dd (E) hh:mm"/>
								</td>
								<td>${board.viewcnt}</td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<td></td>
								<td>지워졌다 ㅅㄱ</td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</c:when>
			<c:otherwise>
			<tr>
				<th colspan="5">없다</th>
			</tr>
			</c:otherwise>
		</c:choose>
		<!-- 페이징 블럭 -->
		<tr>
			<th colspan="5">
				<c:forEach var="i" begin="${pm.startPage}" end="${pm.endPage}">
					<a href="listReply${pm.search(i)}">[${i}]</a>
				</c:forEach>
			</th>
		</tr>
	</table>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
	// 검색 요청
	$("#searchBtn").click(function(){
		var searchType = $("select option:selected").val();
		var keyword = $("#keyword").val();
		console.log("searchType : " + searchType);
		console.log("keyword : " + keyword);
		location.href="listReply?searchType="+searchType+"&keyword="+keyword;
	});
	
	// 새글 작성 요청
	$("#newBtn").click(function(){
		location.href="register";
	});
</script>
</body>
</html>