<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>1번 게시물의 댓글 리스트</title>
<style>
	.commentWrap{
		width:100%;
		border:1px solid #ddd;
		padding:15px;
		margin-top:15px;
		margin-bottom:10px;
		box-sizing:border-box;
	}
	
	.commentWrap .commentWrite textarea.comment_content{
		border:none;
		resize:none;
		outline:0;
		font-size:1.1em;
		color:#333;
		float:left;
		height:70px;
		padding:5px;
		width:85%;
	}
	
	.commentWrap .commentWrite{
		border:1px solid #ccc;
		overflow:hidden;
	}
	
	.commentWrite input[type='submit']{
		float:right;
		width:13%;
		height:80px;
		font-weight:bold;
	}
	
	/* 댓글 리스트 */
	.commentListWrap{
		width:100%;
		border:1px solid #ddd;
		padding:15px;
		margin-top:15px;
		box-sizing:border-box;
	}
	
	.commentListWrap textarea{
		border:none;
		resize:none;
		outline:0;
		font-size:1.3em;
		color:#333;
		width:100%;
	}
	
	.closeBtn{
		float:right;
		border:1px solid #ccc;
		padding:5px;
	}
	
	.closeBtn input{
		border:none;
		outline:none;
		background:none;
	}
	
	.closeBtn:hover input{
		cursor:pointer;
	}
	
	.pagingWrap{
		width:100%;
		text-align:center;
		margin-top:15px;
		margin-bottom:50px;
	}
	
	a{
		text-decoration:none;
		color:black;
	}
	
	a:hover{
		color:#ccc;
	}
	
</style>
</head>
<body>
	<div class="commentWrap">
		<h3>1번 게시글의 댓글 작성</h3>
		<br/>
		<form action="addComment" method="POST"> 
		<div>
		작성자 : <input type="text" name="commentAuth" placeholder="댓글 작성자" required/> <br/><br/>
		</div>
		<div class="commentWrite">
			<input type="hidden" name="bno" value="1"/>
			<textarea name="commentText" class="comment_content" placeholder="댓글 내용" required></textarea>
			<input type="submit" value="등록" />
		</div>
		</form>
	</div>
<!-- 게시글 상세 : boardVO -->
<!-- 댓글 리스트 : list -->
<!-- 댓글 페이징 : pm -->
<c:if test="${!empty list}">
	<br/>
	<h3>댓글 목록[${pm.totalCount}]</h3>
	<c:forEach var="cmt" items="${list}">
		<div class="commentListWrap">
			<c:choose>
				<c:when test="${cmt.commentDelete eq 'N'}">
					<form action="commentDelete" method="POST">
						<input type="hidden" name="cno" value="${cmt.cno}" />
						<input type="hidden" name="bno" value="1" />
						<div class="closeBtn">
							<input type="submit" value="X" title="댓글 삭제"/>
						</div>
					</form>
					<div>
						${cmt.commentAuth}&nbsp;&nbsp;|&nbsp;&nbsp;${cmt.regdate}
					</div>
					<div>
						<textarea readonly>${cmt.commentText}</textarea>
					</div>
				</c:when>
				<c:otherwise>
					<h3>삭제된 댓글입니다.</h3>
				</c:otherwise>
			</c:choose>
		</div>
	</c:forEach>
	<div class="pagingWrap">
		<!-- 댓글 페이징 처리 -->
		<c:forEach var="i" begin="${pm.startPage}" end="${pm.endPage}" step="1">
			<a href="commentList?page=${i}&bno=1">[${i}]</a>
		</c:forEach>
	</div>
</c:if>
<script>
	var result = '${result}';
	if(result != ''){
		alert(result);
	}
</script>
</body>
</html>