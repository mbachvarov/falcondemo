package com.falcon.springboot.falcondemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(tags = {"Message Controller"})
public class MessageController {
	// Monitor live incomming messages
	@GetMapping("/messages/live")
	@ApiOperation("HTML page for live monitoring of the incomming messages.")
	public String getMessagesLive(Model model) {
		return "live_messages";
	}
}
