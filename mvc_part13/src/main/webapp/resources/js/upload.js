/**
 * 
 */
 
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