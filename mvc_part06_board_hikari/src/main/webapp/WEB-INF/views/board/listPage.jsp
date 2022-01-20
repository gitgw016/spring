<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>BOARD LIST PAGE - ${pm.totalCount}</h3>
	<form action="register" method="get">
		<button>NEW BOARD</button>
	</form>
	<hr/>
	<table border=1>
		<tr>
			<th>BNO</th>
			<th>TITLE</th>
			<th>WRITER</th>
			<th>REGDATE</th>
			<th>WIEWCNT</th>
		</tr>
		<c:choose>
			<c:when test="${!empty list}">
		<!-- 게시글 목록 -->
			<c:forEach var="board" items="${list}">
				<tr>
					<td>${board.bno}</td>
					<td><a href="readPage${pm.query(pm.cri.page)}&bno=${board.bno}">${board.title}</a></td>
					<td>${board.writer}</td>
					<td><f:formatDate pattern="yyyy-MM-dd HH:mm" value="${board.regdate}"/></td>
					<td>${board.viewcnt}</td>
				</tr>
			</c:forEach>
		<!-- 페이징 블럭 -->
			<tr>
				<th colspan="5" id="pagination">
					<c:if test="${pm.prev}">
						<a href="${pm.startPage-1}">[이전]</a>
					</c:if>
					<c:forEach var="i" begin="${pm.startPage}" end="${pm.endPage}">
						<a href="${i}">[${i}]</a>
					</c:forEach>
					<c:if test="${pm.next}">
						<a href="${pm.endPage+1}">[다음]</a>
					</c:if>
				</th>
			</tr>
			</c:when>
			<c:otherwise>
				<tr>
					<th colspan="5">등록된게 읎다. ㅠ</th>
				</tr>
			</c:otherwise>
		</c:choose>
	</table>
	<form id="listForm">
		<input type="hidden" name="page" value="${pm.cri.page}"/>
		<input type="hidden" name="perPageNum" value="${pm.cri.perPageNum}"/>
	</form>
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script>
		var result = '${result}';
		if(result != '')alert(result);
		
		$("#pagination a").on("click",function(event){
			// a tag의 기본 이벤트(hyperlink) 이벤트 무시
			event.preventDefault();
			var targetPage = $(this).attr("href");
			// alert(targetPage);
			var listForm = $("#listForm");
			listForm.find("[name='page']").val(targetPage);
			listForm.attr("action","listPage");
			listForm.submit();
		});
	</script>
</body>
</html>