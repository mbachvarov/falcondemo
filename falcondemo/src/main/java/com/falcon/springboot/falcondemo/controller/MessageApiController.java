package com.falcon.springboot.falcondemo.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.falcon.springboot.falcondemo.assembler.MessageResourceAssembler;
import com.falcon.springboot.falcondemo.exception.InvalidInputDataException;
import com.falcon.springboot.falcondemo.model.Message;
import com.falcon.springboot.falcondemo.publisher.Publisher;
import com.falcon.springboot.falcondemo.repository.MessageRepository;
import com.falcon.springboot.falcondemo.service.WebSocketService;
import com.falcon.springboot.falcondemo.util.JSONValidate;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

/*
 * Rest api controller for messages
 */
@RestController
@RequestMapping("/api")
@Api(tags = { "REST Message Api Controller" })
public class MessageApiController {
	@Autowired
	private Publisher redisPublisher;

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private MessageResourceAssembler assembler;

	@Autowired
	private WebSocketService websocketService;

	@Autowired
	private ChannelTopic defaultTopic;

	/*
	 * Returns all messages stored in the database
	 */

	@RequestMapping(value = "/messages", produces = { "application/hal+json",
			"application/json" }, method = RequestMethod.GET)
	@ApiOperation("Returns all messages stored in the database.")
	public Resources<Resource<Message>> getAllMessages() {
		final List<Resource<Message>> messages = messageRepository.findAll().stream().map(assembler::toResource)
				.collect(Collectors.toList());

		return new Resources<>(messages, linkTo(methodOn(MessageApiController.class).getAllMessages()).withSelfRel());
	}

	/*
	 * Receives json messge and pushes it in redis
	 */
	@RequestMapping(value = "/messages", produces = { "application/hal+json",
			"application/json" }, method = RequestMethod.POST)
	@ApiOperation("Creates a new message.")
	ResponseEntity<?> createMessage(
			@ApiParam("JSON data for a new message to be created.") @RequestBody String messageJsonString)
			throws URISyntaxException {
		if (!JSONValidate.isValid(messageJsonString)) {
			throw new InvalidInputDataException(messageJsonString, "Json");
		}
		// publishing in redis
		this.redisPublisher.publish(defaultTopic, messageJsonString);
		// pushing through websocket for listening browser clients
		this.websocketService.broadcast(messageJsonString);
		// generating returned resource object
		final Resource<String> resource = new Resource<>(messageJsonString,
				linkTo(methodOn(MessageApiController.class).getAllMessages()).withSelfRel());

		return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
	}
}
