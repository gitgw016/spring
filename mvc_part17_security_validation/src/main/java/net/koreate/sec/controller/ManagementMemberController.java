package net.koreate.sec.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.koreate.sec.service.MemberService;
import net.koreate.sec.vo.AuthVO;
import net.koreate.sec.vo.ValidationMemberVO;

@Controller
@RequestMapping("/mngt/*")
public class ManagementMemberController {

	@Inject
	MemberService ms;
	
	@GetMapping("main")
	public void main(Model model) throws Exception {
		List<ValidationMemberVO> memberList = ms.getMemberList();
		model.addAttribute("memberList",memberList);
	}
	
	// 활성화 여부 수정
	// /mngt/user/delete
	@PostMapping("user/delete")
	@ResponseBody
	public String deleteYN(ValidationMemberVO vo) throws Exception{
		ms.DeleteYN(vo);
		return vo.getU_withdraw();
	}
	
	// 권한 수정
	@PostMapping("user/changeAuth")
	@ResponseBody
	public List<AuthVO> changeAuth(AuthVO vo) throws Exception{
		List<AuthVO> list = ms.updateAuth(vo);
		System.out.println(list);
		return list;
	}
}
