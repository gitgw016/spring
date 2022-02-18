package net.koreate.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test/*")
public class TestController {

	@GetMapping("all")
	public void all() {
		System.out.println("전체 이용가");
	}
	
	@GetMapping("member")
	public void member() {
		System.out.println("19세 이상 member 이용가");
	}
	
	@GetMapping("master")
	public void master() {
		System.out.println("배민구 관리자용");
	}
}
