package org.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Topic;

@Component
public class ActiveMQTopicProducer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void sendMsg(String message, Topic topic) {
        System.out.println("topic发送的消息为：" + message);
        jmsMessagingTemplate.convertAndSend(topic, message);
    }

}
