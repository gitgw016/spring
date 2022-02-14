package net.koreate.common.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.koreate.common.utils.FileUtil;

@RestController
public class FileController {

	@Resource(name="uploadPath")
	String uploadPath;
	
	@Inject
	ServletContext context;
	
	@PostConstruct
	public void initPath() {
		uploadPath = context.getRealPath(File.separator+uploadPath);
		File file = new File(uploadPath);
		if(!file.exists()) {
			file.mkdirs();
		}
		System.out.println("initPath 됐음  : "+uploadPath);
	}
	
	@PostMapping("/uploadFile")
	public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile[] file) throws Exception{
		ResponseEntity<Object> entity = null;
		System.out.println(file);
		List<String> fileList = new ArrayList<>();
		try {
			for(MultipartFile files : file) {
				String fileName = FileUtil.uploadFile(uploadPath, files);
				fileList.add(fileName);
			}
			entity = new ResponseEntity<>(fileList,HttpStatus.OK);
		} catch (Exception e) {
			HttpHeaders header = new HttpHeaders();
			header.add("Content-Type", "text/plain;charset=utf-8");
			entity = new ResponseEntity<>(e.getMessage(),header,HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	@GetMapping("displayFile")
	public ResponseEntity<byte[]> displayFile(String fileName) throws Exception{
		byte[] bytes = FileUtil.displayFile(uploadPath,fileName);
		HttpHeaders header = FileUtil.getHeader(fileName);
		return new ResponseEntity<>(bytes,header,HttpStatus.OK);
	}
	
	@PostMapping("deleteFile")
	public ResponseEntity<String> deleteFile(String fileName) throws Exception{
		boolean isDeleted = FileUtil.deleteFile(uploadPath,fileName);
		if(isDeleted) {
			return new ResponseEntity<>("bye bye",HttpStatus.OK);
		}
		return new ResponseEntity<>("sucked",HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/deleteAllFiles")
	public ResponseEntity<String> deleteAllFiles(@RequestParam("files[]") List<String> files) throws Exception{
		boolean isDeleted = false;
		for(String s : files) {
			System.out.println("deleteAllFiles : "+s);
			isDeleted = FileUtil.deleteFile(uploadPath, s);
		}
		if(isDeleted) {
			return new ResponseEntity<>("BA BA",HttpStatus.OK);
		}
		
		return new ResponseEntity<>("suckaaaa",HttpStatus.BAD_REQUEST);
	}
}
