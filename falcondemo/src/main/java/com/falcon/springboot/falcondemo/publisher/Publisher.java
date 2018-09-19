package com.falcon.springboot.falcondemo.publisher;

import org.springframework.data.redis.listener.ChannelTopic;

public interface Publisher {
	void publish(ChannelTopic topic, String message);
}