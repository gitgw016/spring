package net.koreate.mvc.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class TestInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		System.out.println("preHandle ㄱㄱ");
		// dispatcher servlet에서 호출될 controller 정보
		HandlerMethod method = (HandlerMethod)handler;
		System.out.println("controller : "+method.getBean());
		System.out.println("method : "+method.getMethod());
		System.out.println("전송방식 : "+request.getMethod());
		String cmd = request.getRequestURI().substring(request.getContextPath().length()+1);
		System.out.println("요청 URL : "+cmd);
		if(cmd.equals("test1")){
			response.sendRedirect("test2");
			System.out.println("prehandle return false");
			return false;
		}
		System.out.println("preHandle ㅂㅂ");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		System.out.println("Post Handler ㄱㄱ");
		Map<String,Object> map = modelAndView.getModel();
		for(String key : map.keySet()) {
			System.out.println("key : "+key);
			System.out.println("value : "+map.get(key));
		}
		
		if(modelAndView.getViewName().equals("result")) {
			modelAndView.setViewName("home");
		}
		
		Object result = modelAndView.getModel().get("result");
		if(result == null) {
			modelAndView.addObject("result","postHandle job");
		}
		
		System.out.println("Post Handler ㅂㅂ");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		System.out.println("afterCompletion ㄱㄱ");
		System.out.println("attr result : "+request.getAttribute("result"));
		System.out.println("attr result1 : "+request.getAttribute("result1"));
		System.out.println(response.getContentType());
		System.out.println("afterCompletion ㅂㅂ");
	}

	
}

