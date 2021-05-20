package com.dwi.basic.msg.redis.pub;

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
    
    /**
     * 发布消息
     * 
     * @param topic
     * @param msg
     */
    public void sendMsg(ChannelTopic topic, String msg){
        redisTemplate.convertAndSend(topic.getTopic(), msg);
	}

}
