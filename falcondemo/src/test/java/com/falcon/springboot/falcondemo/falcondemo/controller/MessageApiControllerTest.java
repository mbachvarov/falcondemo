package com.falcon.springboot.falcondemo.falcondemo.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.falcon.springboot.falcondemo.assembler.MessageResourceAssembler;
import com.falcon.springboot.falcondemo.controller.MessageApiController;
import com.falcon.springboot.falcondemo.model.Message;
import com.falcon.springboot.falcondemo.publisher.Publisher;
import com.falcon.springboot.falcondemo.repository.MessageRepository;
import com.falcon.springboot.falcondemo.service.WebSocketService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = MessageApiController.class)
public class MessageApiControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MessageRepository messageRepository;

	@MockBean
	private Publisher redisPublisher;

	@MockBean
	private WebSocketService websocketService;

	@MockBean
	private MessageResourceAssembler assembler;

	private Message mockMessage = new Message("test message");

	/*
	 * Test getAllMessages whether all messages are returned and all of them are in
	 * the appropriate format
	 */
	@Test
	public void getAllMessages() throws Exception {
		Mockito.when(messageRepository.findAll()).thenReturn(Arrays.asList(mockMessage));
		Resource<Message> resource = new Resource<>(mockMessage,
				linkTo(methodOn(MessageApiController.class).getAllMessages()).withSelfRel());
		Mockito.when(assembler.toResource(mockMessage)).thenReturn(resource);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/messages");
		mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.parseMediaType("application/hal+json;charset=UTF-8")))
				.andExpect(jsonPath("$._embedded.messageList", hasSize(1)))
				.andExpect(jsonPath("$._embedded.messageList[0].id", is(mockMessage.getId())))
				.andExpect(jsonPath("$._embedded.messageList[0].content", is(mockMessage.getContent())))
				.andExpect(jsonPath("$._embedded.messageList[0].createdAt", is(mockMessage.getCreatedAt())))
				.andExpect(jsonPath("$._embedded.messageList[0].updatedAt", is(mockMessage.getUpdatedAt())));
	}

	/*
	 * Test createMessageResponse for badRequest when invalid json is supplied.
	 * Verify that nothing is pushed into Redis and websocket
	 */
	@Test
	public void createMessageResponseStatusBadRequest() throws Exception {
		String testMessage = "test message invalid json";
		Mockito.doNothing().when(redisPublisher).publish(testMessage);
		Mockito.doNothing().when(websocketService).broadcast(testMessage);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/messages").content(testMessage);
		mockMvc.perform(requestBuilder).andExpect(status().isBadRequest());
		Mockito.verify(redisPublisher, Mockito.times(0)).publish(testMessage);
		Mockito.verify(websocketService, Mockito.times(0)).broadcast(testMessage);
	}

	/*
	 * Test createMessageResponse for created http response when valid json is
	 * supplied. Verify that the same json is pushed onces into both Redis and
	 * websocket
	 */
	@Test
	public void createMessage() throws Exception {
		String testMessage = "{\"content\":\"test contetn\"}";

		Mockito.doNothing().when(redisPublisher).publish(testMessage);
		Mockito.doNothing().when(websocketService).broadcast(testMessage);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/messages").content(testMessage);
		mockMvc.perform(requestBuilder).andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.parseMediaType("application/hal+json;charset=UTF-8")))
				.andExpect(jsonPath("$.content", is(testMessage)));

		Mockito.verify(redisPublisher, Mockito.times(1)).publish(testMessage);
		Mockito.verify(redisPublisher).publish(testMessage);
		Mockito.verify(websocketService, Mockito.times(1)).broadcast(testMessage);
		Mockito.verify(websocketService).broadcast(testMessage);
	}
}
