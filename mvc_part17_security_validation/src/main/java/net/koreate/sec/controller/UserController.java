package net.koreate.sec.controller;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import net.koreate.file.util.FileUtils;
import net.koreate.sec.service.MemberService;
import net.koreate.sec.vo.ValidationMemberVO;

@Controller
public class UserController {
	
	@Inject
	MemberService ms;
	
	@Inject
	ServletContext context;
	
	@Resource(name="uploadPath")
	String uploadPath;
	
	String uploadFolder;
	
	@PostConstruct
	public void initPath() {
		uploadFolder = context.getRealPath(File.separator+uploadPath);
		System.out.println(uploadFolder);
		File file = new File(uploadFolder);
		if(!file.exists()) {
			file.mkdirs();
			System.out.println("집만듦");
		}
		System.out.println("controller 초기화");
	}

	@GetMapping("/user/login")
	public String login(String message, Model model) {
		model.addAttribute("message", message);
		return "user/login";
	}

	@GetMapping("/user/join")
	public void join() {
	}

	@GetMapping("/user/logout")
	public void logout() {
	}
	
	// id 중복 체크
	@PostMapping("/user/uidCheck")
	@ResponseBody
	public boolean uidCheck(String u_id) throws Exception{
		boolean isCheck = ms.getMemberByID(u_id);
		System.out.println(isCheck);
		return isCheck;
	}

	@PostMapping("/user/joinPost")
	public String joinPost(ValidationMemberVO vo, MultipartFile profileImage) throws Exception{
		if(profileImage != null && !profileImage.isEmpty()) {
			System.out.println(profileImage.getOriginalFilename());
			String u_profile = FileUtils.uploadFile(profileImage.getOriginalFilename(), uploadFolder, profileImage.getBytes());
			System.out.println(u_profile);
			vo.setU_profile(u_profile);
		}
		System.out.println(vo);
		ms.memberJoin(vo);
		return "redirect:/user/login";
	}
	
	@GetMapping("logOff")
	public String logOff() {
		return "/user/logOff";
	}
}
