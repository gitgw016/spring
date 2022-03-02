package net.koreate.sec.security;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import net.koreate.sec.security.user.CustomMember;
import net.koreate.sec.service.MemberService;
import net.koreate.sec.vo.ValidationMemberVO;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
	
	@Inject
	MemberService ms;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
		CustomMember member = (CustomMember)auth.getPrincipal();
		System.out.println(member);
		ValidationMemberVO vo = member.getMember();
		System.out.println(vo);
		
		try {
			ms.updateVisitDate(vo.getU_id());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String context = request.getServletContext().getContextPath();
		response.sendRedirect(context);
	}

}
