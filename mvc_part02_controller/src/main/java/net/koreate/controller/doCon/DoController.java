package net.koreate.controller.doCon;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.koreate.dao.TestDAO;
import net.koreate.service.TestService;

@Controller
public class DoController {
	
	@Inject
	TestService ts;
	
	@Inject
	TestDAO dao;
	
	public DoController() {
		System.out.println("DoController 왔다");
	}
	
	@RequestMapping("main.do")
	public void main() {
		System.out.println("main.do 요청");
		
		if(dao != null) {
			dao.testDAO("HomeController");
		}else {
			System.out.println("HomeController는 null");
		}
		if(ts != null) {
			ts.testService("DoController");
		}else {
			System.out.println("DoController 는 null");
		}
	}
}
