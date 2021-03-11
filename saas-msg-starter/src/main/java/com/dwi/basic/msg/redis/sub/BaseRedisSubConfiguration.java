package com.dwi.basic.msg.redis.sub;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

public abstract class BaseRedisSubConfiguration {
	
	@Value("${spring.application.name}")
	private String serviceName;
	
	protected final MessageListener messageListener;
	
	 public BaseRedisSubConfiguration(final MessageListener messageListener) {
	        this.messageListener = messageListener;
	    }

    @Bean
    MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(messageListener);
    }

    @Bean
    RedisMessageListenerContainer redisContainer(RedisConnectionFactory factory) {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.addMessageListener(messageListener(), new ChannelTopic(serviceName));
        return container;
    }
}