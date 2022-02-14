<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.fileDrop{
		width:100%;
		height:150px;
		border:1px solid gray;
		background-color:lightgray;
		margin:auto;
	}
	
	.uploadList{
		width:100%;
	}
	
	.uploadList li{
		float:left;
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
	<h3>REGISTER BOARD</h3>
	<form id="registerForm" action="register" method="post">
		<input type="hidden" name="uno" value="${userInfo.uno}"/>
		<table>
			<tr>
				<td>제목</td>
				<td><input type="text" name="title" required/></td>
			</tr>
			<tr>
				<td>작성자</td>
				<td>${sessionScope.userInfo.uname}</td>
			</tr>
			<tr>
				<td>내용</td>
				<td><textarea name="content" rows="30" cols="50"></textarea></td>
			</tr>
			<tr>
				<td colspan="2"><input type="button" id="saveBtn" value="등록"/></td>
			</tr>
		</table>
		<div>
			<label>FILE DROP HERE</label>
			<div class="fileDrop">
			</div>
		</div>
		<div>
			<ul class="uploadList">
			</ul>
		</div>
	</form>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
	var contextPath = '${pageContext.request.contextPath}';
	$(".fileDrop").on("dragenter dragover", function(e){
		e.preventDefault();
	});
	
	$(".fileDrop").on("drop", function(e){
		e.preventDefault();
		var files = e.originalEvent.dataTransfer.files;
		console.log(files);
		
		var maxSize = 10485760;
		
		var formData = new FormData();
		
		for(var i=0; i<files.length; i++){
			if(files[i].size > maxSize){
				alert("너무크다");
				return;
			}
			formData.append("file",files[i]);
		}
		
		$.ajax({
			type : "post",
			url : contextPath+'/uploadFile',
			data : formData,
			dataType : "json",
			processData : false,
			contentType : false,
			success : function(result){
				console.log(result);
				for(var i=0; i<result.length; i++){
					console.log(result[i]);
					var fileInfo = getFileInfo(result[i]);
					console.log(fileInfo);
					var html = "";
					html += "<li>";
					html +="<img src='"+fileInfo.imgSrc+"' alt='attachment' />";
					html +="<div>";
					html +="<a href='"+fileInfo.getLink+"'>";
					html +=fileInfo.fileName;
					html +="</a>";
					html +="</div>";
					html +="<div>";
					html +="<a href='"+fileInfo.fullName+"' class='delBtn'>X</a>";
					html +="</div>";
					html +="</li>";
					$(".uploadList").append(html);
				}
			},
			error : function(res){
				alert(res.responseText);
			}
		});
	});
	
	// image type check
	function checkImageType(fileName){
		return fileName.indexOf("s_") > 0 ? true : false;
	}
	
	function getFileInfo(fullName){
		var imgSrc, fileName, getLink;
		// 이미지 여부 확인
		if(checkImageType(fullName)){
			// 이미지
			// 파일정보 요청 url displayFile
			//imgSrc = contextPath+"/displayFile?fileName="+fullName;
			imgSrc = contextPath+"/resources/upload"+fullName;
			// 다운로드 요청 - 이미지일 경우 원본 이미지 출력
			getLink = contextPath+"/displayFile?fileName="+fullName.replace("s_",""); 
		}else{
			// 일반 파일
			imgSrc = contextPath+"/resources/img/file.png";
			// 다운로드 요청
			getLink = contextPath+"/displayFile?fileName="+fullName;
		}
		
		fileName = fullName.substr(fullName.lastIndexOf("_")+1);
		return {fileName : fileName, imgSrc : imgSrc, fullName : fullName, getLink : getLink};
	}
	
	$(".uploadList").on("click",".delBtn",function(event){
		event.preventDefault();
		var target = $(this);
		
		$.ajax({
			type: "post",
			url : contextPath+"/deleteFile",
			data : {
				fileName : target.attr("href")
			},
			success : function(data){
				console.log(data);
				// .delBtn closest 대상을 기준으로 가장 가까운 조상을 탐색
				target.closest("li").remove();
			}
		});
	});

	$("#saveBtn").click(function(){
		var str = "";
		var fileList = $(".uploadList .delBtn");
		$(fileList).each(function(index){
			var fullName = $(this).attr("href");
			str += "<input type='hidden' name='files["+index+"]' value='"+fullName+"'/>";
		});
		$("#registerForm").append(str);
		$("#registerForm").submit();
	});
</script>
</body>
</html>