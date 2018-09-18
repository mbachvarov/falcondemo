package com.falcon.springboot.falcondemo.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.falcon.springboot.falcondemo.assembler.MessageResourceAssembler;
import com.falcon.springboot.falcondemo.exception.InvalidInputDataException;
import com.falcon.springboot.falcondemo.model.Message;
import com.falcon.springboot.falcondemo.publisher.Publisher;
import com.falcon.springboot.falcondemo.repository.MessageRepository;
import com.falcon.springboot.falcondemo.service.WebSocketService;
import com.falcon.springboot.falcondemo.util.JSONValidate;

/*
 * Rest api controller for messages
 */
public class MessageApiController {
	@Autowired
	private Publisher redisPublisher;

	@Autowired
	MessageRepository messageRepository;

	@Autowired
	MessageResourceAssembler assembler;
	
	@Autowired
	WebSocketService websocketService;
	
	/*
	 * Returns all messages stored in the database
	 */
	@GetMapping("messages")
	public Resources<Resource<Message>> getAllMessages() {
		final List<Resource<Message>> messages = messageRepository.findAll().stream().map(assembler::toResource)
				.collect(Collectors.toList());
		
		return new Resources<>(messages, linkTo(methodOn(MessageApiController.class).getAllMessages()).withSelfRel());
	}

	/*
	 * Receives json messge and pushes it in redis
	 */
	@PostMapping("/messages")
	ResponseEntity<?> createMessage(@RequestBody String messageJsonString) throws URISyntaxException {
		if (!JSONValidate.isValid(messageJsonString)) {
			throw new InvalidInputDataException(messageJsonString, "Json");
		}
		
		// publishing in redis
		this.redisPublisher.publish(messageJsonString);
	
		// pushing through websocket for listening browser clients
		this.websocketService.broadcast(messageJsonString);
		
		// generating returned resource object
		final Resource<String> resource = new Resource<>(messageJsonString,
				linkTo(methodOn(MessageApiController.class).getAllMessages()).withSelfRel());
		
		return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
	}
}
