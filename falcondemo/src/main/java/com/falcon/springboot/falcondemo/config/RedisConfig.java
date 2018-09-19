package com.falcon.springboot.falcondemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

import com.falcon.springboot.falcondemo.consumer.RedisConsumer;
import com.falcon.springboot.falcondemo.publisher.Publisher;
import com.falcon.springboot.falcondemo.publisher.RedisPublisher;
import com.falcon.springboot.falcondemo.repository.MessageRepository;

/*
 * Redis config class
 */
@Configuration
@PropertySource("application.properties")
public class RedisConfig {

	@Value("${spring.redis.host}")
	private String redisHostName;

	@Value("${spring.redis.port}")
	private int redisPort;

	/*
	 * Redis connection bean
	 */
	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		final RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(
				redisHostName, redisPort);
		return new JedisConnectionFactory(redisStandaloneConfiguration);
	}

	/*
	 * Redis template bean for pushing messages in channel
	 */
	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(jedisConnectionFactory());
		template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
		return template;
	}

	/*
	 * Redis message listener adapter bean
	 */
	@Bean
	MessageListenerAdapter messageListener(MessageRepository messageRepository) {
		return new MessageListenerAdapter(redisConsumer(messageRepository));
	}

	/*
	 * Redis container bean for default topic message listener
	 */
	@Bean
	RedisMessageListenerContainer redisContainer(MessageRepository messageRepository, ChannelTopic defaultTopic) {
		final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(jedisConnectionFactory());
		container.addMessageListener(messageListener(messageRepository), defaultTopic);
		return container;
	}

	/*
	 * Redis publisher bean
	 */
	@Bean
	Publisher redisPublisher() {
		return new RedisPublisher();
	}

	/*
	 * Redis consumer bean
	 */
	@Bean
	RedisConsumer redisConsumer(MessageRepository messageRepository) {
		return new RedisConsumer(messageRepository);
	}

	/*
	 * Default channel topic bean
	 */
	@Bean
	ChannelTopic defaultTopic() {
		return new ChannelTopic("pubsub:messages-channel");
	}

	/*
	 * To resolve ${} in @Value
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

}
