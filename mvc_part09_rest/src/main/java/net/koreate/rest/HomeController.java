package net.koreate.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "home";
	}
	
	@GetMapping("/restData")
	public String restData() {
		return "restData";
	}
	
	@GetMapping("/javascript")
	public void javascript() {
	}
	
	@GetMapping("/ajaxTest")
	public void ajaxTest() {
	}
	
}
