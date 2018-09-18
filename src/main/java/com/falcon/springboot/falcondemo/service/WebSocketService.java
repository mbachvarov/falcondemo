package com.falcon.springboot.falcondemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {
	@Autowired
	private SimpMessagingTemplate simpleMessagingTemplate;

	public void broadcast(String message) {
		this.simpleMessagingTemplate.convertAndSend("*", message);
	}
}
