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
	.fileDrop{
		width:100%;
		height:150px;
		border:1px solid black;
		background-color:lightgray;
	}
	
	.uploadList{
		width:100%;
	}
	
	.uploadList li{
		float:left;
		text-align:center;
		padding:20px;
		list-style:none;
	}
	
	.uploadList li a{
		text-decoration:none;
		color:black;
	}
</style>
</head>
<body>
	<h1><a href="${pageContext.request.contextPath}">HOME</a></h1>
	<h3>MODIFY BOARD</h3>
	<form id="modifyForm" action="modifyPage" method="post">
		<input type="hidden" name="uno" value="${userInfo.uno}"/>
		<table>
			<tr>
				<td>제목</td>
				<td><input type="text" name="title" value="${board.title}" required/></td>
			</tr>
			<tr>
				<td>작성자</td>
				<td>${sessionScope.userInfo.uname}</td>
			</tr>
			<tr>
				<td>내용</td>
				<td><textarea name="content" rows="30" cols="50">${board.content}</textarea></td>
			</tr>
			<tr>
				<td colspan="2"><input type="button" id="saveBtn" value="등록"/></td>
			</tr>
		</table>
		<div>
			<label>FILE DROP HERE</label>
			<div class="fileDrop"></div>
		</div>
		<div>
		<c:set var="path" value="${pageContext.request.contextPath}"/>
			<ul class="uploadList">
			<c:forEach var="file" items="${board.files}">
				<li>
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
					<a href="${file}" class='delBtn'>[삭제]</a>
				</li>
			</c:forEach>
			</ul>
		</div>
		<input type="hidden" name="bno" value="${board.bno}"/>
		<input type="hidden" name="page" value="${cri.page}"/>
		<input type="hidden" name="perPageNum" value="${cri.perPageNum}"/>
		<input type="hidden" name="searchType" value="${cri.searchType}"/>
		<input type="hidden" name="keyword" value="${cri.keyword}"/>
	</form>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>var contextPath = '${path}';</script>
<script src="${path}/resources/js/upload.js"></script>
<script>

	$(".fileDrop").on("dragenter dragover",function(event){
		event.preventDefault();
	});
	
	$(".fileDrop").on("drop",function(event){
		event.preventDefault();
		var files = event.originalEvent.dataTransfer.files;
		var maxSize = 10485760;
		var formData = new FormData();
		for(var i=0; i<files.length; i++){
			if(files[i].size > maxSize){
				alert('못넣는다');
				return;
			}
			formData.append("file",files[i]);
		}
		
		$.ajax({
			type : "post",
			url : '${path}/uploadFile',
			data : formData,
			processData : false,
			contentType : false,
			dataType : "json",
			success : function(data){
				console.log(data);
				for(var i=0; i<data.length; i++){
					var fileInfo = getFileInfo(data[i]);
					console.log(fileInfo);
					var html = "<li>";
					html += "<img src='"+fileInfo.imgSrc+"' alt='upload image'/>";
					html += "<div>";
					html += "<a href='"+fileInfo.getLink+"'>";
					html += fileInfo.fileName;
					html += "</a>";
					html += "<a href='"+fileInfo.fullName+"' class='delBtn'>[삭제]</a>";
					html += "</div>";
					html += "</li>";
					$(".uploadList").append(html);
				}
			}
		});
	});
	
	var arr = [];
	
	$(".uploadList").on("click",".delBtn",function(event){
		event.preventDefault();
		var fileLink = $(this).attr("href");
		arr.push(fileLink);
		$(this).closest("li").remove();
	});
	
	$("#saveBtn").click(function(){
		var str = "";
		var fileList = $(".uploadList .delBtn");
		$(fileList).each(function(index){
			var fullName = $(this).attr("href");
			str += "<input type='hidden' name='files["+index+"]' value='"+fullName+"' />";
		});
		$("#modifyForm").append(str);
		
		// 삭제 버튼이 눌러진 모든 파일 데이터 삭제
		$.post(contextPath+"/deleteAllFiles",{files : arr}, function(data){
			alert(data);
		});
		
		$("#modifyForm").submit();
	});
</script>
</body>
</html>