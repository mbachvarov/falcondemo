package com.falcon.springboot.falcondemo.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.falcon.springboot.falcondemo.controller.MessageApiController;
import com.falcon.springboot.falcondemo.model.Message;

@Component
public class MessageResourceAssembler implements ResourceAssembler<Message, Resource<Message>> {

	/*
	 * @see org.springframework.hateoas.ResourceAssembler#toResource(Message
	 * message) Returns a Resource type based on Message type used for rest api
	 * responses
	 */
	@Override
	public Resource<Message> toResource(Message message) {
		return new Resource<>(message, linkTo(methodOn(MessageApiController.class).getAllMessages()).withSelfRel());
	}

}
