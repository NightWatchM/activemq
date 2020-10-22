package org.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.Queue;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ActiveMQQueueProducer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Transactional
    public void sendMsg(String message, Queue queue) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("queue发送的消息为：" + message + "  " + sdf.format(new Date()));
        jmsMessagingTemplate.convertAndSend(queue, message);
        System.out.println("消息发送成功" + "  " + sdf.format(new Date()));

    }

}
