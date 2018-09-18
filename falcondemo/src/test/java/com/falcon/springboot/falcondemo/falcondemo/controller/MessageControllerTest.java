package com.falcon.springboot.falcondemo.falcondemo.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.falcon.springboot.falcondemo.controller.MessageController;

@RunWith(SpringRunner.class)
@WebMvcTest(value = MessageController.class)
public class MessageControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testLiveMessaages() throws Exception{
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/messages/live");
		this.mockMvc.perform(requestBuilder).andExpect(status().isOk()).andExpect(view().name("live_messages"));
	}
	
}
