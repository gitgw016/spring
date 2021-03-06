<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.profile_img_wrap .img_cover > label{
	background-image:url('${path}/resources/img/camera.png');
	background-size:30px;
	background-repeat:no-repeat;
	width:30px;
	height:30px;
	display:inline-block;
	margin:0;
}

.profile_img_wrap .img_cover .img_file{
	display:none;
}

.profile_img_wrap{
	position:relative;
	margin:20px auto;
	width:128px;
}

.profile_img_wrap .profile_img{
	width:128px;
	height:128px;
	border-radius:64px;
}

.profile_img_wrap .img_cover{
	width:40px;
	height:40px;
	position:absolute;
	bottom:5px;
	right:5px;
	z-index:1000;
	border-radius:20px;
	background-color:#aaaaaa99;
	border:1px solid white;
	box-sizing:border-box;
	padding:3px;
	padding-left:4px;
}

.profile_img_wrap .trash_cover > label{
	display:block;
	background-image:url('${path}/resources/img/trash.png');
	background-size:30px;
	background-repeat:no-repeat;
	width:30px;
	height:30px;
	margin:0;
}

.profile_img_wrap .trash_cover{
	width:40px;
	height:40px;
	position:absolute;
	bottom:5px;
	left:5px;
	z-index:1000;
	border:1px solid white;
	background-color:#aaaaaa99;
	box-sizing:border-box;
	border-radius:20px;
	padding:4px;
}

#deleteImage{
	display:none;
}

.fileDrop{
	width:100%;
	height:200px;
	border:1px solid black;
}
</style>
</head>
<body>
	<h1>Upload Ajax</h1>
	<div>
		<img src="${path}/resources/img/profile.jpg" id="sampleImage"/>
		<br/>
		<input type="file" id="profileImage" accept="image/*"/>
	</div>
	<br/>
	<hr/>
	<br/>
	<div class="profile_img_wrap">
		<img id="profile_img" class="profile_img" src="${path}/resources/img/profile.jpg"/>
		<div class="trash_cover" id="deleteImage">
			<label></label>
		</div>
		<div class="img_cover">
			<label for="img_file"></label>
			<input type="file" class="img_file" id="img_file" accept=".gif, .jpg, .png" onchange="profileUpload(this.files)"/>
			<input type="hidden" name="uimage" id="uimage"/>
		</div>
	</div>
	<br/>
	<hr/>
	<br/>
	<h3>file drag &amp; drop</h3>
	<div class="fileDrop"></div>
	<div id="uploadList"></div>
	<br/>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
	$("#profileImage").on("change",function(){
		var files = this.files;
		console.log(files);
		console.log(files[0]);
		var path = window.URL.createObjectURL(files[0]);
		console.log(path);
		$("#sampleImage").attr("src",path);
	});
	
	function profileUpload(files){
		console.log(files[0]);
		// FormData??? ajax??? form ????????? ????????? ???????????? ????????? ??????
		var formData = new FormData();
		formData.append("file",files[0]);
		// encType = urlEncoded
		$.ajax({
			type : "post",
			url : "uploadAjax",
			data : formData,
			// key=value ????????? Query String?????? ?????? ??????
			processData : false,
			contentType : false,
			dataType : "text",
			success : function(result){
				//alert(result);
				$("#profile_img").attr("src","${path}/upload"+result);
				$("#uimage").val(result);
				$("#deleteImage").fadeIn("fast");
			}
		});
	}
	
	// ?????? ?????? ??????
	$("#deleteImage").click(function(){
		var fileName = $("#uimage").val();
		$.ajax({
			url : "deleteFile",
			type : "post",
			data : {fileName : fileName},
			dataType : "text",
			success : function(result){
				alert(result);
				$("#profile_img").attr("src","${path}/resources/img/profile.jpg");
				$("#deleteImage").fadeOut("fast");
			},
			error : function(res){
				alert(res.responseText);
			}
		});
	});
	// drag ???????????? ?????? ????????? ????????? / drag??? ????????? ????????? ???
	$(".fileDrop").on("dragenter dragover",function(event){
		event.preventDefault();
	});
	// ??????????????? .fileDrop ????????? ????????? drop????????????
	$(".fileDrop").on("drop",function(event){
		event.preventDefault();
		alert("drop");
		// ????????? drop ??????????????? ????????? ????????? ????????? ?????????
		var files = event.originalEvent.dataTransfer.files;
		for(var i=0; i<files.length; i++){
			var file = files[i];
			console.log(file);
			console.log(file.size);
			var maxSize = 10485760;
			if(maxSize < file.size){
				alert('?????? ???');
				return;
			}
			
			var formData = new FormData();
			formData.append("file",file);
			$.ajax({
				type : "post",
				url : "uploadAjax",
				data : formData,
				dataType : "text",
				processData : false,
				contentType : false,
				success : function(result){
					console.log(i+" : "+result);
					var str = "";
					if(checkImageType(result)){
						console.log("?????????");
						str += "<div>";
						str += "<a href='displayFile?fileName="+getImageName(result)+"' target='_black'>";
						str += "<img src='${path}/upload"+result+"'/>";
						str += "</a>";
						str += "</div>";
					}else{
						console.log("??? ??????");
						str += "<div>";
						str += "<a href='displayFile?fileName="+result+"'>";
						str += "<img src='${path}/resources/img/file.png'/>";
						str += getOriginalName(result);
						str += "</a>";
						str += "</div>";
					}
					
					$("#uploadList").append(str);
				}
			});
		}
	});
	
	// ????????? ????????? ?????? ???????????? ?????? ?????? ????????? ?????? ??????
	function getImageName(fileName){
		if(!checkImageType(fileName)){
			return;
		}
		return fileName.replace("s_","");
	}
	
	// ?????? ?????? ??????
	function getOriginalName(data){
		var index = data.indexOf("_")+1;
		return data.substr(index);
	}
	
	function checkImageType(fileName){
		// ?????? ????????? - ?????? ????????? ???????????? ?????? ?????? ?????? ??????
		// RegExp match / test
		var pattern = /jpg|gif|png|jpeg/i;
		return fileName.match(pattern);
	}
</script>
</body>
</html>