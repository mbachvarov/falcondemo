package com.falcon.springboot.falcondemo.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

/*
 * This class is responsible for publishing messages into redis
 */
public class RedisPublisher implements Publisher {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public void publish(ChannelTopic topic, String message) {
		redisTemplate.convertAndSend(topic.getTopic(), message);
	}
}
