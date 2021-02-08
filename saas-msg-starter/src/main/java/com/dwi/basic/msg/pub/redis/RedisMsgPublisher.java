package com.dwi.basic.msg.pub.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisMsgPublisher {

    private final RedisTemplate<String, Object> redisTemplate;
    
    private final ChannelTopic topic;

    public void sendMsg(String msg){
        redisTemplate.convertAndSend(topic.getTopic(), msg);
	}

}
