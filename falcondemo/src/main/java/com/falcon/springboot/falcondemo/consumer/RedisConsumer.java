package com.falcon.springboot.falcondemo.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import com.falcon.springboot.falcondemo.repository.MessageRepository;

/*
 * This class is responsible for handling redis messages
 */
public class RedisConsumer implements MessageListener {
	@Autowired
	private MessageRepository messageRepository;

	public RedisConsumer() {
	}

	public RedisConsumer(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}

	@Override
	public void onMessage(Message redisMessage, byte[] pattern) {
		// save messge in db
		messageRepository.save(new com.falcon.springboot.falcondemo.model.Message(redisMessage.toString()));
	}
}