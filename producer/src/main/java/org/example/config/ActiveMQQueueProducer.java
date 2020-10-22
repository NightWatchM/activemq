package org.example.config;

import org.apache.activemq.ScheduledMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.Queue;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class ActiveMQQueueProducer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Transactional
    public void sendMsg(String message, Queue queue) {
        Map<String, Object> headers = new HashMap<>();
        // 延迟投递的时间，延迟5秒
        headers.put(ScheduledMessage.AMQ_SCHEDULED_DELAY, 5000);
        // 重复投递的时间间隔
        headers.put(ScheduledMessage.AMQ_SCHEDULED_PERIOD, 5000);
        // 重复投递次数
        headers.put(ScheduledMessage.AMQ_SCHEDULED_REPEAT, 5);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("queue发送的消息为：" + message + "  " + sdf.format(new Date()));
        jmsMessagingTemplate.convertAndSend(queue, message);
        //参数中加入延迟配置参数，延迟机制生效
        //jmsMessagingTemplate.convertAndSend(queue, message, headers);
        System.out.println("消息发送成功" + "  " + sdf.format(new Date()));
    }

}
