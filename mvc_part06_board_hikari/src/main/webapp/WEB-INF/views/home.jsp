<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="./include/header.jsp"/>
<section class="content">
	<div class="row">
		<!-- 12개로 분류 -->
		<div class="col-md-12">
			<div class="box">
				<div class="box-header with-border">
					<h3 class="box-title">HOME PAGE</h3>
				</div>
				<div class="box-body">
					<a href="board/listPage" class="btn btn-default">게시판</a>
					<a href="board/register" class="btn btn-primary">게시판 글쓰기</a>
					<a href="sboard/register" class="btn btn-warning">검색용 게시판 글쓰기</a>
					<a href="sboard/listPage" class="btn btn-danger">검색용 게시판 목록</a>
				</div>
			</div>
		</div>
	</div>
</section>

<jsp:include page="./include/footer.jsp"/>