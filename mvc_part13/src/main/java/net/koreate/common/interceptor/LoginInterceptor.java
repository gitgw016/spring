package net.koreate.common.interceptor;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import net.koreate.common.session.MySessionEventListener;
import net.koreate.user.dao.BanIPDAO;
import net.koreate.user.service.UserService;
import net.koreate.user.vo.BanIPVO;
import net.koreate.user.vo.LoginDTO;
import net.koreate.user.vo.UserVO;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	@Inject
	UserService us;
	
	@Inject
	BanIPDAO dao; 
	
	@Inject
	MySessionEventListener listener;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		System.out.println("LoginInterceptor preHandle");
		HttpSession session = request.getSession();
		// userInfo - 로그인된 사용자 정보
		if(session.getAttribute("userInfo") != null) {
			session.removeAttribute("userInfo");
		}
		
		String ip = request.getRemoteAddr();
		System.out.println("preHandle IP"+ip);
		BanIPVO banVO = dao.getBanIPVO(ip);
		System.out.println("preHandle"+banVO);
		if(banVO != null && banVO.getCnt() >=5) {
			int limit = 1000 * 60 * 30;
			long saveTime = limit - (System.currentTimeMillis() - banVO.getBandate().getTime());
			System.out.println("지난 시간 : "+saveTime);
			
			if(saveTime > 0) {
				System.out.println("시간 남았다");
				SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
				String time = sdf.format(new Date(saveTime));
				System.out.println("남은 시간 : "+time);
				RequestDispatcher rd = request.getRequestDispatcher("/user/signIn");
				request.setAttribute("message", "로그인 못함 ");
				request.setAttribute("time", saveTime);
				rd.forward(request, response);
				return false;
			}else {
				System.out.println("시간 지남");
				dao.removeBanIp(ip);
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		System.out.println("LoginInterceptor postHandle");
		ModelMap map = modelAndView.getModelMap();
		LoginDTO dto = (LoginDTO)map.get("logDTO");
		System.out.println("Login Interceptor : "+dto);
		
		String ip = request.getRemoteAddr();
		System.out.println("ip : "+ip);
		BanIPVO banVO = dao.getBanIPVO(ip);
		// 아이디와 비밀번호가 일치하는 정보를 가진 사용자 정보 검색
		UserVO vo = us.signIn(dto);
		HttpSession session = request.getSession();
		if(vo != null) {
			boolean result = listener.expireDuplicatedSession(dto.getUid(), session.getId());
			if(result) {
				System.out.println("중복 제거");
			}else {
				System.out.println("첫 로그인");
			}
			session.setAttribute("userInfo", vo);
			if(banVO != null) {
				dao.removeBanIp(ip);
			}
			if(dto.isUseCookie()) {
				Cookie cookie = new Cookie("signInCookie",dto.getUid());
				cookie.setMaxAge(60*60*24*15);
				cookie.setPath("/");
				response.addCookie(cookie);
			}
		}else {
			String message = "";
			int count = 5;
			if(banVO == null) {
				System.out.println("최초 실패");
				dao.signInFail(ip);
				count = count - 1;
			}else {
				System.out.println("중복 실패");
				System.out.println(banVO);
				dao.updateBanIPCnt(ip);
				count = count - (banVO.getCnt()+1);
			}
			if(count > 0) {
				message = "불일치 ㅉㅉ.. 남은 기회 : "+count;
			}else {
				message = "30분 ip아웃 ㅂㅂ";
			}
			
			modelAndView.addObject("message",message);
			modelAndView.setViewName("/user/signIn");
		}
	}

	
}
