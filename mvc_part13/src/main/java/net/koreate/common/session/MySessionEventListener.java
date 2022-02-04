package net.koreate.common.session;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.stereotype.Component;

import net.koreate.user.vo.UserVO;

@Component
public class MySessionEventListener implements HttpSessionListener, HttpSessionAttributeListener {
	
	public static Hashtable<String, Object> sessionRepository;
	
	public MySessionEventListener() {
		System.out.println("MySessionEventListener created");
		if(sessionRepository == null) {
			sessionRepository = new Hashtable<>();
		}
	}

	public boolean expireDuplicatedSession(String uid, String sessionId) {
		System.out.println("Active Session Count : "+sessionRepository.size());
		Enumeration<Object> enumeration = sessionRepository.elements();
		while(enumeration.hasMoreElements()) {
			HttpSession session = (HttpSession)enumeration.nextElement();
			UserVO vo = (UserVO)session.getAttribute("userInfo");
			// 사용자 정보가 존재하고 해당 사용자 정보와 파라미터로 넘어온 사용자 아이디가 같을 경우
			if(vo != null && vo.getUid().equals(uid)) {
				// sessionID가 다를 경우 중복 로그인
				if(!session.getId().equals(sessionId)) {
					System.out.printf("login - user %s, sessionId : %s %n", vo.getUid(), sessionId);
					// 중복된 기존 세션 정보는 만료 처리
					//session.invalidate();
					session.setAttribute("invalidate", "중복 로그인 ㅃㅃ 해킹 ㅅㄱ");
					return true;
				}
			}
		}
		return false;
	}
	
	// 세션 객체에 setAttribute()를 호출하여 새로운 속성(객체)가 등록되었을때 호출
	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		System.out.println("attributeAdded 호출");
		System.out.printf("SESSION ID : %s \n",event.getSession().getId());
		System.out.printf("session에 추가된 attribute name : %s, value : %s %n ", event.getName(), event.getValue());
		if(event.getName().equals("userInfo")) {
			HttpSession session = event.getSession();
			System.out.println("userInfo regist : "+session.getId());
			sessionRepository.put(session.getId(), session);
		}
	}

	// 세션 객체에서 removeAttribute가 호출되어 속성값이 삭제되었을때
	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		System.out.printf("remove session %s %n",event.getSession().getId());
		sessionRepository.remove(event.getSession().getId());
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {

	}

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		System.out.printf("Session 생성 session id %s %n", se.getSession().getId());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		System.out.printf("Session 삭제 session id %s %n", se.getSession().getId());
		sessionRepository.remove(se.getSession().getId());
	}

}
