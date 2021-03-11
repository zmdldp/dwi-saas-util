package com.dwi.basic.msg.redis.pub;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.ChannelTopic;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RedisPubConfig {
	
	private final DiscoveryClient discoveryClient;
	
    /**
     * 订阅发布的主题(服务名)
     * @return
     */
    public List<ChannelTopic> getTopics() {
    	return discoveryClient.getServices().stream().map(s -> new ChannelTopic(s)).collect(Collectors.toList());
    }
}
