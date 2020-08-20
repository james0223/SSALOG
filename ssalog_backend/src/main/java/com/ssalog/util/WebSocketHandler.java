package com.ssalog.util;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketHandler extends TextWebSocketHandler{
	
	private Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<WebSocketSession>());
	//private Map<String, WebSocketSession> userSessionsMap = new HashMap<String, WebSocketSession>();
	
	public void notice() throws Exception{
		Iterator<WebSocketSession> iterator = sessions.iterator();
		
		while(iterator.hasNext()) {
			WebSocketSession session = iterator.next();
			try {
				TextMessage message = new TextMessage("hello");
				session.sendMessage(message);
			}catch(IOException E) {
				iterator.remove();
			}
		}
	}
	// 소켓에 메시지를 보냈을때,
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
		//String senderEmail = getEmail(session); // 모든 유저에게 브로드 캐스팅
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception{
		System.out.println("연결!");
		sessions.add(session);
	}
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessions.remove(session);
	}
	
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception{
		sessions.remove(session);
	}
}
