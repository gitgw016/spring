package net.koreate.controller.home;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.koreate.dao.TestDAO;
import net.koreate.service.TestService;
import net.koreate.vo.ProductVO;


/**
 * @Controller : Presentation Layer에서 Controller를 명시함.
 * @Service	   : Business Layer의 Service를 명시함.
 * @Repository : Persistence Layer의 Database Access Object 를 명시
 * @Component
 */
@Controller
public class HomeController {
	
	// DI Annotation
	//			@Autowired	  	  @Inject	 	 @Resource
	// 범용		spring전용		java에서 지원	 	java에서 지원
	// 연결성 	 	type에 맞춰서 주입   type에 맞춰서 주입	이름으로 주입
	
	//TestService ts = new TestServiceImpl();
	@Autowired(required=false)
	TestService ts;
	
	@Autowired(required=false)
	TestDAO dao;
	
	@Inject
	ProductVO product;
	
	@RequestMapping("doA")
	public void doA() {
		System.out.println("doA 왔다");
		System.out.println(product);
		
		if(dao != null) {
			dao.testDAO("HomeController");
		}else {
			System.out.println("HomeController는 null");
		}
		if(ts != null) {
			ts.testService("HomeController");
		}else {
			System.out.println("HomeController 는 null");
		}
	}
//	public String doA() {
//		System.out.println("doA Call");
//		// request.getReqeustDispatcher(WEB-INF/views/doA.jsp);
//		// request.forward(request,response);
//		return "doA";
//	}
	
	@RequestMapping("doB")
	public String doB() {
		return "home";
	}
	
	@RequestMapping("doC")
	public String doC(Model model) {
		model.addAttribute("msg","model의 뭐요");
		return "result";
	}
//	public String doC(HttpServletRequest request) {
//		request.setAttribute("msg", "뭐요");
//		return "result";
//	}
	
	@RequestMapping(value="doD", method=RequestMethod.GET)
	public String doD(
			// @RequestParam(name="msg",required = true) String message, Model model) {
			String msg, Model model) {
			System.out.println("message : " + msg);
			model.addAttribute("msg",msg);
		return "result";
	}
	
	@RequestMapping(value="doD", method=RequestMethod.POST)
	public String doD(String msg, int age, Model model) {
		System.out.println(msg);
		System.out.println(age);
		model.addAttribute("msg","doD : "+msg+" age : "+age);
		return "result";
	}
	
}






