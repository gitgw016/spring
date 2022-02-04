package net.koreate.user.controller;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.koreate.user.service.UserService;
import net.koreate.user.vo.LoginDTO;
import net.koreate.user.vo.UserVO;

@Controller
@RequestMapping("user")
public class UserController {

	@Inject
	UserService us;
	
	@RequestMapping("signIn")
	public String signIn() {
		return "user/signIn";
	}
	
	@RequestMapping("/signUp")
	public String singUp() {
		return "user/signUp";
	}
	
	@PostMapping("/signInPost")
	public ModelAndView signInPost(LoginDTO dto, ModelAndView mav) {
		System.out.println(dto);
		mav.addObject("logDTO",dto);
		mav.setViewName("redirect:/");
		return mav;
	}
	
	@PostMapping("/signUpPost")
	public String signUpPost(UserVO vo, RedirectAttributes rttr) throws Exception{
		System.out.println(vo);
		// 회원가입 처리
		us.signUp(vo);
		rttr.addFlashAttribute("message","가입성공 ^^");
		return "redirect:/user/signIn";
	}
	
	@GetMapping("/signOut")
	public String signOut(HttpSession session, HttpServletResponse response, /*HttpServletRequest request*/ @CookieValue(name="signInCookie",required=false) Cookie signInCookie) {
		if(session.getAttribute("userInfo") != null) {
			session.removeAttribute("userInfo");
			session.removeAttribute("invalidate");
			//session.invalidate();
			if(signInCookie != null) {
				System.out.println("signInCookie key : "+ signInCookie.getName());
				System.out.println("signInCookie value : "+ signInCookie.getValue());
				signInCookie.setMaxAge(0);
				signInCookie.setPath("/");
				response.addCookie(signInCookie);
			}
		}
		/*
		Cookie cookie = WebUtils.getCookie(request, "signInCookie");
		if(cookie != null) {
			cookie.setPath("/");
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
		*/
		return "redirect:/";
	}
}
