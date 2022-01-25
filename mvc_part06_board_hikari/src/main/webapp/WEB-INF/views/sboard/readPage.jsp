<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f" %>
<jsp:include page="../include/header.jsp"/>
<section class="content">
	<div class="row">
		<div class="col-md-12">
			<div class="box">
				<div class="box-header with-border">
					<h3 class="box-title">SEARCH BOARD READ - ${board.bno}</h3>
				</div>
					<div class="box-body">
						<div class="form-group">
							<label>TITLE</label>
							<input type="text" name="title" class="form-control" placeholder="TITLE" readonly value="${board.title}" />
						</div>
						<div class="form-group">
							<label>WRITER</label>
							<input type="text" name="writer" class="form-control" placeholder="Enter Writer" readonly value="${board.writer}" />
						</div>
						<div class="form-group">
							<label>CONTENT</label>
							<textarea name="content" class="form-control" placeholder="Enter Content" rows="7" readonly>${board.content}</textarea>
						</div>
						<div class="form-group">
							<label>REGDATE</label>
							<f:formatDate pattern="yyyy-MM-dd HH:mm" value="${board.regdate}"/>
						</div>
					</div>
					<div class="box-footer">
						<input type="button" value="MODIFY" class="btn btn-warning"/>
						<input type="button" value="DELETE" class="btn btn-danger"/>
						<input type="button" value="LIST" class="btn btn-primary"/>
					</div>
					<form id="readForm" method="get">
						<input type="hidden" name="page" value="${cri.page}"/>
						<input type="hidden" name="perPageNum" value="${cri.perPageNum}"/>
						<input type="hidden" name="searchType" value="${cri.searchType}"/>
						<input type="hidden" name="keyword" value="${cri.keyword}"/>
						<input type="hidden" name="bno" value="${board.bno}"/>
					</form>
			</div>
		</div>
	</div>
</section>
<script>
	$(function(){
		var formObj = $("#readForm");
		// 수정 버튼 click
		$(".btn-warning").click(function(){
			formObj.attr("action","modifyPage");
			formObj.submit();
		});
		
		// 삭제 버튼 click
		$(".btn-danger").click(function(){
			formObj.attr("method","post");
			formObj.attr("action","removePage");
			formObj.submit();
		});
		
		// List 버튼 click
		$(".btn-primary").click(function(){
			$("input[name='bno']").attr("disabled","disabled");
			formObj.attr("action","listPage").submit();
		});
	});
</script>
<jsp:include page="../include/footer.jsp"/>