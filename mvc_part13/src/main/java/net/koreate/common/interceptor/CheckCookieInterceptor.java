package net.koreate.common.interceptor;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import net.koreate.user.service.UserService;
import net.koreate.user.vo.UserVO;

public class CheckCookieInterceptor extends HandlerInterceptorAdapter{
	
	@Inject
	UserService us;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		System.out.println("CheckCookie ㄱㄱ");
		HttpSession session = request.getSession();
		if(session.getAttribute("userInfo") != null) {
			System.out.println("과자의 userInfo session에 있다.");
			return true;
		}
		
		Cookie cookie = WebUtils.getCookie(request, "signInCookie");
		if(cookie != null) {
			UserVO vo = us.getUserById(cookie.getValue());
			System.out.println("Cookie user : "+vo);
			if(vo != null) {
				session.setAttribute("userInfo", vo);
			}
		}
		/*
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals("signInCookie")) {
				String uid = cookie.getValue();
				UserVO vo = us.getUserById(uid);
				if(vo != null) {
					session.setAttribute("userInfo", vo);
					return true;
				}
			}
		}
		*/
		return true;
	}

	
}
