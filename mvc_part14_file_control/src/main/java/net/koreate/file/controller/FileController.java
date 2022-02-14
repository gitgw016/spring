package net.koreate.file.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import net.koreate.file.vo.FileVO;

@Controller
public class FileController {
	
	@Resource(name="uploadPath")
	String uploadPath;
	
	@Resource(name="uploadFolder")
	String uploadFolder;
	
	@Autowired
	ServletContext context;
	
	public FileController() {
		System.out.println("FileController 생성자 호출");
		System.out.println("생성자 uploadPath : "+uploadPath);
	}

	@PostConstruct
	public void initController() {
		System.out.println("initController uploadPath : "+uploadPath);
		System.out.println("uploadFolder : "+uploadFolder);
		uploadPath = context.getRealPath(File.separator+uploadFolder);
		System.out.println("uploadPath : "+uploadPath);
		File file = new File(uploadPath);
		// 경로상에 폴더가 존재하지 않으면 디렉토리 생성
		if(!file.exists()) {
			file.mkdirs();
			System.out.println("경로 만듬");
		}
		System.out.println("FileController 생성&사용 ㄱㄱ하셈");
	}
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@GetMapping("uploadAjax")
	public void uploadAjax() {}
	
	@GetMapping("uploadForm")
	public void uploadForm() {
		
	}
	
	@PostMapping("uploadForm")
	public String uploadForm(@RequestParam("file") MultipartFile file, Model model) throws Exception{
		System.out.println("file name : "+file.getOriginalFilename());
		System.out.println("file size : "+file.getSize());
		System.out.println("file Type : "+file.getContentType());
		byte[] bytes = file.getBytes();
		String savedName = uploadFile(file.getOriginalFilename(),bytes);
		model.addAttribute("savedName",savedName);
		/*
		File f = new File(uploadPath,file.getOriginalFilename());
		FileCopyUtils.copy(bytes,f);
		*/
		return "uploadResult";
	}
	
	public String uploadFile(String originalFileName, byte[] fileData) throws Exception {
		String savedName = "";
		UUID uid = UUID.randomUUID();
		// 32개의 랜덤한 문자 + 4개의 - = 36개
		System.out.println(uid);
		savedName = uid.toString().replace("-", "")+"_"+originalFileName;
		System.out.println(savedName);
		FileCopyUtils.copy(fileData, new File(uploadPath,savedName));
		return savedName;
	}
	
	@PostMapping("uploadForm1")
	public String uploadForm1(MultipartFile[] file, Model model) throws Exception {
		List<String> saves = new ArrayList<>();
		for(MultipartFile f : file) {
			saves.add(uploadFile(f.getOriginalFilename(),f.getBytes()));
		}
		model.addAttribute("saves",saves);
		return "uploadResult";
	}
	
	@PostMapping("uploadForm2")
	public String uploadForm2(String auth, List<MultipartFile> file, MultipartFile file1, Model model) throws Exception{
		System.out.println("auth : "+auth);
		model.addAttribute("auth",auth);
		List<String> saves = new ArrayList<>();
		for(MultipartFile f : file) {
			String savedName = uploadFile(f.getOriginalFilename(),f.getBytes());
			saves.add(savedName);
		}
		model.addAttribute("saves",saves);
		String savedName = uploadFile(file1.getOriginalFilename(),file1.getBytes());
		model.addAttribute("savedName",savedName);
		return "uploadResult";
	}
	
	@PostMapping("uploadForm3")
	public String uploadForm3(FileVO vo, Model model) throws Exception{
		System.out.println(vo);
		model.addAttribute("auth",vo.getAuth());
		model.addAttribute("content",vo.getContent());
		List<String> saves = new ArrayList<>();
		for(MultipartFile f : vo.getFiles()) {
			String original = f.getOriginalFilename();
			byte[] fileData = f.getBytes();
			String savedName = uploadFile(original,fileData);
			saves.add(savedName);
		}
		model.addAttribute("vo",saves);
		String original = vo.getFile().getOriginalFilename();
		byte[] fileData = vo.getFile().getBytes();
		String savedName = uploadFile(original,fileData);
		model.addAttribute("sav",savedName);
		return "uploadResult";
	}
}
