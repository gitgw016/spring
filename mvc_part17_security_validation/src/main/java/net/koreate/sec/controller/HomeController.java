package net.koreate.sec.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping(value = "/")
	public String home() {
		return "home";
	}

	@GetMapping(value = "/chat")
	public String chat() {
		return "chat";
	}
	
	@GetMapping(value = "/chat2")
	public String chat2() {
		return "chat2";
	}

}
