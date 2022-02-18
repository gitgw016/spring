package net.koreate.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

	@GetMapping("login")
	public String login() {
		return "user/login";
	}
	
	@GetMapping("logout")
	public String logOut() {
		return "user/logOut";
	}
}
