package com.falcon.springboot.falcondemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MessageController {
	// Monitor live incomming messages
	@GetMapping("/messages/live")
	public String getMessagesLive(Model model) {
		return "live_messages";
	}
}
