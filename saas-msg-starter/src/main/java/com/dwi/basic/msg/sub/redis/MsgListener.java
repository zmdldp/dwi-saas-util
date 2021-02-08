package com.dwi.basic.msg.sub.redis;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class MsgListener implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println( "Message received: " + message.toString() );
    }
}