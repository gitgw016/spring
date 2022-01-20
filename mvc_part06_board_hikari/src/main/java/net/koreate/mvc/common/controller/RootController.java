package net.koreate.mvc.common.controller;

import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Controller
public class RootController {
	
	@Inject
	RequestMappingHandlerMapping rmhm;

	@GetMapping("/")
	public String home() {
		Map<RequestMappingInfo , HandlerMethod> map = rmhm.getHandlerMethods();
		Set<RequestMappingInfo> set = map.keySet();
		for(RequestMappingInfo ri : set) {
			System.out.println("key : "+ri);
			System.out.println("value : "+map.get(ri));
		}
		return "home";
	}
}
