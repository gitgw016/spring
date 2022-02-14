package net.koreate.file.controller;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.koreate.file.util.FileUtils;

@RestController
public class AjaxController {
	
	@Resource(name="uploadFolder")
	String uploadFolder;
	
	@Autowired
	ServletContext context;
	
	
	String uploadPath;
	
	@PostConstruct
	public void initPath() {
		uploadPath = context.getRealPath(File.separator+uploadFolder);
		System.out.println("uploadPath : " +uploadPath);
		File file = new File(uploadPath);
		if(!file.exists()) {
			file.mkdirs();
			System.out.println("폴더 박음");
		}
		System.out.println("초기화 됐다");
	}

	@PostMapping("uploadAjax")
	public ResponseEntity<String> uploadAjax(MultipartFile file) throws Exception{
		/*
		String path = FileUtils.calcPath(uploadPath);
		System.out.println(path);
		*/
		ResponseEntity<String> entity = null;
		String origin = file.getOriginalFilename();
		String savedName = FileUtils.uploadFile(origin, uploadPath, file.getBytes());
		HttpHeaders header = new HttpHeaders();
		header.add("Content-type", "text/plain;charset=utf-8");
		entity = new ResponseEntity<>(savedName,header,HttpStatus.OK);
		return entity;
	}
	
	// 파일 삭제 요청 처리
	@PostMapping("deleteFile")
	public ResponseEntity<String> deleteFile(String fileName) throws Exception{
		System.out.println("fileName delete : "+fileName);
		ResponseEntity<String> entity = null;
		boolean isDeleted = FileUtils.deleteFile(uploadPath, fileName);
		if(isDeleted) {
			entity = new ResponseEntity<>("GO HOME",HttpStatus.OK);
		}else {
			entity = new ResponseEntity<>("FUCKED",HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
}
