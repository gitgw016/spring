<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../include/header.jsp"/>
<section class="content">
	<div class="row">
		<div class="col-md-12">
			<div class="box">
				<div class="box-header with-border">
					<h3 class="box-title">SEARCH LIST PAGE - [${pm.totalCount}]</h3>
				<div class="col-md-1 pull-right text-right">
					<a class="btn btn-primary" href="${pageContext.request.contextPath}">HOME</a>
				</div>
			</div>
				<div class="box-body">
					<div class="row">
						<form id="searchForm">
							<div class="col-md-3">
								<select class="form-control" name="searchType">
									<option value="title" ${pm.cri.searchType == 'title' ? 'selected' : ''}>TITLE</option>
									<option value="content" ${pm.cri.searchType == 'content' ? 'selected' : ''}>CONTENT</option>
									<option value="writer" ${pm.cri.searchType == 'writer' ? 'selected' : ''}>WRITER</option>
									<option value="tc" ${pm.cri.searchType == 'tc' ? 'selected' : ''}>TITLE &amp; CONTENT</option>
									<option value="cw" ${pm.cri.searchType == 'cw' ? 'selected' : ''}>CONTENT &amp; WRITER</option>
									<option value="tcw" ${pm.cri.searchType == 'tcw' ? 'selected' : ''}>TITLE &amp; CONTENT &amp; WRITER</option>
								</select>
							</div>
							<div class="col-md-3">
								<input type="text" name="keyword" class="form-control" value="${pm.cri.keyword}"/>
							</div>
							<div class="col-md-4">
								<div class="col-md-6">
									<input type="submit" class="form-control btn btn-warning" value="SEARCH"/>
								</div>
								<div class="col-md-6">
									<input id="newBtn" class="form-control btn btn-primary" type="button" value="NEW BOARD"/>
								</div>
							</div>
							<div class="col-md-2">
								<select id="pageNumSelect" class="form-control" name="perPageNum">
									<option value="${pm.cri.perPageNum}">${pm.cri.perPageNum}개씩 보기</option>
									<option value="10">10개씩 보기</option>
									<option value="15">15개씩 보기</option>
									<option value="20">20개씩 보기</option>
								</select>
							</div>
						</form>
					</div>
				</div>
				<div class="box-body">
					<table class="table table-bordered">
						<tr>
							<th>BNO</th>
							<th>TITLE</th>
							<th>WRITER</th>
							<th>REGDATE</th>
							<th>VIEWCNT</th>
						</tr>
						<!-- 게시물 목록 출력 -->
						<c:choose>
							<c:when test="${!empty list}">
							<!-- 현재시간 -->
							<jsp:useBean id="n" class="java.util.Date"/>
							<f:formatDate var="now" value="${n}" pattern="yyyy-MM-dd"/>
							<c:forEach var="board" items="${list}">
								<tr>
									<td>${board.bno}</td>
									<td><a href="readPage${pm.query(pm.cri.page)}&bno=${board.bno}">${board.title}</a></td>
									<td>${board.writer}</td>
									<td>
										<f:formatDate var="reg" pattern="yyyy-MM-dd" value="${board.regdate}"/>
										<c:choose>
											<c:when test="${reg eq now}">
												<f:formatDate pattern="HH:mm:ss" value="${board.regdate}"/>
											</c:when>
											<c:otherwise>
												${reg}
											</c:otherwise>
										</c:choose>
									</td>
									<td><span class="badge bg-red">${board.viewcnt}</span></td>
								</tr>
							</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="5" class="text-center">게시물이 없다. ㅠ</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</table>
				</div>
				<div class="box-footer">
					<div class="text-center">
						<ul class="pagination">
						<c:if test="${pm.first}">
								<li>
									<a href="listPage${pm.query(1)}">&laquo;&laquo;</a>
								</li>
							</c:if>
							<c:if test="${pm.prev}">
								<li>
									<a href="listPage${pm.query(pm.startPage-1)}">&laquo;</a>
								</li>
							</c:if>
							<c:forEach var="i" begin="${pm.startPage}" end="${pm.endPage}">
							<li ${pm.cri.page == i ? 'class=active' : ''}>
								<a href="listPage${pm.query(i)}">${i}</a>
							</li>
							</c:forEach>
							<c:if test="${pm.next}">
								<li>
									<a href="listPage${pm.query(pm.endPage+1)}">&raquo;</a>
								</li>
							</c:if>
							<c:if test="${pm.last}">
								<li>
									<a href="listPage${pm.query(pm.maxPage)}">&raquo;&raquo;</a>
								</li>
							</c:if>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<script>

	var result = '${result}';
	if(result != ''){
		alert(result);
	}
	
	$(function(){
		$("#newBtn").click(function(){
			location.href="register";
		});
		
		$("#pageNumSelect").on("change",function(){
			$("#searchForm").submit();
		});
	});
</script>
<jsp:include page="../include/footer.jsp"/>