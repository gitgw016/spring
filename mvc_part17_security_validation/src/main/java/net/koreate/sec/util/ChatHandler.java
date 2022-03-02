package net.koreate.sec.util;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ChatHandler extends TextWebSocketHandler {

	private List<WebSocketSession> sessionList = new Vector<>();
	private Map<WebSocketSession, String> sessionMap = new Hashtable<>();
	
	// 연결 수락이 완료 된 후
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("연결 됐다 : "+session.getId());
		sessionList.add(session);
		String userName = session.getPrincipal().getName();
		System.out.println("username : "+userName);
		System.out.println("getPrincipal : "+session.getPrincipal());
		System.out.println("getAttribute : "+session.getAttributes());
		sessionMap.put(session, userName);
		System.out.println("연결된 사용자 수 : "+sessionList.size());
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// 메세지가 전달 되었을때마다 호출
		String msg = message.getPayload();
		System.out.println("msg : "+msg);
		String[] msgs = msg.split(",");
		System.out.println(Arrays.toString(msgs));
		String userName = msgs[0];
		String userMessage = msgs[1];
		TextMessage textMessage = new TextMessage(userName+" - "+userMessage);
		// 연결된 모든 client에 메세지 출력
		for(WebSocketSession s : sessionList) {
			s.sendMessage(textMessage);
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// 연결이 끊겼을 때
		System.out.println("연결 끊김 : "+session.getId());
		String userName = sessionMap.get(session);
		sessionList.remove(session);
		// 사용자 연결 끊김 메세지 전달
		for(WebSocketSession s : sessionList) {
			s.sendMessage(new TextMessage(userName+"가 나갔다."));
		}
		sessionMap.remove(session);
		System.out.println("연결된 사용자 수 : "+sessionList.size());
	}
	
	
}
