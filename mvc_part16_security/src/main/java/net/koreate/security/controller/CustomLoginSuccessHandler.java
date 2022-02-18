package net.koreate.security.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		System.out.println("login 성공");
		
		UserDetails user = (UserDetails)authentication.getPrincipal();
		System.out.println(user);
		
		List<String> roleNames = new ArrayList<>();
		
		for(GrantedAuthority auth : authentication.getAuthorities()) {
			String authrities = auth.getAuthority();
			System.out.println(authrities);
			roleNames.add(authrities);
		}
		
		String contextPath = request.getContextPath();
		
		if(roleNames.contains("ROLE_ADMIN")) {
			System.out.println("관리자 권한");
			response.sendRedirect(contextPath+"/test/master");
			return;
		}
		
		if(roleNames.contains("ROLE_MEMBER")) {
			System.out.println("관리자 권한");
			response.sendRedirect(contextPath+"/test/member");
			return;
		}
		
		response.sendRedirect(contextPath);
		System.out.println("권한 없다");
	}

}
