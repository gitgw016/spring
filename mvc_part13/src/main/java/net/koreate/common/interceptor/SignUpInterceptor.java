package net.koreate.common.interceptor;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import net.koreate.user.service.UserService;
import net.koreate.user.vo.UserVO;

public class SignUpInterceptor extends HandlerInterceptorAdapter {
	
	@Inject
	UserService us;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		System.out.println("signUpPost preHandle");
		String upw = request.getParameter("upw");
		String repw = request.getParameter("repw");
		
		RequestDispatcher rd = request.getRequestDispatcher("/user/signUp");
		String message = "";
		if(!upw.equals(repw)) {
			message = "비밀번호 불일치 ㅉㅉ";
			request.setAttribute("message", message);
			rd.forward(request, response);
			return false;
		}
		
		String uid = request.getParameter("uid");
		UserVO userInfo = us.getUserById(uid);
		if(userInfo != null) {
			message = "못쓴다.";
			request.setAttribute("message", message);
			rd.forward(request, response);
			return false;
		}
		return true;
	}

}
