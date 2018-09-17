package com.falcon.springboot.falcondemo.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;

import com.falcon.springboot.falcondemo.assembler.MessageResourceAssembler;
import com.falcon.springboot.falcondemo.model.Message;
import com.falcon.springboot.falcondemo.repository.MessageRepository;

/*
 * Rest api controller for messages
 */
public class MessageApiController {
	@Autowired
	MessageRepository messageRepository;

	@Autowired
	MessageResourceAssembler assembler;

	/*
	 * Returns all messages stored in the database
	 */
	@GetMapping("messages")
	public Resources<Resource<Message>> getAllMessages() {
		final List<Resource<Message>> messages = messageRepository.findAll().stream().map(assembler::toResource)
				.collect(Collectors.toList());
		return new Resources<>(messages, linkTo(methodOn(MessageApiController.class).getAllMessages()).withSelfRel());
	}
}
