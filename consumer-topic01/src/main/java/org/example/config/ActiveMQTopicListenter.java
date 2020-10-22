package org.example.config;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ActiveMQTopicListenter {

    @JmsListener(destination = "${spring.activemq.topic-name}", containerFactory = "firstFactory")
    public void readQueue(final TextMessage messsage, Session session) throws JMSException {
//        System.out.println(session.getAcknowledgeMode());
//        System.out.println(session.getTransacted());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        System.out.println("topic-01接受的消息为:" + messsage.getText() + "====" + sdf.format(now));

        //messsage.acknowledge();
        //session.recover();
    }

}
