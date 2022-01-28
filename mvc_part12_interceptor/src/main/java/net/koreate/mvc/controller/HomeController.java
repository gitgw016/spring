package net.koreate.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		System.out.println("HomeController ㄱㄱ");
		System.out.println("HomeController ㅃㅃ");
		return "home";
	}
	
	@GetMapping("test1")
	public String test1() {
		System.out.println("HomeController test1 ㄱㄱ");
		System.out.println("HomeController test1 ㅃㅃ");
		return "home";
	}
	
	@GetMapping("test2")
	public String test2(Model model) {
		System.out.println("HomeController test2 get ㄱㄱ");
		model.addAttribute("result","test2 job");
		System.out.println("HomeController test2 get ㅂㅂ");
		return "home";
	}
	
	@GetMapping("test3")
	public String test3(){
		System.out.println("test3() ㄱㄱ");
		System.out.println("test3() ㅂㅂ");
		return "result";
	}
	
}

