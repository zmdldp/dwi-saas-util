package com.dwi.basic.msg.redis.sub;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

public class RedisMsgListener implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println( "Message received: " + message.toString() );
    }
}