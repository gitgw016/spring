package net.koreate.common.interceptor;

import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import net.koreate.board.service.BoardService;
import net.koreate.board.vo.BoardVO;
import net.koreate.user.vo.UserVO;

public class AuthInterceptor extends HandlerInterceptorAdapter {
	
	@Inject
	BoardService bs;

	// 전처리
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 권한 확인
		System.out.println("AuthInterceptor ㄱㄱ");
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		HttpSession session = request.getSession();
		
		Object obj = session.getAttribute("userInfo");
		
		if(obj == null) {
			System.out.println("로그인 정보 없다");
			response.sendRedirect(contextPath+"/user/signIn");
			return false;
		}else {
			if(requestURI.equals(contextPath+"/board/register")) {
				System.out.println("새글 ㄱㄱ");
				return true;
			}
			
			String num = request.getParameter("bno");
			if(num != null & !num.trim().equals("")) {
				int bno = Integer.parseInt(num);
				
				if(requestURI.equals(contextPath+"/board/replyRegister")) {
					System.out.println("답글 작성");
					return true;
				}
				// 요청 들어온 게시글 정보
				BoardVO vo = bs.read(bno);
				// 로그인된 사용자 정보 - session
				UserVO user = (UserVO)obj;
				System.out.println("수정 or 삭제");
				
				if(vo.getUno() == user.getUno()) {
					System.out.println("가능");
					return true;
				}else {
					System.out.println("무단수정 금지");
					response.setContentType("text/html;charset=utf-8");
					PrintWriter out = response.getWriter();
					out.print("<script>");
					out.print("alert('넌 못한다');");
					out.print("history.go(-1);");
					out.print("</script>");
					return false;
				}
				
			}
		}
		return true;
	}

	
}
